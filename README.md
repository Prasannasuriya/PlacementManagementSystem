# Student Placement Management System

A Java CLI-based console application designed to manage student placement records, company recruitment requirements, and check student eligibility. It uses **JDBC** to connect with a **MySQL** database for persistence and **Maven** for build automation and dependency management.

---

## 🚀 Key Features

1. **Add Student**: Insert student details (Name, Department, CGPA) with default placement status set to 'No'.
2. **View Students**: Displays all students in a neatly formatted tabular view.
3. **Add Company**: Insert hiring companies and their minimum required CGPA criteria.
4. **Check Eligibility**: Lists all eligible unplaced students (CGPA >= company's minimum CGPA requirements) for any specific company.
5. **Update Placement Status**: Updates student placement status (e.g. set to 'Yes' or record the hiring company).

---

## 🛠️ Tech Stack & Prerequisites

*   **Java Development Kit (JDK) 17** or higher
*   **MySQL Server** (running locally on port 3306)
*   **Apache Maven** (for dependencies and compilation)
*   **Git** (for version control)

---

## 📁 Repository Structure

```text
PlacementManagementSystem/
├── src/
│   └── main/
│       └── java/
│           ├── Main.java                 # Entry point of the CLI application
│           ├── DBConnection.java         # Establishes connection to MySQL DB
│           ├── PlacementService.java     # Contains core business & database logic
│           ├── Student.java              # Student model class
│           └── Company.java              # Company model class
├── pom.xml                               # Maven project configuration file
├── schema.sql                            # Database initialization script
├── .gitignore                            # Standard git ignore definitions
└── README.md                             # Documentation file (this file)
```

---

## ⚙️ Setup and Installation

### 1. Database Setup

Make sure your MySQL server is running, then log in and run the `schema.sql` file to create the database and tables:

```bash
mysql -u root -p < schema.sql
```

Alternatively, you can run the SQL queries inside the `schema.sql` file manually in your favorite database administration tool (such as MySQL Workbench, phpMyAdmin, or Command Line Client).

### 2. Configure Database Credentials

Open [DBConnection.java](src/main/java/DBConnection.java) and modify the JDBC connection details if yours differ from default (`root`/`password`):

```java
con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/placement_db",
        "YOUR_MYSQL_USERNAME",
        "YOUR_MYSQL_PASSWORD"
);
```

---

## 💻 Build & Execution Instructions

This project is configured using Maven, which downloads the necessary JDBC drivers and handles compiler configurations.

### Compile the Application

Navigate to the project root directory and execute:

```bash
mvn clean compile
```

### Run the Application

Run the compiled Java class using the Exec Maven Plugin:

```bash
mvn exec:java
```

---

## 🎮 How to Use the Application

Upon running, you'll be greeted with the following interactive menu options:

```text
===== STUDENT PLACEMENT MANAGEMENT SYSTEM =====
1. Add Student
2. View Students
3. Add Company
4. Check Eligibility
5. Update Placement Status
6. Exit
Enter Choice: 
```

1. **Add Student**: Input Student Name, Department, and CGPA (e.g. `8.75`).
2. **View Students**: Displays the table of current student records.
3. **Add Company**: Input Company Name and Minimum CGPA requirement (e.g. `8.0`).
4. **Check Eligibility**: Lists all companies. Enter the Company ID to see which unplaced students are eligible.
5. **Update Placement Status**: Enter the Student ID and input the status (e.g. 'Yes' or 'Placed in Tech Corp').
6. **Exit**: Gracefully exits the application.

---

## 🤝 Contributing

Contributions are welcome! Please fork the repository, make your changes, and submit a pull request.
