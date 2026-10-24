# HospitALL

## A Modular Multi-Hospital Management System

HospitALL is a modular, Java-based hospital management system designed around a JavaFX desktop client, a Spring Boot backend, and MariaDB persistence.

The project's long-term objective is to support multiple hospitals operating through independent local databases, with secure data synchronization between hospital locations. The architecture is intended to evolve toward reliable hospital operations during network interruptions, subject to the implementation of offline capabilities and synchronization mechanisms.

**Project status:** Initial foundation and core application functionality are implemented. The JavaFX client supports account creation, login, and a Patient Management Dashboard connected to the backend. JWT-based authentication and protection of patient API endpoints have also been tested. The project is actively under development and is **not yet production-ready**.

## Technology Stack

| Component | Technology |
|---|---|
| Development JDK | Java 27 |
| Java compilation target | Java 21 |
| Desktop application | JavaFX |
| Backend framework | Spring Boot |
| Production database | MariaDB |
| Automated testing database | H2 in-memory database |
| Authentication | JWT-based authentication |
| Build system | Maven Wrapper |
| Architecture | Modular client-server architecture |

## Project Structure

HospitALL uses a multi-module Maven architecture consisting of a parent project and four modules.

```text
HospitALL/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .mvn/
├── .gitignore
├── README.md
├── hospitall-client/
├── hospitall-server/
├── hospitall-shared/
└── hospitall-sync/
```

| Module | Responsibility |
|---|---|
| `hospitall-client` | JavaFX desktop application and user interface |
| `hospitall-server` | Spring Boot backend, REST API, authentication, and database operations |
| `hospitall-shared` | Shared data-transfer objects, classes, and utilities |
| `hospitall-sync` | Foundation for future inter-hospital synchronization |
| Root project | Parent Maven configuration and module management |

## Implemented Features

### Phase 1 — Project Foundation

- Established the multi-module Maven project.
- Configured Java 21 as the compilation target.
- Configured JavaFX and the JavaFX Maven plugin.
- Preserved the Maven Wrapper for consistent project builds.
- Created the JavaFX client application entry point.
- Created the Spring Boot server application and health-check endpoint.
- Configured MariaDB for normal application operation.
- Configured H2 for automated testing.
- Established the initial synchronization module foundation.
- Verified the initial build and server health endpoint.

### Phase 2 — Core Application Functionality

#### 1. Account Creation

- Implemented the account-creation interface.
- Integrated account creation with the backend.
- Established the initial database-backed account workflow.

#### 2. Login and Authentication

- Implemented the JavaFX login interface.
- Integrated login with the backend authentication workflow.
- Implemented JWT-based authentication.
- Added security configuration for protected patient API endpoints.
- Verified that unauthenticated and invalid-token requests are rejected.
- Verified that an authenticated request with a valid JWT can access the protected patient API.

Authentication and authorization still require broader testing and security review before production deployment.

#### 3. Patient Management

- Implemented the Patient Management Dashboard.
- Connected the JavaFX client to the patient REST API.
- Implemented patient CRUD operations: Create, Read, Update, and Delete.
- Connected patient records to backend-managed database persistence.
- Verified that the authenticated client can retrieve patient records.

Further testing is required for validation, error handling, concurrent access, authorization boundaries, and recovery scenarios.

## System Architecture

HospitALL follows a client-server architecture.

```text
+---------------------------+
|    JavaFX Desktop Client  |
|                           |
| Login and Patient UI      |
+-------------+-------------+
              |
              | HTTP REST API
              | JWT Authentication
              v
+---------------------------+
|    Spring Boot Server     |
|                           |
| Authentication and API    |
| Business Logic            |
| Access Control            |
+-------------+-------------+
              |
              | Database Connection
              v
+---------------------------+
|          MariaDB          |
|                           |
| Persistent Hospital Data  |
+---------------------------+
```

The JavaFX client communicates with the Spring Boot backend through the REST API. The backend handles authentication, API requests, application logic, and database operations. The client should not connect directly to MariaDB.

The diagram describes the current core application architecture. Inter-hospital synchronization is a planned extension and is not represented as an already operational component.

## Authentication and Security

The current implementation uses JWT-based authentication to protect patient API endpoints.

Security principles for continued development include:

- Keep JWT signing secrets and database credentials outside source control.
- Use secure password storage with an appropriate password-hashing algorithm.
- Validate authentication tokens and reject invalid or expired tokens.
- Enforce authorization on the server rather than relying only on UI restrictions.
- Introduce role-based permissions for administrators, receptionists, doctors, accountants, and other authorized users as the corresponding features are developed.
- Use HTTPS and appropriate network access controls in deployed environments.
- Avoid logging passwords, authentication tokens, secrets, or sensitive patient information.
- Test authentication, authorization, session handling, and input validation before deployment.

**Important:** A successful login and a protected API endpoint do not, by themselves, establish that the complete application is production-secure.

## Database and Persistence

### MariaDB

MariaDB is configured as the primary database for normal server operation. Application data is managed through the Spring Boot backend.

The server's database connection should be configured using the project's supported configuration properties and environment variables. The database must be running and accessible before normal application operations can succeed.

The default database configuration and actual environment-variable names should be checked in the server's configuration files before deployment.

### H2

H2 is configured as an in-memory database for automated tests. Test data stored in an in-memory H2 database is temporary and is not intended to serve as production data.

### Database Reliability

Before production use, the project needs a documented database initialization and migration strategy, backup and restore procedures, data-integrity checks, and testing of recovery after failures.

## Build and Run Instructions

