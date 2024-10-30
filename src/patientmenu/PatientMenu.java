package patientmenu;

import java.util.Scanner;

public class PatientMenu {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int choice;
            String response;
            config conf = new config(); 

            do {
                System.out.println("------------------------");
                System.out.println("1. ADD PATIENT          |");
                System.out.println("2. VIEW                 |");
                System.out.println("3. VIEW DATA            |");
                System.out.println("4. UPDATE               |");
                System.out.println("5. DELETE               |");
                System.out.println("6. EXIT                 |");
                System.out.println("------------------------");
                System.out.print("Enter your choice: ");

                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 6.");
                    sc.next();
                    System.out.print("Enter your choice: ");
                }
                choice = sc.nextInt();
                sc.nextLine();  

                switch (choice) {
                    case 1:
                        addPatient(sc);
                        break;
                    case 2:
                        viewAllPatients();
                        break;
                    case 3:
                        viewPatientById(sc, conf);
                        break;
                    case 4:
                        viewAllPatients();
                        updatePatient(sc);
                        viewAllPatients();
                        break;
                    case 5:
                        viewAllPatients();
                        deletePatient(sc);
                        viewAllPatients();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }

                System.out.print("Do you want to continue? (yes/no): ");
                response = sc.nextLine().trim();

               
                while (response.equalsIgnoreCase("no")) {
                    System.out.println("You chose not to continue. Showing the menu again.");
                    break; 
                }

            } while (true); 

        }
    }

    public static void addPatient(Scanner sc) {
        config conf = new config();
      
        System.out.print("First Name: ");
        String firstName = sc.nextLine();
        System.out.print("Last Name: ");
        String lastName = sc.nextLine();
        System.out.print("Gender: ");
        String gender = sc.nextLine();
        System.out.print("Status: ");
        String status = sc.nextLine();
        System.out.print("Admission Date (YYYY-MM-DD): ");
        String adDate = sc.nextLine();
        System.out.print("Discharge Date (YYYY-MM-DD): ");
        String disDate = sc.nextLine();
        System.out.print("Treatment Type: ");
        String ttype = sc.nextLine();
        String tbill;

        while (true) {
            System.out.print("Total Bill: ");
            tbill = sc.nextLine();
            if (isNumeric(tbill)) break;
            System.out.println("Error: Total Bill must be a number. Please try again.");
        }

        System.out.print("Payment Status: ");
        String pstatus = sc.nextLine();

        String sql = "INSERT INTO tbl_patients (p_first_name, p_last_name, p_gender, p_status, p_admission_date, p_discharge_date, p_treatment_type, p_total_bill, p_payment_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        conf.addPatients(sql, firstName, lastName, gender, status, adDate, disDate, ttype, tbill, pstatus);
    }

    public static void viewAllPatients() {
        config conf = new config();
        String sqlQuery = "SELECT * FROM tbl_patients";
        String[] columnHeaders = {"Patient ID", "First Name", "Last Name", "Gender", "Status", "Admission Date", "Discharge Date", "Treatment Type", "Total Bill", "Payment Status"};
        String[] columnNames = {"p_id", "p_first_name", "p_last_name", "p_gender", "p_status", "p_admission_date", "p_discharge_date", "p_treatment_type", "p_total_bill", "p_payment_status"};
        
        conf.viewPatients(sqlQuery, columnHeaders, columnNames);
    }

    public static void viewPatientById(Scanner sc, config conf) {
        System.out.print("Enter the ID of the patient to view: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Enter a valid patient ID.");
            sc.next();
        }
        int id = sc.nextInt();
        sc.nextLine(); 
        
        String sqlQuery = "SELECT * FROM tbl_patients WHERE p_id = ?";
        String[] columnHeaders = {"Patient ID", "First Name", "Last Name", "Gender", "Status", "Admission Date", "Discharge Date", "Treatment Type", "Total Bill", "Payment Status"};
        String[] columnNames = {"p_id", "p_first_name", "p_last_name", "p_gender", "p_status", "p_admission_date", "p_discharge_date", "p_treatment_type", "p_total_bill", "p_payment_status"};
        
        conf.viewPatientById(sqlQuery, id, columnHeaders, columnNames);
    }

    public static void updatePatient(Scanner sc) {
        config conf = new config();
        System.out.print("Enter the ID of the patient to edit: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Enter a valid patient ID.");
            sc.next();
        }
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("New First Name: ");
        String firstName = sc.nextLine();
        System.out.print("New Last Name: ");
        String lastName = sc.nextLine();
        System.out.print("New Gender: ");
        String gender = sc.nextLine();
        System.out.print("New Status: ");
        String status = sc.nextLine();
        System.out.print("New Admission Date (YYYY-MM-DD): ");
        String adDate = sc.nextLine();
        System.out.print("New Discharge Date (YYYY-MM-DD): ");
        String disDate = sc.nextLine();
        System.out.print("New Treatment Type: ");
        String ttype = sc.nextLine();
        String tbill;

        while (true) {
            System.out.print("New Total Bill: ");
            tbill = sc.nextLine();
            if (isNumeric(tbill)) break;
            System.out.println("Total Bill must be a number. Try again.");
        }

        System.out.print("New Payment Status: ");
        String pstatus = sc.nextLine();

        String sql = "UPDATE tbl_patients SET p_first_name = ?, p_last_name = ?, p_gender = ?, p_status = ?, p_admission_date = ?, p_discharge_date = ?, p_treatment_type = ?, p_total_bill = ?, p_payment_status = ? WHERE p_id = ?";
        conf.updatePatients(sql, firstName, lastName, gender, status, adDate, disDate, ttype, tbill, pstatus, id);
    }

    public static void deletePatient(Scanner sc) {
        config conf = new config();
        System.out.print("Enter the ID of the patient to delete: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Enter a valid patient ID.");
            sc.next();
        }
        int id = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM tbl_patients WHERE p_id = ?";
        conf.deletePatients(sql, id);
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
