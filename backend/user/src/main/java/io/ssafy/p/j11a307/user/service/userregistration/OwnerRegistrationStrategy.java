package io.ssafy.p.j11a307.user.service.userregistration;

import io.ssafy.p.j11a307.user.entity.Owner;
import io.ssafy.p.j11a307.user.entity.User;
import io.ssafy.p.j11a307.user.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OwnerRegistrationStrategy implements UserRegistrationStrategy {

    private final OwnerRepository ownerRepository;

    @Override
    public User registerUser(User user) {
        Owner owner = Owner.builder().user(user).build();
        return ownerRepository.save(owner);
    }
}
