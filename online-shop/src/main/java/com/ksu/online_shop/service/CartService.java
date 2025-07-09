package com.ksu.online_shop.service;

import com.ksu.online_shop.entities.Cart;
import com.ksu.common.entities.CartStatus;
import com.ksu.online_shop.entities.Product;
import com.ksu.online_shop.entities.User;
import com.ksu.online_shop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;


    public boolean saveCart(Long productId) {
        User user = userService.getCurrentUser();
        Product product = productService.getProductById(productId);

        List<Cart> existingCarts = cartRepository.findAllByUserAndProduct(user, product);

        if (existingCarts.stream().anyMatch((cart->cart.getStatus()==CartStatus.IN_CART))) {
            return false;
        }

        Cart cart = new Cart(user, product);
        cartRepository.save(cart);
        return true;
    }

    public List<Cart> getAllCarts(){
        return cartRepository.findAll();
    }

    public List<Cart> getAllCartsByUserId(Long id){
        return cartRepository.findAllById(Collections.singleton(id));
    }

    public List<Pair<Product,Pair<Integer,Double>>> getAllProductsByUserId(Long id){
        List<Cart> carts = cartRepository.findAllByUserId(id);
        List<Pair<Product,Pair<Integer,Double>>> products = new ArrayList<>();
        for(Cart cart : carts){
            if (cart.getStatus()== CartStatus.IN_CART){
                Product product = cart.getProduct();
                products.add(Pair.of(product,Pair.of(cart.getProductQuantity(),cart.getAmount())));
            }
        }
        return products;
    }
    @Transactional
    public boolean increaseQuantityByProductId(Long productId) {
        User user = userService.getCurrentUser();
        Product product = productService.getProductById(productId);
        Cart cart = cartRepository.findByUserAndProductAndStatus(user, product, CartStatus.IN_CART);
        cart.setProductQuantity(cart.getProductQuantity() + 1);
        cart.setAmount(cart.getAmount()+product.getCost());
        cartRepository.save(cart);
        return true;
    }

    public boolean removeCartByProductId(Long productId){
        User user = userService.getCurrentUser();
        Product product = productService.getProductById(productId);
        Cart cart = cartRepository.findByUserAndProductAndStatus(user, product, CartStatus.IN_CART);
        cartRepository.delete(cart);
        return true;
    }

}