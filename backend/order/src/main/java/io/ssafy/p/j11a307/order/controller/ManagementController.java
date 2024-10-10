package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.dto.GetDailySalesDTO;
import io.ssafy.p.j11a307.order.dto.GetSalesListByTimeTypeDTO;
import io.ssafy.p.j11a307.order.dto.GetSalesTopPlace;
import io.ssafy.p.j11a307.order.dto.GetSurroundingBusinessDistrictDTO;
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

import java.util.List;
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

    @GetMapping("/report")
    @Operation(summary = "종류별 매출 조회", description = "일별, 주별, 월별, 연별의 매출액을 각각 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최근 매출 조회 성공"),
    })
    public ResponseEntity<DataResponse<GetSalesListByTimeTypeDTO>> getSalesListByTimeType(@RequestHeader("Authorization") String token) {

        GetSalesListByTimeTypeDTO getSalesListByTimeTypeDTO = managementService.getSalesListByTimeType(token);

        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.of("종류별 매출 조회에 성공했습니다.", getSalesListByTimeTypeDTO));
    }


    @GetMapping("/spot/top-three")
    @Operation(summary = "주문횟수 상위 간편위치 조회", description = "가장 순위가 높았던 간편위치 3개를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매출 상위 간편위치 조회 성공")
    })
    public ResponseEntity<DataResponse<List<GetSalesTopPlace>>> getSalesTopPlace(@RequestHeader("Authorization") String token) {
        List<GetSalesTopPlace> placeList = managementService.getSalesTopPlace(token);
        
        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.of("주문횟수 상위 간편위치 조회에 성공했습니다.", placeList));
    }

    @GetMapping("/spot/surround-radius")
    @Operation(summary = "빅데이터를 통한 주변 상권 분석", description = "주변 비슷한 가게 수와 인구 밀도를 분석, 추천 점수를 내준다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주변 상권 분석 결과 조회 성공")
    })
    @Parameters({
            @Parameter(name = "latitude", description = "선택한 장소의 위도"),
            @Parameter(name = "longitude", description = "선택한 장소의 경도"),
    })
    public ResponseEntity<DataResponse<GetSurroundingBusinessDistrictDTO>> getSurroundingBusinessDistrict(@RequestHeader("Authorization") String token,
                                                                                                          @RequestParam Double latitude,
                                                                                                          @RequestParam Double longitude) {
        GetSurroundingBusinessDistrictDTO getSurroundingBusinessDistrictDTO = managementService.getSurroundingBusinessDistrict(token, latitude, longitude);

        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.of("주변 상권 분석 결과 조회 성공했습니다.", getSurroundingBusinessDistrictDTO));
    }



















    }