package com.example.dbms.service;



import com.example.dbms.domain.po.Role;

import java.util.List;

public interface RoleService {
    List<Role> findRoleByUsername(String username);
}
