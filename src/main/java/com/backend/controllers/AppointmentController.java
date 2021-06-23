package com.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.entities.Admin;
import com.backend.entities.Agent;
import com.backend.entities.Appointment;
import com.backend.exceptions.AlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.services.AppointmentService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AppointmentController {
	private final AppointmentService appointmentService;
	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService=appointmentService;
	}
	/*	
	//GET
	@GetMapping("/appointments")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Appointment>> getAppointments()
	{
		List<Appointment> appointments = appointmentService.getAppointments();
		return new ResponseEntity<>(appointments,HttpStatus.OK);
	}	
*/	
	//GET
	@PostMapping("/addAppointment")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment)
	{
		Appointment newAppointment = appointmentService.addAppointment(appointment);
		return new ResponseEntity<>(newAppointment,HttpStatus.CREATED);
	}
	
	@PutMapping("/appointment/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateAppointment(@PathVariable Long id , @RequestBody(required=false) Appointment appointment)throws NotFoundException
	{
		appointmentService.updateAppointment(id, appointment);
	}	
	
	@DeleteMapping("/appointment/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAppointment(@PathVariable Long id) throws NotFoundException
	{
		appointmentService.removeAppointment(id);
	}
	
}
