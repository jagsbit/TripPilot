# TripMind

**TripMind** is a multi-agent AI travel planner built with **Java, Spring Boot, and Spring AI**.

It uses specialized agents to handle different parts of trip planning:

- ✈️ **Flight Agent** — searches and compares flights
- 🏨 **Hotel Agent** — searches and compares hotels
- 🗺️ **Itinerary Agent** — creates a day-wise itinerary
- 💬 **Final Response Agent** — combines the results into the final response

Agents communicate through a shared `TravelState`, while **PostgreSQL** is used for long-term memory such as conversation history and user preferences.

## Architecture

```text
User
  |
  v
Orchestrator
  |
  +--> Flight Agent ----> Flight API
  |
  +--> Hotel Agent -----> Search/Hotel API
  |
  +--> Itinerary Agent -> Maps/Search API
  |
  v
Shared TravelState
  |
  v
Final Response Agent
  |
  v
User

PostgreSQL --> Long-term memory
```

## Tech Stack

- Java 17+
- Spring Boot
- Spring AI
- PostgreSQL
- Maven
- LLM API
- External travel/search APIs

## How to Run

### 1. Clone the repository

```bash
git clone <repository-url>
cd tripmind-ai
```

### 2. Create PostgreSQL database

```sql
CREATE DATABASE tripmind;
```

### 3. Configure environment variables

Set the required API keys:

```text
LLM_API_KEY=your_api_key
TAVILY_API_KEY=your_api_key
FLIGHT_API_KEY=your_api_key
GOOGLE_MAPS_API_KEY=your_api_key
```

Configure the database in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tripmind
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 4. Run the application

```bash
mvn spring-boot:run
```

The application will start at:

```text
http://localhost:8080
```

## Project Structure

```text
src/main/java/com/example/tripmind/
├── agent/
│   ├── FlightAgent.java
│   ├── HotelAgent.java
│   ├── ItineraryAgent.java
│   └── FinalResponseAgent.java
├── orchestration/
│   └── TravelOrchestrator.java
├── state/
│   └── TravelState.java
├── tools/
│   ├── FlightTools.java
│   ├── HotelTools.java
│   └── MapTools.java
└── controller/
    └── TravelController.java
```

