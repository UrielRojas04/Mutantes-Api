package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceCacheTest {

    @Mock
    private MutantDetector detector;
    @Mock
    private DnaRecordRepository repository;
    @InjectMocks
    private MutantService service;

    @Test
    void secondCallUsesCache() {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        when(detector.isMutant(dna)).thenReturn(true);
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(DnaRecord.class))).thenAnswer(inv -> inv.getArgument(0));

        assertTrue(service.analyzeAndPersist(dna)); // primera llamada calcula
        assertTrue(service.analyzeAndPersist(dna)); // segunda llamada cache

        verify(detector, times(1)).isMutant(dna);
        verify(repository, times(1)).findByDnaHash(anyString());
        verify(repository, times(1)).save(any(DnaRecord.class));
    }
}
