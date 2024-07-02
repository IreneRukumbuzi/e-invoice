# E-Invoice

## Overview
The E-invoice Application is a web-based platform designed to manage user invoices. The application allows users to sign up, log in, and be able to create, read, edit and delete invoices.

## Features
- **User Registration**: Allows new users to create an account.
- **User Login**: Enables existing users to log in to the application.
- **Invoices Management**: Users can view and update their invoices.
- **Authentication**: Secure login and registration using JWT (JSON Web Tokens).

## Technologies Used

### Backend
- Spring Boot
- Spring Security
- JWT for authentication (Token)
- Postgres for database
- Lombok

## Prerequisites

- Java JDK 11 or higher
- PostgreSQL

## Getting Started

### Backend Setup
1. Clone the repository:
    ```bash
    git clone https://github.com/IreneRukumbuzi/e-invoice.git
    cd e-invoice
    ```

2. Configure Postgres database:
    - Create a new database in Postgres.
    - Update the `application.yaml` file with your database details.

3. Add a `.env` file in the root directory and add the following:
    ```bash
    DATABASE_URL=jdbc:postgresql://localhost:5432/your-database-name
    DB_USERNAME=your-database-username
    DB_PASSWORD=your-database-password
    ```

4. Build and run the Spring Boot application:
    ```bash
    ./mvnw spring-boot:run
    ```

## Usage
1. Open your web browser and navigate to `http://localhost:8080`.
2. To access the auth endpoints, add ```/auth/register``` for signup and ```/auth/login``` for login
3. To access the invoices endpoints, add ```/invoices```, use these methods: ```GET``` to get all, ```POST``` to create an invoice, ```DELETE``` to delete an invoice and ```PUT``` to update, remember to provide the token in the Authorization paramater like this: ```bearer ${token}```
