package com.example.dbms.service;


import com.example.dbms.domain.po.Users;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;


//@CacheConfig(cacheNames = "Users")
public interface UsersService {
    int insert(Users users);

    //@Cacheable(key = "#p0")
    Users  findByUsername(String username);

    Users findById(int id);
}
