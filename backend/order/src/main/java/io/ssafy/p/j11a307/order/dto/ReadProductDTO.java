package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadProductDTO {
    Integer id;
    Integer storeId;
    String name;
    Integer price;
    String src;
    List<String> categories;
    List<String> optionCategories;
}