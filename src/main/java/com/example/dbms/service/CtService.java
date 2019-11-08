package com.example.dbms.service;


import com.example.dbms.domain.po.Ct_report;

public interface CtService {
    int insertCt(Ct_report ct_report);
    int updateCT(Ct_report ct_report, int patient_id);
    Ct_report findById(int patient_id);
}
