package com.example.dbms.service.impl;


import com.example.dbms.dao.InvitationMapper;
import com.example.dbms.domain.po.Invitation;
import com.example.dbms.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("InvitationService")
public class InvitationServiceImpl implements InvitationService {
    @Autowired
    private InvitationMapper invitationMapper;
    @Override
    public Invitation findByCode(String code)
    {
      return invitationMapper.findByCode(code);
    }


}
