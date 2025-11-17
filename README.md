# Garage Gestion

Spring Boot project to manage garages, vehicles, and accessories. REST API, MapStruct mapping, integration tests, and usage examples

Technologies
- Java 17
- Spring Boot
- Maven
- MapStruct
- Lombok
- JUnit 5
- Mockito
- Kafka

### Getting Started

This application uses Docker Compose for a quick and easy setup, but requires building the project first using Maven.

**Building the Project:**

1. Clone the repository:

```bash
git clone https://github.com/Fatima-bou111/garage-gestion.git 
```

2. Navigate to the project directory:

```bash
cd garage-gestion
```

3. Build the project using Maven:

```bash
mvn clean install
```

This command will download dependencies, compile the code, and package the application for deployment.

### Running the application:

Now that the project is built, you can proceed with starting the kafka using Docker Compose:

1. Start Running the kafka service:

```bash
docker-compose up 
```
### Functionalities
Garage management: create, read, update, delete (CRUD)
Vehicle management: CRUD operations associated with a garage
Accessory management: CRUD operations and association with vehicles