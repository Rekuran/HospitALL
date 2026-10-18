# HospitALL

**A Modular Multi-Hospital Management System**

HospitALL is an early-stage Java desktop application project designed to support hospital management and future secure data synchronization between hospitals. The long-term goal is to support local hospital operations, including offline availability, with synchronization between hospital locations.

> **Project status:** Phase 1 completed — foundational architecture established and initial build verified. Not yet ready for production use.

## Technology Stack

- **Development JDK:** Java 27
- **Java compilation target:** Java 21
- **Desktop application:** JavaFX
- **Backend:** Spring Boot
- **Production database configuration:** MariaDB
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
| `hospitall-client` | JavaFX desktop application |
| `hospitall-server` | Spring Boot backend and health endpoint |
| `hospitall-shared` | Shared classes and utilities |
| `hospitall-sync` | Foundation for future hospital synchronization |
| Root project | Parent Maven configuration and module management |

## Phase 1 — Completed Work

### 1. Project foundation

- Established the multi-module Maven project.
- Configured Java 21 as the compilation target.
- Configured JavaFX and the JavaFX Maven plugin.
- Preserved the Maven Wrapper for project builds.

### 2. JavaFX client

- Created the JavaFX application entry point.
- Confirmed that the desktop application launches successfully.

The client currently displays a basic placeholder window. Functional screens and navigation have not yet been implemented.

### 3. Spring Boot server

- Created the Spring Boot application entry point.
- Implemented a health-check endpoint.
- Configured an H2 testing profile.
- Verified the health endpoint using an automated test.

### 4. Database configuration

- Prepared MariaDB datasource configuration using environment variables.
- Included H2 for automated testing.

The hospital-management database schema, entities, and migration scripts are not yet implemented.

### 5. Synchronization foundation

- Established the `hospitall-sync` module.
- Created an initial foundation class for future development.

Actual inter-hospital communication, synchronization, and conflict resolution are not implemented.

## Build and Verification

The following command was run manually and completed successfully:

```powershell
.\mvnw.cmd clean verify
```

**Verified results:**
- All five Maven projects completed successfully.
- The server health test passed: 1 test, 0 failures, 0 errors.
- The JavaFX client window opened successfully.

### Launch the JavaFX client

On Windows, run this command from the project root:

```powershell
.\mvnw.cmd -pl hospitall-client javafx:run
```

The command uses the Maven Wrapper and the project's configured JavaFX setup.

## Current Limitations

- No functional login screen or dashboard.
- No authentication or role-based authorization.
- No patient registration, appointments, treatments, or billing functionality.
- No completed hospital-management database schema.
- No implemented hospital-to-hospital synchronization.
- No production-ready security, deployment, backup, or recovery setup.

## Planned Development

The next development phase is expected to focus on:

1. Building the JavaFX login screen, layouts, styling, and dashboard structure.
2. Implementing authentication and role-based access control.
3. Designing database entities and migration scripts.
4. Establishing secure communication and synchronization foundations.
5. Developing hospital-management modules incrementally.

## Development Notes

JDK 27 is the current development environment, while the project targets Java 21 for compilation. To maintain Java 21 runtime compatibility, avoid relying on APIs introduced after Java 21 unless the project's target requirements change.

Database credentials and other secrets must be supplied through local environment variables or secure configuration. Do not commit passwords, private keys, or production credentials to version control.

## Disclaimer

HospitALL is an ongoing software development project. It has not yet been validated for production healthcare workflows or real patient data. Appropriate security, privacy, reliability, and regulatory requirements must be addressed before real-world deployment.

---

**Project:** HospitALL  
**Current milestone:** Phase 1 — Foundation  
**Next milestone:** Phase 2 — UI foundation, authentication, and database design