HospitALL
A Modular Multi-Hospital Management System
HospitALL is a modular Java-based hospital management system built around a JavaFX desktop client, a Spring Boot REST backend, and MariaDB persistence.

The long-term goal is to support multiple hospitals operating with independent local databases and secure synchronization between locations. Offline operation and inter-hospital synchronization remain future capabilities until their implementation and reliability have been verified.

Project status: Under active development and not production-ready. The current implementation includes the modular project foundation, account creation and login, JWT authentication, protected patient API endpoints, patient CRUD integration, automatic persistent JWT signing-key generation, and an Admin Dashboard whose design/integration is partially complete.

Technology Stack
Component	Technology
Development JDK	Java 27
Java compilation target	Java 21
Desktop application	JavaFX
Backend framework	Spring Boot
Primary database	MariaDB
Automated testing database	H2 in-memory database
Authentication	JWT-based authentication
Build system	Maven Wrapper
Architecture	Modular client-server architecture
Project Structure
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
Module	Responsibility
hospitall-client	JavaFX desktop application and user interface
hospitall-server	Spring Boot backend, REST API, authentication, and database operations
hospitall-shared	Shared data-transfer objects, classes, and utilities
hospitall-sync	Foundation for future inter-hospital synchronization
Root project	Parent Maven configuration and module management
Implemented Features
Phase 1 — Project Foundation
Established the multi-module Maven project.
Configured Java 21 as the compilation target.
Configured JavaFX and its Maven plugin.
Preserved the Maven Wrapper for consistent builds.
Created the JavaFX client application entry point.
Created the Spring Boot server application and health-check endpoint.
Configured MariaDB for normal application operation.
Configured H2 for automated tests.
Established the initial synchronization module foundation.
Phase 2 — Core Application Functionality
Account Creation and Login
Implemented the JavaFX account-creation interface.
Integrated account creation with the backend.
Implemented the JavaFX login interface and backend authentication workflow.
Added JWT-based authentication.
Automatic JWT Signing-Key Generation
The server is configured to generate a cryptographically secure JWT signing key automatically on first startup and persist it for subsequent startups.
Default storage location:
Windows: %USERPROFILE%\.hospitall\jwt.secret
Linux/macOS: ~/.hospitall/jwt.secret
Set JWT_KEY_PATH to configure the key storage location, according to the current implementation.
If JWT_SECRET is set, the server uses it instead of the generated persistent key for backward compatibility.
Keep the signing key private and out of source control. Do not delete or rotate the key casually: doing so can invalidate existing tokens or prevent previously issued tokens from validating.
API Security
Added security configuration for protected patient API endpoints.
Tested rejection of unauthenticated and invalid-token requests.
Tested access to the protected patient API using a valid JWT.
These checks do not constitute a complete production security audit. Token lifecycle, authorization boundaries, and all application endpoints still require broader testing.

Patient Management
Implemented the Patient Management Dashboard.
Connected the JavaFX client to the patient REST API.
Implemented patient CRUD operations: Create, Read, Update, and Delete.
Connected patient records to backend-managed database persistence.
Further validation is needed for error handling, concurrent access, authorization boundaries, and recovery scenarios.

Admin Dashboard
Admin Dashboard design and integration are partially complete.
The dashboard work is being developed alongside the existing authentication and patient-management functionality.
The dashboard should not be considered fully complete until all intended sections, backend integrations, permissions, and workflows have been implemented and verified.
System Architecture
HospitALL follows a client-server architecture:

+---------------------------+
|     JavaFX Desktop Client |
|                           |
| Login and application UI  |
+-------------+-------------+
              |
              | HTTP REST API
              | JWT authentication
              v
+---------------------------+
|      Spring Boot Server   |
|                           |
| Authentication and API    |
| Business logic             |
| Access control             |
+-------------+-------------+
              |
              | Database connection
              v
+---------------------------+
|          MariaDB          |
|                           |
| Persistent hospital data  |
+---------------------------+
The JavaFX client communicates with the Spring Boot backend through REST APIs. The backend handles authentication, API requests, business logic, and database operations. The client should not connect directly to MariaDB.

This diagram describes the current core architecture. Inter-hospital synchronization is a planned extension and is not yet represented as an operational component.

Authentication and Security
The current implementation uses JWT-based authentication to protect patient API endpoints. The server automatically generates and persists its JWT signing key unless an explicit JWT_SECRET override is configured.

Security requirements for continued development:

Keep JWT signing keys and database credentials out of source control.
Use an appropriate password-hashing algorithm; never store plaintext passwords.
Validate tokens and reject invalid or expired tokens.
Enforce authorization on the server, not only by hiding or showing UI controls.
Implement and test role-based permissions as the corresponding features are developed.
Use HTTPS and appropriate network access controls in deployed environments.
Avoid logging passwords, tokens, secrets, or unnecessary sensitive patient information.
Test authentication, authorization, session handling, input validation, and account lifecycle behavior before deployment.
Planned Staff Roles and Account Approval
The intended role model includes:

ADMIN
RECEPTIONIST
DOCTOR
ACCOUNTANT
The planned registration workflow will allow a person to request a non-admin role. After the first administrator is securely established, subsequent account requests should remain pending until an administrator approves or rejects them. Pending and rejected requests must not be able to log in or access protected APIs. Public registration must never allow a user to self-assign ADMIN.

Status: This workflow is a planned/in-progress enhancement and should not be treated as implemented until the backend enforcement, persistence, UI, and verification are complete.

Database and Persistence
MariaDB
MariaDB is configured as the primary database for normal server operation. Application data is managed through the Spring Boot backend.

