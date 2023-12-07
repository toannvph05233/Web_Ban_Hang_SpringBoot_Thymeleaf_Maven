package com.example.demo.service;

import com.example.demo.models.TypeProduct;

public interface TypeProductService {
    Iterable<TypeProduct> findAll();

    TypeProduct getTypeProduct(Long id);
}
