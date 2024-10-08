package io.ssafy.p.j11a307.user.dto;

import io.ssafy.p.j11a307.user.entity.Owner;

public record OwnerProfile(
        String ownerName,
        String gender,
        Integer age,
        String birth,
        String address,
        String tel,
        String phone,
        String email,
        String snsAccount
) {
    public OwnerProfile(Owner owner) {
        this(
                owner.getUsername(),
                owner.getGender(),
                owner.getAge(),
                owner.getBirth(),
                owner.getAddress(),
                owner.getTel(),
                owner.getPhone(),
                owner.getEmail(),
                owner.getSnsAccount()
        );
    }
}
