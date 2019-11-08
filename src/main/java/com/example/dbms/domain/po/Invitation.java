package com.example.dbms.domain.po;

import java.io.Serializable;

public class Invitation implements Serializable {
	private static final long serialVersionUID = -339516038496531944L;
	private int id;

	private String code;
	
	public Invitation() {
		
	}
	
	public Invitation(String code) {
		this.code = code;
	}
	
	public Invitation(int id, String code) {
		this.id = id;
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
