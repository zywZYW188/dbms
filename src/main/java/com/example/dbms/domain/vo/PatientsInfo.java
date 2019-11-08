package com.example.dbms.domain.vo;

import java.io.Serializable;

public class PatientsInfo implements Serializable {

	private static final long serialVersionUID = -339516038496531960L;
	private int id;

	private String name;
	
	private String sex;
	
	private int age;
	
	private String admissionnumber;
	
	private String hos_time;
	
	public PatientsInfo() {
		
	}
	
	public PatientsInfo(String name, String sex, int age, String admissionnumber,
                        String hos_time) {
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.admissionnumber = admissionnumber;
		this.hos_time = hos_time;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAdmissionnumber() {
		return admissionnumber;
	}

	public void setAdmissionnumber(String admissionnumber) {
		this.admissionnumber = admissionnumber;
	}

	public String getHos_time() {
		return hos_time;
	}

	public void setHos_time(String hos_time) {
		this.hos_time = hos_time;
	}

}
