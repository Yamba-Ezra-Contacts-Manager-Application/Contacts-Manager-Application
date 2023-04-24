package contacts;

import java.io.*;
import java.nio.file.*;
import java.util.*;
public class ContactsManagerApplication {

    private static final String directory = "data";
    private static final String fileName = "contacts.txt";
    private static final String filePath = directory + "/" + fileName;

    public static void main(String[] args) {
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
        }
    }
    // Display contacts from the contact file
    private static void viewContacts() throws IOException {
        List<Contact> contacts = readContactsFromFile();
        System.out.println("Contacts:");
        System.out.println("Name        | Phone number");
        System.out.println("--------------------------");
        for (Contact contact : contacts) {
            System.out.println(contact.getName() + " | " + contact.getPhoneNumber());
        }
    }
    // Add a contact to the contact filer
    private static void addContact() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the contact: ");
        String name = scanner.nextLine();
        System.out.print("Enter the phone number of the contact: ");
        String phoneNumber = scanner.nextLine();

        Contact contact = new Contact(name, phoneNumber);
        List<Contact> contacts = readContactsFromFile();
        contacts.add(contact);
        writeContactsToFile(contacts);
        System.out.println("Contact added successfully.");
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
                    Contact contact = new Contact(name, phoneNumber);
                    contacts.add(contact);
                }
            }
        }
        return contacts;
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

