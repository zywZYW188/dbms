package com.example.dbms.domain.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class Diagnose_info implements Serializable {
    private static final long serialVersionUID = -339516038496531948L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "patient_id", nullable = false)
    private int patient_id;

    @Column(name = "complaints")
    private String complaints;

    @Column(name = "diagnose_result")
    private String diagnose_result;

    public Diagnose_info(){}
    public Diagnose_info(int patient_id, String complaints, String diagnose_result)
    {
        this.patient_id=patient_id;
        this.complaints=complaints;
        this.diagnose_result=diagnose_result;
    }
    public Diagnose_info(int id, int patient_id, String complaints, String diagnose_result)
    {
        this.id=id;
        this.patient_id=patient_id;
        this.complaints=complaints;
        this.diagnose_result=diagnose_result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setDiagnose_result(String diagnose_result) {
        this.diagnose_result = diagnose_result;
    }

    public String getDiagnose_result() {
        return diagnose_result;
    }
}
