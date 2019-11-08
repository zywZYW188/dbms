package com.example.dbms.dao;

import com.example.dbms.domain.po.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;

@Component
@Mapper
//@CacheConfig(cacheNames = "Users")
public interface UsersMapper {
    Users findByUsername(String username);

    Users findById(int id);

    int insert(Users users);
    /*int update(Users users);*/
}
