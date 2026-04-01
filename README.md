# Job Detector Backend

Spring Boot backend for the Enhanced Rule-Based Fake Internship & Job Offer Detection System.

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE job_detector;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/job_detector
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

### Running the Application
1. Navigate to the backend directory:
```bash
cd backend
```

2. Build and run the application:
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login user

### Job Analysis
- `POST /api/jobs/analyze` - Analyze a job offer
- `GET /api/jobs` - Get all analyzed jobs

## Project Structure
```
src/main/java/com/jobdetector/
├── JobDetectorApplication.java     # Main application class
├── controller/
│   ├── AuthController.java         # Authentication endpoints
│   └── JobController.java          # Job analysis endpoints
├── entity/
│   ├── User.java                   # User entity
│   └── Job.java                    # Job entity
├── repository/
│   ├── UserRepository.java         # User repository
│   └── JobRepository.java          # Job repository
└── service/
    └── JobAnalysisService.java     # Rule-based analysis logic
```
