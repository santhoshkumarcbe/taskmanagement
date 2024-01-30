# <h1 align="center"> Task Management System and Collaboration Tool</h1>
___ 


<p align="left">

## Overview

The Task Management and Collaboration Tool is a Spring Boot-based application designed to facilitate tasks. It offers a set of RESTful API endpoints for assigning and monitoring tasks, collaboration with between `manager` and `contributor`.

## Technologies Used

- **Framework:** Spring Boot
- **Language:** Java
- **Database:** MySQL
- **Build Tool:** Maven

## Dependencies Used

The project utilizes various dependencies to achieve its functionality:

- **Spring Boot Starter Data JPA**: Simplifies database access using Spring Data repositories.

- **Spring Boot Starter Web**: Provides support for building web applications, including RESTful APIs.

- **MySQL Connector/J**: The MySQL JDBC driver for connecting to MySQL databases.

- **Project Lombok**: A library for reducing boilerplate code, such as getters and setters.

- **Spring Boot Starter Test (For Testing)**: Provides support for testing Spring Boot applications.

- **Other Dependencies:** Various other dependencies are included in the `pom.xml` file for specific functionalities, such as Jackson for JSON serialization, Spring Web for web-related features, and more.

For a comprehensive list of all dependencies and their versions, please refer to the project's `pom.xml` file.

## Project Structure

The project follows a structured and organized architecture:

- **Main Application Class:** The entry point for the application is defined in the main class.
- **Entities:** The application includes entities such as `User`, `Task`, `Assignment`, and `Chat` to model the data.
- **Repository Interfaces:** Spring Data JPA repository interfaces manage data access.
- **Service Classes:** Business logic is implemented in service classes for managing assignments,tasks, collaboration, and users.
- **Controller Classes:** These classes define and document RESTful API endpoints for creating, assigning, monitoring tasks and contributor and manager collaboration.

## Data Flow

The Task Management and Collaboration Tool follows a structured data flow for managing assignments and collaboration:

1. **Task Creation**:
    - A task can be created by manager by sending a `POST` request to `/task/post` endpoint, specifying task title, description and Due date with time.

    - The system checks if the specified task is available by `task title` which is unique.

    - If the task is not available, a new `Task` entity is created and saved in the database, associating the manager.

    - The system sends a response indicating a successful task creation.

2. **Assigning and Monitoring task**:
    - A manager can assign task by sending a `POST` request to the `/assignment/publish` endpoint, specifying the contributor `userId` to assign task to contributor.

    - The system fetch user by userId given in `Assignment` entity, if the provided `userId` exists and checks wheather the `userRole` is `contributor`.

    - If the `userRole` is `contributor`, task is assigned to the contributor and `assignment` stored in the database.

    - A contributor can update their task status by sending a `PUT` request to the `/assignment/update-status` as `RequestParam` with value for keys `assignmentId` and `status`.

    - The system sends a response confirming the status updated.

    -Manager can monitor assignment status by sending a `GET` request to the `/assignment/getallbytaskid/{taskId}` or `/assignment/getbyid/{assignmentId}`

3. **Get All Users**:
    - To retrieve a list of all available users, an admin sends a `GET` request to the `/user/getall` endpoint.

    - The system queries the database to fetch the list of all users.

    - The system sends a response containing the list of users.

4. **User Registration (Sign Up)**:
    - Admin can register manager and contributor, manager can register contributor by sending a `POST` request to the `/api/auth/register` endpoint, providing registration details such as emailId, fullName, mobileNumber, passwordHash, userName and userRole.

    - The system checks if the provided email is unique, creates a new `User` entity with the user's information, and stores it in the database after encrypting the password.

    - The system sends a response indicating successful registration.

5. **User Authentication (Sign In)**:
    - To authenticate, an User sends a `POST` request to the `/api/auth/login` endpoint with their email and password.

    - The system validates the provided email and password, checking against the stored data in the database.

    - If the credentials are valid, the system generates an authentication token, associates it with the user, and saves it in the database.

    - The system sends a response with the authentication token.

6. **Security**:
    - User authentication and authorization are handled securely, ensuring that user credentials are protected and that authentication tokens are used for secure API access.

7. **Data Persistence**:
    - The application relies on a relational database for data storage. Entities like `User`, `Task`, `Assignment`, and `Chat` are mapped to the corresponding database tables.

8. **RESTful API Endpoints**:
    - RESTful API endpoints provide a clear interface for managers to create, assign and monitor assignments.



## RESTful API Endpoints

The application provides RESTful API endpoints for various functionalities:

### Assignment 

- **Create Assignment:** `POST /assignment/publish`
- **Get all Assignments:** `GET /assignment/getall`
- **Get Assignment by assignmentId:** `GET /assignment/getbyid/{assignmentId}`
- **Get all Assignments by taskId:** `GET /assignment/getallbytaskid/{taskId}`
- **Update Status of Assignment:** `PUT /assignment/update-status`

### Task
- **Create Task:** `POST /task/post`
- **Get all Tasks:** `GET /task/getall`
- **Get Task by Title:** `GET /task/findbytitle/{title}`
- **Update Task:** `PUT /task/update/{taskId}`

### Chat
- **Create Chat:** `POST /chat/post`
- **Get all chats:** `GET /chat/getallchats`
- **Delete Chat:** `DELETE /chat/{chatId}`

