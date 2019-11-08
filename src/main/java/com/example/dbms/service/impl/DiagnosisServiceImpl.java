package com.example.dbms.service.impl;



import com.example.dbms.dao.DiagnosisMapper;
import com.example.dbms.domain.po.Diagnosis;
import com.example.dbms.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DiagnosisService")
public class DiagnosisServiceImpl implements DiagnosisService {
    @Autowired
    private DiagnosisMapper diagnosisMapper;
    @Override
    public List<Diagnosis> queryDiagnosis(String admissionnumber)
    {
        return diagnosisMapper.queryDiagnosis(admissionnumber);
    }
}
