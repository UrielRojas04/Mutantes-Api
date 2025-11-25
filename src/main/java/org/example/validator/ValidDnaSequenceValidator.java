package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Set<Character> VALID = Set.of('A','T','C','G');

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        if (value == null || value.length == 0) return false;
        int n = value.length;
        for (String row : value) {
            if (row == null || row.length() != n) return false;
            for (char ch : row.toCharArray()) {
                if (!VALID.contains(ch)) return false;
            }
        }
        return true;
    }
}
