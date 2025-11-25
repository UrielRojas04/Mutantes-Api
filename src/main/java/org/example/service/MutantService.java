package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.exception.DnaHashCalculationException;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository repository;
    // Caché en memoria para evitar recalcular y golpear BD reiteradamente.
    private final Map<String, Boolean> cache = new ConcurrentHashMap<>();

    public boolean analyzeAndPersist(String[] dna) {
        String hash = hashDna(dna);
        Boolean cached = cache.get(hash);
        if (cached != null) {
            return cached;
        }
        // Buscar en BD primero (deduplicación persistente)
        var existing = repository.findByDnaHash(hash);
        if (existing.isPresent()) {
            boolean found = existing.get().isMutant();
            cache.put(hash, found);
            return found;
        }
        boolean isMutant = mutantDetector.isMutant(dna);
        repository.save(new DnaRecord(hash, isMutant));
        cache.put(hash, isMutant);
        return isMutant;
    }

    private String hashDna(String[] dna) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            for (String row : dna) {
                md.update(row.getBytes());
            }
            return HexFormat.of().formatHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new DnaHashCalculationException("Error calculando hash", e);
        }
    }
}