Run the following commands from the HospitALL project root.

### Prerequisites

- A compatible JDK installed.
- Java 21 compilation-target compatibility.
- Maven Wrapper files present in the project.
- MariaDB installed and configured for normal server operation.
- The required database and user created with appropriate permissions.
- Database credentials and the JWT signing secret supplied through secure local configuration or environment variables.

### 1. Build the Project

**Windows PowerShell:**

```powershell
cd C:\Users\Rana\Desktop\HospitALL
.\mvnw.cmd clean install -DskipTests
```

**Linux/macOS:**

```bash
./mvnw clean install -DskipTests
```

This compiles and installs the project modules while skipping automated test execution. A successful build with `-DskipTests` does not demonstrate that the tests pass.

### 2. Configure the Server

Before starting the server, configure MariaDB and the environment variables required by the current server configuration.

For example, if the server configuration supports these variable names, PowerShell can be used to set them for the current terminal session:

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:mariadb://localhost:3306/hospitall_db"
$env:SPRING_DATASOURCE_USERNAME = "your_database_user"
$env:SPRING_DATASOURCE_PASSWORD = "your_database_password"
$env:JWT_SECRET = "your_secure_secret_of_at_least_32_bytes"
```

Replace the example database values with your actual local configuration. Use a securely generated JWT signing secret and keep it private. The JWT secret must meet the application's configured requirements; use a securely generated value with sufficient cryptographic strength rather than a predictable phrase.

These variables apply to the current PowerShell session. They are examples, so verify that the variable names match the implementation in your server configuration and security code. Do not commit real credentials or secrets to Git.

### 3. Start the Spring Boot Server

**Windows PowerShell:**

```powershell
.\mvnw.cmd -pl hospitall-server spring-boot:run
```

**Linux/macOS:**

```bash
./mvnw -pl hospitall-server spring-boot:run
```

The server normally listens on port `8080`, unless the configuration specifies a different port. Keep the server running while using the desktop client.

### 4. Start the JavaFX Client

Open a second terminal at the project root.

**Windows PowerShell:**

```powershell
.\mvnw.cmd -pl hospitall-client javafx:run
```

**Linux/macOS:**

```bash
./mvnw -pl hospitall-client javafx:run
```

This launches the JavaFX desktop application using the configured JavaFX Maven plugin.

Start the backend first, then launch the client. Ensure the client is configured to connect to the correct backend address.

### 5. Run the Verification Build

To compile the project and run the configured verification lifecycle, use:

**Windows PowerShell:**

```powershell
.\mvnw.cmd clean verify
```

**Linux/macOS:**

```bash
./mvnw clean verify
```

Run this command after changes and review the test results. A successful build is useful verification, but it is not a substitute for functional, security, database, and deployment testing.

## Planned Development Roadmap

The following milestones describe intended future work; they should not be interpreted as completed features.

1. **Authentication and authorization:** Strengthen credential handling, token lifecycle management, role-based permissions, and security testing.
2. **Database design:** Refine entities, relationships, constraints, schema migrations, and data-integrity rules.
3. **Hospital-management modules:** Develop doctor management, appointments, treatments, billing, accounting, and additional required workflows.
4. **Role-specific dashboards:** Implement appropriate interfaces and server-enforced permissions for each user role.
5. **Multi-hospital support:** Define hospital identity, local database ownership, tenant boundaries, and cross-hospital access rules.
6. **Synchronization:** Implement secure inter-hospital communication, change tracking, retry mechanisms, duplicate prevention, conflict resolution, and synchronization monitoring.
7. **Offline operation:** Define which workflows can function without connectivity and how local changes are reconciled when connectivity returns.
8. **Deployment and reliability:** Develop installation procedures, environment-specific configuration, logging, monitoring, backups, restoration, and upgrade processes.
9. **Testing and quality assurance:** Expand unit, integration, security, concurrency, recovery, and end-to-end tests.

## Development Notes

- The development environment uses JDK 27, while the project targets Java 21. Avoid relying on APIs newer than Java 21 unless the target changes.
- Preserve the existing Maven Wrapper configuration unless a deliberate, separately reviewed upgrade is required.
- Keep database credentials, JWT signing secrets, private keys, and other sensitive configuration out of version control.
- Do not commit real patient records, production database dumps, or exported clinical information.
- Keep client, server, shared, and synchronization responsibilities separated.
- Review staged Git changes before committing or pushing.
- Run the build and relevant tests after code changes.
- Use synthetic data for development and testing whenever possible.

## Current Limitations

The project is still under active development. The following areas remain incomplete or require additional verification:

- Comprehensive role-based access control and security review.
- Full hospital-management workflows beyond the current patient-management functionality.
- Finalized database schema and migration strategy.
- Operational multi-hospital synchronization and conflict resolution.
- Clearly defined offline capabilities and recovery behavior.
- Production-grade deployment, monitoring, backup, and restoration procedures.
- Performance, concurrency, and reliability testing.
- Assessment of applicable healthcare privacy, security, and regulatory requirements.

## Disclaimer

HospitALL is a software development project and has not been validated for production healthcare operations. Do not use it to manage real patient data or rely on it for clinical workflows until appropriate security, privacy, access control, reliability, backup, recovery, and applicable regulatory requirements have been assessed and satisfied.

---

**Project:** HospitALL  
**Architecture:** JavaFX desktop client with Spring Boot backend  
**Primary database:** MariaDB  
**Testing database:** H2  
**Current milestone:** Foundation and initial patient-management functionality  
**Next milestones:** Security hardening, database design, role-specific workflows, and additional hospital-management modules
