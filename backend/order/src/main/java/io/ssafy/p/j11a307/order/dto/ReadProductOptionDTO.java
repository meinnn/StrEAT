package io.ssafy.p.j11a307.order.dto;

import lombok.Getter;

@Getter
public class ReadProductOptionDTO {
    Integer id;                     // ProductOption ID
    Integer productId;            // Product ID
    Integer productOptionCategoryId;  // 옵션 카테고리 ID
    String productOptionName; //옵션 이름
    Integer productOptionPrice; // 옵션 가격
}
