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
@RequestMapping("/api/business-days")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class BusinessDayController {

    private final BusinessDayService businessDayService;

    /**
     * BusinessDay 생성
     */
    @PostMapping
    @Operation(summary = "영업일 생성")
    public ResponseEntity<MessageResponse> createBusinessDay(@RequestBody CreateBusinessDayDTO createDTO) {
        businessDayService.createBusinessDay(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("영업일 생성 성공"));
    }

    /**
     * BusinessDay 조회 (단일)
     */
    @GetMapping("/{id}")
    @Operation(summary = "영업일 단일 조회")
    public ResponseEntity<DataResponse<ReadBusinessDayDTO>> getBusinessDayById(@PathVariable Integer id) {
        ReadBusinessDayDTO businessDay = businessDayService.getBusinessDayById(id);
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
    @PutMapping("/{id}")
    @Operation(summary = "영업일 수정")
    public ResponseEntity<MessageResponse> updateBusinessDay(@PathVariable Integer id, @RequestBody UpdateBusinessDayDTO updateDTO) {
        businessDayService.updateBusinessDay(id, updateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("영업일 수정 성공"));
    }

    /**
     * BusinessDay 삭제
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "영업일 삭제")
    public ResponseEntity<MessageResponse> deleteBusinessDay(@PathVariable Integer id) {
        businessDayService.deleteBusinessDay(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("영업일 삭제 성공"));
    }
}
