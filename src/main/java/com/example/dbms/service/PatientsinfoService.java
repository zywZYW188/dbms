package com.example.dbms.service;

import com.example.dbms.domain.vo.PatientsInfo;
import com.example.dbms.domain.vo.QueryParam;

import java.util.List;

public interface PatientsinfoService {
    List<PatientsInfo> queryPatientsInfo(QueryParam queryParam);
}
