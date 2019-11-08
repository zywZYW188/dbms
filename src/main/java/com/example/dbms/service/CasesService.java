package com.example.dbms.service;


import com.example.dbms.domain.po.Cases;

public interface CasesService {
    Cases findCasesByAdmissionnumber(String admissionnumber);
}
