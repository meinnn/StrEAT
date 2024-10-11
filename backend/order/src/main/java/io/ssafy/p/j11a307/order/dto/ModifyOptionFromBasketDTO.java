package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@Schema(description = "옵션 수정 정보를 담기 위한 DTO")
public class ModifyOptionFromBasketDTO {
    @Schema(description = "옵션 수정 리스트", example= "[1]")
    List<Integer> optionList;

    @Schema(description = "수량", example = "5")
    Integer quantity;

//    @Schema(description = "가격", example= "100000")
//    Integer price;
}
