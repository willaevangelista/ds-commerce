package com.willaevangelista.dscommerce.services;

import com.willaevangelista.dscommerce.dto.OrderDTO;
import com.willaevangelista.dscommerce.entities.Order;
import com.willaevangelista.dscommerce.repositories.OrderRepository;
import com.willaevangelista.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public OrderDTO findAById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found"));
        return new OrderDTO(order);
    }
}
