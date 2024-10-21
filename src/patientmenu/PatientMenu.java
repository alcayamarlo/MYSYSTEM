package patientmenu;

import java.util.Scanner;

public class PatientMenu {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int choice;
            String response;
            do {
                System.out.println("------------------------");
                System.out.println("1. ADD PATIENT :        |");
                System.out.println("2. VIEW :               |");
                System.out.println("3. UPDATE :             |");
                System.out.println("4. DELETE :             |");
                System.out.println("5. EXIT :               |");
                System.out.println("------------------------");
                System.out.print("Enter your choice: ");

                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    sc.next(); 
                    System.out.print("Enter your choice: ");
                }
                choice = sc.nextInt();

             
                while (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    choice = sc.nextInt();
                }

                PatientMenu patient = new PatientMenu();
                sc.nextLine(); 

                switch (choice) {
                    case 1:
                        addPatient(sc);
                        break;
                    case 2:
                        viewPatient();
                        break;
                    case 3:
                        viewPatient();
                        updatePatient(sc);
                        viewPatient();
                        break;
                    case 4:
                        viewPatient();
                        deletePatient(sc);
                        viewPatient();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                }

               
                do {
                    System.out.print("Do you want to continue? (yes/no): ");
                    response = sc.nextLine().trim(); 
                    if (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
                        System.out.println("Invalid response. Please enter 'yes' or 'no'.");
                    }
                } while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no"));

            } while (response.equalsIgnoreCase("yes"));

            System.out.println("Thank you, see you soon!");
        }
    }

    public static void addPatient(Scanner sc) {
        config conf = new config();
        
        System.out.print("Patient Name: ");
        String name = sc.nextLine();
        System.out.print("Patient Admission Date: ");
        String adDate = sc.nextLine();
        System.out.print("Patient Discharge Date: ");
        String disDate = sc.nextLine();
        System.out.print("Patient Treatment Type: ");
        String ttype = sc.nextLine();
        
        String tbill;
        while (true) {
            System.out.print("Patient Total Bill: ");
            tbill = sc.nextLine();
            if (isNumeric(tbill)) {
                break; 
            } else {
                System.out.println("Error: Total Bill must be a number. Please try again.");
            }
        }
        
        System.out.print("Patient Payment Status: ");
        String pstatus = sc.nextLine();
        
        String sql = "INSERT INTO tbl_patients (p_name, p_admission_date, p_discharge_date, p_treatment_type, p_total_bill, p_payment_status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addPatients(sql, name, adDate, disDate, ttype, tbill, pstatus);
    }

    public static void viewPatient() {
        config conf = new config();
        String sqlQuery = "SELECT * FROM tbl_patients";
        String[] columnHeaders = {"Patient ID", "Patient Name", "Admission Date", "Discharge Date", "Treatment Type", "Total Bill", "Payment Status"};
        
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        String[] columnNames = {"p_id", "p_name", "p_admission_date", "p_discharge_date", "p_treatment_type", "p_total_bill", "p_payment_status"};
        conf.viewPatients(sqlQuery, columnHeaders, columnNames);
    }

    public static void updatePatient(Scanner sc) {
        config conf = new config();

        int id;
      
        while (true) {
            System.out.print("Enter the ID of the patient to edit: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                if (id > 0) {
                    sc.nextLine();
                    break; 
                } else {
                    System.out.println("Error: Patient ID must be a positive integer. Please try again.");
                }
            } else {
                System.out.println("Error: Invalid input. Please enter a positive integer.");
                sc.next();
            }
        }

        System.out.print("Enter new Patient Name: ");
        String name = sc.nextLine();
        System.out.print("Enter new Admission Date: ");
        String adDate = sc.nextLine();
        System.out.print("Enter new Discharge Date: ");
        String disDate = sc.nextLine();
        System.out.print("Enter new Treatment Type: ");
        String ttype = sc.nextLine();
        
        String tbill;
        while (true) {
            System.out.print("Enter new Total Bill: ");
            tbill = sc.nextLine();
            if (isNumeric(tbill)) {
                break; 
            } else {
                System.out.println("Error: Total Bill must be a number. Please try again.");
            }
        }

        System.out.print("Enter new Payment Status: ");
        String pstatus = sc.nextLine();

        String sql = "UPDATE tbl_patients SET p_name = ?, p_admission_date = ?, p_discharge_date = ?, p_treatment_type = ?, p_total_bill = ?, p_payment_status = ? WHERE p_id = ?";
        conf.updatePatients(sql, name, adDate, disDate, ttype, tbill, pstatus, id);
        System.out.println("Patient updated successfully.");
    }

    public static void deletePatient(Scanner sc) {
        config conf = new config();

        int id;
        while (true) {
            System.out.print("Enter the ID of the patient to delete: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                if (id > 0) {
                    break; 
                } else {
                    System.out.println("Error: Patient ID must be a positive integer. Please try again.");
                }
            } else {
                System.out.println("Error: Invalid input. Please enter a positive integer.");
                sc.next();
            }
        }

        String sql = "DELETE FROM tbl_patients WHERE p_id = ?";
        conf.deletePatients(sql, id);
        System.out.println("Patient deleted successfully.");
    }

    public static boolean isNumeric(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
