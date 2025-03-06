# spring-jpa-database-locking

This Repository Contains Implementation for Database Locking with Spring Data JPA

## Tools and Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- H2 Database (for testing)
- JMeter (for performance testing)

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/your-repo/spring-jpa-database-locking.git
    ```
2. Navigate to the project directory:
    ```sh
    cd spring-jpa-database-locking
    ```
3. Build the project:
    ```sh
    ./mvnw clean install
    ```

## Usage

1. Run the application:
    ```sh
    ./mvnw spring-boot:run
    ```
2. Access the application at `http://localhost:8080`.

## Examples

### Pessimistic Locking

```java
// ...existing code...
@Entity
public class ExampleEntity {
    // ...existing code...
    @Version
    private Long version;
    // ...existing code...
}
```

### Optimistic Locking

```java
// ...existing code...
@Entity
public class ExampleEntity {
    // ...existing code...
    @Version
    private Long version;
    // ...existing code...
}
```

## Testing

### Testing Optimistic Locking with JMeter

1. Open JMeter.
2. Load the provided JMeter test plan located at `Database Locking JPA Test Plan.jmx`.
3. Start the test plan to simulate concurrent updates and observe the results.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## License

This project is licensed under the MIT License.
