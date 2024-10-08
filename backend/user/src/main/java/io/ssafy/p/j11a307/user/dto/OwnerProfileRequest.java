package io.ssafy.p.j11a307.user.dto;

public record OwnerProfileRequest(
        String gender,
        Integer age,
        String birth,
        String address,
        String tel,
        String phone,
        String email,
        String snsAccount
) {}
