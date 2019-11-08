package com.example.dbms.service.impl;


import com.example.dbms.dao.DiagnoseInfoMapper;
import com.example.dbms.domain.po.Diagnose_info;
import com.example.dbms.service.DiagnosisInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DiagnosisInfoService")
public class DiagnosisInfoServiceImpl implements DiagnosisInfoService {
    @Autowired
    private DiagnoseInfoMapper diagnoseInfoMapper;
    @Override
    public int insertDiagnoseInfo(Diagnose_info diagnose_info)
    {
        return diagnoseInfoMapper.insertDiagnoseInfo(diagnose_info);
    }
    @Override
    public int updateDiagnoseInfo( Diagnose_info diagnose_info, int patient_id)
    {
        return diagnoseInfoMapper.updateDiagnoseInfo(diagnose_info,patient_id);
    }
    @Override
    public Diagnose_info findById(int patient_id)
    {
        return diagnoseInfoMapper.findById(patient_id);
    }
}
