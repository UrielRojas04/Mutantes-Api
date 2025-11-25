package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MutantDetector {

    private static final int SEQ_LEN = 4;
    private static final Set<Character> VALID_BASES = Set.of('A','T','C','G');

    public boolean isMutant(String[] dna) {
        if (!isValidDna(dna)) return false;
        int n = dna.length;

        char[][] m = new char[n][];
        for (int i = 0; i < n; i++) {
            m[i] = dna[i].toCharArray();
        }

        int sequences = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                char base = m[r][c];
                // Horizontal
                if (c <= n - SEQ_LEN &&
                        base == m[r][c+1] && base == m[r][c+2] && base == m[r][c+3]) {
                    if (++sequences > 1) return true; }
                // Vertical
                if (r <= n - SEQ_LEN &&
                        base == m[r+1][c] && base == m[r+2][c] && base == m[r+3][c]) {
                    if (++sequences > 1) return true; }
                // Diagonal ↘
                if (r <= n - SEQ_LEN && c <= n - SEQ_LEN &&
                        base == m[r+1][c+1] && base == m[r+2][c+2] && base == m[r+3][c+3]) {
                    if (++sequences > 1) return true; }
                // Diagonal ↗
                if (r >= SEQ_LEN - 1 && c <= n - SEQ_LEN &&
                        base == m[r-1][c+1] && base == m[r-2][c+2] && base == m[r-3][c+3]) {
                    if (++sequences > 1) return true; }
            }
        }
        return false;
    }

    private boolean isValidDna(String[] dna) {
        if (dna == null || dna.length == 0) return false;
        int n = dna.length;
        for (String row : dna) {
            if (row == null || row.length() != n) return false;
            for (char ch : row.toCharArray()) {
                if (!VALID_BASES.contains(ch)) return false;
            }
        }
        return true;
    }
}
