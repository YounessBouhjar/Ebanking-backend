package com.backend.entities;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
public @Data class VirementSuperClass implements Serializable{
	
	@JoinColumn(name="DEBITEUR_VIREMENT")
	@ManyToOne
	Compte debiteur;
	
	@Column(name="DATE_VIREMENT")
	LocalDateTime date;
	
	@Column(name="SOMME_ENV_VIREMENT")
	double sommeEnv;
}