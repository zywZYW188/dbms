package com.example.dbms.service;


import com.example.dbms.domain.po.ComprehensiveCase;

public interface ComprehensiveCaseService {
    int insertComprehensiveCase(ComprehensiveCase comprehensiveCase);
    int updateComprehensiveCase(ComprehensiveCase comprehensiveCase, int patient_id);
    ComprehensiveCase findById(int patient_id);
}
