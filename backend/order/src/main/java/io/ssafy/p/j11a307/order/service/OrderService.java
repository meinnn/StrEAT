package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.GetStoreOrderDTO;
import io.ssafy.p.j11a307.order.dto.GetStoreOrderListDTO;
import io.ssafy.p.j11a307.order.dto.GetStoreOrderListProductsDTO;
import io.ssafy.p.j11a307.order.dto.ReadStoreDTO;
import io.ssafy.p.j11a307.order.entity.OrderProduct;
import io.ssafy.p.j11a307.order.entity.OrderProductOption;
import io.ssafy.p.j11a307.order.entity.Orders;
import io.ssafy.p.j11a307.order.exception.BusinessException;
import io.ssafy.p.j11a307.order.exception.ErrorCode;
import io.ssafy.p.j11a307.order.global.OrderCode;
import io.ssafy.p.j11a307.order.repository.OrderProductRepository;
import io.ssafy.p.j11a307.order.repository.OrdersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final StoreClient storeClient;
    private final OwnerClient ownerClient;
    private final ProductClient productClient;
    private final OrdersRepository ordersRepository;
    private final OrderProductRepository orderProductRepository;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    @Transactional
    public GetStoreOrderDTO getStoreOrderList(Integer storeId, Integer pgno, Integer spp, String status, String token) {
        Integer ownerId = ownerClient.getOwnerId(token, internalRequestKey);
        Pageable pageable = PageRequest.of(pgno, spp);

        //1. 해당 점포가 존재하지 않는다면?
        ReadStoreDTO readStoreDTO = storeClient.getStoreInfo(storeId).getData();
        if(readStoreDTO == null) throw new BusinessException(ErrorCode.STORE_NOT_FOUND);

        //2. 만약 해당 유저가 store의 사장이 아니라면?
        if(readStoreDTO.getUserId() != ownerId) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

        List<String> statusList = new ArrayList<>();

        switch (status) {
            case "PROCESSING":
                statusList.addAll(Arrays.asList("WAITING_FOR_PROCESSING", "PROCESSING"));
                break;
            case "RECEIVING":
                statusList.addAll(Arrays.asList("WAITING_FOR_RECEIPT", "RECEIVED"));
                break;
            default:
                throw new BusinessException(ErrorCode.WRONG_STATUS);
        }

        //해당 점포의 주문내역을 가져옴!!
        Page<Orders> orders = ordersRepository.findByStoreId(storeId,statusList.get(0), statusList.get(1), pageable);

        List<GetStoreOrderListDTO> getStoreOrderListDTOs = new ArrayList<>();

        for (Orders order : orders.getContent()) {
            //해당 order에 대한 주문 메뉴들 필요
            //일단 product id들을 가져온다.
            List<OrderProduct> orderProducts = orderProductRepository.findByOrdersId(order);

            List<Integer> productIds = orderProducts.stream().map(OrderProduct::getProductId).toList();
            List<String> productNames = productClient.getProductNamesByProductIds(productIds).getData();

            //order에 대한 메뉴들을 가져와서 개수와 이름을 DTO에 넣기
            List<GetStoreOrderListProductsDTO> productDTOs = IntStream.range(0, productNames.size())
                    .mapToObj(i -> new GetStoreOrderListProductsDTO(
                            productNames.get(i),
                            orderProducts.get(i).getCount())).toList();


            GetStoreOrderListDTO getStoreOrderListDTO = GetStoreOrderListDTO.builder()
                    .menuCount(productDTOs.size())
                    .orderCreatedAt(order.getCreatedAt())
                    .products(productDTOs)
                    .status(order.getStatus())
                    .totalPrice(order.getTotalPrice())
                    .orderNumber(order.getOrderNumber())
                    .id(order.getId())
                    .build();

            getStoreOrderListDTOs.add(getStoreOrderListDTO);
        }

        GetStoreOrderDTO getStoreOrderDTO = GetStoreOrderDTO.builder()
                .getStoreOrderLists(getStoreOrderListDTOs)
                .totalPageCount(orders.getTotalPages())
                .totalDataCount(orders.getTotalElements())
                .build();

        return getStoreOrderDTO;
    }

    @Transactional
    public void handleOrders(Integer ordersId, Integer flag, String token) {
        Integer ownerId = ownerClient.getOwnerId(token, internalRequestKey);

        //1. 주문 내역이 존재하지 않는다면?
        Optional<Orders> orders = ordersRepository.findById(ordersId);

        if(orders.isPresent()) {
            //2. 처리할 권한이 없다면?
            ReadStoreDTO readStoreDTO = storeClient.getStoreInfo(orders.get().getStoreId()).getData();
            if(ownerId != readStoreDTO.getUserId()) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

            //3. 내역이 대기 중인 상태가 아니라면?
            if(orders.get().getStatus().equals(OrderCode.WAITING_FOR_PROCESSING)) {
                if (flag == 0) orders.get().updateStatus(OrderCode.REJECTED);
                else if(flag == 1) orders.get().updateStatus(OrderCode.PROCESSING);
                else throw new BusinessException(ErrorCode.WRONG_FLAG);

                ordersRepository.save(orders.get());

            } else throw new BusinessException(ErrorCode.WRONG_ORDER_ID);
        } else throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
    }

    @Transactional
    public void handleCompleteOrders(Integer ordersId, String token) {
        Integer ownerId = ownerClient.getOwnerId(token, internalRequestKey);

        //1. 주문 내역이 존재하지 않는다면?
        Optional<Orders> orders = ordersRepository.findById(ordersId);

        if(orders.isPresent()) {
            //2. 처리할 권한이 없다면?
            ReadStoreDTO readStoreDTO = storeClient.getStoreInfo(orders.get().getStoreId()).getData();
            if(ownerId != readStoreDTO.getUserId()) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

            //3. 내역이 조리 중인 상태가 아니라면?
            if(orders.get().getStatus().equals(OrderCode.PROCESSING)) {
                orders.get().updateStatus(OrderCode.WAITING_FOR_RECEIPT);

                ordersRepository.save(orders.get());
            } else throw new BusinessException(ErrorCode.WRONG_ORDER_ID);
        } else throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
    }


}
