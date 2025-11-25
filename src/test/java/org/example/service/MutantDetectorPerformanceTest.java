package org.example.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MutantDetectorPerformanceTest {

    @Test
    @DisplayName("Early termination sobre matriz grande rápido")
    void earlyTerminationLargeMatrix() {
        int n = 200; // tamaño moderado para entorno CI
        String[] dna = new String[n];
        for (int i = 0; i < n; i++) {
            dna[i] = "A".repeat(n); // múltiples secuencias
        }
        MutantDetector detector = new MutantDetector();
        assertTimeout(Duration.ofMillis(500), () -> {
            assertTrue(detector.isMutant(dna));
        });
    }
}
