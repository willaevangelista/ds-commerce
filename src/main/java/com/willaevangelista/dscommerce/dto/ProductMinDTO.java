package com.willaevangelista.dscommerce.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.willaevangelista.dscommerce.entities.Product;

@JsonPropertyOrder({"id", "name", "price", "imgUrl"})

public class ProductMinDTO {

    private Long id;
    private String name;
    private Double price;
    private String imgUrl;

    public ProductMinDTO() {}

    public ProductMinDTO (Long id, String name, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductMinDTO (Product entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        imgUrl = entity.getImgURL();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
