package com.willaevangelista.dscommerce.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.willaevangelista.dscommerce.entities.Category;
import com.willaevangelista.dscommerce.entities.Product;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"id", "name", "description", "price", "imgUrl"})
public class ProductDTO {

    private Long id;
    @Size(min = 3, max = 80, message = "Name must be between 3 and 80 characters")
    @NotBlank(message = "Field name is required")
    private String name;
    @Size(min = 10, message = "Description must have at least 3 characters")
    @NotBlank(message = "Field description is required")
    private String description;
    @NotNull(message = "Field price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;
    private String imgUrl;

    @NotEmpty(message = "Categories list must have at least one category")
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {}

    public ProductDTO (Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDTO (Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgURL();

        for (Category category : entity.getCategories()) {
            categories.add(new CategoryDTO(category));
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}
