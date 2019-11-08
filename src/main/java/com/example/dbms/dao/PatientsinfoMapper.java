package com.example.dbms.dao;

import com.example.dbms.domain.vo.PatientsInfo;
import com.example.dbms.domain.vo.QueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PatientsinfoMapper {
    List<PatientsInfo> queryPatientsInfo(QueryParam queryParam);
}
