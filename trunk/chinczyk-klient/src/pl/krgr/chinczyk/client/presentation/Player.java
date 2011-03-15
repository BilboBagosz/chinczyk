package pl.krgr.chinczyk.client.presentation;

import pl.krgr.chinczyk.model.Camp;

public class Player {
	
	private String name;
	private Camp camp;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCamp(Camp camp) {
		this.camp = camp;
	}
	
	public Camp getCamp() {
		return camp;
	}
}