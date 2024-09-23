package io.ssafy.p.j11a307.user.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoInfoVo {
    private Long kakaoId;
    private String accessToken;
    private String refreshToken;
    private String imageSrc;
    private String nickname;
}
