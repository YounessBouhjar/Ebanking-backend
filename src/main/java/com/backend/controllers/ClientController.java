package com.backend.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.entities.*;
import com.backend.exceptions.*;
import com.backend.services.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ClientController {
	
	
	ClientService service;
	
	@Autowired
	public ClientController(ClientService service) {
		
		this.service=service;
	}
	
	//GET
			@GetMapping("/clients")
			@ResponseStatus(HttpStatus.OK)
			public List<Client> getClients(@RequestParam(name="id", required=false) Long id) throws NotFoundException
			{
				return service.getClients(id);
			}
			
			
			@GetMapping("/client/username/{username}")
			@ResponseStatus(HttpStatus.OK)
			public Client getByUsername(@PathVariable(name="username") String username)
			{
				return service.getByUsername(username);
			}
			
			
			
			@GetMapping("/client/{id}/comptes")
			@ResponseStatus(HttpStatus.OK)
			public List<Compte> getComptes(@PathVariable(name="id") Long id) throws NotFoundException
			{
				return service.getComptes(id);
			}
			@GetMapping("/client/{id}/benef")
			@ResponseStatus(HttpStatus.OK)
			public List<Beneficiaire> getBeneficiaires(@PathVariable(name="id") Long id) throws NotFoundException
			{
				return service.getBeneficiaires(id);
			}
		
		
		//POST
			
			@PostMapping("/clients")
			@ResponseStatus(HttpStatus.CREATED)
			public void addClient(@RequestBody Client client)  throws AlreadyExistsException
			{
				service.addClient(client);
			}
		
		
		
		//PUT
			
			@PutMapping("/client/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void updateClient(@PathVariable(name="id") Long id , @RequestBody(required=false) Client client)  throws NotFoundException, AlreadyExistsException
			{
				service.updateClient(id,client);
			}
	

			@PutMapping("/client/update")
			public ResponseEntity<Client> updateClient(@RequestBody Client client){
				Client newClient = service.updateClientNew(client);
				return new ResponseEntity<>(newClient,HttpStatus.CREATED);
				
			}
			
		//DELETE
			
			@DeleteMapping("/client/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void deleteClient(@PathVariable(name="id") Long id) throws NotFoundException
			{
				service.removeClient(id);
			}
			
	

}

