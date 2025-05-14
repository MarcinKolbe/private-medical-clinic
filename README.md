# Private Medical Clinic

A Spring Boot application for managing a private medical clinic. Includes REST APIs for doctors, patients, appointments, diagnoses, and ratings, with auditing support.

---

## Frontend

The frontend is built with Vaadin and is under active development:

🔗 [Vaadin Private Medical Clinic](https://github.com/MarcinKolbe/vaadin-private-medical-clinic)

---

## Prerequisites

* **Java 17+**
* **Maven 3.6+** or **Gradle**
* **MySQL 8.x**
* **Internet access** for fetching drug information from OpenFDA

---

## Database Setup & Auditing

On startup, the application creates auditing tables, stored procedures, and triggers. If you encounter errors related to binary logging or function creation, enable `log_bin_trust_function_creators`:

### Temporary (session) workaround:

```sql
SET GLOBAL log_bin_trust_function_creators = 1;
```

### Permanent (my.cnf / my.ini):

```
[mysqld]
log_bin_trust_function_creators = 1
```

After making changes, restart the MySQL server.

---

## Running the Application

1. **Clone the repository**

   ```bash
   git clone https://github.com/MarcinKolbe/private-medical-clinic.git
   cd private-medical-clinic
   ```

2. **Configure `application.yml`**

   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/clinic_db?useSSL=false&serverTimezone=UTC
       username: your_user
       password: your_password

   management:
     endpoints:
       web:
         exposure:
           include: health,info,metrics,loggers,env,httptrace,threaddump,heapdump,caches
   ```

3. **Build & Run**

   ```bash
   mvn clean spring-boot:run
   # or with Gradle
   ./gradlew bootRun
   ```

4. **Access Swagger UI**

    * `http://localhost:8080/swagger-ui.html`
    * or `http://localhost:8080/swagger-ui/index.html`

---

## API Usage

All endpoints and DTO schemas are available in Swagger.

### Typical Workflow

1. **Create a doctor and patient**

    * User accounts are created automatically alongside doctor/patient entities.

2. **Add the doctor's work schedule**

    * 30-minute appointment slots are generated automatically for the next 7 days.

3. **Create an appointment**

    * Book a visit in a free slot.

4. **Add a diagnosis**

    * Optional: provide a drug generic name to fetch details from OpenFDA.
    * Marks the appointment as **Completed**, unlocking rating functionality.

5. **Add a rating**

    * A database procedure automatically calculates and stores the average rating for the doctor.

---

## Actuator Endpoints

* `GET /actuator/health`
* `GET /actuator/info`
* `GET /actuator/metrics`
* `GET /actuator/metrics/{metric.name}`
* `GET /actuator/loggers`
* `POST /actuator/loggers/{logger.name}`
* `GET /actuator/httptrace`
* `GET /actuator/threaddump`
* `GET /actuator/heapdump`
* `GET /actuator/env`
* `GET /actuator/caches`
* `GET /actuator/caches/{cacheName}`

---

## Troubleshooting

* **Auditing errors**: Enable `log_bin_trust_function_creators` as described above.
* **Swagger not available**: Check that `springdoc-openapi-ui` (or `springfox`) dependency is included and that the UI path matches your configuration.

---

