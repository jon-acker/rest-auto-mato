# Example of automation tests using Cucumber


###  Minimum requirements:
 - Java 17+

### Run tests against remote api:

```mvn clean test```

### Run tests against local fake api:

Enable SpringBootTest on line 13 to start the local fake api server

Then run the command:

```mvn clean test -Dapi.baseUrl=http://localhost```

### Nice to have:
 - Integrate with CI/CD pipeline
 - Generate test reports
