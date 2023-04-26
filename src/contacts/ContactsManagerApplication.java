package contacts;

import java.io.IOException;
import java.util.InputMismatchException;

public class ContactsManagerApplication extends UtilMethod{

    public static void runContactApp () {
        try {
            boolean confirm = true;
            do {
                int option = Menu.menu();
                switch (option) {
                    case 1 -> viewContacts();
                    case 2 -> addContact();
                    case 3 -> searchContact();
                    case 4 -> deleteContact();
                    case 5 -> {
                        System.out.println("Exiting...");
                        confirm = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } while (confirm);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }catch (InputMismatchException e){
            System.out.println("+------------------------------+");
            System.out.println("Error! Input Mismatch! Please select option 1-5.");
            System.out.println("+------------------------------+");
        }
    }

    public static void main(String[] args) {
        createFile();
        runContactApp();
    }

}

