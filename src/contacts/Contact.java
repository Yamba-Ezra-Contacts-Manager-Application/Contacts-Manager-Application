package contacts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Collections;

public class Contact {
    private static final String directory = "data";
    private static final String fileName = "contacts.txt";
    private static final String filePath = directory + "/" + fileName;
    private String name;
    private String phoneNumber;

    public Contact(){
    }

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber;
    }

    public static String runContactApp () {
        try {
            // Create the data directory if it does not exist
            Path dataDirectory = Paths.get(directory);
            if (Files.notExists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }

            // Create the contacts file if it does not exist
            Path dataFile = Paths.get(directory, fileName);
            if (!Files.exists(dataFile)) {
                Files.createFile(dataFile);
            }

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
    // Display contacts from the contact file
    private static void viewContacts() throws IOException {
        List<Contact> contacts = readContactsFromFile();
        System.out.println("Contacts:");
        System.out.println("+------------------------------+");
        System.out.println("| Name          | Phone number |");
        System.out.println("+---------------+--------------+");
        for (Contact contact : contacts) {
            System.out.printf( "| %-14s| %-12s |%n",contact.getName(),contact.getPhoneNumber());
        }
        System.out.println("+---------------+--------------+");
    }
    // Add a contact to the contact filer
    private static void addContact() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the contact: ");
        String name = scanner.nextLine();
        if (searchDuplicateContact(name)){
            System.out.printf("There's already a contact named %s. Do you want to overwrite it? (Yes/No)\n",name);
            String userConfirm = scanner.nextLine();
            if (userConfirm.equalsIgnoreCase("Yes") || userConfirm.equalsIgnoreCase("y")){
                System.out.print("Enter the phone number of the contact: ");
                String phoneNumber = scanner.nextLine();
                List<Contact> contacts = readContactsFromFile();
                List<Contact> newContacts = new ArrayList<>();
                for (Contact con: contacts){
                    if (!con.getName().equalsIgnoreCase(name.trim())){
                        newContacts.add(con);
                    }
                }
                Contact contact = new Contact(name, phoneNumFormatted(phoneNumber));
                newContacts.add(contact);
                writeContactsToFile(newContacts);
                System.out.println("Contact added successfully.");
            } else {
                runContactApp();
            }
        }
    }
    //Search a contact by name
    private static void searchContact() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the contact to search: ");
        String searchName = scanner.nextLine();
        List<Contact> contacts = readContactsFromFile();
        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(searchName)) {
                System.out.println("Contact found:");
                System.out.println("Name: " + contact.getName());
                System.out.println("Phone number: " + contact.getPhoneNumber());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Contact not found.");
        }
    }
    //Search for a duplicate name return true/false
    private static boolean searchDuplicateContact(String name) throws IOException {
        List<Contact> contacts = readContactsFromFile();
        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name.trim())) {
                found = true;
                break;
            }
        }
        return found;
    }
    //Delete a contact from the contact file
    private static void deleteContact() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the contact to delete ");
        String deleteName = scanner.nextLine();
        List<Contact> contacts = readContactsFromFile();
        boolean found = false;
        for (Iterator<Contact> iterator = contacts.iterator(); iterator.hasNext(); ) {
            Contact contact = iterator.next();
            if (contact.getName().equalsIgnoreCase(deleteName)) {
                iterator.remove();
                found = true;
                System.out.println("Contact deleted successfully.");
                break;
            }
        }
        if (!found) {
            System.out.println("Contact not found.");
        } else {
            writeContactsToFile(contacts);
        }
    }
    //
    private static List<Contact> readContactsFromFile() throws IOException {
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String phoneNumber = parts[1].trim();
                    Contact contact = new Contact(name, phoneNumFormatted(phoneNumber));
                    contacts.add(contact);
                }
            }
        }
        return contacts;
    }

    public static String phoneNumFormatted (String phoneNum){
        if (phoneNum.length() == 10) {
            String phoneNum1 = phoneNum.substring(0, 3);
            String phoneNum2 = phoneNum.substring(3, 6);
            String phoneNum3 = phoneNum.substring(6, 10);
            return phoneNum1 + "-" + phoneNum2 + "-" + phoneNum3;
        } else if (phoneNum.length() == 7) {
            String phoneNum1 = phoneNum.substring(0, 3);
            String phoneNum2 = phoneNum.substring(3, 7);
            return phoneNum1 + "-" + phoneNum2;
        } else {
            return phoneNum;
        }
    }

    private static void writeContactsToFile(List<Contact> contacts) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            for (Contact contact : contacts) {
                String line = contact.getName() + " | " + contact.getPhoneNumber();
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
