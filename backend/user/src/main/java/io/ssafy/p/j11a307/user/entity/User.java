package io.ssafy.p.j11a307.user.entity;

import io.ssafy.p.j11a307.user.vo.KakaoInfoVo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long kakaoId;
    private String username;
    private String kakaoAccessToken;
    private String kakaoRefreshToken;
    private String profileImgSrc;
    private String fcmToken;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ColumnDefault("true")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean orderStatusAlert = true;

    @ColumnDefault("true")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean dibsStoreAlert = true;

    public User(KakaoInfoVo kakaoInfoVo) {
        this.kakaoId = kakaoInfoVo.getKakaoId();
        this.username = kakaoInfoVo.getNickname();
        this.profileImgSrc = kakaoInfoVo.getImageSrc();
        this.kakaoAccessToken = kakaoInfoVo.getAccessToken();
        this.kakaoRefreshToken = kakaoInfoVo.getRefreshToken();
    }

    public User(User user) {
        this.kakaoId = user.kakaoId;
        this.username = user.username;
        this.kakaoAccessToken = user.kakaoAccessToken;
        this.kakaoRefreshToken = user.kakaoRefreshToken;
        this.profileImgSrc = user.profileImgSrc;
        this.createdAt = user.createdAt;
        this.orderStatusAlert = user.orderStatusAlert;
        this.dibsStoreAlert = user.dibsStoreAlert;
        this.fcmToken = user.fcmToken;
    }

    public void refreshKakaoTokens(String kakaoAccessToken, String kakaoRefreshToken) {
        this.kakaoAccessToken = kakaoAccessToken;
        if (kakaoRefreshToken != null) { // 경우에 따라 kakao token은 refresh 되지 않을 수 있음
            this.kakaoRefreshToken = kakaoRefreshToken;
        }
    }

    public void logout() {
        this.kakaoAccessToken = null;
        this.kakaoRefreshToken = null;
    }

    public void toggleOrderStatusAlert(boolean alertOn) {
        this.orderStatusAlert = alertOn;
    }

    public void toggleDibsStoreAlert(boolean alertOn) {
        this.dibsStoreAlert = alertOn;
    }
}
