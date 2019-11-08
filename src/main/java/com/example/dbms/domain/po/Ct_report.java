package com.example.dbms.domain.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ct_report")
public class Ct_report implements Serializable {
    private static final long serialVersionUID = -339516038496531949L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "patient_id", nullable = false)
    private int patient_id;

    @Column(name = "description")
    private String description;

    @Column(name = "result_id")
    private int result_id;

    @Column(name = "date")
    private String date;

    public Ct_report()
    {}
    public Ct_report(int patient_id, String description, int result_id, String date)
    {
        this.patient_id=patient_id;
        this.description=description;
        this.result_id=result_id;
        this.date=date;
    }
    public Ct_report(int id, int patient_id, String description, int result_id, String date)
    {
        this.id=id;
        this.patient_id=patient_id;
        this.description=description;
        this.result_id=result_id;
        this.date=date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setResult_id(int result_id) {
        this.result_id = result_id;
    }

    public int getResult_id() {
        return result_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
