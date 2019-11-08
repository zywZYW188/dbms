package com.example.dbms.service.impl;


import com.example.dbms.dao.RoleMapper;
import com.example.dbms.domain.po.Role;
import com.example.dbms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RoleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public  List<Role> findRoleByUsername(String username)
    {
        return roleMapper.findRoleByUsername(username);
    }
}
