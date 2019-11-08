package com.example.dbms.domain.DTO;

public class Point {
	
	private String x;
	
	private String y;

	public Point() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Point(String x, String y) {
		super();
		this.x = x;
		this.y = y;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
}
