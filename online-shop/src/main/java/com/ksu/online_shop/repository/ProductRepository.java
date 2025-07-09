package com.ksu.online_shop.repository;

import com.ksu.online_shop.entities.Product;
import com.ksu.online_shop.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Role findByName(String name);
    List<Product> findByQuantityGreaterThan(int quantity);
    @Query("SELECT p.cost FROM Product p WHERE p.id = :id")
    Double findCostById(@Param("id") Long id);
}
