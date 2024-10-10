package io.ssafy.p.j11a307.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class ResponseData {
    @JsonProperty("header")
    private Header header;

    @JsonProperty("body")
    private Body body;

    public List<StoreListInRadiusListDTO> getItems() {
        return this.body.items;
    }

    @Data
    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Getter
    @Data
    public static class Body {
        @JsonProperty("items")
        private List<StoreListInRadiusListDTO> items;
    }
}
