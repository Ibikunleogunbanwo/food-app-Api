# 🍔 Food Delivery Application - Backend API


## 📝 Description

This is a backend API for a food delivery application built with **Spring Boot**. It provides endpoints for managing users, vendors (business owners), and stores. The API supports CRUD operations, authentication, and various filtering options for stores.

---

## 🚀 Features


### 👤 User Management

- User registration and profile management  
- Password encryption using **MD5**  
- Address and contact information storage

### 🧑‍💼 Vendor Management

- Vendor registration and profile management  
- Store creation and management  
- ID card and profile photo storage

### 🏪 Store Management

- Store creation with details like address, category, and operating hours  
- Filter stores by category, name, location, or keyword  
- Manage delivery and pickup availability

---

## 🔧 Technologies Used

- Java 17  
- Spring Boot 3.x  
- Spring Data JPA  
- Hibernate  
- MySQL Database  
- Lombok for boilerplate code reduction  
- Maven for dependency management

---

## 📂 Project Structure


<pre>

src/
├── main/
│   ├── java/
│   │   └── com/Afrochow/food_app/
│   │       ├── config/          # Configuration classes and constants
│   │       ├── controller/      # REST controllers
│   │       ├── DTO/             # Data Transfer Objects
│   │       ├── model/           # Entity classes
│   │       ├── pojo/            # Plain Java Objects for requests/responses
│   │       ├── repository/      # JPA repositories
│   │       └── services/        # Business logic
│   └── resources/               # Configuration files

</pre>


## 🛠️ Installation & Setup

### Prerequisites

- Java 17 JDK  
- Maven 3.8+  
- MySQL 8.0+

### Clone the repository

```bash

git clone https://github.com/yourusername/food-delivery-api.git
cd food-delivery-api

Configure the database

	1.	Create a MySQL database
	2.	Update application.properties with your database credentials


Build and run
mvn clean install
mvn spring-boot:run
```

🌐 API Endpoints

<pre>

👤 User Endpoints
  
• POST    /food-api/customer/register       - Register a new user
• PUT     /food-api/customer/update         - Update user profile
• GET     /food-api/customer                - Get all users
• GET     /food-api/customer/:id            - Get user by ID
• DELETE  /food-api/customer/delete-user    - Delete a user

🧑‍💼 Vendor Endpoints
  
• POST    /food-api/vendor/register         - Register a new vendor
• PUT     /food-api/vendor/update           - Update vendor profile
• GET     /food-api/vendor                  - Get all vendors
• GET     /food-api/vendor/:id              - Get vendor by ID
• DELETE  /food-api/vendor/delete           - Delete a vendor

🏪 Store Endpoints
  
• POST    /food-api/store/register          - Create a new store
• PUT     /food-api/store/update            - Update store details
• GET     /food-api/store                   - Get all stores (filterable by category/name)
• GET     /food-api/stores/:id              - Get store by ID
• GET     /food-api/stores/search           - Search stores by keyword
• GET     /food-api/stores/location         - Search stores by location
• DELETE  /food-api/store/delete-store      - Delete a store

</pre>


⸻
<pre>

🔒 Security
  
	•	Password encryption using MD5 hashing
	•	Input validation for all endpoints
	•	Proper error handling and status codes

</pre>
⸻

🤝 Contributing

Contributions are welcome!
Please fork the repository and create a pull request with your changes.

⸻

📧 Contact

For questions or support, reach out via email: ibikunleogunbanwo@gmail.com
