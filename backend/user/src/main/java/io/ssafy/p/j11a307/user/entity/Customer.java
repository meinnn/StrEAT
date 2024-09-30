package io.ssafy.p.j11a307.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@PrimaryKeyJoinColumn(name = "id") // 부모(User)의 id를 기본 키로 사용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends User {

    @Builder
    public Customer(User user) {
        super(user);
    }
}
