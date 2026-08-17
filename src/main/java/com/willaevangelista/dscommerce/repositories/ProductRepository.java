package com.willaevangelista.dscommerce.repositories;

import com.willaevangelista.dscommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
