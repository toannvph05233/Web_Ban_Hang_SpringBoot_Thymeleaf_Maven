package com.example.demo.repository;

import com.example.demo.models.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends PagingAndSortingRepository<Orders, Long> {
    Page<Orders> findAllByAccountuserContaining(String account, Pageable pageable);
}
