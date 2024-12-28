import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class IDandPasswords {
    public String newUsername , newPassword;
    private HashMap<String, String> logininfo = new HashMap<>();
    private final static String filePath = "users.txt"; // ملف لتخزين المستخدمين

    // Constructor: Initialize from file or default user
    IDandPasswords() {
        loadFromFile();
    }

    // Retrieve login info
    public HashMap<String, String> getLoginInfo() {
        return logininfo;
    }

    // Add new user method
    public void addNewUser() {
        Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.print("Enter new username: ");
            newUsername = scanner.nextLine();
            if (logininfo.containsKey(newUsername)) {
                System.out.println("Username already exists. Please try again with a different username.");
            }
        }while(logininfo.containsKey(newUsername));

            System.out.print("Enter new password: ");
            newPassword = scanner.nextLine();

            // Add new username and password to the map
            logininfo.put(newUsername, newPassword);
            saveToFile(); // Save updates to file
            System.out.println("User added successfully!");


        // Check if username already exists

    }
    public void addNewUserEd() {


        logininfo.put(newUsername, newPassword);
        saveToFile();
        System.out.println("User added successfully!");


    }

    // Load users from file
    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    logininfo.put(parts[0], parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("User file not found. Default user will be added.");
            logininfo.put("Laith", "l1234"); // Default user
        } catch (IOException e) {
            System.out.println("Error reading the user file: " + e.getMessage());
        }
    }

    // Save users to file
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String username : logininfo.keySet()) {
                writer.write(username + ":" + logininfo.get(username));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to the user file: " + e.getMessage());
        }
    }

}
