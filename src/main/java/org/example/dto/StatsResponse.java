package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta de estadísticas de ADN")
public class StatsResponse {
    private long count_mutant_dna;
    private long count_human_dna;
    private double ratio;
}
