package com.example.dbms.dao;

import com.example.dbms.domain.po.Patients;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PatientsMapper {
    int findId(String admissionnumber);
    int updatePatients(@Param("patients") Patients patients, @Param("id") int id);
    int findPatient_id(String admissionnumber);
    Patients findPatients(String admissionnumber);
    int insertPatients(Patients patients);
}
