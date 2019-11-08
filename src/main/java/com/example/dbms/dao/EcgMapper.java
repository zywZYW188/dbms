package com.example.dbms.dao;


import com.example.dbms.domain.po.Ecg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface EcgMapper {
   //Ecg findECGData(String testId);
   String findECGData(String testId);
   int findId(@Param("patient_id") int patient_id, @Param("test_id") String testId);
   List<String> findTest_id(int patient_id);
   int updateEcg(@Param("id") int id, @Param("ecg_data") String ecg_data, @Param("surgery") int surgery, @Param("ecg_result") int ecg_result, @Param("ecg_info") String ecg_info);
   int insertEcg(Ecg ecg);
}
