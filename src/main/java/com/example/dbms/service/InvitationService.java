package com.example.dbms.service;


import com.example.dbms.domain.po.Invitation;

public interface InvitationService {
    Invitation findByCode(String code);
}
