package com.example.dbms.service;



import com.example.dbms.domain.po.Permission;

import java.util.List;

public interface PermissionService  {
    List<Permission> findUserPermissions(String userName);
}
