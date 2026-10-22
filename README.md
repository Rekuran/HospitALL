# HospitALL

## A Modular Multi-Hospital Management System

HospitALL is a modular Java desktop application designed to support hospital management and future secure data synchronization between hospitals. Its long-term goal is to enable hospitals to operate locally, maintain availability during network interruptions where supported, and synchronize data securely between hospital locations.

**Project status:** Phase 1 (Foundation) completed. Initial application functionality—including account creation, login, and the Patient Management Dashboard—has been implemented and tested. The project remains under active development and is not yet production-ready.

## Technology Stack

- **Development JDK:** Java 27
- **Java compilation target:** Java 21
- **Desktop application:** JavaFX
- **Backend:** Spring Boot
- **Production database:** MariaDB
- **Testing database:** H2 in-memory database
- **Build system:** Maven Wrapper

## Project Structure

HospitALL uses a multi-module Maven architecture consisting of a parent project and four modules.

```text
HospitALL/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .mvn/
├── hospitall-client/
├── hospitall-server/
├── hospitall-shared/
└── hospitall-sync/
```

| Module | Responsibility |
|---|---|
| `hospitall-client` | JavaFX desktop application and user interface |
| `hospitall-server` | Spring Boot backend and REST API |
| `hospitall-shared` | Shared DTOs, classes, and utilities |
| `hospitall-sync` | Foundation for future hospital synchronization |
| Root project | Parent Maven configuration and module management |

## Completed Work

### Phase 1 — Foundation

- Established the multi-module Maven project.
- Configured Java 21 as the compilation target.
- Configured JavaFX and the JavaFX Maven plugin.
- Preserved the Maven Wrapper.
- Created the JavaFX application entry point.
- Created the Spring Boot application entry point and health-check endpoint.
- Configured MariaDB for normal operation and H2 for automated testing.
- Established the initial synchronization module foundation.
- Verified the initial build and server health endpoint.

### Phase 2 — Initial Application Functionality

#### 1. Account Creation

- Implemented the account-creation user interface.
- Added account creation functionality and integration with the backend.
- Connected account creation to the application's database-backed workflow.

#### 2. Login

- Implemented the login user interface.
- Connected the login flow to the backend authentication workflow.
- Established the initial authentication functionality.

#### 3. Patient Management Dashboard

- Implemented the Patient Management Dashboard.
- Integrated the JavaFX client with the patient REST API.
- Implemented patient CRUD operations (Create, Read, Update, and Delete).
- Connected patient records to persistent database storage through the backend.

The account creation, login, and Patient Management Dashboard have been reported as implemented and tested. Further verification and security hardening are still required before production use.

## Database and Architecture

HospitALL uses a client-server architecture:

- The JavaFX client provides the desktop interface.
- The Spring Boot backend handles API requests and database operations.
- MariaDB is configured for normal application operation.
- H2 provides an in-memory database for automated tests.

The JavaFX client should communicate with the backend through its API rather than connecting directly to MariaDB.

The long-term architecture aims to support multiple hospitals with local databases and secure synchronization. Full synchronization, offline conflict resolution, and multi-hospital consistency are not yet implemented.

## Build and Run Instructions

Run the following commands from the HospitALL project root.

### Prerequisites

- A compatible JDK installed.
- The configured Java 21 compilation target.
- MariaDB installed and configured for normal server operation.
- Database credentials supplied through environment variables or secure local configuration.

### 1. Compile and Install the Project

```bash
./mvnw clean install -DskipTests
```

On Windows PowerShell, use:

```powershell
.\mvnw.cmd clean install -DskipTests
```

This builds and installs the Maven modules while skipping test execution. It does **not** verify that automated tests pass.

### 2. Run the Spring Boot Server

```bash
./mvnw -pl hospitall-server spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd -pl hospitall-server spring-boot:run
```

The server uses the configured Spring Boot application settings and normally starts on port `8080`, unless the configuration specifies a different port.

Ensure MariaDB is running and the required database and credentials are configured before starting the server.

Keep the server terminal open while using the client.

### 3. Run the JavaFX Client UI

```bash
./mvnw javafx:run -pl hospitall-client
```

On Windows PowerShell:

```powershell
.\mvnw.cmd javafx:run -pl hospitall-client
```

The command launches the HospitALL desktop interface using the configured JavaFX Maven plugin.

Start the Spring Boot server first, then launch the client in a separate terminal.

### 4. Run the Full Verification Build

To compile the modules and run the configured automated tests, use:

```bash
./mvnw clean verify
```

On Windows PowerShell:

```powershell
.\mvnw.cmd clean verify
```

Use this command when you want to verify the project rather than only compile and install it.

## Planned Development

The next development milestones include:

1. **Authentication and authorization hardening** — validate credential handling, secure password storage, and enforce role-based access to protected endpoints.
2. **Database design** — refine hospital-management entities, relationships, schema migrations, and data integrity.
3. **Hospital-management modules** — develop appointments, doctor management, treatments, billing, and other required modules incrementally.
4. **UI improvements** — refine layouts, styling, navigation, validation, and usability after core functionality stabilizes.
5. **Synchronization foundation** — define hospital identity, secure inter-hospital communication, change tracking, conflict resolution, and offline retry behavior.
6. **Deployment and reliability** — develop backup, recovery, configuration, logging, and installation procedures.

## Development Notes

- The development environment uses JDK 27, while the project targets Java 21. Avoid APIs introduced after Java 21 unless the target requirements change.
- Keep database credentials and other secrets in environment variables or secure local configuration.
- Never commit passwords, private keys, production credentials, or real patient data to version control.
- Keep the client, server, shared, and synchronization responsibilities separated.
- Test changes before deploying them to a real hospital environment.

## Current Limitations

Although initial account creation, login, and patient-management functionality is implemented, the following areas still require development or verification:

- Complete role-based access control and authentication security.
- Complete hospital-management database schema and migration strategy.
- Appointment, doctor, treatment, and billing modules.
- Actual inter-hospital synchronization and conflict resolution.
- Production deployment, backup, recovery, monitoring, and security hardening.
- Validation against healthcare privacy, security, reliability, and regulatory requirements.

## Disclaimer

HospitALL is an ongoing software development project. It has not been validated for production healthcare workflows or real patient data. Appropriate security, privacy, reliability, backup, recovery, and regulatory requirements must be addressed before real-world deployment.

---

**Project:** HospitALL  
**Architecture:** Modular JavaFX client with Spring Boot backend  
**Current milestone:** Foundation and initial application functionality  
**Next milestone:** Authentication hardening, database design, and additional hospital-management modules