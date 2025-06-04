#  Employee Payroll Spring Boot Application

This project is a Spring Boot application designed to manage employee payroll data via RESTful APIs. It demonstrates building a multi-layered application (Controller, Service, Repository) and integrates with an H2 in-memory database.

---

##  Features

- **Employee Data Management**: CRUD (Create, Read, Update, Delete) operations for employee payroll records.
- **In-Memory Data Persistence**: Uses H2 in-memory DB; data is volatile and stored in RAM.
- **H2 Console**: Web-based interface to inspect the in-memory database.
- **Structured API Design**: RESTful principles with clear, meaningful endpoints.
- **Layered Architecture**: Controller, Service, Repository separation.
- **DTO Pattern**: Clear contract for incoming request payloads.
- **Robust Error Handling**: Custom `EmployeeNotFoundException` for 404 Not Found scenarios.
- **Explicit Codebase**: Avoids Lombok for clear Java fundamentals — all methods are handwritten.

---

##  Technologies Used

- Java 17+
- Spring Boot 3.2.5+
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

---
## API Endpoints
The API base URL is `http://localhost:8080/employeepayroll`

### 1. Get All Employees
* **Endpoint:** `/employeepayroll/get`
* **Method:** `GET`
*  Retrieves a list of all employee payroll records from the database.

**CURL Example:**
```bash
  curl http://localhost:8080/employeepayroll/get
```

### 2. Get Employee by ID
* **Endpoint:** `/employeepayroll/get/{empId}`
* **Method:** `GET`
*  Retrieves a single employee payroll record by its unique ID.
* **Response:** Returns `200 OK` with the employee data if found, or `404 Not Found` if no employee with the given ID exists.

**CURL Example:**
```bash
  curl http://localhost:8080/employeepayroll/get/{employeeId}
```
*(Replace `employeeId` with actual I'd from your database)*

### 3. Create Employee
* **Endpoint:** `/employeepayroll/create`
* **Method:** `POST`
* Creates a new employee payroll record in the database.

**Request Body (JSON):**
```json
{
    "name": "Stuti Mehtha",
    "salary": 75000
}
```

**Response:** Returns `201 Created` with the newly created `EmployeePayrollData` object, including the auto-generated `employeeId`.

**CURL Example:**
```bash
  curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"Jane Smith\",\"salary\":70000}" http://localhost:8080/employeepayroll/create
```

### 4. Update Employee
* **Endpoint:** `/employeepayroll/update/{empId}`
* **Method:** `PUT`
* Updates an existing employee payroll record identified by its ID.

**Request Body (JSON):**
```json
{
    "name": "Krethik",
    "salary": 85000
}
```

**Response:** Returns `200 OK` with the updated `EmployeePayrollData` if found, or `404 Not Found` if no employee with the given ID exists.

**CURL Example:**
```bash
  curl -X PUT -H "Content-Type: application/json" -d "{\"name\":\"Bob Updated\",\"salary\":95000}" http://localhost:8080/employeepayroll/update/2
```

### 5. Delete Employee
* **Endpoint:** `/employeepayroll/delete/{empId}`
* **Method:** `DELETE`
* Deletes an employee payroll record from the database by its ID.
* **Response:** Returns `200 OK` with a confirmation message if deletion is successful, or `404 Not Found` if no employee with the given ID exists.

**CURL Example:**
```bash
  curl -X DELETE http://localhost:8080/employeepayroll/delete/3
```


