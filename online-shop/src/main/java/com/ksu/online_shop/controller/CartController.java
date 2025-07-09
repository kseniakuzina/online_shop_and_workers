package com.ksu.online_shop.controller;

import com.ksu.online_shop.entities.Product;
import com.ksu.online_shop.service.CartService;
import com.ksu.online_shop.service.UserService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/home/cart")
public class CartController {
    private UserService userService;
    private CartService cartService;

    public CartController(UserService userService, CartService cartService){
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping
    public String cart(Model model){
        Long UserId = userService.getCurrentUserId();
        List<Pair<Product,Pair<Integer,Double>>> userProducts = cartService.getAllProductsByUserId(UserId);
        model.addAttribute("products", userProducts);
        model.addAttribute("username", userService.getCurrentUsername());
        return "cart";
    }

    @PostMapping
    public String addToCart(@RequestParam Long productId, RedirectAttributes redirectAttributes){
        boolean adding = cartService.saveCart(productId);
        if (!adding) {
            // Если добавление не удалось, добавляем сообщение в модель
            redirectAttributes.addFlashAttribute("errorMessage", "Данный товар уже добавлен в корзину.");
            return "redirect:/home";
        }
        return "redirect:/home/cart";
    }

    @PostMapping("/increaseQuantity")
    public String increaseQuantity(@RequestParam Long productId){
        cartService.increaseQuantityByProductId(productId);
        return "redirect:/home/cart";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long productId){
        cartService.removeCartByProductId(productId);
        return "redirect:/home/cart";
    }

}
