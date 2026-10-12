# Sentinel Hostel Management System — Backend

A secure, scalable RESTful backend service built with **Spring Boot 3**, **Spring Security 6**, and **MySQL**.

## Features

- **Authentication & Authorization**:
  - Stateless JWT (JSON Web Token) authentication using JJWT 0.12.6.
  - Role-Based Access Control (**ADMIN**, **WARDEN**, **STUDENT**).
- **Room Allocation Management**: Room creation, status toggling, and occupant tracking.
- **Complaint & Ticket Lifecycle**: Student issue filing with Warden action tracking.
- **Night-out & Outstation Leaves**: Application submission and approval workflow.
- **Official Notices & Circulars**: Central bulletin board for residents.
- **Student GPS / Radar Tracking**: Coordinates reporting and Admin live radar monitoring.
- **Hostel Treasury & Payment Ledger**:
  - Payment records for Room Rent, Mess Charges, Maintenance, and Security Deposit.
  - Integration support for UPI (VPA / QR) and Net Banking with transaction IDs.
  - Automated bill generation by Administrators.
- **Data Initializer**: Pre-seeded demo dataset with rooms, complaints, notices, leaves, and billing records.

## Technologies Used

- **Java**: 21
- **Framework**: Spring Boot 3.4+
- **Security**: Spring Security 6 + JJWT 0.12.6
- **Database**: MySQL 8.0
- **ORM / Persistence**: Spring Data JPA (Hibernate)
- **Build Tool**: Apache Maven (Maven Wrapper included)

## Getting Started

### Prerequisites
- Java 21 JDK
- MySQL 8.0 Server

### Database Setup
Create MySQL database `hostel_management`:
```sql
CREATE DATABASE hostel_management;
```

Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hostel_management?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Running the Backend
```bash
# Clone the repository
git clone https://github.com/rohith57509/HostelManagementSystem-Backend.git
cd HostelManagementSystem-Backend

# Build and run
.\mvnw.cmd clean compile
.\mvnw.cmd spring-boot:run
```

The backend REST API will be available at: `http://localhost:8080`

### Demo Accounts
| Role | Email | Password |
|---|---|---|
| **Admin** | `admin@hostel.com` | `admin123` |
| **Warden** | `warden@hostel.com` | `warden123` |
| **Student** | `student@hostel.com` | `student123` |
