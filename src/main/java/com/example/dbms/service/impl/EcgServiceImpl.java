package com.example.dbms.service.impl;


import com.example.dbms.dao.EcgMapper;
import com.example.dbms.domain.po.Ecg;
import com.example.dbms.service.EcgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("EcgService")
public class EcgServiceImpl implements EcgService {
    @Autowired
    private EcgMapper ecgMapper;
    @Override
    public String findECGData(String testId)
    {
        return  ecgMapper.findECGData(testId);
    }
    @Override
    public int findId(int patient_id,  String testId)
    {
        return ecgMapper.findId(patient_id,testId);
    }
    @Override
    public List<String> findTest_id(int patient_id)
    {
        return ecgMapper.findTest_id(patient_id);
    }
    @Override
    public int updateEcg(int id,String ecg_data, int surgery, int ecg_result,  String ecg_info)
    {
       return ecgMapper.updateEcg(id,ecg_data,surgery,ecg_result,ecg_info);
    }
    @Override
    public int insertEcg(Ecg ecg)
    {
        return ecgMapper.insertEcg(ecg);
    }
}
