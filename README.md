Project overview
Features
Tech stack
Architecture
How to run locally
Database setup
Docker setup
AWS deployment
Link to API documentation




# Job Portal

A full-stack monolithic Job Portal application built using Spring Boot.

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- REST API
- Docker
- AWS

## Features

- User registration and login
- Job creation
- Job search
- Job application
- User/job management

## REST API Documentation

Complete API documentation is available here:

[REST API Documentation](docs/REST-API.md)

## Running Locally


### `docs/REST-API.md`

You can document each API like this:

# Job Portal REST API Documentation

Base URL:

`http://localhost:8080/api`

---

## 1. User APIs

### Register User

**POST** `/users/register`

#### Request

```json
{
  "name": "Suraj",
  "email": "suraj@example.com",
  "password": "password123"
}

```bash
mvn clean install
mvn spring-boot:run
