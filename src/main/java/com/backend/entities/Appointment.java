package com.backend.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
@Entity
public @Data class Appointment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@JoinColumn(name="APPOINTMENT_CLIENT")
	@ManyToOne
	Client client;
	String motif;
	String status;
	@Column(nullable=true)
	Date dateRdv;
	@Column(name="DATE_DEMANDE")
	LocalDateTime dateDemande;
	
}
