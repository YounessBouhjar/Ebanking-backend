package com.backend.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Entity
@Table(name="BENEFICIAIRE")
public @Data class Beneficiaire  {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
		
	@JoinColumn(name="Client_proprio")
	@ManyToOne
	Client client;
	
	@Column(unique = true)@JoinColumn(name="numero_compte")
	String numeroCompte;

	
	@JoinColumn(name="Compte_owner")
	@ManyToOne
	Compte compteOwner;
		
}
