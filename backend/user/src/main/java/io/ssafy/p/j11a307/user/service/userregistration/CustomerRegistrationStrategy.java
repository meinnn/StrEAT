package io.ssafy.p.j11a307.user.service.userregistration;

import io.ssafy.p.j11a307.user.entity.Customer;
import io.ssafy.p.j11a307.user.entity.User;
import io.ssafy.p.j11a307.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerRegistrationStrategy implements UserRegistrationStrategy {

    private final CustomerRepository customerRepository;

    @Override
    public User registerUser(User user) {
        Customer customer = new Customer(user);
        return customerRepository.save(customer);
    }
}
