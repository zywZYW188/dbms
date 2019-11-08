package com.example.dbms.dao;

import com.example.dbms.domain.po.Ct_report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CtMapper {
    int insertCt(Ct_report ct_report);
    int updateCT(@Param("ct_report") Ct_report ct_report, @Param("patient_id") int patient_id);
    Ct_report findById(int patient_id);
}
