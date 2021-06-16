package com.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VirementMultipleBeneficiaire implements Serializable{
	
    @EmbeddedId
    private VirementMultipleBenificiaireKey id = new VirementMultipleBenificiaireKey();
    
    @ManyToOne
    @MapsId("virementMultipleId")
    @JoinColumn(name = "virement_multiple_id")
    @JsonIgnore
    private VirementMultiple virementMultiple;
    
    @ManyToOne
    @MapsId("beneficiaireId")
    @JoinColumn(name = "beneficiaire_id")
    private Beneficiaire beneficiaire;
    
    Double montant;
}