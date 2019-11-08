package com.example.dbms.domain.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ecg")
public class Ecg implements Serializable {
	private static final long serialVersionUID = -339516038496531946L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "patient_id", nullable = false)
	private int patient_id;

	@Column(name = "test_id")
	private String test_id;

	@Column(name = "ecg_data")
	private String ecg_data;

	@Column(name = "ecg_result")
	private int ecg_result;

	@Column(name = "ecg_info")
	private String ecg_info;

	@Column(name = "surgery")
	private int surgery;

	@Column(name = "date")
	private String date;

	public Ecg() {

	}

	public Ecg(int patient_id, String test_id, String ecg_data, int ecg_result,
               String ecg_info, int surgery, String date ) {
		this.patient_id=patient_id;
		this.test_id=test_id;
		this.ecg_data=ecg_data;
		this.ecg_result=ecg_result;
		this.ecg_info=ecg_info;
		this.surgery=surgery;
		this.date=date;
	}
	public Ecg(int id, int patient_id, String test_id, String ecg_data, int ecg_result,
               String ecg_info, int surgery, String date ) {
		this.id=id;
		this.patient_id=patient_id;
		this.test_id=test_id;
		this.ecg_data=ecg_data;
		this.ecg_result=ecg_result;
		this.ecg_info=ecg_info;
		this.surgery=surgery;
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

	public void setTest_id(String test_id) {
		this.test_id = test_id;
	}

	public String getTest_id() {
		return test_id;
	}

	public void setEcg_data(String ecg_data) {
		this.ecg_data = ecg_data;
	}

	public String getEcg_data() {
		return ecg_data;
	}

	public void setEcg_result(int ecg_result) {
		this.ecg_result = ecg_result;
	}

	public int getEcg_result() {
		return ecg_result;
	}

	public void setEcg_info(String ecg_info) {
		this.ecg_info = ecg_info;
	}

	public String getEcg_info() {
		return ecg_info;
	}

	public void setSurgery(int surgery) {
		this.surgery = surgery;
	}

	public int getSurgery() {
		return surgery;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}
}
