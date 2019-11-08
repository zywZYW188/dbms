package com.example.dbms.service.impl;


import com.example.dbms.dao.PermissionMapper;
import com.example.dbms.domain.po.Permission;
import com.example.dbms.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PermissionService")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> findUserPermissions(String userName)
    {
        return permissionMapper.findUserPermissions(userName);
    }
}
