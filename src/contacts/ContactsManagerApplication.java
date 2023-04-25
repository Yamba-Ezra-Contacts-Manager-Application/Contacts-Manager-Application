package contacts;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ContactsManagerApplication extends UtilMethod{

    public static String runContactApp () {
        try {
            Scanner scanner = new Scanner(System.in);
            int option;
            do {
                System.out.println("1. View contacts.");
                System.out.println("2. Add a new contact.");
                System.out.println("3. Search a contact by name.");
                System.out.println("4. Delete an existing contact.");
                System.out.println("5. Exit.");
                System.out.print("Enter an option (1, 2, 3, 4 or 5): ");
                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (option) {
                    case 1:
                        viewContacts();
                        break;
                    case 2:
                        addContact();
                        break;
                    case 3:
                        searchContact();
                        break;
                    case 4:
                        deleteContact();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } while (option != 5);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }catch (InputMismatchException e){
            System.out.println("+------------------------------+");
            System.out.println("Error! Input Mismatch! Please select option 1-5.");
            System.out.println("+------------------------------+");
            runContactApp();
            return "";
        }
        return "";
    }

    public static void main(String[] args) {
        createFile();
       runContactApp();


    }

}

