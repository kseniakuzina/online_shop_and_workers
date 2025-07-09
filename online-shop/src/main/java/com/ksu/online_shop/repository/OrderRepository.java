package com.ksu.online_shop.repository;

import com.ksu.online_shop.entities.Order;
import com.ksu.online_shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    public List<Order> findAllByUser(User user);
}

