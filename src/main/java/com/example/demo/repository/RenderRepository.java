package com.example.demo.repository;

import com.example.demo.models.Render;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenderRepository extends CrudRepository<Render, Long> {
}
