# Age Calculator Service

![Java](https://img.shields.io/badge/Java-8-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.x-green)
![Maven](https://img.shields.io/badge/Maven-Build-orange)
![H2 Database](https://img.shields.io/badge/H2%20Database-In--Memory-lightgrey)

## Descrizione del Progetto

**Age Calculator Service** è un microservizio sviluppato in Java con Spring Boot che permette di calcolare l'età di una persona in base alla sua data di nascita. Oltre al calcolo dell'età, il servizio include funzionalità per:

- Salvare informazioni sugli utenti (nome, cognome e data di nascita) in un database H2 in memoria.
- Recuperare una lista di utenti salvati.
- Calcolare l'età media degli utenti memorizzati nel database.

### Obiettivi del Progetto

- **Praticità**: Offrire un servizio veloce e intuitivo per il calcolo dell'età.
- **Modularità**: Consentire l'uso del microservizio come una "black-box" in altre applicazioni.
- **Scalabilità**

---

## Funzionalità

1. **Calcolo dell'Età**:
    - Endpoint per calcolare l'età a partire da una data di nascita fornita.

2. **Gestione Utenti**:
    - Creazione di nuovi utenti con nome, cognome e data di nascita.
    - Recupero di tutti gli utenti salvati.

3. **Analisi Statistiche**:
    - Calcolo dell'età media degli utenti salvati nel database.

---

`#### Esempio

GET http://localhost:8080/api/calculate-age?dateOfBirth=1990-05-15 `

**Risposta:**

`{
    "years": 34,
    "months": 8,
    "days": 8
}`

### Creazione di un Utente

**POST** `/api/users`

-   **Body**:

`{
    "firstName": "Mario",
    "lastName": "Rossi",
    "dateOfBirth": "1990-05-15"
}`

**Risposta:**

`{
    "id": 1,
    "firstName": "Mario",
    "lastName": "Rossi",
    "dateOfBirth": "1990-05-15"
}`

### Recupero di Tutti gli Utenti

**GET** `/api/users`

#### Esempio

`GET http://localhost:8080/api/users`

**Risposta:**

`[
    {
        "id": 1,
        "firstName": "Mario",
        "lastName": "Rossi",
        "dateOfBirth": "1990-05-15"
    }
]`

### Calcolo dell'Età Media

**GET** `/api/users/average-age`

#### Esempio

`GET http://localhost:8080/api/users/average-age`

**Risposta:**

`{
    "years": 30,
    "months": 6,
    "days": 15
}`

* * * * *

Tecnologie Utilizzate
---------------------

-   **Java 8**: Linguaggio di programmazione principale.
-   **Spring Boot 2.7.x**: Framework per la costruzione del microservizio.
-   **H2 Database**: Database in memoria per il testing e la memorizzazione temporanea dei dati.
-   **Maven**: Strumento di build per la gestione delle dipendenze.
-   **Swagger/OpenAPI**: Documentazione interattiva per l'API.

Configurazione del Progetto
---------------------------

### Prerequisiti

-   **Java 8** o versioni successive installate.
-   **Maven** installato (opzionale se si usa l'IDE).
-   **IDE** come IntelliJ IDEA (consigliato).

### Passaggi per l'Esecuzione

1.  **Clona il repository:**

    `git clone https://github.com/tuo-username/age-calculator-service.git`

2.  **Accedi alla directory del progetto:**

    `cd age-calculator-service`

3.  **Avvia l'applicazione:**

    `mvn spring-boot:run`

4.  **Apri il browser e accedi alla documentazione Swagger:**

    `http://localhost:8080/swagger-ui/`

* * * * *

Miglioramenti Futuri
--------------------

-   **Integrazione con un Database Persistente**:

    -   Migrazione da H2 a un database come MySQL o PostgreSQL per la memorizzazione dei dati.
-   **Autenticazione e Autorizzazione**:

    -   Implementazione di meccanismi di sicurezza per proteggere gli endpoint (es. OAuth2, JWT).
-   **Gestione degli Errori Avanzata**:

    -   Aggiungere un sistema di logging e tracciamento degli errori.
-   **Supporto per Lingue Multiple**:

    -   Internazionalizzazione (i18n) per rendere l'applicazione disponibile in più lingue.
-   **Front-End**:

    -   Creazione di un'interfaccia utente per interagire con il servizio in modo visuale.

* * * * *

Contributi
----------

Contributi al progetto sono benvenuti! Per contribuire:

1.  **Fai un fork del repository.**

2.  **Crea un branch per la tua feature:**

    `git checkout -b feature-nome-feature`

3.  **Fai le modifiche e committa:**

    `git commit -m "Descrizione della tua modifica"`

4.  **Manda una pull request.**

* * * * *

Contatti
--------

Per domande o suggerimenti, [contattami su GitHub](https://github.com/ddiseri0).
