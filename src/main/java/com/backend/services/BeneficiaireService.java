package com.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.entities.Beneficiaire;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.BeneficiaireRepository;

@Service
public class BeneficiaireService {
	@Autowired
	BeneficiaireRepository beneficiaireRepository;
	public void addBeneficiaire(Beneficiaire beneficiaire)
	{
		//if(beneficiaireRepository.findByNumeroCompte(beneficiaire.getNumeroCompte()).isPresent()) 
			//throw new ConflictException("Un bénéficiaire avec le numéro de compte "+beneficiaire.getNumeroCompte()+" existe déjà.");
		
		beneficiaireRepository.save(beneficiaire);
		
	}
	
	
	public Beneficiaire getBeneficiaire(Long id)
	{
		
		Beneficiaire beneficiaire = beneficiaireRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun bénéficiaire avec l'id "+id+" trouvé."));
		
		return beneficiaire;
	}
	

	public Beneficiaire getBeneficiaireByNumeroCompte(String numero)
	{
		
		Beneficiaire beneficiaire = beneficiaireRepository.findByNumeroCompte(numero)
				.orElseThrow(() -> new NotFoundException("Aucun bénéficiaire avec le compte "+numero+" trouvé."));
		
		return beneficiaire;
	}
	
	
	public void deleteBeneficiaire(Long id)
	{
		if(!beneficiaireRepository.findById(id).isPresent())  throw new NotFoundException("Aucun bénéficiaire avec l'id "+id+" trouvé.");
		beneficiaireRepository.deleteById(id);
	}


}
