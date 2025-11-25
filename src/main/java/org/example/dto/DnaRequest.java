package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validator.ValidDnaSequence;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para verificar ADN mutante")
public class DnaRequest {
    @NotNull(message = "dna no puede ser null")
    @NotEmpty(message = "dna no puede estar vacío")
    @ValidDnaSequence
    private String[] dna;
}
