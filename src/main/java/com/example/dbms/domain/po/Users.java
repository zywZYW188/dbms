package com.example.dbms.domain.po;

import javax.persistence.*;
import java.io.Serializable;


public class Users implements Serializable {

	private static final long serialVersionUID = -339516038496531942L;
	private int id;
	private String username;
	private String password;
	private String hospital;
	private String realname;
	private String mobile;
	private String email;
	public Users() {

	}

	public Users(String username, String password, String hospital, String realname, String mobile, String email) {
		this.username = username;
		this.password = password;
		this.hospital = hospital;
		this.realname = realname;
		this.mobile = mobile;
		this.email = email;
	}

	public Users(int id, String username, String password, String hospital, String realname, String mobile, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.hospital = hospital;
		this.realname = realname;
		this.mobile = mobile;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
 
	public String gethospital() {
		return hospital;
	}

	public void sethospital(String hospital) {
		this.hospital = hospital;
	}
	
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}



/*@Entity
@Table(name = "users")
public class Users implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "hospital", nullable = false)
	private String hospital;

	@Column(name = "realname", nullable = false)
	private String realname;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "email")
	private String email;

	public Users() {

	}

	public Users(String username, String password, String hospital,String realname, String mobile, String email) {
		this.username = username;
		this.password = password;
		this.hospital = hospital;
		this.realname = realname;
		this.mobile = mobile;
		this.email = email;
	}

	public Users(int id, String username, String password, String hospital,String realname, String mobile, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.hospital = hospital;
		this.realname = realname;
		this.mobile = mobile;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String gethospital() {
		return hospital;
	}

	public void sethospital(String hospital) {
		this.hospital = hospital;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}*/

