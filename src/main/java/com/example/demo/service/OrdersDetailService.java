package com.example.demo.service;

import com.example.demo.models.OrdersDetail;

public interface OrdersDetailService {
    void saveOrdersDetail(OrdersDetail ordersDetail);

    Iterable<OrdersDetail> findOrdersDetailById_Order(Long id);
}