The database must be running and accessible before normal server operations can succeed. Configure the database using the properties and environment variables supported by the current server configuration.

H2
H2 is configured as an in-memory database for automated tests. Data stored in an in-memory test database is temporary and is not production data.

Database Reliability
Before production use, the project needs a documented database initialization and migration strategy, backup and restore procedures, data-integrity checks, and recovery testing.

Build and Run Instructions
Run commands from the HospitALL project root.

Prerequisites
A compatible JDK installed.
Java 21 compilation-target compatibility.
Maven Wrapper files present in the project.
MariaDB installed and configured for normal server operation.
A database and database user with appropriate permissions.
Database credentials configured securely.
The server's JWT signing key can be generated automatically on first startup; set JWT_SECRET only if deliberately overriding that behavior.
1. Build the Project
Windows PowerShell

cd C:\Users\Rana\Desktop\HospitALL
.\mvnw.cmd clean install -DskipTests
Linux/macOS

./mvnw clean install -DskipTests
This cleans, compiles, packages, and installs the project modules while skipping automated test execution. A successful build with -DskipTests does not demonstrate that the tests pass.

2. Configure the Server
Set the database environment variables according to the server configuration. For example, if the project uses these names, Windows PowerShell can set them for the current terminal session:

$env:SPRING_DATASOURCE_URL = "jdbc:mariadb://localhost:3306/hospitall_db"
$env:SPRING_DATASOURCE_USERNAME = "your_database_user"
$env:SPRING_DATASOURCE_PASSWORD = "your_database_password"
Replace the example values with your actual local configuration. These variables apply to the current PowerShell session. Confirm the variable names against the server's configuration before use. Never commit real credentials or secrets to Git.

3. Start the Spring Boot Server
Windows PowerShell

.\mvnw.cmd -pl hospitall-server spring-boot:run
Linux/macOS

./mvnw -pl hospitall-server spring-boot:run
The server normally listens on port 8080, unless configured otherwise. Keep the server running while using the desktop client.

4. Start the JavaFX Client
Open a second terminal at the project root.

Windows PowerShell

.\mvnw.cmd -pl hospitall-client javafx:run
Linux/macOS

./mvnw -pl hospitall-client javafx:run
Start the backend first, then launch the client. Ensure the client is configured to connect to the correct backend address.

5. Run Verification
Windows PowerShell

.\mvnw.cmd clean verify
Linux/macOS

./mvnw clean verify
This runs the configured Maven verification lifecycle, including tests unless they are skipped or otherwise configured differently. Review test failures rather than routinely ignoring them. A successful build is not a substitute for functional, security, database, and deployment testing.

Planned Development Roadmap
These milestones describe intended future work and must not be interpreted as completed features.

Authentication and authorization: Complete security hardening, token lifecycle management, role-based permissions, and security testing.
Registration approval: Implement secure first-admin initialization and administrator approval/rejection of staff account requests.
Admin Dashboard: Complete the dashboard design, navigation, backend integration, and administrative workflows.
Database design: Refine entities, relationships, constraints, schema migrations, and data-integrity rules.
Hospital-management modules: Develop and integrate doctor management, appointments, treatments, billing, accounting, and additional workflows.
Role-specific dashboards: Implement appropriate interfaces and server-enforced permissions for each role.
Multi-hospital support: Define hospital identity, local database ownership, tenant boundaries, and cross-hospital access rules.
Synchronization: Implement secure inter-hospital communication, change tracking, retry mechanisms, duplicate prevention, conflict resolution, and monitoring.
Offline operation: Define supported offline workflows and reconciliation when connectivity returns.
Deployment and reliability: Develop installation procedures, environment-specific configuration, logging, monitoring, backups, restoration, and upgrade processes.
Testing and quality assurance: Expand unit, integration, security, concurrency, recovery, and end-to-end tests.
Development Notes
The development environment uses JDK 27, while the project targets Java 21. Avoid relying on APIs newer than Java 21 unless the target changes.
Preserve the existing Maven Wrapper configuration unless a deliberate, separately reviewed upgrade is required.
Keep database credentials, JWT signing keys, private keys, and other sensitive configuration out of version control.
Do not commit real patient records, production database dumps, or exported clinical information.
Keep client, server, shared, and synchronization responsibilities separated.
Review staged Git changes before committing or pushing.
Run the build and relevant tests after code changes.
Use synthetic data for development and testing whenever possible.
Current Limitations
The project is still under active development. Areas that remain incomplete or require additional verification include:

Comprehensive role-based access control and security review.
Registration approval workflow and secure first-admin initialization.
Completion of the Admin Dashboard design and integration.
Full hospital-management workflows beyond the current patient-management functionality.
Finalized database schema and migration strategy.
Operational multi-hospital synchronization and conflict resolution.
Clearly defined offline capabilities and recovery behavior.
Production-grade deployment, monitoring, backup, and restoration procedures.
Performance, concurrency, and reliability testing.
Assessment of applicable healthcare privacy, security, and regulatory requirements.
Disclaimer
HospitALL is a software development project and has not been validated for production healthcare operations. Do not use it to manage real patient data or rely on it for clinical workflows until appropriate security, privacy, access control, reliability, backup, recovery, and applicable regulatory requirements have been assessed and satisfied.

Project: HospitALL
Architecture: JavaFX desktop client with Spring Boot backend
Primary database: MariaDB
Testing database: H2
Current milestone: Core patient-management functionality, persistent automatic JWT signing-key generation, and partial Admin Dashboard design/integration
Next milestones: Complete admin workflows, role-based access control, registration approval, database design, and additional hospital-management modules