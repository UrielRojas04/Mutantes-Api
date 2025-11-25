package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private MutantDetector detector;

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private MutantService service;

    @Test
    void persistNewMutant() {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        when(detector.isMutant(dna)).thenReturn(true);
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(DnaRecord.class))).thenAnswer(inv -> inv.getArgument(0));
        boolean result = service.analyzeAndPersist(dna);
        assertTrue(result);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void skipPersistExistingHash() {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        // No debe invocarse detector.isMutant porque la BD ya conoce el hash.
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(new DnaRecord("hash", true)));
        boolean result = service.analyzeAndPersist(dna);
        assertTrue(result);
        verify(repository, never()).save(any());
        verify(detector, never()).isMutant(dna);
    }

    @Test
    void persistHuman() {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGACGG","GCGTCA","TCACTG"};
        when(detector.isMutant(dna)).thenReturn(false);
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(DnaRecord.class))).thenAnswer(inv -> inv.getArgument(0));
        boolean result = service.analyzeAndPersist(dna);
        assertFalse(result);
        verify(repository).save(any(DnaRecord.class));
    }
}
