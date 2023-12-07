package com.example.demo.service.impl;

import com.example.demo.models.Roles;
import com.example.demo.repository.RolesRepository;
import com.example.demo.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesServiceImpl implements RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public Iterable<Roles> findAll() {
        return rolesRepository.findAll();
    }

    @Override
    public Roles getRoleUser() {
        return rolesRepository.findById(1L).orElse(null);
    }

    @Override
    public Roles getRoleStaff() {
        return rolesRepository.findById(3L).orElse(null);
    }
}
