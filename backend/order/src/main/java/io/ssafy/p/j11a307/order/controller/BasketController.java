package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.dto.AddProductToBasketDTO;
import io.ssafy.p.j11a307.order.global.MessageResponse;
import io.ssafy.p.j11a307.order.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class BasketController {
    private final BasketService basketService;

    @PostMapping(value= "/{id}/basket")
    @Operation(summary = "장바구니 추가", description = "상품 id에 해당하는 상품을 장바구니에 추가: 완전 중복 옵션이면 가격과 수량 업데이트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "장바구니에 추가 성공"),
            @ApiResponse(responseCode = "404", description = "상품 존재하지 않음"),
            @ApiResponse(responseCode = "400", description = "상품에 들어있지 않은 옵션"),
    })
    public ResponseEntity<MessageResponse> addProductToBasket(@PathVariable Integer id,
                                                              @RequestBody AddProductToBasketDTO addProductToBasketDTO,
                                                              @RequestHeader("Authorization") String token) {
        basketService.addProductToBasket(id, addProductToBasketDTO, token);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("장바구니에 상품 추가를 완료했습니다."));
    }

    //장바구니 조회

    //장바구니 옵션 수정

    //장바구니에서 삭제
    @DeleteMapping(value= "/{id}/basket")
    @Operation(summary = "장바구니에서 삭제", description = "장바구니 상품내역 id에 해당하는 상품을 삭제: 옵션도 함께 삭제")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "장바구니에 추가 성공"),
//            @ApiResponse(responseCode = "404", description = "상품 존재하지 않음"),
//            @ApiResponse(responseCode = "400", description = "상품에 들어있지 않은 옵션"),
//    })
    public ResponseEntity<MessageResponse> deleteProductFromBasket(@PathVariable Integer id,
                                                              @RequestHeader("Authorization") String token) {
        basketService.deleteProductFromBasket(id, token);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("장바구니에서 삭제를 완료했습니다."));
    }






    }
