package io.ssafy.p.j11a307.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssafy.p.j11a307.payment.dto.TossPaymentBaseRequest;
import io.ssafy.p.j11a307.payment.entity.CardPayment;
import io.ssafy.p.j11a307.payment.entity.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    @Value("${toss.confirm-url}")
    private String confirmUrl;

    @Value("${toss.secret-key}")
    private String tossSecretKey;

    private final String HEADER_AUTH = "Authorization";

    public void tossRequestPayment(TossPaymentBaseRequest tossPaymentBaseRequest) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HEADER_AUTH, "Basic " + tossSecretKey);

        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(tossPaymentBaseRequest);

        HttpEntity<String> httpEntity = new HttpEntity<>(message, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(confirmUrl, httpEntity, String.class);
        JsonNode jsonNode = objectMapper.readTree(response);
        Payment payment = new Payment(jsonNode);
        JsonNode cardNode = jsonNode.get("card");
        if (cardNode != null && !cardNode.asText().equals("null")) {
            CardPayment cardPayment = new CardPayment(payment, cardNode);
            payment.addCardPayment(cardPayment);
        }
    }
}
