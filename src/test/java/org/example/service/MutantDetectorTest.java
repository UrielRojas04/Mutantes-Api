package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach
    void setUp() { detector = new MutantDetector(); }

    @Test
    @DisplayName("Mutante con secuencias horizontal y diagonal")
    void mutantHorizontalDiagonal() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Humano sin secuencias suficientes")
    void humanNoSequences() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza matriz no NxN")
    void invalidNotSquare() {
        String[] dna = {"ATGC","CAGT","TTAT"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza caracteres inválidos")
    void invalidCharacters() {
        String[] dna = {"ATGX","CAGT","TTAT","AGAC"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Mutante con diagonales opuestas")
    void mutantBothDiagonals() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Mutante vertical simple")
    void mutantVertical() {
        String[] dna = {
                "AAGCTA",
                "AAGCTA",
                "AAGCTA",
                "AAGCTA",
                "CCCAGG",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Humano con una sola secuencia horizontal")
    void humanSingleHorizontal() {
        String[] dna = {
                "TTTTGA", // una sola secuencia horizontal
                "CAGTAC", // rompe posible columna de G
                "ATATGT",
                "AGACAG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Humano matriz mínima 4x4 sin secuencias")
    void humanMinSizeNoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Mutante con diagonal descendente única y otra vertical")
    void mutantDiagonalAndVertical() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAAG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza array vacío")
    void invalidEmpty() {
        String[] dna = {};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza fila null")
    void invalidRowNull() {
        String[] dna = {"ATGC","CAGT",null,"AGAC"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza minúsculas")
    void invalidLowercase() {
        String[] dna = {"atgc","cagt","ttat","agac"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Mutante early termination en matriz grande")
    void mutantEarlyTerminationLarge() {
        int n = 40;
        String[] dna = new String[n];
        for (int i = 0; i < n; i++) {
            dna[i] = "A".repeat(n);
        }
        // Muchas secuencias; debería ser mutante rápidamente
        assertTrue(detector.isMutant(dna));
    }
}
