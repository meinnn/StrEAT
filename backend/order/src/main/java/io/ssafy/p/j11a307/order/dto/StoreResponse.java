package io.ssafy.p.j11a307.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreResponse {
        Integer id;
        String name;
        String address;
        String latitude;
        String longitude;
        String type;
        String bankAccount;
        String bankName;
        String ownerWord;
        String status;
        Integer userId;

        //사진
        List<String> images;

}
