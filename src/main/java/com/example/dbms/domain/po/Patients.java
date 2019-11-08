package com.example.dbms.domain.po;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "patients")
public class Patients implements Serializable {
	private static final long serialVersionUID = -339516038496531943L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "sex", nullable = false)
	private String sex;
	
	@Column(name = "age", nullable = false)
	private int age;
	
	@Column(name = "admissionnumber", nullable = false)
	private String admissionnumber;

	@Column(name = "nationality", nullable = false)
	private int nationality;

	@Column(name = "type_tag")
	private int type_tag;

	@Column(name = "bir_place")
	private String bir_place;

	@Column(name = "hos_time")
	private String hos_time;

	@Column(name = "discharged_time")
	private String discharged_time;

	@Column(name = "ownner")
	private String ownner;
    //在bean中显示必须加上无参构造函数,进行序列化与反序列化
	public Patients() {
	}
	public Patients(int id, String name, String sex, int age, String admissionnumber, int nationality, int type_tag, String bir_place, String hos_time, String discharged_time, String ownner) {
		this.id=id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.admissionnumber = admissionnumber;
		this.nationality=nationality;
		this.type_tag=type_tag;
		this.bir_place=bir_place;
		this.hos_time=hos_time;
		this.discharged_time=discharged_time;
		this.ownner=ownner;
	}
	public Patients(String name, String sex, int age, String admissionnumber, int nationality,
                    int type_tag, String bir_place, String hos_time, String discharged_time, String ownner) {
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.admissionnumber = admissionnumber;
		this.nationality=nationality;
		this.type_tag=type_tag;
		this.bir_place=bir_place;
		this.hos_time=hos_time;
		this.discharged_time=discharged_time;
		this.ownner=ownner;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		this.admissionnumber= admissionnumber;
	}

	public int getNationality() {
		return nationality;
	}
	public void setnationality(int nationality) {
		this.nationality = nationality;
	}

	public int getType_tag() {
		return type_tag;
	}
	public void setType_tag(int type_tag) {
		this.type_tag = type_tag;
	}

	public String getBir_place() {
		return bir_place;
	}
	public void setBir_place(String bir_place) {
		this.bir_place = bir_place;
	}

	public String getHos_time() {
		return hos_time;
	}
	public void setHos_time(String hos_time) {
		this.hos_time = hos_time;
	}

	public String getDischarged_time() {
		return discharged_time;
	}
	public void setDischarged_time(String discharged_time) {
		this.discharged_time = discharged_time;
	}

	public String getOwnner() {
		return ownner;
	}
	public void setOwnner(String ownner) {
		this.ownner = ownner;
	}
}
