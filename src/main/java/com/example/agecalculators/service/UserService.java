package com.example.agecalculators.service;

import com.example.agecalculators.model.AgeResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class UserService {
    public AgeResponse calculateAge(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        if (dateOfBirth.isAfter(now)) {
            throw new IllegalArgumentException("La data di nascita non può essere nel futuro.");
        }
        Period period = Period.between(dateOfBirth, now);
        return new AgeResponse(period.getYears(), period.getMonths(), period.getDays());
    }
}
