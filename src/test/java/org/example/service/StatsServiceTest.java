package org.example.service;

import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private StatsService statsService;

    @Test
    void ratioZeroWhenNoHumans() {
        when(repository.countByIsMutant(true)).thenReturn(5L);
        when(repository.countByIsMutant(false)).thenReturn(0L);
        var resp = statsService.getStats();
        assertEquals(5L, resp.getCount_mutant_dna());
        assertEquals(0L, resp.getCount_human_dna());
        assertEquals(0.0, resp.getRatio());
    }

    @Test
    void ratioCalculated() {
        when(repository.countByIsMutant(true)).thenReturn(4L);
        when(repository.countByIsMutant(false)).thenReturn(8L);
        var resp = statsService.getStats();
        assertEquals(0.5, resp.getRatio());
    }
}
