package io.ssafy.p.j11a307.announcement.dto;

public record OwnerProfile (
        String ownerName,
        String gender,
        Integer age,
        String birth,
        String address,
        String tel,
        String phone,
        String email,
        String snsAccount
)
{ }
