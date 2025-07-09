package com.ksu.online_shop.service;

import com.ksu.common.entities.ProductDTO;
import com.ksu.online_shop.DTO.UserDTO;
import com.ksu.online_shop.entities.Product;
import com.ksu.online_shop.entities.Role;
import com.ksu.online_shop.entities.User;
import com.ksu.online_shop.repository.ProductRepository;
import com.ksu.online_shop.repository.RoleRepository;
import com.ksu.online_shop.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public boolean saveProduct(ProductDTO dto) {
        Product product = new Product(dto.getName(),dto.getQuantity(),dto.getMaterial(),dto.getCost());
        productRepository.save(product);
        return true;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> getAllProductsWhereQuantityGreaterThenZero(){
        return productRepository.findByQuantityGreaterThan(0);
    }

    public Product getProductById(Long id){
        return productRepository.getReferenceById(id);
    }

    public Double getCostById(Long id){
        return productRepository.findCostById(id);
    }

    public void updateProductQuantity(Long productId){
        Product product = productRepository.getReferenceById(productId);
        product.setQuantity(100);
        productRepository.save(product);
    }

}