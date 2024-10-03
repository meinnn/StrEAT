package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.*;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final StoreClient storeClient;
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
                shoppingCartRepository.save(shoppingCart);
                return;
            }
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

    @Transactional
    public void deleteProductFromBasket(Integer id, String token) {
        int customerId = ownerClient.getCustomerId(token, internalRequestKey);

        //1. 장바구니 내역이 존재하지 않는다면?
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElse(null);
        if(shoppingCart == null) throw new BusinessException(ErrorCode.SHOPPING_CART_NOT_FOUND);

        //2. 그 내역을 가진 본인이 아니라면?
        if(customerId != shoppingCart.getCustomerId()) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

        shoppingCartRepository.delete(shoppingCart);
    }

    @Transactional
    public void modifyOptionFromBasket(Integer id, ModifyOptionFromBasketDTO modifyOptionFromBasketDTO, String token) {
        int customerId = ownerClient.getCustomerId(token, internalRequestKey);

        //1. 장바구니 내역이 존재하지 않는다면?
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElse(null);
        if(shoppingCart == null) throw new BusinessException(ErrorCode.SHOPPING_CART_NOT_FOUND);

        //2. 그 내역을 가진 본인이 아니라면?
        if(customerId != shoppingCart.getCustomerId()) throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

        //가격, 개수 수정
        shoppingCart.modifyOption(modifyOptionFromBasketDTO.getPrice(), modifyOptionFromBasketDTO.getQuantity());
        //shoppingCartRepository.save(shoppingCart);

        //옵션 대체
        List<ShoppingCartOption> shoppingCartOptions = shoppingCartOptionRepository.findAllByShoppingCartId(shoppingCart);
        shoppingCartOptionRepository.deleteAll(shoppingCartOptions);

        List<ReadProductOptionDTO> readProductOptionDTOs =  productClient.getProductOptionList(modifyOptionFromBasketDTO.getOptionList(), internalRequestKey);

        for (ReadProductOptionDTO option : readProductOptionDTOs) {
            if(!option.getProductId().equals(shoppingCart.getProductId())) throw new BusinessException(ErrorCode.WRONG_PRODUCT_OPTION);

            ShoppingCartOption shoppingCartOption = ShoppingCartOption.builder()
                    .shoppingCartId(shoppingCart)
                    .productOptionId(option.getId())
                    .build();

            shoppingCartOptionRepository.save(shoppingCartOption);
        }
    }

    @Transactional
    public GetBasketListDTO getBasketList(String token, Integer pgno, Integer spp) {
        int customerId = ownerClient.getCustomerId(token, internalRequestKey);

        //페이징 코드
        Pageable pageable = PageRequest.of(pgno, spp);
        Page<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByCustomerId(customerId, pageable);

        if(shoppingCarts != null && !shoppingCarts.isEmpty()) {
            List<GetBasketOptionInfoDTO> basketList = new ArrayList<>();

            //쇼핑카트에서 productid들 뽑아서 그 상품정보들 모두 가져온다.(아이디, 이름, 사진)
            //그리고 해당 주문내역에 해당하는 옵션 이름목록들 가져옴
            ReadProductDTO productData = new ReadProductDTO();

            for (ShoppingCart shoppingCart : shoppingCarts) {
                productData =  productClient.getProductById(shoppingCart.getProductId()).getData();

                List<Integer> optionIdList = shoppingCartOptionRepository.findAllByShoppingCartId(shoppingCart).stream()
                        .map(ShoppingCartOption::getProductOptionId).toList();

                //가져온 옵션 id들의 이름을 가져와야 함
                List<String> readProductOptions = productClient.getProductOptionList(optionIdList, internalRequestKey)
                        .stream().map(ReadProductOptionDTO::getProductOptionName).toList();

                GetBasketOptionInfoDTO getBasketOptionInfoDTO = GetBasketOptionInfoDTO.builder()
                        .basketId(shoppingCart.getId())
                        .quantity(shoppingCart.getQuantity())
                        .price(shoppingCart.getPrice())
                        .productId(productData.getId())
                        .productName(productData.getName())
                        .productSrc(productData.getSrc())
                        .OptionNameList(readProductOptions)
                        .build();

                basketList.add(getBasketOptionInfoDTO);
            }

            ReadStoreDTO storeData = storeClient.getStoreInfo(productData.getStoreId()).getData();
            ReadStoreBasicInfoDTO photoData = storeClient.getStoreBasicInfo(productData.getStoreId()).getData();

            GetBasketListDTO getBasketListDTO = GetBasketListDTO.builder()
                    .storeId(storeData.getId())
                    .storeName(storeData.getName())
                    .storeSrc(photoData.src())
                    .basketList(basketList)
                    .totalPageCount(shoppingCarts.getTotalPages())
                    .totalDataCount(shoppingCarts.getTotalElements())
                    .build();

            return getBasketListDTO;
        }

        throw new BusinessException(ErrorCode.SHOPPING_CART_NOT_FOUND);
    }
}
