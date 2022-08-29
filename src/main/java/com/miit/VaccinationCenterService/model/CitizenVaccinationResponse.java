package com.miit.VaccinationCenterService.model;

import java.util.List;

public class CitizenVaccinationResponse {
	
	private VaccinationCenter center;
	
	private List<Citizen> citizens;

	public VaccinationCenter getCenter() {
		return center;
	}

	public void setCenter(VaccinationCenter center) {
		this.center = center;
	}

	public List<Citizen> getCitizens() {
		return citizens;
	}

	public void setCitizens(List<Citizen> citizens) {
		this.citizens = citizens;
	}
	
	
}
