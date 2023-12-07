package com.example.demo.service;

import com.example.demo.models.Roles;
import com.example.demo.models.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserInfo> findAll(Pageable pageable);

    Page<UserInfo> findAllByRoles(Roles roles, Pageable pageable);

    Page<UserInfo> findAllByName(String regex, Roles roles, Pageable pageable);

    UserInfo findOne(String account);

    void saveUser(UserInfo userInfo);

    void deleteUser(Long id);
}
