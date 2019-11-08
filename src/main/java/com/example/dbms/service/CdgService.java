package com.example.dbms.service;



import com.example.dbms.domain.po.Cdg;

import java.util.List;

public interface CdgService {
    Cdg findByTestId(String testId);
    List findByPatientId(int patient_id);
    int  updateCdg(int ecg_id, int patient_id, String cdg_data, double thi, double shi,
                   double di, int result_id);
    int  insertCdg(Cdg cdg);
}
