package com.example.dbms.service.impl;


import com.example.dbms.dao.NationalityMapper;
import com.example.dbms.service.NationalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("NationalityService")
public class NationalityServiceImpl implements NationalityService {
    @Autowired
    private NationalityMapper nationalityMapper;
    @Override
    public int findNationalityId(String natinality)
    {
      return nationalityMapper.findNationalityId(natinality);
    }
    @Override
    public String findNationality(int id)
    {
        return  nationalityMapper.findNationality(id);
    }

}
