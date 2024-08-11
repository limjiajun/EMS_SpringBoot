# Employee Management System

## Table of Contents
1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Project Directory Structure](#project-directory-structure)
5. [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Setup Instructions](#setup-instructions)
        1. [Generate the Project Using Spring Boot Initializr](#1-generate-the-project-using-spring-boot-initializr)
        2. [Import the Project into IntelliJ IDEA](#2-import-the-project-into-intellij-idea)
        3. [Configure the Database](#3-configure-the-database)
        4. [Build and Run the Application](#4-build-and-run-the-application)
        5. [Access the API Documentation](#5-access-the-api-documentation)
        6. [Test the API Endpoints](#6-test-the-api-endpoints)
6. [Advanced Requirements](#advanced-requirements)
    - [Security Implementation](#1-security-implementation)
    - [Testing](#2-testing)
    - [Exception Handling](#3-exception-handling)
    - [Data Validation](#4-data-validation)
    - [Audit Logging (AOP)](#5-audit-logging-aop)
    - [Performance (Cache)](#6-performance-cache)
7. [References](#references)

## Project Overview
The **Employee Management System** is a Java Spring Boot application designed to manage employees, departments, and projects within a company. It provides a RESTful API for performing CRUD operations on employee data, integrated with MySQL for data storage. This project also includes basic security with Spring Security, API documentation using Swagger, and performance optimization with caching.

## Features

- **CRUD Operations:** Create, Read, Update, and Delete operations for employees, departments, and projects.
- **API Security:** Basic authentication to secure the API endpoints.
- **API Documentation:** Interactive documentation using Swagger UI.
- **Data Validation:** Input validation using Spring's validation annotations.
- **Audit Logging:** Log CRUD operations with timestamps and user actions.
- **Performance Optimization:** Caching for frequently accessed data.

## Technologies Used

- **Java:** Programming language used for the application.
- **Spring Boot:** Framework for building the application.
- **Spring Data JPA:** ORM framework for data access.
- **Spring Security:** Security framework for authentication.
- **MySQL:** Relational database for data storage.
- **Swagger:** API documentation tool.
- **Postman:** Tool for testing API endpoints.
- **JUnit:** Framework for writing and running tests.

## Project Directory Structure
```
employee-management-system
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── example
│   │               └── employee_management_system
│   │                   ├── controller
│   │                   │   ├── DepartmentController.java
│   │                   │   ├── EmployeeController.java
│   │                   │   └── ProjectController.java
│   │                   │
│   │                   ├── DTO
│   │                   │   ├── DepartmentDTO.java
│   │                   │   └── EmployeeDTO.java
│   │                   │
│   │                   ├── exception
│   │                   │   ├── GlobalExceptionHandler.java
│   │                   │   └── ResourceNotFoundException.java
│   │                   │
│   │                   ├── model
│   │                   │   ├── Department.java
│   │                   │   ├── Employee.java
│   │                   │   └── Project.java
│   │                   │
│   │                   ├── repository
│   │                   │   ├── DepartmentRepository.java
│   │                   │   ├── EmployeeRepository.java
│   │                   │   └── ProjectRepository.java
│   │                   │
│   │                   ├── service
│   │                   │   ├── DepartmentService.java
│   │                   │   ├── EmployeeService.java
│   │                   │   └── ProjectService.java
│   │                   │
│   │                   ├── EmployeeManagementSystemApplication.java
│   │                   ├── ErrorResponse.java
│   │                   └── SecurityConfig.java
│   │
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── employee_management_system
│                       ├── controller
│   │                   │   ├── EmployeeControllerIntegrationTest.java
│   │                   │   ├── EmployeeTest.java
│   │                   ├── service
│   │                   │   ├── DepartmentServiceTest.java
│   │                   │   ├── EmployeeServiceTest.java
│   │                   │   └── ProjectServiceTest.java
│   │                   │
│   │                   ├── EmployeeManagementSystemApplicationTest.java
│   │                   └── SecurityConfig.java              
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or later
- MySQL Server
- MySQL Workbench (optional for managing the database)
- IntelliJ IDEA or any Java IDE
- Postman (for testing API endpoints)

### Setup Instructions

#### 1. Generate the Project Using Spring Boot Initializr

1. Go to [Spring Boot Initializr](https://start.spring.io/).
2. Configure the project with the following settings:
   - **Project:** Maven Project
   - **Language:** Java
   - **Spring Boot:** 2.7.9 (or the latest stable version)
   - **Group:** `com.example`
   - **Artifact:** `employee-management-system`
   - **Name:** `EmployeeManagementSystem`
   - **Description:** Employee Management System using Spring Boot
   - **Package Name:** `com.example.employeemanagementsystem`
   - **Packaging:** Jar
   - **Java:** 11 (or your preferred version)
3. Add the following dependencies:
   - **Spring Web**
   - **Spring Data JPA**
   - **MySQL Driver**
   - **Spring Security**
   - **Spring Boot DevTools** (optional for development)
4. Click **Generate** to download the project.

#### 2. Import the Project into IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Select **File > Open** and navigate to the directory where you downloaded the Spring Boot project.
3. Select the `pom.xml` file and click **Open**.
4. IntelliJ will import the project and download the necessary dependencies.

#### 3. Configure the Database

**Import Data using `ems.sql` file**

1. Open IntelliJ IDEA.
2. Select **File > Open** and navigate to the directory where you downloaded the Spring Boot project.
3. Select the `pom.xml` file and click **Open**.
4. IntelliJ will import the project and download the necessary dependencies.

#### 3. Configure the Database
## Import Data use ems.sql file 
1. **Create a Database:**
   - Open MySQL Workbench.
   - Connect to your MySQL server.
   - Execute the following SQL command to create a new database:

```sql
    CREATE DATABASE ems;
 ```
## Database Design and Structure

The database for this application is designed to manage employees, departments, and projects. It consists of the following tables:
### Tables
- **`department`**: Stores department information.
  - **Columns**: `id` (INT, Primary Key), `name` (VARCHAR)
- **`employee`**: Stores employee information.
  - **Columns**: `id` (INT, Primary Key), `name` (VARCHAR), `position` (VARCHAR), `department_id` (INT, Foreign Key)
- **`project`**: Stores project information.
  - **Columns**: `id` (INT, Primary Key), `name` (VARCHAR)
- **`employee_project`**: Stores the relationship between employees and projects.
- **Columns**: `employee_id` (INT, Foreign Key), `project_id` (INT, Foreign Key)


<p align="center">
  <img src="https://github.com/user-attachments/assets/4474d4c6-75bf-4aa8-a504-d882df9ea705" alt="uml" />
</p>

    
2. **Update `application.properties`:**
   - In `src/main/resources/application.properties`, add the following properties to configure your MySQL database connection:
     
```properties
# Application name for the Spring Boot application
spring.application.name=employee-management-system

# DATASOURCE Configuration
# JDBC URL for connecting to the MySQL database
spring.datasource.url=jdbc:mysql://localhost:3306/ems
# Username for database connection
spring.datasource.username=user
# Password for database connection
spring.datasource.password=1234

# Hibernate DDL Auto Configuration
# Defines the action to be taken with respect to the database schema
# Options: create, create-drop, validate, update
# "update" will automatically update the schema with changes
spring.jpa.hibernate.ddl-auto=update

# SQL Logging Configuration
# Enables logging of SQL statements executed by Hibernate
logging.level.org.hibernate.SQL=DEBUG
# Enables logging of the types of parameters and their values
logging.level.org.hibernate.type=TRACE

# SQL Initialization Mode
# Specifies when to initialize the database schema
spring.sql.init.mode=always

# JPA Show SQL Configuration
# Enables the display of SQL statements in the logs
spring.jpa.show-sql=true
# Formats SQL statements for better readability
spring.jpa.properties.hibernate.format_sql=true

# Springdoc Configuration
# Path for accessing the API documentation
springdoc.api-docs.path=/api-docs

# Logging Levels Configuration
# Sets the logging level for Spring Security (DEBUG level will provide detailed logs)
logging.level.org.springframework.security=DEBUG
# Sets the logging level for the specific package (DEBUG level for detailed logs)
logging.level.com.example.employee_management_system=DEBUG
```
##Replace `root` and `password` with your MySQL username and password.

#### 4. Build and Run the Application
Open a terminal in IntelliJ IDEA.
Run the following Maven command to build the project:
```
mvn clean install
```
After the build is successful, run the application:
```
mvn spring-boot:run
```
The application will start on 
[localhost:8080](http://localhost:8080).
```
http://localhost:8080/
```

#### 5. Access the API Documentation
Open your web browser.
Go to [SwaggerUI](http://localhost:8080)to access the Swagger UI documentation.
```
http://localhost:8080/swagger-ui.html
```
#### 6. Test the API Endpoints
#Use Postman to test the API endpoints. Here are some examples:
GET /api/employees
POST /api/employees
PUT /api/employees/{id}
DELETE /api/employees/{id}
cAPI Security
The API is secured with basic authentication. Use the following credentials to access the endpoints:
```
Username: user
Password: password
```
Example cURL command:
```
curl -u user:password http://localhost:8080/api/employees
```
#### Advanced Requirements (Question 2):
### 1. Security Implementation:
   - Secure the REST API using Spring Security. Implement basic authentication to protect the API endpoints.
```
curl -u user:password http://localhost:8080/api/employees 
```

### 2. Testing:
   - Write a unit and integration tests for the APIs using JUnit and Spring Test.

Go to [Unit testing and Integration testing on Spring boot project Using Junit](https://medium.com/@tariqbin.bits/unit-testing-and-integration-testing-on-spring-boot-project-using-junit-80de061f8e11).

### 3. Exception Handling:
   - Implement global exception handling with custom error messages for different exception types, enhancing the API's reliability and ease of use.

Go to [Spring Boot Global Exception Handler](https://medium.com/@aedemirsen/spring-boot-global-exception-handler-842d7143cf2a).

### 4. Data Validation:
- Use Spring's validation annotations to validate request payloads. 

Go to [Validating Objects (Entities) in Spring Boot](https://medium.com/@bouguern.mohamed/validating-objects-entities-in-spring-boot-9757fc01211f).

### 5. Audit Logging (AOP):
- Implement audit logging for an entity CRUD operation. Each log entry should capture the user's action, the affected entity, and the timestamp.

Go to [Auditing and Logging in Security](https://medium.com/@nagarjun_nagesh/auditing-and-logging-in-security-3738eafa6cb5).

### 6. Performance (Cache):
- Optimize API performance using Spring Boot's caching mechanisms. Demonstrate this by caching frequent database queries.

Go to [Caching Strategies for APIs: Improving Performance and Reducing Load](https://medium.com/@satyendra.jaiswal/caching-strategies-for-apis-improving-performance-and-reducing-load-1d4bd2df2b44).
## References
1. Spring Boot Documentation  
   [Documentation Overview](https://docs.spring.io/spring-boot/documentation.html).

2. Spring Security Documentation  
   [Spring Security](https://docs.spring.io/spring-security/reference/index.html).

3. Spring Data JPA Documentation  
   [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html).

4. MySQL Documentation  
   [MySQL Documentation](https://dev.mysql.com/doc/).

5. Swagger Documentation  
   [Swagger Documentation](https://swagger.io/docs/).

6. Swagger RESTful API Documentation Specification  
   [Swagger RESTful API](https://docs.swagger.io/spec.html).

7. JUnit 5 Documentation  
   [JUnit](https://junit.org/junit5/docs/current/user-guide/).

8. Spring AOP Documentation  
   [Aspect Oriented Programming with Spring](https://docs.spring.io/spring-framework/reference/core/aop.html).


## License
This project is licensed under the MIT License - see the LICENSE file for details.


This `README.md` file provides a comprehensive overview of the Employee Management System project, its features, setup instructions, and additional details to guide users and contributors.
