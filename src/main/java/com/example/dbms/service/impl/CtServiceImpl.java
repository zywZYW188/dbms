package com.example.dbms.service.impl;


import com.example.dbms.dao.CtMapper;
import com.example.dbms.domain.po.Ct_report;
import com.example.dbms.service.CtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CtService")
public class CtServiceImpl implements CtService {
    @Autowired
    private CtMapper ctMapper;
    @Override
    public int insertCt(Ct_report ct_report)
    {
        return ctMapper.insertCt(ct_report);
    }
    @Override
    public int updateCT( Ct_report ct_report, int patient_id)
    {
        return ctMapper.updateCT(ct_report,patient_id);
    }
    @Override
    public Ct_report findById(int patient_id)
    {
        return ctMapper.findById(patient_id);
    }
}
