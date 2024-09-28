package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.dto.CreateReviewDTO;
import io.ssafy.p.j11a307.order.exception.ErrorCode;
import io.ssafy.p.j11a307.order.global.MessageResponse;
import io.ssafy.p.j11a307.order.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201"),
//            @ApiResponse(responseCode = "401"),
//            @ApiResponse(responseCode = "404")})
    @PostMapping(value= "/{id}/review", consumes = {"multipart/form-data"})
    @Operation(summary = "리뷰 등록")
    public ResponseEntity<MessageResponse> createReview(@PathVariable Integer id,
                                                        @ModelAttribute CreateReviewDTO createReviewDTO,
                                                        @RequestHeader("Authorization") String token) {
       reviewService.createReview(id, createReviewDTO, token);

        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(MessageResponse.of("리뷰 등록을 완료했습니다."));
    }

    //리뷰 수정






    //리뷰 삭제

    //점포별 리뷰 조회

    //내가 쓴 리뷰 조회

    //2. 이미 해당 주문 내역에 대한 리뷰를 달았다면?(내 주문내역 조회에서 처리해줘야 함!!)
}