### User
- **Create User:** `POST /api/auth/register`
- **Login User:** `POST /api/auth/login`
- **Get User By Id:** `GET /user/getbyid/{id}`
- **Get All Users:** `GET /user/getall`
- **Get User By User Role:** `GET /user/getallbyrole/{role}`
- **Get User By User Name:** `GET /user/getbyusername/{username}`
- **Get User By User Email:** `GET /user/getbyemail/{email}`
- **Get User By User Mobile Number:** `GET /user/getbymobilenumber/{mobilenumber}`
- **Delete User:** `DELETE /user/delete/{userId}`


### Delete operations

- **Delete User:** `DELETE /user/delete/{userId}`
- **Delete Assignment:** `DELETE /assignment/delete/{assignmntId}`
- **Delete Task:** `DELETE /task/delete/{taskId}`
- `Note:` If a task is deleted, all `Assignments` related to the specified task are also deleted.


## Database Design

The application uses a relational database to store data, including user and task information, assignment, chat and authentication tokens. Key attributes and tables include:

### User Table

| Column Name | Data Type    | Description                 |
| ----------- | ------------ | --------------------------- |
| userId      | INT          | Unique identifier for user |
| emaiId      | VARCHAR(255) |  Unique emailId for user     |
| fullName      | VARCHAR(255) | User Full Name  |
| mobileNumber  | VARCHAR(255) | User mobile number   |
| passwordHash  | VARCHAR(255) | Hashed password     |
| userName      | VARCHAR(255) | User name   |
| userRole        | VARCHAR(255) | User role ("ADMIN","MANAGER","CONTRIBUTOR")  |

### Chat Table Description

- `userId`: Unique identifier for each user.
- `emailId`: Unique email Id for User
- `fullName`: User Full Nam
- `mobileNumber`: Unique mobile number for User
- `passwordHash`: Hashed user password
- `userName`: Unique user Name for User
- `userRole`: Role of User ("ADMIN","MANAGER","CONTRIBUTOR")

### chat Table

| Column Name | Data Type    | Description                 |
| ----------- | ------------ | --------------------------- |
| chatId      | INT          | Unique identifier for chat |
| contributorId  | VARCHAR(255) | contributor userId      |
| managerId      | VARCHAR(255) | manager userId   |
| message        | VARCHAR(255) | chat content   |

### Chat Table Description

- `chatId` `contributorId` `managerId`: Unique identifier for each chat.
- `message`: chat content between manager and contributor.

### Task Table

| Column Name      | Data Type    | Description               |
| --------------- | ------------ | ------------------------- |
| taskId       | INT          | Unique identifier for tasks |
| description  | VARCHAR(255) | Task description |
| dueDate      | datetime(6)  | Due date and time to submit task  |
| managerId    | VARCHAR(255) | identifier of user `manager` |
| title        | VARCHAR(255) | Unique title of task |


### Task Table Description

- `taskId`: Unique identifier for each task.
- `description`: describes each task.
- `dueDate`: due date and time for task.
- `managerId`: userId (manager) who creates specified task.
- `title`: Unique title for each task.


### Assignment Table

| Column Name | Data Type    | Description                 |
| ----------- | ------------ | --------------------------- |
| assignmentId | INT         | Unique identifier for assignments |
| contributorId | INT        | Task assigned to specific contributor |
| status      | VARCHAR(255) | Current Status of task assigned to specific contributor |
| TaskId      | INT          | task of specific assignment |

### Assignment Table Description

- `assignmentId`: Unique identifier for each assignment.
- `contributorId`: contributor to whom task is assigned.
- `status`: Current Status of task assigned to specific contributor updated by contributor.
- `TaskId`: A many-to-one relationship with the task.


## Data Structures Used

1. **Entities**:
    - **User**: Represents a user with attributes like `userId`, `emailId`, `fullName`, `mobileNumber`, `passwordHash`, `userName`, `userRole`.

    - **Task**: Represents a task with attributes like `taskId`, `title`, `description`, `dueDate` and `managerId`.

    - **Assignment**: Represents an assignment assigned by a manager to a contributor. It contains an embedded primary key `assignmentId`, a reference to the associated `manager`, and a reference to the `task`.

    - **Chat**:  Represents a chat interaction between a manager and a contributor. It contains an embedded primary key `chatId`, a reference to the associated `manager`, and a reference to the `contributor`.

2. **Repositories**:
    - JPA repositories for data access, including repositories for Users(`Contributor`, `Manager`, `Admin`), tasks, and assignments.


## Database Configuration

The database connection properties, including the URL, username, and password, are specified in the `application.properties` file. Ensure that these properties are correctly configured to connect to your MySQL database.

Example configuration for MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager
spring.datasource.username=username
spring.datasource.password=password
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect

```

Please replace `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` with your database connection details.

## Project Summary

Task Management and Colloboration Tool is a Spring Boot-based system that simplifies manager-contributor task management and collaboration. It provides RESTful API endpoints for creating, assigning and monitoring tasks, and collaboration.


### Acknowledgments

I would like to thank the entire intern team, Mathan Sangar (Software developer), Karthick (Backend Trainer) and Trustrace for providing various resources in diffirent fields for the development of this project.

### Contact

For questions or feedback, please contact [SANTHOSHKUMAR K](mailto:santhoshkumarcbe35@gmail.com).