package com.example.demo.service;

import com.example.demo.models.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrdersService {
    void saveOrders(Orders orders);

    Page<Orders> findAll(Pageable pageable);

    Page<Orders> findByAccountUser(String account, Pageable pageable);
}
