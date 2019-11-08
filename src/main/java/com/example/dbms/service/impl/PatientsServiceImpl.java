package com.example.dbms.service.impl;


import com.example.dbms.dao.PatientsMapper;
import com.example.dbms.domain.po.Patients;
import com.example.dbms.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PatientsService")
public class PatientsServiceImpl implements PatientsService {
    @Autowired
    private PatientsMapper patientsMapper;
    @Override
    public void insertPatients(Patients patients)
    {
      patientsMapper.insertPatients(patients);
    }
    @Override
    public int findId(String admissionnumber)
    {
      return patientsMapper.findId(admissionnumber);
    }
    @Override
    public int updatePatients(Patients patients,int id)
    {
        return patientsMapper.updatePatients(patients,id);
    }
    @Override
    public int findPatient_id(String admissionnumber)
    {
        return patientsMapper.findPatient_id(admissionnumber);
    }
    @Override
    public Patients findPatients(String admissionnumber)
    {
        return patientsMapper.findPatients(admissionnumber);
    }

}
