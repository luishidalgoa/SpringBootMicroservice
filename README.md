dockerfile:
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
    image: luishidalgoa/springbootmicroservicios-eureka:v2
    container_name: msvc-eureka
    environment:
      EUREKA_HOSTNAME: msvc-eureka
    ports:
      - "8761:8761"
    networks:
      - microservices-network

  student:
    image: luishidalgoa/springbootmicroservicios-student:v2
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
    image: luishidalgoa/springbootmicroservicios-course-service:v2
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
    image: luishidalgoa/springbootmicroservicios-gateway:v2
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
