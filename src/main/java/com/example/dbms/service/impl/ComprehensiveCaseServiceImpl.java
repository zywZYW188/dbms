package com.example.dbms.service.impl;

import com.example.dbms.dao.ComprehensiveCaseMapper;
import com.example.dbms.domain.po.ComprehensiveCase;
import com.example.dbms.service.ComprehensiveCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ComprehensiveCaseService")
public class ComprehensiveCaseServiceImpl implements ComprehensiveCaseService {
    @Autowired
    private ComprehensiveCaseMapper comprehensiveCaseMapper;
    @Override
    public int insertComprehensiveCase(ComprehensiveCase comprehensiveCase)
    {
       return comprehensiveCaseMapper.insertComprehensiveCase(comprehensiveCase);
    }
    @Override
    public int updateComprehensiveCase(ComprehensiveCase comprehensiveCase, int patient_id)
    {
        return comprehensiveCaseMapper.updateComprehensiveCase(comprehensiveCase,patient_id);
    }
    @Override
    public ComprehensiveCase findById(int patient_id)
    {
        return comprehensiveCaseMapper.findById(patient_id);
    }
}
