package com.ksu.online_shop.controller;

import com.ksu.common.entities.ProductDTO;
import com.ksu.online_shop.entities.Role;
import com.ksu.online_shop.entities.User;
import com.ksu.online_shop.service.ProductService;
import com.ksu.online_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("home/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String adminPage(Model model) {
        return "admin";
    }

    @GetMapping("/allusers")
    public String getAllUsers(Model model) {
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);
        for (User i:users) System.out.println(i.getUsername());
        return "allusers";
    }

    @GetMapping("/newproduct")
    public String NewProduct(Model model) {
        return "newproduct";
    }
    @PostMapping("/newproduct")
    public String addNewProduct(ProductDTO dto) {
        productService.saveProduct(dto);
        return "redirect:/home/admin";
    }
}
