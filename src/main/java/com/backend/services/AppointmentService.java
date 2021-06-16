package com.backend.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.backend.entities.Agent;
import com.backend.entities.Appointment;
import com.backend.entities.Client;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.AppointmentRep;
@Service
@Transactional
public class AppointmentService {
	
	private AppointmentRep appointmentRep;
	@Autowired
	private ClientService clientService;
	
	@Autowired
	public AppointmentService(AppointmentRep appointmentRep) {
		this.appointmentRep=appointmentRep;
	}
	
	public List<Appointment> getAppointments()
	{
		return appointmentRep.findAll();
	}
	
	
	
	public Appointment addAppointment(Appointment appointment) {
		Client client=clientService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		appointment.setClient(client);
		appointment.setDateDemande(LocalDateTime.now());
		return 	appointmentRep.save(appointment);
	}
	
	public void updateAppointment(Long id,Appointment appointment)throws NotFoundException {
		
		Appointment updated = appointmentRep.findById(id).orElseThrow(() -> new NotFoundException("Aucun rendez-vous avec l'id "+id+" trouvé"));

		updated.setClient(appointment.getClient());
		updated.setDateDemande(appointment.getDateDemande());
		updated.setDateRdv(appointment.getDateRdv());
		updated.setId(appointment.getId());
		updated.setMotif(appointment.getMotif());
		updated.setStatus(appointment.getStatus());
			appointmentRep.save(updated);

	}
	public void removeAppointment(Long id) throws NotFoundException
	{
		
		Appointment appointment=appointmentRep.findById(id).orElseThrow(() -> new NotFoundException("Aucun rendezvous avec l'id "+id+" n'est trouvé"));
		appointmentRep.delete(appointment);
		

	}

}
