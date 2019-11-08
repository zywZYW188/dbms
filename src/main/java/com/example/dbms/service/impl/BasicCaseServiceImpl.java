package com.example.dbms.service.impl;


import com.example.dbms.dao.BasicCaseMapper;
import com.example.dbms.domain.po.Basiccase;
import com.example.dbms.service.BasicCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BasicCaseService")
public class BasicCaseServiceImpl implements BasicCaseService {
    @Autowired
    private BasicCaseMapper basicCaseMapper;
    @Override
    public Basiccase findById(int patient_id)
    {
       return  basicCaseMapper.findById(patient_id);
    }
    @Override
    public int insertBasicCase(Basiccase basiccase)
    {
      return basicCaseMapper.insertBasicCase(basiccase);
    }
    @Override
   public int updateBasiccase(Basiccase basiccase, int patient_id)
    {
        return updateBasiccase(basiccase,patient_id);
    }

}
