dockerfile:
```yml
version: '3.8'  # Especifica la versión de Docker Compose

services:
  postgres-db:
    image: postgres:latest
    environment:
      POSTGRES_DB: db_course
      POSTGRES_PASSWORD: 1234
      POSTGRES_USER: root
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - microservices-network

  mysql-db:
    image: mysql:latest
    environment:
      MYSQL_DATABASE: db_student
      MYSQL_ROOT_PASSWORD: 1234
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - microservices-network

  eureka:
    image: luishidalgoa/springbootmicroservicios-eureka:v1
    environment:
      EUREKA_URL: http://springbootmicroservicios-eureka-1:8761  # Cambia a usar el nombre de servicio
      EUREKA_HOSTNAME: springbootmicroservicios-eureka-1
    ports:
      - "8761:8761"
    networks:
      - microservices-network

  student-service:
    build:
      context: ./microservice-student
      dockerfile: Dockerfile
    environment:
      DB_MYSQL_HOST: springbootmicroservicios-mysql-1
      DB_MYSQL_PORT: 3306
      DB_MYSQL_NAME: db_student
      DB_MYSQL_USERNAME: root
      DB_MYSQL_PASSWORD: 1234
      DB_MYSQL_DDL_AUTO: create-drop
      EUREKA_URL: http://springbootmicroservicios-eureka-1:8761
      EUREKA_HOSTNAME: springbootmicroservicios-eureka-1
    ports:
      - "8090:8090"
    depends_on:
      - mysql-db
      - eureka
    networks:
      - microservices-network

  course-service:
    build:
      context: ./microservice-course
      dockerfile: Dockerfile
    environment:
      DB_POSTGRES_HOST: springbootmicroservicios-postgres-1
      DB_POSTGRES_PORT: 5432
      DB_POSTGRES_NAME: db_course
      DB_POSTGRES_USERNAME: root
      DB_POSTGRES_PASSWORD: 1234
      DB_POSTGRES_DDL_AUTO: create-drop
      EUREKA_URL: http://springbootmicroservicios-eureka-1:8761
      EUREKA_HOSTNAME: springbootmicroservicios-eureka-1
    ports:
      - "9090:9090"
    depends_on:
      - postgres-db
      - eureka
    networks:
      - microservices-network

  gateway:
    build:
      context: ./microservice-gateway
      dockerfile: Dockerfile
    environment:
      COURSE_SERVICE_URL: http://springbootmicroservicios-course-service-1:9090
      STUDENT_SERVICE_URL: http://springbootmicroservicios-student-service-1:8090
    ports:
      - "8080:8080"
    depends_on:
      - course-service
      - student-service
      - eureka
    networks:
      - microservices-network

volumes:
  postgres_data:
  mysql_data:

networks:
  microservices-network:
    driver: bridge  # Utiliza el controlador bridge (por defecto)

```
