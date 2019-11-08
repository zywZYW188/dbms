package com.example.dbms.service;


import com.example.dbms.domain.po.Diagnosis;

import java.util.List;

public interface DiagnosisService {
    List<Diagnosis> queryDiagnosis(String admissionnumber);
}
