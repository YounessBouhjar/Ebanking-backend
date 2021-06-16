package com.backend.entities;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VirementMultipleBenificiaireKey implements Serializable {
    private Long virementMultipleId;
    private Long beneficiaireId;

}