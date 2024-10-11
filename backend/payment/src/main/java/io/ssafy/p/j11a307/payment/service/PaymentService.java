package io.ssafy.p.j11a307.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssafy.p.j11a307.payment.dto.*;
import io.ssafy.p.j11a307.payment.entity.CardPayment;
import io.ssafy.p.j11a307.payment.entity.Payment;
import io.ssafy.p.j11a307.payment.entity.TossCancel;
import io.ssafy.p.j11a307.payment.entity.TossEasyPayment;
import io.ssafy.p.j11a307.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    @Value("${toss.payments.confirm-url}")
    private String confirmUrl;

    @Value("${toss.secret-key}")
    private String tossSecretKey;

    @Value("${toss.payments.base-url}")
    private String tossBaseUrl;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    private final String HEADER_AUTH = "Authorization";

    private final OrderService orderService;

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponse tossRequestPayment(TossPaymentBaseRequest tossPaymentBaseRequest) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HEADER_AUTH, "Basic " + tossSecretKey);

        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(tossPaymentBaseRequest);

        HttpEntity<String> httpEntity = new HttpEntity<>(message, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().addFirst(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String response;
        try {
            response = restTemplate.exchange(confirmUrl, HttpMethod.POST, httpEntity, String.class).getBody();
        } catch (Exception e) {
            PayProcessRequest payProcessRequest = PayProcessRequest.builder()
                    .orderNumber(tossPaymentBaseRequest.orderId())
                    .isSuccess(0)
                    .build();
            orderService.completeOrder(payProcessRequest, internalRequestKey);
            return null;
        }
        JsonNode jsonNode = objectMapper.readTree(response);
        Payment payment = new Payment(jsonNode);
        JsonNode cardNode = jsonNode.get("card");
        if (cardNode != null && !cardNode.asText().equals("null")) {
            CardPayment cardPayment = new CardPayment(payment, cardNode);
            payment.addCardPayment(cardPayment);
        }
        JsonNode easyPayNode = jsonNode.get("easyPay");
        if (easyPayNode != null && !easyPayNode.asText().equals("null")) {
            TossEasyPayment tossEasyPayment = new TossEasyPayment(payment, easyPayNode);
            payment.addTossEasyPayPayment(tossEasyPayment);
        }

        PayProcessRequest payProcessRequest = PayProcessRequest.builder()
                .orderNumber(payment.getOrderId())
                .approvedAt(payment.getApprovedAt())
                .method(payment.getMethod())
                .isSuccess(1)
                .build();

        DataResponseFromOtherService<Integer> dataResponseFromOtherService = orderService.completeOrder(payProcessRequest, internalRequestKey);
        Integer orderId = dataResponseFromOtherService.getData();

        paymentRepository.save(payment);
        log.info("service finished");
        return new PaymentResponse(payment.getId(), orderId);
    }

    @Transactional
    public void cancelTossPayment(String orderId, TossPaymentCancelRequest tossPaymentCancelRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HEADER_AUTH, "Basic " + tossSecretKey);
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow();
        RestTemplate restTemplate = new RestTemplate();
        String url = tossBaseUrl + "/" + payment.getPaymentKey() + "/cancel";

        HttpEntity<TossPaymentCancelRequest> requestHttpEntity = new HttpEntity<>(tossPaymentCancelRequest, httpHeaders);

        ResponseEntity<TossPaymentCancelResponse> response =
                restTemplate.exchange(url, HttpMethod.POST, requestHttpEntity, TossPaymentCancelResponse.class);

        TossPaymentCancelResponse tossPaymentCancelResponse = response.getBody();
        List<CancelDetail> cancels = tossPaymentCancelResponse.cancels();
        List<TossCancel> tossCancels = cancels.stream().map(TossCancel::new).toList();
        payment.addTossCancels(tossCancels);
    }
}
