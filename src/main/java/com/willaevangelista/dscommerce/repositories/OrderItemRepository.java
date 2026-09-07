package com.willaevangelista.dscommerce.repositories;

import com.willaevangelista.dscommerce.entities.OrderItem;
import com.willaevangelista.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> { }
