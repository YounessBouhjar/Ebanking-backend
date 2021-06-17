package com.backend.entities;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VirementMultiple extends VirementSuperClass {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long virementMultipleId;
    
    @OneToMany(mappedBy = "virementMultiple", cascade = CascadeType.ALL)
    private Collection<VirementMultipleBeneficiaire> vmb = new ArrayList<>();
    
    @ManyToOne
	Client client;
}