import java.sql.*;
import java.util.Scanner;

public class PlacementService {

    Scanner sc = new Scanner(System.in);

    Connection con = DBConnection.getConnection();

    // Add Student
    public void addStudent() {
        if (con == null) {
            System.out.println("Database connection failed!");
            return;
        }

        try {

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            System.out.print("Enter CGPA: ");
            double cgpa = sc.nextDouble();
            sc.nextLine();

            String placed = "No";

            String query = "INSERT INTO students(name, department, cgpa, placed) VALUES(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, dept);
            ps.setDouble(3, cgpa);
            ps.setString(4, placed);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student Added Successfully");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void viewStudents() {
        if (con == null) {
            System.out.println("Database connection failed!");
            return;
        }
        try {
            String query = "SELECT * FROM students";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("\n=================================== STUDENTS LIST ===================================");
            System.out.printf("| %-5s | %-25s | %-20s | %-6s | %-15s |\n", "ID", "Name", "Department", "CGPA", "Placed");
            System.out.println("-------------------------------------------------------------------------------------");
            boolean hasStudents = false;
            while (rs.next()) {
                hasStudents = true;
                System.out.printf("| %-5d | %-25s | %-20s | %-6.2f | %-15s |\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("cgpa"),
                        rs.getString("placed")
                );
            }
            if (!hasStudents) {
                System.out.println("|                                No students found.                                 |");
            }
            System.out.println("=====================================================================================");
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error viewing students: " + e.getMessage());
        }
    }

    public void addCompany() {
        if (con == null) {
            System.out.println("Database connection failed!");
            return;
        }
        try {
            System.out.print("Enter Company Name: ");
            String companyName = sc.nextLine();

            System.out.print("Enter Minimum CGPA Required: ");
            double minimumCgpa = sc.nextDouble();
            sc.nextLine(); // consume leftover newline

            String query = "INSERT INTO companies(company_name, minimum_cgpa) VALUES(?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, companyName);
            ps.setDouble(2, minimumCgpa);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Company Added Successfully!");
            }
            ps.close();
        } catch (Exception e) {
            System.out.println("Error adding company: " + e.getMessage());
        }
    }

    public void checkEligibility() {
        if (con == null) {
            System.out.println("Database connection failed!");
            return;
        }
        try {
            // First, display all companies
            String listCompaniesQuery = "SELECT * FROM companies";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(listCompaniesQuery);

            System.out.println("\n============================= AVAILABLE COMPANIES =============================");
            System.out.printf("| %-10s | %-30s | %-15s |\n", "Comp ID", "Company Name", "Min CGPA Req");
            System.out.println("--------------------------------------------------------------------------------");
            boolean hasCompanies = false;
            while (rs.next()) {
                hasCompanies = true;
                System.out.printf("| %-10d | %-30s | %-15.2f |\n",
                        rs.getInt("company_id"),
                        rs.getString("company_name"),
                        rs.getDouble("minimum_cgpa")
                );
            }
            System.out.println("================================================================================");
            rs.close();
            stmt.close();

            if (!hasCompanies) {
                System.out.println("No companies available. Please add a company first.");
                return;
            }

            System.out.print("Enter Company ID to check student eligibility: ");
            int companyId = sc.nextInt();
            sc.nextLine(); // consume newline

            // Get the specific company details
            String findCompanyQuery = "SELECT * FROM companies WHERE company_id = ?";
            PreparedStatement psCompany = con.prepareStatement(findCompanyQuery);
            psCompany.setInt(1, companyId);
            ResultSet rsCompany = psCompany.executeQuery();

            if (rsCompany.next()) {
                String companyName = rsCompany.getString("company_name");
                double minCgpa = rsCompany.getDouble("minimum_cgpa");

                System.out.println("\nEligible unplaced students for: " + companyName + " (Min CGPA: " + minCgpa + ")");
                
                String eligibleQuery = "SELECT * FROM students WHERE cgpa >= ? AND placed = 'No'";
                PreparedStatement psStudents = con.prepareStatement(eligibleQuery);
                psStudents.setDouble(1, minCgpa);
                ResultSet rsStudents = psStudents.executeQuery();

                System.out.println("================================= ELIGIBLE LIST =================================");
                System.out.printf("| %-5s | %-25s | %-20s | %-6s | %-15s |\n", "ID", "Name", "Department", "CGPA", "Placed");
                System.out.println("---------------------------------------------------------------------------------");
                boolean hasEligible = false;
                while (rsStudents.next()) {
                    hasEligible = true;
                    System.out.printf("| %-5d | %-25s | %-20s | %-6.2f | %-15s |\n",
                            rsStudents.getInt("id"),
                            rsStudents.getString("name"),
                            rsStudents.getString("department"),
                            rsStudents.getDouble("cgpa"),
                            rsStudents.getString("placed")
                    );
                }
                if (!hasEligible) {
                    System.out.println("|                No eligible unplaced students found for this company.           |");
                }
                System.out.println("=================================================================================");
                rsStudents.close();
                psStudents.close();
            } else {
                System.out.println("Invalid Company ID. No such company exists.");
            }
            rsCompany.close();
            psCompany.close();
        } catch (Exception e) {
            System.out.println("Error checking eligibility: " + e.getMessage());
        }
    }

    public void updatePlacementStatus() {
        if (con == null) {
            System.out.println("Database connection failed!");
            return;
        }
        try {
            System.out.print("Enter Student ID to update status: ");
            int studentId = sc.nextInt();
            sc.nextLine(); // consume newline

            // Verify if student exists
            String checkStudentQuery = "SELECT * FROM students WHERE id = ?";
            PreparedStatement psCheck = con.prepareStatement(checkStudentQuery);
            psCheck.setInt(1, studentId);
            ResultSet rsCheck = psCheck.executeQuery();

            if (rsCheck.next()) {
                String name = rsCheck.getString("name");
                String currentStatus = rsCheck.getString("placed");
                System.out.println("Found student: " + name + " (Current Status: " + currentStatus + ")");

                System.out.print("Enter New Placement Status (e.g. Yes / No / Company Name): ");
                String newStatus = sc.nextLine();

                String updateQuery = "UPDATE students SET placed = ? WHERE id = ?";
                PreparedStatement psUpdate = con.prepareStatement(updateQuery);
                psUpdate.setString(1, newStatus);
                psUpdate.setInt(2, studentId);

                int rows = psUpdate.executeUpdate();
                if (rows > 0) {
                    System.out.println("Placement status updated successfully!");
                }
                psUpdate.close();
            } else {
                System.out.println("Student with ID " + studentId + " not found.");
            }
            rsCheck.close();
            psCheck.close();
        } catch (Exception e) {
            System.out.println("Error updating placement status: " + e.getMessage());
        }
    }
}