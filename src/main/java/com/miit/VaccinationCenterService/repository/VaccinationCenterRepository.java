package com.miit.VaccinationCenterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miit.VaccinationCenterService.model.VaccinationCenter;

public interface VaccinationCenterRepository extends JpaRepository<VaccinationCenter, Integer> {

}
