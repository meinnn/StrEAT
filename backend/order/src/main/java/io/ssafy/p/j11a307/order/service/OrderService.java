package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.*;
import io.ssafy.p.j11a307.order.entity.OrderProduct;
import io.ssafy.p.j11a307.order.entity.OrderProductOption;
import io.ssafy.p.j11a307.order.entity.Orders;
import io.ssafy.p.j11a307.order.entity.Review;
import io.ssafy.p.j11a307.order.exception.BusinessException;
import io.ssafy.p.j11a307.order.exception.ErrorCode;
import io.ssafy.p.j11a307.order.global.OrderCode;
import io.ssafy.p.j11a307.order.repository.OrderProductOptionRepository;
import io.ssafy.p.j11a307.order.repository.OrderProductRepository;
import io.ssafy.p.j11a307.order.repository.OrdersRepository;
import io.ssafy.p.j11a307.order.repository.ReviewRepository;
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
    private final OrderProductOptionRepository orderProductOptionRepository;
    private final ReviewRepository reviewRepository;

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
        if(readStoreDTO.userId() != ownerId) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

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
            if(ownerId != readStoreDTO.userId()) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

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
            if(ownerId != readStoreDTO.userId()) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

            //3. 내역이 조리 중인 상태가 아니라면?
            if(orders.get().getStatus().equals(OrderCode.PROCESSING)) {
                orders.get().updateStatus(OrderCode.WAITING_FOR_RECEIPT);

                ordersRepository.save(orders.get());
            } else throw new BusinessException(ErrorCode.WRONG_ORDER_ID);
        } else throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
    }

    @Transactional
    public GetMyOrderDTO getMyOrderList(Integer pgno, Integer spp, String token) {
        Integer customerId = ownerClient.getCustomerId(token, internalRequestKey);
        Pageable pageable = PageRequest.of(pgno, spp);

        //나의 주문내역 리스트를 가져온다.
        Page<Orders> orders = ordersRepository.findByUserId(customerId, pageable);
        List<GetMyOrderListDTO> getMyOrderListDTOs = new ArrayList<>();

        for (Orders order : orders.getContent()) {
            //order 점포 id를 줬을 때 점포 정보 필요.
            ReadStoreBasicInfoDTO readStoreBasicInfoDTO = storeClient.getStoreBasicInfo(order.getStoreId()).getData();

            //해당 order_id가 review 테이블에 존재하는지 살피면 됨.
            Review review = reviewRepository.searchReview(order.getId());
            Boolean isReviewed = review != null;

            //주문에 포함된 상품 목록(위와 중복, 후일 모듈화 필요)
            List<OrderProduct> orderProducts = orderProductRepository.findByOrdersId(order);

            List<Integer> productIds = orderProducts.stream().map(OrderProduct::getProductId).toList();
            List<String> productNames = productClient.getProductNamesByProductIds(productIds).getData();

            //order에 대한 메뉴들을 가져와서 개수와 이름을 DTO에 넣기
            List<GetStoreOrderListProductsDTO> productDTOs = IntStream.range(0, productNames.size())
                    .mapToObj(i -> new GetStoreOrderListProductsDTO(
                            productNames.get(i),
                            orderProducts.get(i).getCount())).toList();

            GetMyOrderListDTO getMyOrderListDTO = GetMyOrderListDTO.builder()
                    .ordersId(order.getId())
                    .ordersCreatedAt(order.getCreatedAt())
                    .orderStatus(order.getStatus())
                    .isReviewed(isReviewed)
                    .storePhoto(readStoreBasicInfoDTO.src())
                    .storeName(readStoreBasicInfoDTO.storeName())
                    .storeId(order.getStoreId())
                    .products(productDTOs)
                    .build();

            getMyOrderListDTOs.add(getMyOrderListDTO);
        }

        GetMyOrderDTO getMyOrderDTO = GetMyOrderDTO.builder()
                .getMyOrderList(getMyOrderListDTOs)
                .totalPageCount(orders.getTotalPages())
                .totalDataCount(orders.getTotalElements())
                .build();

        return getMyOrderDTO;
    }

    @Transactional
    public GetOrderDetailDTO getOrderDetail(Integer ordersId, String token) {
        Integer userId = ownerClient.getUserId(token, internalRequestKey);

        //1. 해당 주문 내역이 존재하지 않는다면?
        Optional<Orders> orders = ordersRepository.findById(ordersId);

        if (orders.isPresent()) {
            ReadStoreDTO readStoreDTO = storeClient.getStoreInfo(orders.get().getStoreId()).getData();
            ReadStoreBasicInfoDTO readStoreBasicInfoDTO = storeClient.getStoreBasicInfo(orders.get().getStoreId()).getData();
            if(readStoreDTO == null) throw new BusinessException(ErrorCode.STORE_NOT_FOUND);

            //2. 해당 주문 상세를 볼 자격이 없다면?
            if(orders.get().getUserId() != userId && readStoreDTO.userId() != userId) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

            //해당 주문 내역에 있는 상품들
            List<GetOrderDetailProductListDTO> productList = new ArrayList<>();
            List<OrderProduct> orderProducts = orderProductRepository.findByOrdersId(orders.get());

            for (OrderProduct orderProduct : orderProducts) {
                ReadProductDTO readProductDTO = productClient.getProductById(orderProduct.getProductId()).getData();

                List<Integer> optionIdList = orderProductOptionRepository.findByOrderProductId(orderProduct.getId());
                List<ReadProductOptionDTO>  readProductOptionDTOS = productClient.getProductOptionList(optionIdList, internalRequestKey);

                List<GetOrderDetailProductOptionListDTO> productOptions = readProductOptionDTOS.stream().map(readProductOptionDTO -> new GetOrderDetailProductOptionListDTO(
                        readProductOptionDTO.getProductOptionName(),
                        readProductOptionDTO.getProductOptionPrice())).toList();

                GetOrderDetailProductListDTO productDTO =GetOrderDetailProductListDTO.builder()
                        .optionList(productOptions)
                        .productName(readProductDTO.getName())
                        .productPrice(readProductDTO.getPrice())
                        .productSrc(readProductDTO.getSrc())
                        .quantity(orderProduct.getCount())
                        .build();

                productList.add(productDTO);
            }


            GetOrderDetailDTO getOrderDetailDTO = GetOrderDetailDTO.builder()
                    .productList(productList)
                    .storeId(readStoreDTO.id())
                    .storeSrc(readStoreBasicInfoDTO.src())
                    .storeName(readStoreBasicInfoDTO.storeName())
                    .menuCount(productList.size())
                    .orderCreatedAt(orders.get().getCreatedAt())
                    .orderNumber(orders.get().getOrderNumber())
                    .totalPrice(orders.get().getTotalPrice())
                    .ordersId(orders.get().getId())
                    .status(orders.get().getStatus())
                    .build();

            return getOrderDetailDTO;
        }
        else throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
    }
}
