# Example of automation tests using Cucumber


###  Minimum requirements:
 - Java 17+

### Run tests against remote api:

```mvn clean test```

### Run tests against local fake api:

Enable SpringBootTest on line 13 to start the local fake api server

Then run the command:

```mvn clean test -Dapi.baseUrl=http://localhost```

### Run the application locally

Use the Spring Boot Maven plugin to start the embedded app without running the full test suite:

```bash
mvn spring-boot:start
mvn spring-boot:stop
```

### In progress
- Integrate with CI/CD pipeline

### Nice to have:
 - Integrate with CI/CD pipeline
 - Generate test reports

### Coding
