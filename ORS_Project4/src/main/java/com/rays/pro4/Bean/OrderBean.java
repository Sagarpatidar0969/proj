package com.rays.pro4.Bean;

public class OrderBean extends BaseBean{
	
	 private String type;
	 private String name;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getkey() {
		
		return id+"";
	}
	@Override
	public String getValue() {
		
		return type + " " + name;
	}
	 

}
