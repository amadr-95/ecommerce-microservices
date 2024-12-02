# E-commerce App

## Docker Compose
The application includes the following services:

- **postgres**: PostgreSQL database service. Uses the `postgres` image and stores data in a volume named `postgres`. The container's port 5432 is mapped to port 5432 on the host.
- **mongo**: MongoDB database service. Uses the `mongo` image and stores data in a volume named `mongo`. The container's port 27017 is mapped to port 27017 on the host.
- **mongo-express**: Web interface service for MongoDB. Uses the `mongo-express` image and connects to the `mongo` service. The container's port 8081 is mapped to port 8081 on the host.
- **maildev**: SMTP and web server service for email testing. Uses the `maildev/maildev` image. The container's ports 1080 and 1025 are mapped to the same ports on the host.


## Microservices
This application is built under a monorepo system. Services need to be executed in order to run properly and for that reason 
the easiest way is to execute `start_microservices.sh` file.

All the services that have a database will use `Flyway` for database migration. This requires this structure of folders under resources:
```
resources
| - db
    | - migration
```  
The file inside this folder requires a specific name such as `V1__name.sql`.
> [!NOTE]
> Flyway dependency is the best approach when it comes to use databases in real production environments.
> It is advisable not to delegate database management to the application itself (create-drop, etc.)
> Every time you make some structural changes in your database changing code you need to provide the proper script
> that matches with those changes in order to `validate` the schema, otherwise application will not run dur to this discrepancy.

### Configuration Server Service (`config-server`)
This microservice acts like a centralized configuration server that provides the configuration for the rest of the services.
Uses `@EnableConfigServer` annotation to enable the server and the configuration for each service is stored in `resources/configurations` path, as indicated in [`application.yml`](services/config-server/src/main/resources/application.yml)🔗 file.  
Service runs on `http://localhost:8888`.

### Discovery Server Service (`discovery-server`)
This microservice acts like a discovery server, allowing other services to register and discover between them. It uses `@EnableEurekaServer` annotation
from Spring Cloud Eureka to manage the registry and discover of the services.  
Service runs on `http://localhost:8761`.
> [!NOTE]
> Discovery server does not have to register itself, that's why we need to provide this config in [`discovery-service.yml`](services/config-server/src/main/resources/configurations/discovery-service.yml)🔗 in config-server service
```yaml
  register-with-eureka: false
  fetch-registry: false
```
> This configuration ensures that discovery-server works properly without integrating itself within Eureka server (otherwise it will end up in a loop)

### Customer service (`customer`)
Microservice in charge of managing customer info. It uses Swagger for documenting the API (`http://localhost:8080/swagger-ui/index.html`).  
Dependencies:
- Config client: client that allows connecting to the `config-server` and fetching all the configuration
- Eureka client: client that allows the customer microservice to register with the `discovery-server`
- Spring web (REST API)
- Lombok (avoid boilerplate)
- JPA
- Postgres (database of choice)
- Flyway (database migration)
- Validation (to perform field validations)
- OpenAPI specification (Swagger) 

####  CustomerController API Endpoints

| HTTP Method | Endpoint                              | Description                                     | Input parameters                                               | Response                                 |
|-------------|---------------------------------------|-------------------------------------------------|----------------------------------------------------------------|------------------------------------------|
| POST        | /api/v1/customers                     | creates a new customer                          | `@RequestBody @Valid @NotNull CustomerRequest customerRequest` | `ResponseEntity<Integer>`                |
| PUT         | /api/v1/customers                     | updates an existing customer                    | `@RequestBody @Valid @NotNull CustomerRequest customerRequest` | `ResponseEntity<?>`                      |
| GET         | /api/v1/customers                     | retrieves all the customers                     | None                                                           | `ResponseEntity<List<CustomerResponse>>` |
| GET         | /api/v1/customers/exists/{customerId} | verifies whether a customer exists by ID or not | `@PathVariable @NotNull Integer customerId`                    | `ResponseEntity<Boolean>`                |
| GET         | /api/v1/customers/find/{customerId}   | retrieves a customer by ID                      | `@PathVariable @NotNull Integer customerId`                    | `ResponseEntity<CustomerResponse>`       |
| DELETE      | /api/v1/customers/delete/{customerId} | deletes a customer by ID                        | `@PathVariable @NotNull Integer customerId`                    | `ResponseEntity<?>`                      |

### Product service (`product`)
Microservice in charge of managing product info. Service runs on `http://localhost:8081`.  
Dependencies:
- Config client: client that allows connecting to the `config-server` and fetching all the configuration
- Eureka client: client that allows the customer microservice to register with the `discovery-server`
- Spring web (REST API)
- Lombok (avoid boilerplate)
- JPA
- Postgres (database of choice)
- Flyway (database migration)
- Validation (to perform field validations)

####  ProductController API Endpoints

| HTTP Method | Endpoint                            | Description                                    | Input parameters                                                               | Response                                        |
|-------------|-------------------------------------|------------------------------------------------|--------------------------------------------------------------------------------|-------------------------------------------------|
| POST        | /api/v1/products                    | creates a new product                          | `@RequestBody @Valid @NotNull ProductRequest productRequest`                   | `ResponseEntity<Integer>`                       |
| POST        | /api/v1/products/purchase           | creates a list of products to purchase         | `@RequestBody @Valid @NotNull List<ProductPurchaseRequest> productListRequest` | `ResponseEntity<List<ProductPurchaseResponse>>` |
| GET         | /api/v1/products/find/{productId}   | retrieves a product by ID                      | `@PathVariable @NotNull Integer productId`                                     | `ResponseEntity<ProductResponse>`               |
| GET         | /api/v1/products                    | retrieves all the products                     | None                                                                           | `ResponseEntity<List<ProductResponse>>`         |
| DELETE      | /api/v1/products/delete/{productId} | deletes a product by ID                        | `@PathVariable @NotNull Integer productId`                                     | `ResponseEntity<?>`                             |

