package com.example.agecalculators;

import com.example.agecalculators.model.User;
import com.example.agecalculators.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Period;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AgeCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser_ValidUser() throws Exception {
        User validUser = new User("Giuseppe", "Verdi", LocalDate.of(1985, 10, 15));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.years").exists())
            .andExpect(jsonPath("$.months").exists())
            .andExpect(jsonPath("$.days").exists());
    }

    @Test
    public void testCreateUser_DateOfBirthInFuture() throws Exception {
        User futureUser = new User("Anna", "Bianchi", LocalDate.of(2030, 5, 20));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(futureUser)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("La data di nascita non puÃ² essere nel futuro."));
    }

    @Test
    public void testCreateUser_MissingFields() throws Exception {
        User invalidUser = new User("Luca", "", null);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Tutti i campi (firstName, lastName, dateOfBirth) sono obbligatori."));
    }

    @Test
    public void testCalculateAge_InvalidDate() throws Exception {
        mockMvc.perform(get("/api/calculate-age")
                .param("dateOfBirth", "invalid-date"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.TEXT_PLAIN))
            .andExpect(content().string("Formato data non valido. Utilizzare YYYY-MM-DD."));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Popola il database con un utente
        userRepository.save(new User("Mario", "Rossi", LocalDate.of(1990, 5, 15)));

        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].firstName").value("Mario"))
            .andExpect(jsonPath("$[0].lastName").value("Rossi"))
            .andExpect(jsonPath("$[0].dateOfBirth").value("1990-05-15"));
    }

    @Test
    public void testCalculateAge_ValidDate() throws Exception {
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthDate, now);

        mockMvc.perform(get("/api/calculate-age")
                .param("dateOfBirth", "1990-05-15"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.years").value(period.getYears()))
            .andExpect(jsonPath("$.months").value(period.getMonths()))
            .andExpect(jsonPath("$.days").value(period.getDays()));
    }

    @Test
    public void testGetAverageAge() throws Exception {
        LocalDate dob1 = LocalDate.of(1990, 5, 15);
        LocalDate dob2 = LocalDate.of(1985, 10, 15);

        // Calcola età attese
        int expectedAge1 = Period.between(dob1, LocalDate.now()).getYears();
        int expectedAge2 = Period.between(dob2, LocalDate.now()).getYears();
        int expectedAverage = (expectedAge1 + expectedAge2) / 2;

        // Inserimento tramite endpoint
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new User("Mario", "Rossi", dob1))))
            .andExpect(status().isOk());

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new User("Giuseppe", "Verdi", dob2))))
            .andExpect(status().isOk());

        // Verifica l'età media calcolata
        mockMvc.perform(get("/api/users/average-age"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.years").value(expectedAverage))
            .andExpect(jsonPath("$.months").value(0))
            .andExpect(jsonPath("$.days").value(0));
    }
}
