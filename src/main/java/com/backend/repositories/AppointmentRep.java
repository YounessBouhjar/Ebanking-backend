package com.backend.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Appointment;

public interface AppointmentRep extends JpaRepository<Appointment, Long>{

}
