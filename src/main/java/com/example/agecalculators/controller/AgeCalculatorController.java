package com.example.agecalculators.controller;

import com.example.agecalculators.model.AgeResponse;
import com.example.agecalculators.model.User;
import com.example.agecalculators.repository.UserRepository;
import com.example.agecalculators.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AgeCalculatorController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/calculate-age")
    public AgeResponse calculateAge(@RequestParam String dateOfBirth) {
        try {
            LocalDate dob = LocalDate.parse(dateOfBirth);
            return userService.calculateAge(dob);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato data non valido. Utilizzare YYYY-MM-DD.");
        }
    }

    // Endpoint per creare un nuovo utente e calcolare l'età
    @PostMapping("/users")
    public AgeResponse createUser(@RequestBody User user) {
        if (user.getDateOfBirth() == null || user.getFirstName() == null || user.getLastName() == null) {
            throw new IllegalArgumentException("Tutti i campi (firstName, lastName, dateOfBirth) sono obbligatori.");
        }

        AgeResponse ageResponse = userService.calculateAge(user.getDateOfBirth());

        user.setAge(ageResponse.getYears());
        userRepository.save(user);

        return ageResponse;
    }

    // Endpoint per ottenere tutti gli utenti
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Endpoint per calcolare l'età media degli utenti
    @GetMapping("/users/average-age")
    public AgeResponse getAverageAge() {
        Double average = userRepository.calculateAverageAge();
        if (average == null) {
            throw new IllegalArgumentException("Nessun utente presente per calcolare l'età media.");
        }
        return new AgeResponse(average.intValue(), 0, 0);
    }
}