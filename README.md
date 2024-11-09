# E-commerce App


## Docker compose
Our app have the services below:

- postgres: Servicio de base de datos PostgreSQL. Utiliza la imagen `postgres` y almacena los datos en un volumen llamado `postgres`. El puerto 5432 del contenedor se mapea al puerto 5432 del host.
- mongo: Servicio de base de datos MongoDB. Utiliza la imagen `mongo` y almacena los datos en un volumen llamado `mongo`. El puerto 27017 del contenedor se mapea al puerto 27017 del host.
- mongo-express: Servicio de interfaz web para MongoDB. Utiliza la imagen `mongo-express` y se conecta al servicio `mongo`. El puerto 8081 del contenedor se mapea al puerto 8081 del host.
- maildev: Servicio de servidor SMTP y web para pruebas de correo electrónico. Utiliza la imagen `maildev/maildev`. Los puertos 1080 y 1025 del contenedor se mapean a los mismos puertos en el host.

## Microservices
### config-server
Este microservicio actúa como un servidor de configuración centralizado, proporcionando configuraciones al resto de microservicios. Usa la anotación `@EnableConfigServer` 
y las configuraciones para cada servicio se alojan en el path `resources/configurations` como se indica en el archivo `application.yml`.  
Dependencies:
- Config server: habilita el servidor para proporcionar configuraciones al resto de microservices

### discovery-server
Este microservicio actúa como un servidor de descubrimiento, permitiendo que otros microservicios se registren y descubran entre sí. Utiliza la anotacion `@EnableEurekaServer`
de Eureka de Spring Cloud para gestionar el registro y descubrimiento de servicios.  
Dependencies:
- Config client: cliente que permite conectarse al config-server y extraer toda la configuracion 
- Eureka server: habilita el servidor de registro y descubrimiento de servicios. **NOTE:** discovery (Eureka) server does not have to register itself, that's why we need to provide this config under `resources/configurations/discovery-service.yml` in config-server service:
```yaml
  eureka:
    instance:
      hostname: localhost
    client:
      register-with-eureka: false 
      fetch-registry: false
      service-url:
        defaultZone: http://${eureka.instance.hostname}/${server.port}/eureka/
  server:
    port: 8761
 ```
  This configuration ensures that discovery-server works properly without integrating itself within Eureka server (otherwise it will end up in a loop)
### customer
Microservicio encargado de gestionar la información de los clientes.  
Dependencies:
- Config client: cliente que permite conectarse al config-server y extraer toda la configuracion
- Eureka client: cliente que permite que customer microservice se registre en discovery (eureka) server
- Spring web (REST API)
- Lombok (avoid boilerplate)
- Postgres (database of choice)
- Validation (to perform field validations)

##  CustomerController API Endpoints

| Método HTTP | Endpoint                              | Descripción                           | Parámetros de entrada                                          | Respuesta                                |
|-------------|---------------------------------------|---------------------------------------|----------------------------------------------------------------|------------------------------------------|
| POST        | /api/v1/customers                     | Crear un nuevo cliente                | `@RequestBody @Valid @NotNull CustomerRequest customerRequest` | `ResponseEntity<Integer>`                |
| PUT         | /api/v1/customers                     | Actualizar un cliente existente       | `@RequestBody @Valid @NotNull CustomerRequest customerRequest` | `ResponseEntity<?>`                      |
| GET         | /api/v1/customers                     | Obtener todos los clientes            | Ninguno                                                        | `ResponseEntity<List<CustomerResponse>>` |
| GET         | /api/v1/customers/exists/{customerId} | Verificar si un cliente existe por ID | `@PathVariable @NotNull Integer customerId`                    | `ResponseEntity<Boolean>`                |
| GET         | /api/v1/customers/find/{customerId}   | Obtener un cliente por ID             | `@PathVariable @NotNull Integer customerId`                    | `ResponseEntity<CustomerResponse>`       |
| DELETE      | /api/v1/customers/delete/{customerId} | Eliminar un cliente por ID            | `@PathVariable @NotNull Integer customerId`                    | `ResponseEntity<?>`                      |

