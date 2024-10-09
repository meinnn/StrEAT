package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.dto.GetDailySalesDTO;
import io.ssafy.p.j11a307.order.dto.GetSalesListByTimeTypeDTO;
import io.ssafy.p.j11a307.order.global.DataResponse;
import io.ssafy.p.j11a307.order.service.ManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-manage")
@CrossOrigin
public class ManagementController {
    private final ManagementService managementService;

    @GetMapping("/day-list")
    @Operation(summary = "일별 매출 조회", description = "선택한 달의 일별 매출을 리스트로 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일별 매출 조회 성공"),
    })
    @Parameters({
            @Parameter(name = "month", description = "월(month)"),
    })
    public ResponseEntity<DataResponse<Map<Integer, GetDailySalesDTO>>> getDailySalesList(@RequestParam("month") Integer month,
                                                                                          @RequestHeader("Authorization") String token) {
        Map<Integer, GetDailySalesDTO> dailySalesDTOMap = managementService.getDailySalesList(month, token);

        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.of("일별 매출 조회에 성공했습니다.", dailySalesDTOMap));
    }

    //일별, 주별, 월별, 연별 매출 정보 조회(어떤 상품이 몇 퍼센트로 많이 나갔는지도)
    //이거 개수 퍼센트. 총 개수 중에 얘 개수 따져서 몇 퍼센트인지 확인!!
    //1월, 2월, 3월.. ? -> 월별
    //2020년, 2021년, 2022년 .. -> 연별
    @GetMapping("/{storeId}/report")
    @Operation(summary = "종류별 매출 조회", description = "일별, 주별, 월별, 연별의 매출액을 각각 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최근 매출 조회 성공"),
    })
    public ResponseEntity<DataResponse<GetSalesListByTimeTypeDTO>> getSalesListByTimeType(@PathVariable Integer storeId,
                                                                                          @RequestHeader("Authorization") String token) {

        GetSalesListByTimeTypeDTO getSalesListByTimeTypeDTO = managementService.getSalesListByTimeType(storeId, token);

        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.of("종류별 매출 조회에 성공했습니다.", getSalesListByTimeTypeDTO));
    }














}