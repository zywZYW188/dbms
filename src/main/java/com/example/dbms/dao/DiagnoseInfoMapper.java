package com.example.dbms.dao;

import com.example.dbms.domain.po.Diagnose_info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface DiagnoseInfoMapper {
    int insertDiagnoseInfo(Diagnose_info diagnose_info);
    int updateDiagnoseInfo(@Param("diagnose_info") Diagnose_info diagnose_info, @Param("patient_id") int patient_id);
    Diagnose_info findById(int patient_id);
}


