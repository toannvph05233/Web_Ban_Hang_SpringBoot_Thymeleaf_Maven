package com.example.demo.service.impl;

import com.example.demo.models.TypeProduct;
import com.example.demo.repository.TypeProductRepository;
import com.example.demo.service.TypeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeProductServiceImpl implements TypeProductService {
    @Autowired
    private TypeProductRepository typeProductRepository;
    @Override
    public Iterable<TypeProduct> findAll() {
        return typeProductRepository.findAll();
    }

    @Override
    public TypeProduct getTypeProduct(Long id) {
        return typeProductRepository.findById(id).orElse(null);
    }
}
