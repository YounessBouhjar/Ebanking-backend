package com.backend.services;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.backend.entities.Beneficiaire;
import com.backend.entities.Client;
import com.backend.entities.Compte;
import com.backend.entities.VirementMultiple;
import com.backend.entities.VirementMultipleBeneficiaire;
import com.backend.exceptions.AlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.ClientRepository;
import com.backend.repositories.VirementMultipleRepository;

@Service
public class VirementMultipleService {

    @Autowired
    private VirementMultipleRepository virementMultipleRepository;
	
	@Autowired
	CompteService compteService;

	@Autowired
	ClientService clientService;
	
	@Autowired
	RecuVirementService recuService;
	
	@Autowired
	DeviseService deviseService;

    @Autowired
    BeneficiaireService benificiaireService;

    @Transactional
    public VirementMultiple saveVirementMultiple(VirementMultiple virement) throws Exception, AlreadyExistsException
	{
    	Client client=clientService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		virement.setClient(client);
    	System.out.println("$$$$$$$$$$$------- " + virement.getDebiteur().getId() + " -------$$$$$$$$$");
		Compte debiteur = compteService.getComptes(virement.getDebiteur().getId()).get(0);
		Compte creancier = new Compte();
		
		Double total=0.0;
		for (VirementMultipleBeneficiaire vmbenef : virement.getVmb()) {
			total += vmbenef.getMontant();
		}
		
		if(debiteur.getSolde() < virement.getSommeEnv()) throw new Exception("Vous n'avez pas de solde suffisant !");
		if(virement.getSommeEnv() != total) throw new Exception("le total des montant envoyés est incompatible avec la somme définie !");
		
		virement.setDate(LocalDateTime.now());
		 
		debiteur.setSolde(debiteur.getSolde() - virement.getSommeEnv());
		compteService.rep.save(debiteur);
    	System.out.println("$$$$$$$$$$$------- " + 1 + " -------$$$$$$$$$");
		
		for (VirementMultipleBeneficiaire vmbenef : virement.getVmb()) {
			
			creancier = compteService.getCompteByNumero(vmbenef.getBeneficiaire().getNumeroCompte());
			creancier.setSolde(creancier.getSolde() + vmbenef.getMontant());
			
			compteService.rep.save(creancier);
		}

    	System.out.println("$$$$$$$$$$$------- " + 2 + " -------$$$$$$$$$");
    	VirementMultiple vm = new VirementMultiple();
		vm.setClient(client);
    	vm.setDate(virement.getDate());
    	vm.setDebiteur(debiteur);
    	vm.setSommeEnv(virement.getSommeEnv());
    	System.out.println("$$$$$$$$$$$------- " + 3 + " -------$$$$$$$$$");
    	
    	vm.getVmb().addAll((virement.getVmb()
                .stream()
                .map(vmb -> {
                    Beneficiaire beneficiaire = benificiaireService.getBeneficiaireByNumeroCompte(vmb.getBeneficiaire().getNumeroCompte());
                    VirementMultipleBeneficiaire newVMB = new VirementMultipleBeneficiaire();
                    newVMB.setBeneficiaire(beneficiaire);
                    newVMB.setVirementMultiple(vm);
                    newVMB.setMontant(vmb.getMontant());
                    return newVMB;
                })
                .collect(Collectors.toList())
        ));
    	System.out.println("$$$$$$$$$$$------- " + 4 + " -------$$$$$$$$$");
        return virementMultipleRepository.save(vm);
    }

	public Collection<VirementMultiple> getVirementMultiples() {
		return (Collection<VirementMultiple>) virementMultipleRepository.findAll();
	}

	public VirementMultiple getVirementMultipleById(Long id) {
		// TODO Auto-generated method stub
		return virementMultipleRepository.findById(id).get();
	}
	
	@Autowired
	ClientRepository rep;
	public List<VirementMultiple> getVirementMultipleByClient(Long id) throws NotFoundException
	{
		Client client= rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun client avec l'id "+id+" trouvé"));
		if(client.getVirementMultiple().isEmpty()) throw new NotFoundException("Cet client n'a aucun virement multiple.");
		return client.getVirementMultiple();
		
	}
}