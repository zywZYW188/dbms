package com.example.dbms.dao;

import com.example.dbms.domain.po.Cdg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CdgMapper {
    Cdg findByTestId(String testId);
    List findByPatientId(int patient_id);
    int  updateCdg(@Param("ecg_id") int ecg_id, @Param("patient_id") int patient_id, @Param("cdg_data") String cdg_data, @Param("thi") double thi, @Param("shi") double shi,
                   @Param("di") double di, @Param("result_id") int result_id);
    int  insertCdg(Cdg cdg);
}
