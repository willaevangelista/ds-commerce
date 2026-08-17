package com.willaevangelista.dscommerce.services;

import com.willaevangelista.dscommerce.dto.ProductDTO;
import com.willaevangelista.dscommerce.entities.Product;
import com.willaevangelista.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findAById(Long id) {
        Product product = productRepository.findById(id).get();
        return new ProductDTO(product);
    }
}
