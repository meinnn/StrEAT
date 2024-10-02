package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.AddProductToBasketDTO;
import io.ssafy.p.j11a307.order.dto.ReadProductDTO;
import io.ssafy.p.j11a307.order.dto.ReadProductOptionDTO;
import io.ssafy.p.j11a307.order.entity.ShoppingCart;
import io.ssafy.p.j11a307.order.entity.ShoppingCartOption;
import io.ssafy.p.j11a307.order.exception.BusinessException;
import io.ssafy.p.j11a307.order.exception.ErrorCode;
import io.ssafy.p.j11a307.order.global.DataResponse;
import io.ssafy.p.j11a307.order.repository.ShoppingCartOptionRepository;
import io.ssafy.p.j11a307.order.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final OwnerClient ownerClient;
    private final ProductClient productClient;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartOptionRepository shoppingCartOptionRepository;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    @Transactional
    public void addProductToBasket(Integer id, AddProductToBasketDTO addProductToBasketDTO, String token) {
        int customerId = ownerClient.getCustomerId(token, internalRequestKey);

        //1. 존재하지 않는 상품이라면?
        DataResponse<ReadProductDTO> readProducts = productClient.getProductById(id);
        if (readProducts.getData() == null) throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);

        //상품 가격
        Integer priceP = readProducts.getData().getPrice();
        Integer quan = addProductToBasketDTO.getQuantity();

        //상품 옵션 리스트의 가격 합
        List<Integer> optionList = addProductToBasketDTO.getProductOptionIds();
        Integer priceO = productClient.sumProductOption(optionList, internalRequestKey);

        //옵션에서 그 항목들을 검색하면서 검색된 옵션항목들이 옵션리스트와 동일하면, 개수와 가격을 업데이트
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByCustomerIdAndProductId(customerId, id);

        for (ShoppingCart shoppingCart : shoppingCarts) {
            //각 상품 항목들을 옵션에서 검색하고 옵션 리스트를 반환
            List<Integer> shoppingCartOptions = new LinkedList<>(shoppingCartOptionRepository.findAllByShoppingCartId(shoppingCart)
                    .stream().map(ShoppingCartOption::getProductOptionId).toList());

            Collections.sort(shoppingCartOptions);
            Collections.sort(optionList);

            if(shoppingCartOptions.equals(optionList)) {
                //개수와 가격 업데이트
                shoppingCart.updateQuantityAndPrice(quan);
                return;
            } else break;
        }

        //장바구니에 다른 요소로 추가
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .customerId(customerId)
                .price(quan*(priceO+priceP))
                .quantity(quan)
                .productId(id)
                .build();
        shoppingCartRepository.save(shoppingCart);

        List<ReadProductOptionDTO> readProductOptionDTOs =  productClient.getProductOptionList(optionList, internalRequestKey);

        for (ReadProductOptionDTO option : readProductOptionDTOs) {
            if(!option.getProductId().equals(id)) throw new BusinessException(ErrorCode.WRONG_PRODUCT_OPTION);

            ShoppingCartOption shoppingCartOption = ShoppingCartOption.builder()
                    .shoppingCartId(shoppingCart)
                    .productOptionId(option.getId())
                    .build();

            shoppingCartOptionRepository.save(shoppingCartOption);
        }
    }
}
