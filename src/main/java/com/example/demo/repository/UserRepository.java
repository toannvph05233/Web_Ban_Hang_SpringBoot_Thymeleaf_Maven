package com.example.demo.repository;

import com.example.demo.models.Roles;
import com.example.demo.models.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserInfo, Long> {
    Page<UserInfo> findAllByNameContainingAndRoles(String regex, Roles roles, Pageable pageable);

    Page<UserInfo> findAllByRoles(Roles roles, Pageable pageable);

    UserInfo findByAccount(String account);
}
