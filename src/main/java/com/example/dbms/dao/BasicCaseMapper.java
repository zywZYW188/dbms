package com.example.dbms.dao;

import com.example.dbms.domain.po.Basiccase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BasicCaseMapper {
    Basiccase findById(int patient_id);
    int insertBasicCase(Basiccase basiccase);
    int updateBasiccase(@Param("basiccase") Basiccase basiccase, @Param("patient_id") int patient_id);
}
