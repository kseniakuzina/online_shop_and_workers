package com.ksu.common.entities;


public class CartDTO {
    private Long id;
    private ProductDTOWithId product;
    private int productQuantity;
    private Double amount;
    private CartStatus status;

    public CartDTO(Long id, ProductDTOWithId product, int productQuantity, Double amount, CartStatus status) {
        this.id = id;
        this.product = product;
        this.productQuantity = productQuantity;
        this.amount = amount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTOWithId getProduct() {
        return product;
    }

    public void setProduct(ProductDTOWithId product) {
        this.product = product;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }
}

