package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.dto.CreateReviewDTO;
import io.ssafy.p.j11a307.order.dto.GetMyReviewsDTO;
import io.ssafy.p.j11a307.order.dto.GetStoreReviewsDTO;
import io.ssafy.p.j11a307.order.global.DataResponse;
import io.ssafy.p.j11a307.order.global.MessageResponse;
import io.ssafy.p.j11a307.order.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping(value= "/{id}/review", consumes = {"multipart/form-data"})
    @Operation(summary = "리뷰 등록", description = "주문 id를 보내고 리뷰 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "리뷰 등록 성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "주문 존재하지 않음"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 리뷰")
    })
    @Parameters({
            @Parameter(name = "score", description = "점수"),
            @Parameter(name = "content", description = "내용")
    })
    public ResponseEntity<MessageResponse> createReview(@PathVariable Integer id,
                                                        @RequestHeader("Authorization") String token,
                                                        @RequestPart(value = "image", required = false) MultipartFile[] images,
                                                        @RequestParam(value="score") Integer score,
                                                        @RequestParam(value="content") String content) {
        CreateReviewDTO createReviewDTO = CreateReviewDTO.builder().score(score).content(content).build();
        reviewService.createReview(id, createReviewDTO, images, token);

        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(MessageResponse.of("리뷰 등록을 완료했습니다."));
    }

    @DeleteMapping("/{id}/review")
    @Operation(summary = "리뷰 삭제", description = "해당 리뷰 id를 보내고 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "리뷰 존재하지 않음")
    })
    public ResponseEntity<MessageResponse> deleteReview(@PathVariable Integer id,
                                                        @RequestHeader("Authorization") String token) {
        reviewService.deleteReview(id, token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("리뷰 삭제를 완료했습니다."));
    }


    @GetMapping("/mine/reviews")
    @Operation(summary = "내가 쓴 리뷰 조회", description = "보낸 토큰에 해당하는 유저의 리뷰만 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 리뷰 조회 성공"),
    })
    public ResponseEntity<DataResponse<List<GetMyReviewsDTO>>> getMyReviews(@RequestHeader("Authorization") String token) {
        log.info("controller called");
        List<GetMyReviewsDTO> getMyReviewsDTOs = reviewService.getMyReviews(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("내 리뷰 조회에 성공했습니다.", getMyReviewsDTOs));
    }

    @GetMapping("/stores/{id}/reviews")
    @Operation(summary = "점포별 리뷰 조회", description = "보낸 점포 id에 해당하는 리뷰만 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "점포별 리뷰 조회 성공"),
    })
    public ResponseEntity<DataResponse<List<GetStoreReviewsDTO>>> getStoreReviews(@PathVariable Integer id) {
        List<GetStoreReviewsDTO> getStoreReviewsDTO = reviewService.getStoreReviews(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포별 리뷰 조회에 성공했습니다.", getStoreReviewsDTO));
    }
}
