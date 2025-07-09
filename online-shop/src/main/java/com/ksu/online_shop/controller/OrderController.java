package com.ksu.online_shop.controller;

import com.ksu.common.entities.OrderStatus;
import com.ksu.online_shop.service.CartService;
import com.ksu.online_shop.service.OrderService;
import com.ksu.online_shop.service.ProductService;
import com.ksu.online_shop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/home/order")
public class OrderController {
    private UserService userService;
    private CartService cartService;
    private OrderService orderService;
    private ProductService productService;

    public OrderController(UserService userService, CartService cartService, OrderService orderService, ProductService productService) {
        this.userService = userService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping
    public String orderPage(Model model){
        model.addAttribute("username", userService.getCurrentUsername());
        model.addAttribute("orders", orderService.getAllOrdersByUser());
        return "order";
    }

    @PostMapping
    public String createOrder(@RequestParam String address, RedirectAttributes redirectAttributes, HttpServletRequest request){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("service-account", "secure-password");
        if (csrfToken != null) {
            System.out.println("туть");
            headers.add(csrfToken.getHeaderName(), csrfToken.getToken());
        }

        boolean mooving = orderService.moveCartToOrder(address,headers);
        if(!mooving){
            redirectAttributes.addFlashAttribute("errorMessage", "Указанное количество товара отсутсвует на складе.");
            return "redirect:/home/cart";
        }
        return "redirect:/home/order";
    }

    @PostMapping("/changestatus")
    public String changeOrderStatus(@RequestBody Pair<Long, OrderStatus> orderIdAndStatus){
        orderService.updateOrderStatus(orderIdAndStatus);
        return "redirect:/home/order";
    }


    @PostMapping("/changeproductquantity")
    public String changeProductQuantity(@RequestBody Long productId){
        productService.updateProductQuantity(productId);
        return "redirect:/home/order";
    }
}
