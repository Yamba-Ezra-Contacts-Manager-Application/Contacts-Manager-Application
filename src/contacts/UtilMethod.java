package contacts;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class UtilMethod {
    private static final String directory = "data";
    private static final String fileName = "contacts.txt";

    private static final String filePath = directory + "/" + fileName;

  static void createFile(){
      // Create the data directory if it does not exist
      try{
          Path dataDirectory = Paths.get(directory);
          if (Files.notExists(dataDirectory)) {
              Files.createDirectories(dataDirectory);
          }

          // Create the contacts file if it does not exist
          Path dataFile = Paths.get(directory, fileName);
          if (!Files.exists(dataFile)) {
              Files.createFile(dataFile);
          }
      }catch (IOException e) {
          System.err.println("Error: " + e.getMessage());
      }
  }
    static void viewContacts() throws IOException {
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
    static void addContact() throws IOException {
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
    static void searchContact() throws IOException {
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
    static void deleteContact() throws IOException {
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
    public static void main(String[] args) {

    }
}
