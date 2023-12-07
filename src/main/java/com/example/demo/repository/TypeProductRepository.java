package com.example.demo.repository;

import com.example.demo.models.TypeProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeProductRepository extends CrudRepository<TypeProduct, Long> {
}
