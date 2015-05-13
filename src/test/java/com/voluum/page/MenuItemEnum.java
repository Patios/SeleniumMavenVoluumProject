package com.voluum.page;

public enum MenuItemEnum {
	
	DASHBOARD("Dashboard"),
	CAMPAIGNS("Campaigns");
	


	private String name;

	private MenuItemEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	

}
