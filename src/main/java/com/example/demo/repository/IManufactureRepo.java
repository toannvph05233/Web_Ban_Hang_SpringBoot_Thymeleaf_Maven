package com.example.demo.repository;

import com.example.demo.models.Manufacture;
import org.springframework.data.repository.CrudRepository;

public interface IManufactureRepo extends CrudRepository<Manufacture, Long> {
}
