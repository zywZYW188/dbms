package com.example.dbms.service.impl;


import com.example.dbms.dao.CasesMapper;
import com.example.dbms.domain.po.Cases;
import com.example.dbms.service.CasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CaseService")
public class CasesServiceImpl implements CasesService {
    @Autowired
    private CasesMapper casesMapper;
    public Cases findCasesByAdmissionnumber(String admissionnumber)
    {
        return casesMapper.findCasesByAdmissionnumber(admissionnumber);
    }
}
