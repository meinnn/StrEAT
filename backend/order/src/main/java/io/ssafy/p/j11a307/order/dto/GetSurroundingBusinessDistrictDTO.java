package io.ssafy.p.j11a307.order.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GetSurroundingBusinessDistrictDTO(
        /*
        근처 비슷한 가게 수, 가게들 위치정보 및 이름,

        여기서 제일 가까운 상권은 어디 근처예요!
        상권 매출 평균-> 연령별, 성별별, 시간별 제일 잘 팔리는 곳!(여기는 연령대 전체 평균보다 얘들이 몇 퍼센트 더 높아요!)
         */

        String districtName,
        String businessDistrictName,

        //연령대
        GroupDTO ageGroup,

        // 성별별 건수: 어떤 성별에게 인기인지. 인기 없는지. 평균보다 몇 퍼센트 높거나 낮은지.
        GroupDTO genderGroup,

        // 요일별 건수: 어느 요일에 인기인지. 인기 없는지. 평균보다 몇 퍼센트 높거나 낮은지.
        GroupDTO dayGroup,


        // 시간대별 건수: 어느 시간대에 인기인지. 인기 없는지.
        GroupDTO timeGroup,



        Integer sameStoreNum,
        List<SameStoreDTO> sameStoreList
) {
}
