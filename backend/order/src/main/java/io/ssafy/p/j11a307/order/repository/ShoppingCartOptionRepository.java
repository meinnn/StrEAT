package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.ShoppingCart;
import io.ssafy.p.j11a307.order.entity.ShoppingCartOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartOptionRepository extends JpaRepository<ShoppingCartOption, Integer> {
    List<ShoppingCartOption> findAllByShoppingCartId(ShoppingCart shoppingCartId);

    ShoppingCartOption findByProductOptionIdAndShoppingCartId(Integer productOptionId, ShoppingCart shoppingCart);

}
