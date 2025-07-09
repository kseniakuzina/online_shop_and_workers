package com.ksu.common.entities;


public class ProductDTOWithId {
    private Long id;
    private String name;
    private Integer quantity;
    private String material;
    private Double cost;

    public ProductDTOWithId(Long id, String name, Integer quantity, String material, Double cost) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.material = material;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
