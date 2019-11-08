package com.example.dbms.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NationalityMapper {
    int findNationalityId(String natinality);
    String findNationality(int id);
}
