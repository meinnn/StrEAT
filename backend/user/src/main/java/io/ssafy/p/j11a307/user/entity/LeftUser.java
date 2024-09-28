package io.ssafy.p.j11a307.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LeftUser {

    @Id
    private Integer id;
    private String name;
    private Long kakaoId;
    @CreatedDate
    private LocalDateTime createdAt;

    public LeftUser(User user) {
        this.id = user.getId();
        this.name = user.getUsername();
        this.kakaoId = user.getKakaoId();
    }
}
