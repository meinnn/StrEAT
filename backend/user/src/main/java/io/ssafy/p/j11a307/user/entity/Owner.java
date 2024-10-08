package io.ssafy.p.j11a307.user.entity;

import io.ssafy.p.j11a307.user.dto.OwnerProfileRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@PrimaryKeyJoinColumn(name = "id") // 부모(User)의 id를 기본 키로 사용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Owner extends User {

    private String gender;
    private Integer age;
    private String birth;
    private String address;
    private String tel;
    private String phone;
    private String email;
    private String snsAccount;

    @Builder
    public Owner(User user) {
        super(user);
    }

    public void updateOwnerProfile(OwnerProfileRequest ownerProfileRequest) {
        this.gender = ownerProfileRequest.gender();
        this.age = ownerProfileRequest.age();
        this.birth = ownerProfileRequest.birth();
        this.address = ownerProfileRequest.address();
        this.tel = ownerProfileRequest.tel();
        this.phone = ownerProfileRequest.phone();
        this.email = ownerProfileRequest.email();
        this.snsAccount = ownerProfileRequest.snsAccount();
    }
}
