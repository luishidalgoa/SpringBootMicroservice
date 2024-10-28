# Proyecto de Microservicios en Spring
![Microservicios imagen](https://www.arrobasolutions.com/wp-content/uploads/2021/10/Arquitectura-de-microservicios-que-es-y-cuales-son-sus-ventajas.jpg)
## **Introducción:**
La idea principal de este proyecto es aprender a crear una aplicación con arquitectura orientada a **microservicios** en lugar de un monolito. Adicionalmente, toda la aplicación está **dockerizada** para facilitar su despliegue y administración.

### **Documentación implementada**
Se ha implementado la **documentación a través de Swagger** para cada microservicio. Sin embargo, **no ha sido posible integrarla en el Gateway** debido a una incompatibilidad entre **Spring Gateway** y **Spring Web**. ¡Cualquier recomendación para implementar Swagger en el microservicio Gateway será bienvenida!

## Arquitectura (Diagrama)

![Diagrama](https://github.com/luishidalgoa/luishidalgoa/blob/main/Images/portfolio/Spring-microservices/diagrama.png?raw=true)

## **Documentación de la API:**

1. **Documentación en Postman**  
   Puedes acceder a la documentación pública de la API en Postman:
    - [**Postman Documentation Link**](https://galactic-crescent-156854.postman.co/workspace/Team-Workspace~6e7e21b0-4367-49af-8246-fc3174491d32/collection/31150730-7669aff2-2597-4fae-970b-1375dcc5d398?action=share&creator=31150730)

2. **Documentación de Swagger**  
   La documentación Swagger para cada microservicio está disponible en los siguientes enlaces locales:
    - **Microservicio Course**: [Swagger Course](http://localhost:9090/swagger-ui/index.html)
    - **Microservicio Student**: [Swagger Student](http://localhost:8090/swagger-ui/index.html)

## **Script Docker-compose:**
### **Instalación:**
- **Docker**: Deberás tener Docker o Docker Hub instalado en tu sistema:
    - [**Guía de instalación de Docker**](https://docs.docker.com/get-docker/)
- **Docker Compose**: También es necesario instalar Docker Compose:
    - [**Guía de instalación de Docker Compose**](https://docs.docker.com/compose/install/)

### **Ejecutar el Script:**
Para levantar la infraestructura de microservicios con Docker Compose, ejecuta el siguiente comando en la terminal:
### Script run:
```bash
docker compose up -d
```
```yml
services:
  postgres-db:
    image: postgres:latest
    environment:
      POSTGRES_DB: db_course
      POSTGRES_USER: root
      POSTGRES_PASSWORD: 1234
    ports:
      - "5432:5432"
    healthcheck:
      test: [ "CMD", "pg_isready", "-U", "root" ]
      interval: 10s
      timeout: 5s
      retries: 3
      start_period: 10s
    container_name: postgres-db
    networks:
      - microservices-network

  mysql-db:
    image: mysql:latest
    environment:
      MYSQL_DATABASE: db_student
      MYSQL_ROOT_PASSWORD: 1234
    container_name: mysql-db
    ports:
      - "3306:3306"
    healthcheck:
      test: [ "CMD", "mysqladmin", "ping", "-h", "localhost" ]
      interval: 10s
      timeout: 5s
      retries: 3
      start_period: 10s
    networks:
      - microservices-network

  eureka:
    image: luishidalgoa/springbootmicroservicios-eureka:v3
    container_name: msvc-eureka
    environment:
      EUREKA_HOSTNAME: msvc-eureka
    ports:
      - "8761:8761"
    networks:
      - microservices-network

  student:
    image: luishidalgoa/springbootmicroservicios-student:v3
    container_name: msvc-student
    environment:
      MYSQL_HOST: mysql-db
      MYSQL_USER: root
      MYSQL_PASSWORD: 1234
      EUREKA_HOST_NAME: msvc-eureka
    ports:
      - "8090:8090"
    depends_on:
      mysql-db:
        condition: service_healthy
      eureka:
        condition: service_started
    networks:
      - microservices-network

  course:
    image: luishidalgoa/springbootmicroservicios-course-service:v3
    container_name: msvc-course
    environment:
      POSTGRES_HOST: postgres-db
      POSTGRES_USER: root
      POSTGRES_PASSWORD: 1234
      EUREKA_HOST_NAME: msvc-eureka
    ports:
      - "9090:9090"
    depends_on:
      postgres-db:
        condition: service_healthy
      eureka:
        condition: service_started
    networks:
      - microservices-network

  gateway:
    image: luishidalgoa/springbootmicroservicios-gateway:v3
    container_name: msvc-gateway
    environment:
      STUDENT_HOST: msvc-student
      COURSE_HOST: msvc-course
      EUREKA_HOST_NAME: msvc-eureka
    ports:
      - "8080:8080"
    depends_on:
      - student
      - course
      - eureka
    networks:
      - microservices-network

networks:
  microservices-network:
    driver: bridge


  #VARIABLES GLOBALES
  #MYSQL_HOST
  #MYSQL_USER
  #MYSQL_PASSWORD

  #POSTGRES_HOST
  #POSTGRES_USER
  #POSTGRES_PASSWORD

  #CONFIG_SERVER
  #EUREKA_HOST_NAME

  #STUDENT_HOST
  #COURSE_HOST


```
