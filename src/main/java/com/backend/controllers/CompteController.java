package com.backend.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.entities.*;
import com.backend.exceptions.*;
import com.backend.services.*;
import com.itextpdf.text.DocumentException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CompteController {
	
	
	CompteService service;
	
	@Autowired
	public CompteController(CompteService service) {
		
		this.service=service;
	}
	
	//GET
			@GetMapping("/comptes")
			@ResponseStatus(HttpStatus.OK)
			public List<Compte> getComptes(@RequestParam(name="id", required=false) Long id) throws NotFoundException
			{
				return service.getComptes(id);
			}
			
			
			@GetMapping("/compte/{numero}")
			@ResponseStatus(HttpStatus.OK)
			public Compte getCompteByNumero(@PathVariable(name="numero") String numero)
			{
				return service.getCompteByNumero(numero);
			}
			
			@GetMapping("/compte/prop/{prop}")
			@ResponseStatus(HttpStatus.OK)
			public List<Compte> getCompteByProp(@PathVariable(name="prop") String prop)
			{
				return service.getCompteByNomClient(prop);
			}
			
			@GetMapping("/compte/agent/{agent}")
			@ResponseStatus(HttpStatus.OK)
			public List<Compte> getCompteByAg(@PathVariable(name="agent") String agent)
			{
				return service.getCompteByAgent(agent);
			}
			
			@GetMapping("/comptes/all")
			public ResponseEntity<List<Compte>> getAllClients(){
				List<Compte> comptes = service.findAllComptes();
				return new ResponseEntity<>(comptes,HttpStatus.OK);
			}
			
			@GetMapping("/compte/{id}/virements")
			@ResponseStatus(HttpStatus.OK)
			public List<Virement> getVirements(@PathVariable(name="id") Long id) throws NotFoundException
			{
				return service.getVirements(id);
			}
			
			
			@GetMapping("/compte/{id}/virementsEnvoyes")
			@ResponseStatus(HttpStatus.OK)
			public List<Virement> getVirementsEnvoyes(@PathVariable(name="id") Long id) throws NotFoundException
			{
				return service.getVirementsEnvoyes(id);
			}
			
			
			@GetMapping("/compte/{id}/virementsRecus")
			@ResponseStatus(HttpStatus.OK)
			public List<Virement> getVirementsRecus(@PathVariable(name="id") Long id) throws NotFoundException
			{
				return service.getVirementsRecus(id);
			}
			
			
			
	
			
//			@GetMapping(value="/contratPDF/{id}", produces = "application/pdf")
//			@ResponseStatus(HttpStatus.OK)
//			public ResponseEntity<InputStreamResource> getContratPDF(@PathVariable(name="id") Long id) throws IOException
//			{
//				return service.getContratPDF(id);
//			}
			
			
		 
		
		//POST
			
			@PostMapping("/comptes")
			@ResponseStatus(HttpStatus.CREATED)
			public void addCompte(@RequestBody Compte compte)  throws AlreadyExistsException, DocumentException, FileNotFoundException
			{
				service.addCompte(compte);
			}
		
		
		
		//PUT
			
			@PutMapping("/compte/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void updateCompte(@PathVariable Long id , @RequestBody(required=false) Compte compte)  throws NotFoundException, AlreadyExistsException
			{
				service.updateCompte(id,compte);
			}
	
			
			@PutMapping("/compte/update")
			public ResponseEntity<Compte> updateCompte(@RequestBody Compte compte){
				Compte newCompte = service.updateCompteNew(compte);
				return new ResponseEntity<>(newCompte,HttpStatus.CREATED);
				
			}
		
		
			
		//DELETE
			
			@DeleteMapping("/compte/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void deleteCompte(@PathVariable Long id) throws NotFoundException
			{
				service.removeCompte(id);
			}
			
	

}

