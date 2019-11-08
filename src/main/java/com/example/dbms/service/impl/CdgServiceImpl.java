package com.example.dbms.service.impl;

import com.example.dbms.dao.CdgMapper;
import com.example.dbms.domain.po.Cdg;
import com.example.dbms.service.CdgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CdgService")
public class CdgServiceImpl implements CdgService {
    @Autowired
    private CdgMapper cdgMapper;
    @Override
    public Cdg findByTestId(String testId)
    {
       return cdgMapper.findByTestId(testId);
    }
    @Override
    public List findByPatientId(int patient_id )
    {
     return cdgMapper.findByPatientId(patient_id);
    }
    @Override
    public int  updateCdg(int ecg_id,int patient_id,String cdg_data,double thi, double shi,
                   double di, int result_id)
    {
      return cdgMapper.updateCdg( ecg_id, patient_id,cdg_data,thi, shi,
         di, result_id);
    }
    @Override
    public int  insertCdg(Cdg cdg)
    {
       return cdgMapper.insertCdg(cdg);
    }

}
