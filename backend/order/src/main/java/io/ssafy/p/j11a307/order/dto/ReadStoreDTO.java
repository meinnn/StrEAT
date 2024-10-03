package io.ssafy.p.j11a307.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReadStoreDTO {
        Integer id;
        String name;
        String address;
        Double latitude;
        Double longitude;
        String type;
        String bankAccount;
        String bankName;
        String ownerWord;
        String status;
        Integer userId;
}
