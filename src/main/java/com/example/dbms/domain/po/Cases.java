package com.example.dbms.domain.po;


import java.io.Serializable;

/**
 * 对应于数据库中的cases表
 *
 *
 */
public class Cases implements Serializable {
	private static final long serialVersionUID = -339516038496531952L;
	private String name;
	private String reportTag;
	private String ctTag;
	private String complaints;
	private String diagnose_result;
	private String nationality;
	private String bir_place;
	private String smokeTag;
	private String drinkTag;
	private String genetic_history;
	private String drug_history;
	private int heart_rate;
	private int ultrasonicef;
	private int lv;
	private float triglyceride;
	private float blood_sugur;
	private float creatinine;
	private float ldlc;
	private float hdlc;
	private float hscp;
	private float lipoprotein_a;
	private float glycated_hemoglobin;
	private float bmi;
	private float ffr;
	private float bnp;
	private float thyroid_function;
	private float blood_potassium;
	private float blood_sodium;
	private int blood_pressure_high;
	private int blood_pressure_low;


	public Cases(){}
	public Cases(String name, String reportTag, String ctTag, String complaints, String diagnose_result, String nationality,
                 String bir_place, String smokeTag, String drinkTag, String genetic_history, String drug_history, int heart_rate, int ultrasonicef,
                 int lv, float triglyceride, float blood_sugur, float creatinine, float ldlc, float hdlc, float hscp, float lipoprotein_a,
                 float glycated_hemoglobin, float bmi, float ffr, float bnp, float thyroid_function, float blood_potassium, float blood_sodium, int blood_pressure_high, int blood_pressure_low)
	{
		this.name=name;
		this.reportTag=reportTag;
		this.ctTag=ctTag;
		this.complaints=complaints;
		this.diagnose_result=diagnose_result;
		this.nationality = nationality;
		this.bir_place = bir_place;
		this.smokeTag = smokeTag;
		this.drinkTag = drinkTag;
		this.genetic_history = genetic_history;
		this.drug_history = drug_history;
		this.heart_rate = heart_rate;
		this.ultrasonicef = ultrasonicef;
		this.lv = lv;
		this.triglyceride = triglyceride;
		this.blood_sugur = blood_sugur;
		this.creatinine = creatinine;
		this.ldlc = ldlc;
		this.hdlc = hdlc;
		this.hscp = hscp;
		this.lipoprotein_a = lipoprotein_a;
		this.glycated_hemoglobin = glycated_hemoglobin;
		this.bmi = bmi;
		this.ffr = ffr;
		this.bnp = bnp;
		this.thyroid_function = thyroid_function;
		this.blood_potassium = blood_potassium;
		this.blood_sodium = blood_sodium;
		this.blood_pressure_low=blood_pressure_low;
		this.blood_pressure_high=blood_pressure_high;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setReportTag(String reportTag) {
		this.reportTag = reportTag;
	}

	public String getReportTag() {
		return reportTag;
	}

	public void setCtTag(String ctTag) {
		this.ctTag = ctTag;
	}

	public String getCtTag() {
		return ctTag;
	}

	public void setComplaints(String complaints) {
		this.complaints = complaints;
	}

	public String getComplaints() {
		return complaints;
	}

	public void setDiagnose_result(String diagnose_result) {
		this.diagnose_result = diagnose_result;
	}

	public String getDiagnose_result() {
		return diagnose_result;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNationality() {
		return nationality;
	}

	public void setBir_place(String bir_place) {
		this.bir_place = bir_place;
	}

	public String getBir_place() {
		return bir_place;
	}

	public void setSmokeTag(String smokeTag) {
		this.smokeTag = smokeTag;
	}

	public String getSmokeTag() {
		return smokeTag;
	}

	public void setDrinkTag(String drinkTag) {
		this.drinkTag = drinkTag;
	}

	public String getDrinkTag() {
		return drinkTag;
	}

	public void setGenetic_history(String genetic_history) {
		this.genetic_history = genetic_history;
	}

	public String getGenetic_history() {
		return genetic_history;
	}

	public void setDrug_history(String drug_history) {
		this.drug_history = drug_history;
	}

	public String getDrug_history() {
		return drug_history;
	}

	public void setHeart_rate(int heart_rate) {
		this.heart_rate = heart_rate;
	}

	public int getHeart_rate() {
		return heart_rate;
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

	public void setBlood_pressure_high(int blood_pressure_high) {
		this.blood_pressure_high = blood_pressure_high;
	}

	public int getBlood_pressure_high() {
		return blood_pressure_high;
	}


	public void setBlood_pressure_low(int blood_pressure_low) {
		this.blood_pressure_low = blood_pressure_low;
	}
	public int getBlood_pressure_low() {
		return blood_pressure_low;
	}

}