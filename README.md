// README.md
# Weather API

## Introduction
This project is a Spring Boot-based RESTful API that provides weather data by integrating with OpenWeatherMap. The API follows best practices, including SOLID principles, proper layering, and security measures such as API key enforcement and rate limiting.

## Features
- REST API with proper layering (Controller, Service, Repository)
- Fetches weather data from OpenWeatherMap API
- Stores fetched weather data in an H2 in-memory database
- Implements API key-based authentication
- Rate limiting: Each API key allows up to 5 requests per hour
- Returns only the weather description field

## Implementation Details
- **Spring Boot 2.x** is used for application development.
- **H2 Database** is used for in-memory persistence.
- **Spring Data JPA** manages database interactions.
- **RestTemplate** is used to call the OpenWeatherMap API.
- **Exception Handling** ensures proper error messages for invalid requests.

## How to Run
1. Clone this repository:
   ```sh
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```sh
   cd WeatherReport
   ```
3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```
4. The application will start on `http://localhost:8080`.

## API Endpoints
- **GET /api/weather?city={city}&country={country}**
    - Headers: `API-Key: {your-api-key}`
    - Response: `{ "city": "London", "country": "UK", "description": "clear sky" }`

## Assumptions & Trade-offs
- This project does not handle API key generation or distribution.
- The API keys are stored in config for simplicity.
- Rate limiting is handled in-memory and resets after application restart.

## Dependencies (Defined in `pom.xml`)
- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `h2`
- `spring-boot-starter-test`

---
