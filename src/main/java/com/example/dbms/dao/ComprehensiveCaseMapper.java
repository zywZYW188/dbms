package com.example.dbms.dao;

import com.example.dbms.domain.po.ComprehensiveCase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ComprehensiveCaseMapper {
    int insertComprehensiveCase(ComprehensiveCase comprehensiveCase);
    int updateComprehensiveCase(@Param("comprehensiveCase") ComprehensiveCase comprehensiveCase, @Param("patient_id") int patient_id);
    ComprehensiveCase findById(int patient_id);
}
