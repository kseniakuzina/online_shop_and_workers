package com.ksu.online_shop.entities;

import jakarta.persistence.*;


import com.ksu.common.entities.OrderStatus;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "t_order_id_seq", allocationSize = 1)
    private Long id;
    private Integer quantity;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String address;

    public Order(User user, List<Cart> carts, String address) {
        this.user = user;
        this.carts = carts;
        this.status = OrderStatus.ACCEPTED;
        quantity = 0;
        amount = 0.0;
        for (Cart cart : carts){
            quantity += cart.getProductQuantity();
            amount += cart.getAmount();
        }
        this.address = address;
    }
    public Order(){}

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
