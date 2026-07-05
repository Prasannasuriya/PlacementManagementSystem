-- Create Database if not exists
CREATE DATABASE IF NOT EXISTS placement_db;
USE placement_db;

-- Table structure for table `students`
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    cgpa DOUBLE NOT NULL,
    placed VARCHAR(100) DEFAULT 'No'
);

-- Table structure for table `companies`
CREATE TABLE IF NOT EXISTS companies (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL,
    minimum_cgpa DOUBLE NOT NULL
);

-- Sample Data for Demonstration (Optional)
-- INSERT INTO students (name, department, cgpa, placed) VALUES ('Alice Smith', 'Computer Science', 8.5, 'No');
-- INSERT INTO students (name, department, cgpa, placed) VALUES ('Bob Jones', 'Information Technology', 7.2, 'No');
-- INSERT INTO companies (company_name, minimum_cgpa) VALUES ('Tech Corp', 8.0);
-- INSERT INTO companies (company_name, minimum_cgpa) VALUES ('Info Sys', 7.0);
