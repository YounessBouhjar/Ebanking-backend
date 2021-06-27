package com.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.entities.*;

public interface CompteRepository extends JpaRepository<Compte, Long> {

	Optional<Compte> findByNumero(String numero);
	
	@Query("SELECT c FROM Compte c WHERE c.proprietaire.nom = ?1")
	List<Compte> findByProprietaire(String proprietaire);
	
	@Query("SELECT c FROM Compte c WHERE c.creationAgent.username = ?1")
	List<Compte> findByCreationAgent(String agent);

}
