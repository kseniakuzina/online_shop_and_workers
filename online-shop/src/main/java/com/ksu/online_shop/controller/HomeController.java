package com.ksu.online_shop.controller;

import com.ksu.online_shop.entities.User;
import com.ksu.online_shop.service.ProductService;
import com.ksu.online_shop.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private ProductService productService;

    public HomeController(UserService userService, ProductService productService){
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public String success(Model model) {
        String username = userService.getCurrentUsername();
        var tr = userService.getCurrentUser().getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : tr) {
            System.out.println(authority.getAuthority());
            roles.add(authority.getAuthority());
        }
        model.addAttribute("username", username);
        model.addAttribute("roles", roles);
        model.addAttribute("products", productService.getAllProductsWhereQuantityGreaterThenZero());
        return "home";
    }
    @GetMapping("/account")
    public String account(Model model){
        User currUser = userService.getCurrentUser();
        model.addAttribute("currUser",currUser);
        return "account";
    }

    @PostMapping("/account/update")
    public String updateAccount(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String phone) {
        userService.updateUser(firstName,lastName,username,email,phone);
        return "redirect:/home/account";
    }

}
