## Interswitch-task
A demo task for interswitch group
## Book Management System using Spring Boot
The Book Management System, built with Spring Boot, facilitates an efficient book inventory with attributes including title, genre (limited to Fiction, Thriller, Mystery, Poetry, Horror, and Satire), ISBN code, author details, and year of publication. 
Users can seamlessly search for books based on title, author, year of publication, or genre. The system incorporates a shopping cart for users to add and view selected books. 
Checkout options include Web, USSD, and Transfer payment methods, with a simulated payment process. Additionally, users can conveniently access their purchase history. 
This concise and powerful system ensures a user-friendly experience in managing and exploring a diverse book collection.

## Table of Contents
- [Getting Started](#getting-started)
    - [Prerequisites Requirement](#prerequisites-requirement)
    - [Installation](#installation)
    - [Optional Requirement](#optional-requirement)
- [Technologies Used](#technologies-used)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Postman Documentation](#postman-documentation)

## Getting Started

### Prerequisites Requirement

#### Note:
Please note that the values of the following keys in the application.properties
have been sent via email. Kindly add to the application.properties before launching 
the application:

1. AWS_REGION=*****
2. SERVICE_BUCKET_NAME=*****
3. AWS_ACCESS_KEY_ID=*****
4. AWS_SECRET_ACCESS_KEY=*****
5. application.security.jwt.secret-key=*****

Before getting started, ensure you have the following components installed:

1. **This project was built using JDK 17, you would need JDK 17 installed on you local machine.**

- [Java Development Kit (JDK 17)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)


### Installation

1. **Clone the Repository:**

   ```bash
   git https://github.com/Adewale103/interswitch-task
   cd book-store
   ```

2. **Configure the Database:**

   A default database configuration exist using H2 connection. 
   if there is need to change, modify the `src/main/resources/application.properties` file to include your database connection details. 
   
   ```
## Build and Run the Application:

Execute the following command to build and run the application:

````bash
mvn spring-boot:run
````

## Access the Application

Open your browser and navigate to `http://localhost:8086` to access the Book Management System.

### Optional Requirement (To run with docker file)

1. **Docker.**
- **A docker file is on the project root directory, you would need docker installed on your local machine to run the docker file.**
- **The first command builds the docker image.**
- **The second command runs the docker build.**

    ```bash
   docker build -t book-store:latest . 
   
   docker run -d -p 6082:6082 book-store:latest
    ```
- [Docker](https://www.docker.com/products/docker-desktop/)

## Technologies Used

The Book Management System leverages modern technologies to deliver a robust and efficient experience:

- **Java**: The fundamental programming language employed for crafting application logic.
- **Spring Boot**: A powerful framework for building robust and scalable applications.
- **Spring Data JPA**: Provides data access and manipulation capabilities using the Java Persistence API.
- **Spring Web**: Facilitates the creation of web APIs and interfaces.
- **H2**: A widely-used in-memory database management system.
- **Maven**: Manages project dependencies and provides a structured build process.
- **Git**: Version control for collaborative development.
- **Docker**: Containerization lets you build, test, and deploy applications quickly.
- **AmazonS3**: A remote storage client where books were being uploaded to and saved.

## Usage

## API Endpoints

The Book Management System offers the following API endpoints:

### Authentication Endpoints

- Register: `POST /api/v1/auth/register`
- Login: `POST /api/v1/auth/login`
- Refresh Token: `POST /api/v1/auth/refresh-token`
- Get Authentication: `POST /api/v1/auth/info`
- Logout: `POST /api/v1/auth/logout`

### Author Endpoints

- Add new author: `POST /api/v1/writer/create`
- Update author: `PUT /api/v1/writer/update?bookId=id`
- Delete author: `DELETE /api/v1/writer/delete?authorId=id`
- 
### Book Endpoints

- Add new book: `POST /api/v1/book/create`
- Update book: `PUT /api/v1/book/update?bookId=id`
- Delete book: `DELETE /api/v1/book/delete?bookId=id`
- Search book: `GET /api/v1/book/search-book?q=title~title&page=0&size=10`
- Filter book by author: `GET /api/v1/book/filter/{id}`
- Get all books: `GET /api/v1/book/all`

### Cart Endpoints

- Add to cart: `POST /api/v1/cart/add`
- Get cart items: `GET /api/v1/cart/get/{id}`
- Remove from cart: `DELETE /api/v1/cart/delete/{cartId}/{isbn}`

### Payment Endpoints

- Checkout via ussd: `POST /api/v1/payment/ussd`
- Checkout via web: `POST /api/v1/payment/web`
- Checkout via transfer: `POST /api/v1/payment/transfer`
- View purchase history: `Get /api/v1/payment/uview/{id}`

## postman documentation

For more detailed information about these API endpoints, refer to the API documentation.
- [Postman Documentation Collection](https://documenter.getpostman.com/view/21596281/2s9YknAi7y)


