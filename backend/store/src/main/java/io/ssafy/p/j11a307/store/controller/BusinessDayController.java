package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.CreateBusinessDayDTO;
import io.ssafy.p.j11a307.store.dto.ReadBusinessDayDTO;
import io.ssafy.p.j11a307.store.dto.UpdateBusinessDayDTO;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.global.MessageResponse;
import io.ssafy.p.j11a307.store.service.BusinessDayService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business-days")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class BusinessDayController {

    private final BusinessDayService businessDayService;

    /**
     * BusinessDay 생성
     */
    @PostMapping("/business-day")
    @Operation(summary = "token으로 영업일 생성")
    public ResponseEntity<MessageResponse> createBusinessDayByToken(@RequestHeader("Authorization") String token, @RequestBody CreateBusinessDayDTO createDTO) {
        businessDayService.createBusinessDayByToken(token, createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("영업일 생성 성공"));
    }

    /**
     * BusinessDay 조회 (storeId로)
     */
    @GetMapping("/{storeId}")
    @Operation(summary = "가게 ID로 영업일 조회")
    public ResponseEntity<DataResponse<ReadBusinessDayDTO>> getBusinessDayByStoreId(@PathVariable Integer storeId) {
        ReadBusinessDayDTO businessDay = businessDayService.getBusinessDayByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("영업일 조회 성공", businessDay));
    }

    /**
     * BusinessDay 전체 조회
     */
    @GetMapping
    @Operation(summary = "영업일 전체 조회")
    public ResponseEntity<DataResponse<List<ReadBusinessDayDTO>>> getAllBusinessDays() {
        List<ReadBusinessDayDTO> businessDays = businessDayService.getAllBusinessDays();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("영업일 전체 조회 성공", businessDays));
    }

    /**
     * BusinessDay 수정
     */
    @PutMapping("/update")
    @Operation(summary = "token으로 영업일 수정")
    public ResponseEntity<MessageResponse> updateBusinessDayByToken(@RequestHeader("Authorization") String token, @RequestBody UpdateBusinessDayDTO updateDTO) {
        businessDayService.updateBusinessDayByToken(token, updateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("영업일 수정 성공"));
    }

    /**
     * BusinessDay 삭제
     */
    @DeleteMapping("/business-day")
    @Operation(summary = "token으로 영업일 삭제")
    public ResponseEntity<MessageResponse> deleteBusinessDayByToken(@RequestHeader("Authorization") String token) {
        businessDayService.deleteBusinessDayByToken(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("영업일 삭제 성공"));
    }
}
