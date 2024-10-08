package io.ssafy.p.j11a307.user.entity;

import io.ssafy.p.j11a307.user.dto.OwnerProfile;
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

    public void updateOwnerProfile(OwnerProfile ownerProfile) {
        this.gender = ownerProfile.gender();
        this.age = ownerProfile.age();
        this.birth = ownerProfile.birth();
        this.address = ownerProfile.address();
        this.tel = ownerProfile.tel();
        this.phone = ownerProfile.phone();
        this.email = ownerProfile.email();
        this.snsAccount = ownerProfile.snsAccount();
    }
}
