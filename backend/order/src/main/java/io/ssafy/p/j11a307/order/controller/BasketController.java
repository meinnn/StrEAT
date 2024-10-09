package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.dto.AddProductToBasketDTO;
import io.ssafy.p.j11a307.order.dto.GetBasketOptionDTO;
import io.ssafy.p.j11a307.order.dto.GetBasketListDTO;
import io.ssafy.p.j11a307.order.dto.ModifyOptionFromBasketDTO;
import io.ssafy.p.j11a307.order.global.DataResponse;
import io.ssafy.p.j11a307.order.global.MessageResponse;
import io.ssafy.p.j11a307.order.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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


    @GetMapping(value= "/basket/list")
    @Operation(summary = "장바구니 리스트 조회", description = "장바구니에 있는 데이터들을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리스트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 장바구니 페이지가 비어 있음"),
    })
    @Parameters({
            @Parameter(name = "pgno", description = "페이지 번호(0번부터 시작)"),
            @Parameter(name = "spp", description = "한 페이지에 들어갈 개수")
    })
    public ResponseEntity<DataResponse<GetBasketListDTO>> getBasketList(@RequestHeader("Authorization") String token,
                                                                        @RequestParam("pgno") Integer pgno,
                                                                        @RequestParam("spp") Integer spp) {
        GetBasketListDTO getBasketListDTO = basketService.getBasketList(token, pgno, spp);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("장바구니 리스트 조회에 성공했습니다.", getBasketListDTO));
    }

    @GetMapping(value= "/basket/{id}/info")
    @Operation(summary = "장바구니 내역 상세조회", description = "장바구니 내역 id를 받아, 장바구니에서 옵션 수정 시 불러오는 데이터들")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상세조회 성공"),
            @ApiResponse(responseCode = "404", description = "장바구니 내역 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "조회 권한 없음"),
    })
    public ResponseEntity<DataResponse<GetBasketOptionDTO>> getBasketInfo(@PathVariable Integer id,
                                                                          @RequestHeader("Authorization") String token) {
        GetBasketOptionDTO getBasketOptionDTO = basketService.getBasketInfo(id, token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("장바구니 내역 상세조회에 성공했습니다.", getBasketOptionDTO));
    }


    @PatchMapping(value = "/{id}/basket")
    @Operation(summary = "옵션 수정", description = "장바구니 상품내역 id에 해당하는 상품 옵션을 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "옵션 수정 성공"),
            @ApiResponse(responseCode = "401", description = "수정 권한 없음"),
            @ApiResponse(responseCode = "404", description = "장바구니 내역 존재하지 않음"),
            @ApiResponse(responseCode = "400", description = "상품에 들어있지 않은 옵션"),
    })
    public ResponseEntity<MessageResponse> modifyOptionFromBasket(@PathVariable Integer id,
                                                                   @RequestBody ModifyOptionFromBasketDTO modifyOptionFromBasketDTO,
                                                                   @RequestHeader("Authorization") String token) {
        basketService.modifyOptionFromBasket(id, modifyOptionFromBasketDTO, token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("장바구니 옵션 수정을 완료했습니다."));
    }


    @DeleteMapping(value= "/{id}/basket")
    @Operation(summary = "장바구니에서 삭제", description = "장바구니 상품내역 id에 해당하는 상품을 삭제: 옵션도 함께 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니에서 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "장바구니 내역 존재하지 않음"),
    })
    public ResponseEntity<MessageResponse> deleteProductFromBasket(@PathVariable Integer id,
                                                              @RequestHeader("Authorization") String token) {
        basketService.deleteProductFromBasket(id, token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("장바구니에서 삭제를 완료했습니다."));
    }

    @DeleteMapping(value= "/basket")
    @Operation(summary = "장바구니 비우기", description = "내 장바구니 내역을 모두 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니에서 삭제 성공"),
    })
    public ResponseEntity<MessageResponse> deleteBasket(@RequestHeader("Authorization") String token) {

        basketService.deleteBasket(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("장바구니 비우기를 완료했습니다."));
    }



}