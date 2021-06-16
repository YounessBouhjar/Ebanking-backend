package com.backend.controllers;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.entities.VirementMultiple;
import com.backend.exceptions.AlreadyExistsException;
import com.backend.services.VirementMultipleService;


@RestController
@CrossOrigin
@RequestMapping
public class VirementMultipleController {
	
    @Autowired
    private VirementMultipleService virementMultipleService;

    //POST
		    @PostMapping("virement/multiple")
		    public VirementMultiple saveVirementMultiple(@RequestBody VirementMultiple virement) throws AlreadyExistsException, Exception {
		        return virementMultipleService.saveVirementMultiple(virement);
		    }
    
	//GET	    
		    @GetMapping("virement/multiple")
			@ResponseStatus(HttpStatus.OK)
		    public Collection<VirementMultiple> getCustomers(){
		    	return virementMultipleService.getVirementMultiples();
		    }
		    
		    @GetMapping("virement/multiple/{id}")
			@ResponseStatus(HttpStatus.OK)
			public VirementMultiple getRecuVirementPDF(@PathVariable(name="id") Long id) throws IOException
			{
				return virementMultipleService.getVirementMultipleById(id);
			}
			
}