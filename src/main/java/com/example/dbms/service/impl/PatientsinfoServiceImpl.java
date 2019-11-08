package com.example.dbms.service.impl;


import com.example.dbms.dao.PatientsinfoMapper;
import com.example.dbms.domain.vo.PatientsInfo;
import com.example.dbms.domain.vo.QueryParam;
import com.example.dbms.service.PatientsinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PatientsinfoService")
public class PatientsinfoServiceImpl implements PatientsinfoService {
    @Autowired
    private PatientsinfoMapper patientsinfoMapper;
    @Override
    public List<PatientsInfo> queryPatientsInfo(QueryParam queryParam)
    {
        return patientsinfoMapper.queryPatientsInfo(queryParam);
    }


}
