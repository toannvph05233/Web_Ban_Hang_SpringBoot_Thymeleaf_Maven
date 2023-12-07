package com.example.demo.service;

import com.example.demo.models.Roles;

public interface RolesService {
    Iterable<Roles> findAll();

    Roles getRoleUser();

    Roles getRoleStaff();
}
