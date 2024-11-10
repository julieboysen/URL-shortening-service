# URL Shortening Service

Simple URL shortening service built with Java, Spring Boot, and MySQL. It allows users to shorten long URLs, retrieve the original URL, update the short URL, delete a short URL, and view access statistics for each shortened URL.

## Features

- **Shorten URL**: Generate a short code for a given long URL.
- **Retrieve Original URL**: Access the original URL using the short code.
- **Update URL**: Modify the original URL associated with a short code.
- **Delete URL**: Delete a short URL by its short code.
- **View Statistics**: Track access count and timestamps for each shortened URL.

## Technologies Used

- **Java** & **Spring Boot**: Back-end framework.
- **MySQL**: Database to store URLs and statistics.
- **JUnit** & **Mockito**: Testing framework for unit and integration tests.
- **Spring MockMvc**: Used for testing the web layer.

## Project Structure

```plaintext
urlshortener
├── src
│   ├── main
│   │   ├── java/com/urlshortener/urlshortener
│   │   │   ├── controller       # Controllers for handling HTTP requests
│   │   │   ├── model            # ShortUrl entity model
│   │   │   ├── repository       # JPA Repository for database access
│   │   │   └── service          # Service layer for business logic
│   │   └── resources
│   │       ├── application.properties # Database configurations
│   └── test/java/com/urlshortener/urlshortener
│       └── ShortUrlControllerTest.java # Unit tests for controller
└── README.md
```

## Getting Started

### Prerequisites

- **Java 11+**
- **Maven** (for dependency management)
- **MySQL** (running locally or on a remote server)

### Setup and Configuration

1. **Clone the repository**:

   ```bash
   git clone https://github.com/julieboysen/URL-shortening-service.git
   cd URL-shortening-service
   ```

2. **Configure MySQL Database**:

   - Create a MySQL database named `shorturl_db`.
   - Update the database connection details in `src/main/resources/application.properties`:
     ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/shorturl_db
      spring.datasource.username=shorturl_user
      spring.datasource.password=your_password
      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=true
     ```

3. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

The application will start at `http://localhost:8080`.

## API Endpoints

### 1. Create Short URL

- **URL**: `/shorten`
- **Method**: `POST`
- **Body**:
  ```json
  {
    "url": "https://www.example.com"
  }
  ```
- **Response**:
  ```json
  {
    "id": 1,
    "url": "https://www.example.com",
    "shortCode": "abc123",
    "createdAt": "2024-11-10T12:34:56",
    "updatedAt": "2024-11-10T12:34:56",
    "accessCount": 0
  }
  ```

### 2. Get Original URL

- **URL**: `/shorten/{shortCode}`
- **Method**: `GET`
- **Response**:
  ```json
  {
    "id": 1,
    "url": "https://www.example.com",
    "shortCode": "abc123",
    "createdAt": "2024-11-10T12:34:56",
    "updatedAt": "2024-11-10T12:34:56",
    "accessCount": 1
  }
  ```

### 3. Update URL

- **URL**: `/shorten/{shortCode}`
- **Method**: `PUT`
- **Body**:
  ```json
  {
    "url": "https://www.new-url.com"
  }
  ```
- **Response**:
  ```json
  {
    "id": 1,
    "url": "https://www.new-url.com",
    "shortCode": "abc123",
    "createdAt": "2024-11-10T12:34:56",
    "updatedAt": "2024-11-10T12:50:00",
    "accessCount": 1
  }
  ```

### 4. Delete Short URL

- **URL**: `/shorten/{shortCode}`
- **Method**: `DELETE`
- **Response**: `204 No Content`

### 5. Get URL Statistics

- **URL**: `/shorten/{shortCode}/stats`
- **Method**: `GET`
- **Response**:
  ```json
  {
    "id": 1,
    "url": "https://www.example.com",
    "shortCode": "abc123",
    "createdAt": "2024-11-10T12:34:56",
    "updatedAt": "2024-11-10T12:50:00",
    "accessCount": 10
  }
  ```

## Running Tests

To run tests, use the following command:

```bash
mvn test
```

### Example Tests

- **ShortUrlControllerTest**: Unit tests for the controller layer.
- Tests include creating, retrieving, updating, deleting, and getting statistics for short URLs.

## Project Link

For more details about this project, visit the [URL Shortening Service Project](https://roadmap.sh/projects/url-shortening-service).
