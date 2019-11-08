package com.example.dbms.service;


import com.example.dbms.domain.po.Patients;

public interface PatientsService {
    void insertPatients(Patients patients);
    int findId(String admissionnumber);
    int updatePatients(Patients patients, int id);
    int findPatient_id(String admissionnumber);
    Patients findPatients(String admissionnumber);
}
