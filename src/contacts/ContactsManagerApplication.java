package contacts;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ContactsManagerApplication {

    private static final String directory = "data";
    private static final String fileName = "contacts.txt";
    private static final String filePath = directory + "/" + fileName;

    public static void main(String[] args) {

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
