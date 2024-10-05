package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.dto.CreateReviewDTO;
import io.ssafy.p.j11a307.order.dto.GetMyReviewsDTO;
import io.ssafy.p.j11a307.order.dto.GetStoreReviewsDTO;
import io.ssafy.p.j11a307.order.global.DataResponse;
import io.ssafy.p.j11a307.order.global.MessageResponse;
import io.ssafy.p.j11a307.order.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{id}/review")
    @Operation(summary = "리뷰 삭제")
    public ResponseEntity<MessageResponse> deleteReview(@PathVariable Integer id,
                                                        @RequestHeader("Authorization") String token) {
        reviewService.deleteReview(id, token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("리뷰 삭제를 완료했습니다."));
    }


    @GetMapping("/mine/reviews")
    @Operation(summary = "내가 쓴 리뷰 조회")
    public ResponseEntity<DataResponse<List<GetMyReviewsDTO>>> getMyReviews(@RequestHeader("Authorization") String token) {
        List<GetMyReviewsDTO> getMyReviewsDTOs = reviewService.getMyReviews(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("내 리뷰 조회에 성공했습니다.", getMyReviewsDTOs));
    }

    @GetMapping("/stores/{id}/reviews")
    @Operation(summary = "점포별 리뷰 조회")
    public ResponseEntity<DataResponse<List<GetStoreReviewsDTO>>> getStoreReviews(@PathVariable Integer id) {
        List<GetStoreReviewsDTO> getStoreReviewsDTO = reviewService.getStoreReviews(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포별 리뷰 조회에 성공했습니다.", getStoreReviewsDTO));
    }
}
