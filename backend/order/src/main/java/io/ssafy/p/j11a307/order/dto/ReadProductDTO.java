package io.ssafy.p.j11a307.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ReadProductDTO {
    Integer id;
    Integer storeId;
    String name;
    Integer price;
    List<String> photos;
    List<String> categories;
    List<String> optionCategories;
    Boolean stockStatus;

}