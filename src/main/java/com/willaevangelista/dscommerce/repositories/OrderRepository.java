package com.willaevangelista.dscommerce.repositories;

import com.willaevangelista.dscommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> { }
