package com.example.dbms.service;


import com.example.dbms.domain.po.Basiccase;

public interface BasicCaseService {
    Basiccase findById(int patient_id);
    int insertBasicCase(Basiccase basiccase);
    int updateBasiccase(Basiccase basiccase, int patient_id);
}
