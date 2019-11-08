package com.example.dbms.domain.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comprehensive_case")
public class ComprehensiveCase implements Serializable {
    private static final long serialVersionUID = -339516038496531950L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "patient_id", nullable = false)
    private int patient_id;

    @Column(name = "heart_rate")
    private int heart_rate;

    @Column(name = "blood_pressure_high")
    private int blood_pressure_high;

    @Column(name = "blood_pressure_low")
    private int blood_pressure_low;

    @Column(name = "ultrasonicef")
    private int ultrasonicef;

    @Column(name = "lv")
    private int lv;

    @Column(name = "triglyceride")
    private float triglyceride;

    @Column(name = "blood_sugur")
    private float blood_sugur;

    @Column(name = "creatinine")
    private float creatinine;

    @Column(name = "ldlc")
    private float ldlc;

    @Column(name = "hdlc")
    private float hdlc;

    @Column(name = "hscp")
    private float hscp;

    @Column(name = "lipoprotein_a")
    private float lipoprotein_a;

    @Column(name = "glycated_hemoglobin")
    private float glycated_hemoglobin;

    @Column(name = "bmi")
    private float bmi;

    @Column(name = "ffr")
    private float ffr;

    @Column(name = "bnp")
    private float bnp;

    @Column(name = "thyroid_function")
    private float thyroid_function;

    @Column(name = "blood_potassium")
    private float blood_potassium;

    @Column(name = "blood_sodium")
    private float blood_sodium;

    public ComprehensiveCase()
    {

    }
    public ComprehensiveCase(int id, int patient_id, int heart_rate, int blood_pressure_high, int blood_pressure_low,
                             int ultrasonicef, int lv, float triglyceride, float blood_sugur,
                             float creatinine, float ldlc, float hdlc, float hscp,
                             float lipoprotein_a, float glycated_hemoglobin, float bmi,
                             float ffr, float bnp, float thyroid_function, float blood_potassium,
                             float blood_sodium)
    {
        this.id = id;
        this.patient_id = patient_id;
        this.heart_rate=heart_rate;
        this.blood_pressure_high=blood_pressure_high;
        this.blood_pressure_low=blood_pressure_low;
        this.ultrasonicef=ultrasonicef;
        this.lv=lv;
        this.triglyceride=triglyceride;
        this.blood_sugur=blood_sugur;
        this.creatinine=creatinine;
        this.ldlc=ldlc;
        this.hdlc=hdlc;
        this.hscp=hscp;
        this.lipoprotein_a=lipoprotein_a;
        this.glycated_hemoglobin=glycated_hemoglobin;
        this.bmi=bmi;
        this.ffr=ffr;
        this.bnp=bnp;
        this.thyroid_function=thyroid_function;
        this.blood_potassium=blood_potassium;
        this.blood_sodium=blood_sodium;
    }
    public ComprehensiveCase(int patient_id, int heart_rate, int blood_pressure_high, int blood_pressure_low,
                             int ultrasonicef, int lv, float triglyceride, float blood_sugur,
                             float creatinine, float ldlc, float hdlc, float hscp,
                             float lipoprotein_a, float glycated_hemoglobin, float bmi,
                             float ffr, float bnp, float thyroid_function, float blood_potassium,
                             float blood_sodium)
    {
        this.patient_id = patient_id;
        this.heart_rate=heart_rate;
        this.blood_pressure_high=blood_pressure_high;
        this.blood_pressure_low=blood_pressure_low;
        this.ultrasonicef=ultrasonicef;
        this.lv=lv;
        this.triglyceride=triglyceride;
        this.blood_sugur=blood_sugur;
        this.creatinine=creatinine;
        this.ldlc=ldlc;
        this.hdlc=hdlc;
        this.hscp=hscp;
        this.lipoprotein_a=lipoprotein_a;
        this.glycated_hemoglobin=glycated_hemoglobin;
        this.bmi=bmi;
        this.ffr=ffr;
        this.bnp=bnp;
        this.thyroid_function=thyroid_function;
        this.blood_potassium=blood_potassium;
        this.blood_sodium=blood_sodium;
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

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public int getHeart_rate() {
        return heart_rate;
    }

    public int getBlood_pressure_high() {
        return blood_pressure_high;
    }

    public void setBlood_pressure_high(int blood_pressure_high) {
        this.blood_pressure_high = blood_pressure_high;
    }

    public int getBlood_pressure_low() {
        return blood_pressure_low;
    }

    public void setBlood_pressure_low(int blood_pressure_low) {
        this.blood_pressure_low = blood_pressure_low;
    }

    public void setUltrasonicef(int ultrasonicef) {
        this.ultrasonicef = ultrasonicef;
    }

    public int getUltrasonicef() {
        return ultrasonicef;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getLv() {
        return lv;
    }

    public void setTriglyceride(float triglyceride) {
        this.triglyceride = triglyceride;
    }

    public float getTriglyceride() {
        return triglyceride;
    }

    public void setBlood_sugur(float blood_sugur) {
        this.blood_sugur = blood_sugur;
    }

    public float getBlood_sugur() {
        return blood_sugur;
    }

    public void setCreatinine(float creatinine) {
        this.creatinine = creatinine;
    }

    public float getCreatinine() {
        return creatinine;
    }

    public void setLdlc(float ldlc) {
        this.ldlc = ldlc;
    }

    public float getLdlc() {
        return ldlc;
    }

    public void setHdlc(float hdlc) {
        this.hdlc = hdlc;
    }

    public float getHdlc() {
        return hdlc;
    }

    public void setHscp(float hscp) {
        this.hscp = hscp;
    }

    public float getHscp() {
        return hscp;
    }

    public void setLipoprotein_a(float lipoprotein_a) {
        this.lipoprotein_a = lipoprotein_a;
    }

    public float getLipoprotein_a() {
        return lipoprotein_a;
    }

    public void setGlycated_hemoglobin(float glycated_hemoglobin) {
        this.glycated_hemoglobin = glycated_hemoglobin;
    }

    public float getGlycated_hemoglobin() {
        return glycated_hemoglobin;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getBmi() {
        return bmi;
    }
    public void setFfr(float ffr) {
        this.ffr = ffr;
    }

    public float getFfr() {
        return ffr;
    }
    public void setBnp(float bnp) {
        this.bnp = bnp;
    }

    public float getBnp() {
        return bnp;
    }

    public void setThyroid_function(float thyroid_function) {
        this.thyroid_function = thyroid_function;
    }

    public float getThyroid_function() {
        return thyroid_function;
    }

    public void setBlood_potassium(float blood_potassium) {
        this.blood_potassium = blood_potassium;
    }

    public float getBlood_potassium() {
        return blood_potassium;
    }

    public void setBlood_sodium(float blood_sodium) {
        this.blood_sodium = blood_sodium;
    }

    public float getBlood_sodium() {
        return blood_sodium;
    }

}
