package com.example.dbms.domain.vo;

import java.io.Serializable;

public class QueryParam implements Serializable {

    private static final long serialVersionUID = -339516038496531961L;
    private int type_tag;
    private String admissionnumber;
    private String name;
    private String sex;
    private int ecg_result;
    private int imaging_result;
    private int cdg_result;
    private String hospital;
    private float cdg_Index1;
    private float cdg_Index2;
    public QueryParam(int type_tag, String admissionnumber,
                 String name, String sex, int ecg_result,
                 int imaging_result, int cdg_result,
                 String hospital, float cdg_Index1,
                 float cdg_Index2)
    {
        this.type_tag=type_tag;
        this.admissionnumber=admissionnumber;
        this.name=name;
        this.sex=sex;
        this.ecg_result=ecg_result;
        this.imaging_result=imaging_result;
        this.cdg_result=cdg_result;
        this.hospital=hospital;
        this.cdg_Index1=cdg_Index1;
        this.cdg_Index2=cdg_Index2;
    }
    public void setType_tag(int type_tag) {
        this.type_tag = type_tag;
    }

    public int getType_tag() {
        return type_tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setEcg_result(int ecg_result) {
        this.ecg_result = ecg_result;
    }

    public int getEcg_result() {
        return ecg_result;
    }

    public void setAdmissionnumber(String admissionnumber) {
        this.admissionnumber = admissionnumber;
    }
    public String getAdmissionnumber() {
        return admissionnumber;
    }

    public float getCdg_Index1() {
        return cdg_Index1;
    }

    public void setCdg_Index1(float cdg_Index1) {
        this.cdg_Index1 = cdg_Index1;
    }

    public void setCdg_Index2(float cdg_Index2) {
        this.cdg_Index2 = cdg_Index2;
    }



    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setImaging_result(int imaging_result) {
        this.imaging_result = imaging_result;
    }

    public float getCdg_Index2() {
        return cdg_Index2;
    }

    public int getCdg_result() {
        return cdg_result;
    }

    public void setCdg_result(int cdg_result) {
        this.cdg_result = cdg_result;
    }

    public int getImaging_result() {
        return imaging_result;
    }

    public String getHospital() {
        return hospital;
    }

}
