package com.ksu.common.entities;


import java.util.ArrayList;
import java.util.List;


public class OrderDTO {
    private Long id;
    private Integer quantity;
    private Double amount;
    private List<CartDTO> carts = new ArrayList<>();
    private OrderStatus status;
    private String address;

    public OrderDTO(Long id, Integer quantity, Double amount, List<CartDTO> carts, OrderStatus status, String address) {
        this.id = id;
        this.quantity = quantity;
        this.amount = amount;
        this.carts = carts;
        this.status = status;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<CartDTO> getCarts() {
        return carts;
    }

    public void setCarts(List<CartDTO> carts) {
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

