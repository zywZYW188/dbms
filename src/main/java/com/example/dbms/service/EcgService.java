package com.example.dbms.service;



import com.example.dbms.domain.po.Ecg;

import java.util.List;

public interface EcgService {
    String findECGData(String testId);
    int findId(int patient_id, String testId);
    List<String> findTest_id(int patient_id);
    int updateEcg(int id, String ecg_data, int surgery, int ecg_result, String ecg_info);
    int insertEcg(Ecg ecg);
}
