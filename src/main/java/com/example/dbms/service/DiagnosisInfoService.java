package com.example.dbms.service;


import com.example.dbms.domain.po.Diagnose_info;

public interface DiagnosisInfoService {
    int insertDiagnoseInfo(Diagnose_info diagnose_info);
    int updateDiagnoseInfo(Diagnose_info diagnose_info, int patient_id);
    Diagnose_info findById(int patient_id);
}
