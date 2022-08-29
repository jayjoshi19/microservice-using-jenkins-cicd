package com.miit.VaccinationCenterService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.miit.VaccinationCenterService.model.Citizen;
import com.miit.VaccinationCenterService.model.CitizenVaccinationResponse;
import com.miit.VaccinationCenterService.model.VaccinationCenter;
import com.miit.VaccinationCenterService.repository.VaccinationCenterRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RestController
@RequestMapping("/vaccinationcenter")
public class VaccinationCenterController {
	
	@Autowired
	private VaccinationCenterRepository centerRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/add")
	public ResponseEntity<VaccinationCenter> addCitizen(@RequestBody VaccinationCenter vaccinationCenter){

		return new ResponseEntity<VaccinationCenter>(centerRepository.save(vaccinationCenter), HttpStatus.OK);
	}
	
	// get list of citizens that are registered to particular vaccination center
	@GetMapping("/id/{id}")
	@HystrixCommand(fallbackMethod = "handleCitizenDownTime")
	public ResponseEntity<CitizenVaccinationResponse> getAllDataByCenterId(@PathVariable ("id") Integer id){
		
		CitizenVaccinationResponse response = new CitizenVaccinationResponse();
		
		// get vaccination center details
		VaccinationCenter center = centerRepository.findById(id).get();
		response.setCenter(center);
		 
		// get all citizens registered to vaccination center --> for this we conntct to Citizen MicroService
		List<Citizen> citizensList = restTemplate.getForObject("http://citizen-service/citizen/id/"+id, List.class);
		response.setCitizens(citizensList);
		
		return new ResponseEntity<CitizenVaccinationResponse>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<CitizenVaccinationResponse> handleCitizenDownTime(@PathVariable ("id") Integer id){
		CitizenVaccinationResponse response = new CitizenVaccinationResponse();
		
		// get vaccination center details
		VaccinationCenter center = centerRepository.findById(id).get();
		response.setCenter(center);
		
		return new ResponseEntity<CitizenVaccinationResponse>(response, HttpStatus.OK);
	}
}
