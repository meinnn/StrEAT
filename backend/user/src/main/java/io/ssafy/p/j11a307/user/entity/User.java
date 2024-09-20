package io.ssafy.p.j11a307.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long kakaoId;
    private String username;
    private String kakaoAccessToken;
    private String kakaoRefreshToken;
    private String profileImgSrc;

    @CreatedDate
    private LocalDateTime createdAt;

    public void refreshKakaoTokens(String kakaoAccessToken, String kakaoRefreshToken) {
        this.kakaoAccessToken = kakaoAccessToken;
        if (kakaoRefreshToken != null) { // 경우에 따라 kakao token은 refresh 되지 않을 수 있음
            this.kakaoRefreshToken = kakaoRefreshToken;
        }
    }
}
