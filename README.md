# E-Invoice

## Overview
The E-invoice Application is a Spring Boot Application designed to manage users and invoices. The application allows users to sign up, log in, and be able to create, view, edit and delete their invoices.

## Swagger Documentation

- `http://localhost:8080/swagger-ui/index.html#/`

## Features
- **User Registration**: Allows new users to create an account.
- **User Login**: Enables existing users to log in to the application.
- **Invoices Management**: Users can create, view, update and delete their invoices.
- **Authentication**: Secure login and registration using JWT (JSON Web Tokens).

## Technologies Used

### Backend
- Spring Boot
- Spring Security
- JWT for authentication (Token)
- Postgres for database
- Lombok

## Prerequisites

- Java JDK 17
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
1. Open Postman or insomnia and Add the baseurl in the env `http://localhost:8080`.
2. To access the auth endpoints, add ```/auth/register``` for signup and ```/auth/login``` for login
3. To access the invoices endpoints, add ```/invoices```, use these methods: ```GET``` to get all, ```POST``` to create an invoice, ```DELETE``` to delete an invoice and ```PATCH``` to update, remember to provide the token in the Authorization paramater like this: ```bearer ${token}```


### Database Schema

<img width="787" alt="Database Schema" src="https://github.com/IreneRukumbuzi/e-invoice/assets/68101724/6fffb001-9056-4dc5-96bc-d35c1a6084ad">

