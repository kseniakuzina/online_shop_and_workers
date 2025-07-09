package com.ksu.online_shop.repository;


import com.ksu.common.entities.CartStatus;
import com.ksu.online_shop.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    public List<Cart> findAllByUserId(Long userId);
    public List<Cart> findAllByUserIdAndStatus(Long userId, CartStatus Status);
    List<Cart> findAllByUserAndProduct(User user, Product product);
    Cart findByUserAndProductAndStatus(User user, Product product, CartStatus status);
}
