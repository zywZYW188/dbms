package com.example.dbms.domain.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cdg")
public class Cdg implements Serializable {
	private static final long serialVersionUID = -339516038496531951L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "ecg_id")
	private int ecg_id;

	@Column(name = "patient_id")
	private int patient_id;

	@Column(name = "cdg_data")
	private String cdg_data;

	@Column(name = "thi")
	private double thi;

	@Column(name = "shi")
	private double shi;

	@Column(name = "di")
	private double di;

	@Column(name = "result_id")
	private int result_id;

	public Cdg() {

	}

	public Cdg(int ecg_id, int patient_id, String cdg_data, double thi, double shi, double di, int result_id) {
		this.ecg_id = ecg_id;
		this.patient_id = patient_id;
		this.cdg_data = cdg_data;
		this.thi = thi;
		this.shi = shi;
		this.di=di;
		this.result_id=result_id;
	}
	public Cdg(int id, int ecg_id, int patient_id, String cdg_data, double thi, double shi, double di, int result_id) {
		this.id=id;
		this.ecg_id = ecg_id;
		this.patient_id = patient_id;
		this.cdg_data = cdg_data;
		this.thi = thi;
		this.shi = shi;
		this.di=di;
		this.result_id=result_id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setEcg_id(int ecg_id) {
		this.ecg_id = ecg_id;
	}

	public int getEcg_id() {
		return ecg_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	public int getPatient_id() {
		return patient_id;
	}

	public void setResult_id(int result_id) {
		this.result_id = result_id;
	}

	public int getResult_id() {
		return result_id;
	}

	public void setCdg_data(String cdg_data) {
		this.cdg_data = cdg_data;
	}

	public String getCdg_data() {
		return cdg_data;
	}

	public void setDi(double di) {
		this.di = di;
	}

	public double getDi() {
		return di;
	}

	public void setShi(double shi) {
		this.shi = shi;
	}

	public double getShi() {
		return shi;
	}

	public void setThi(double thi) {
		this.thi = thi;
	}

	public double getThi() {
		return thi;
	}
}
