/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Java;

/**
 *
 * @author user
 */
import java.util.Scanner;

public class ChatApp {
    private Login login;        // The Login object that handles all logic
    private Scanner scanner;    // For reading user input

    public ChatApp() {
        this.login = new Login();          // Create a Login object
        this.scanner = new Scanner(System.in);  // Create a Scanner for input
    }

    public static void main(String[] args) {
        ChatApp app = new ChatApp();  // Create a ChatApp object
        app.start();                  // Start the application
    }

    public void start() {
        System.out.println("========================================");
        System.out.println("     WELCOME TO THE CHAT APPLICATION    ");
        System.out.println("========================================");
        System.out.println();

        int choice = 0;

        // Main menu loop
        do {
            displayMenu();
            choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println();
                    System.out.println("Thank you for using the Chat Application!");
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid choice! Please enter 1, 2, or 3.");
                    System.out.println();
            }

        } while (choice != 3);

        scanner.close();  // Close the scanner when done
    }

    public void displayMenu() {
        System.out.println("========================================");
        System.out.println("            MAIN MENU                   ");
        System.out.println("========================================");
        System.out.println("1. Register New Account");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("========================================");
    }

    public void registerUser() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("          REGISTER NEW ACCOUNT          ");
        System.out.println("========================================");
        System.out.println();


        String username;
        do {
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            if (login.checkUserName(username)) {
                System.out.println("Username successfully captured.");
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        } while (!login.checkUserName(username));

        System.out.println();

        //Password validate immediately, loop until valid
        String password;
        do {
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            if (login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        } while (!login.checkPasswordComplexity(password));

        System.out.println();

        // Phone number validate immediately, loop until the input is  valid 
        String phoneNumber;
        do {
            System.out.print("Enter cell phone number: ");
            phoneNumber = scanner.nextLine();
            if (login.checkCellPhoneNumber(phoneNumber)) {
                System.out.println("Cell phone number successfully added.");
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            }
        } while (!login.checkCellPhoneNumber(phoneNumber));

        System.out.println();

        //first and last name no rules on he formating of the names 
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.println();
        System.out.println("----------------------------------------");


        String result = login.registerUser(username, password, phoneNumber,
                                           firstName, lastName);

        System.out.println(result);
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println();

        if (result.startsWith("Welcome")) {
            System.out.println("Your account has been created successfully!");
            System.out.println("Username: " + username);
            System.out.println("Phone: " + phoneNumber);
            System.out.println();
            System.out.println("You can now login using your credentials.");
        }
        System.out.println();
    }

    public void loginUser() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("              LOGIN                     ");
        System.out.println("========================================");
        System.out.println();

        // Check if anyone is registered yet
        if (login.getStoredUsername().isEmpty()) {
            System.out.println("No users are registered yet!");
            System.out.println("Please register an account first.");
            System.out.println();
            return;
        }

        // Get username
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        // Get password
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.println();
        System.out.println("----------------------------------------");

        // Call Login's loginUser method to verify
        boolean isAuthenticated = login.loginUser(username, password);

        // Display this message
        if (isAuthenticated) {
            String firstName = login.getFirstName();
            String lastName = login.getLastName();
            System.out.println("Welcome " + firstName + " " + lastName +
                               " it is great to see you.");
            System.out.println();
            System.out.println("Login successful! You are now logged in.");
            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println();

            // STEP 3: once logged in, hand off to the QuickChat messaging menu
            quickChatMenu();
        } else {
            System.out.println("Username or password incorrect, please try again.");
            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println();
        }
    }

    // ============================================
    // STEP 3: QuickChat messaging menu (after login)
    // ============================================
    public void quickChatMenu() {
        System.out.println("========================================");
        System.out.println("Welcome to QuickChat.");
        System.out.println("========================================");
        System.out.println();

        int choice = 0;

        // Keep showing the QuickChat menu until the user chooses Quit
        while (choice != 3) {
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("========================================");

            choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    sendMessages();
                    break;
                case 2:
                    System.out.println();
                    System.out.println("Coming Soon.");
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    System.out.println("Exiting QuickChat. Goodbye!");
                    System.out.println();
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid choice! Please enter 1, 2, or 3.");
                    System.out.println();
            }
        }
    }

    // Asks how many messages to send, then loops that many times
    // letting the user enter and process each message.
    private void sendMessages() {
    System.out.println();
    int numMessages = getIntInput("How many messages would you like to send? ");
    System.out.println();

    for (int i = 0; i < numMessages; i++) {
        System.out.println("----------------------------------------");
        System.out.println("Message " + (i + 1) + " of " + numMessages);
        System.out.println("----------------------------------------");

        // Recipient: loop until valid
        String recipient = "";
        boolean validRecipient = false;
        while (!validRecipient) {
            System.out.print("Enter recipient cell number (international code): ");
            recipient = scanner.nextLine();

            Message temp = new Message(recipient, "test");
            String check = temp.checkRecipientCell();
            System.out.println(check);

            if (check.equals("Cell phone number successfully captured.")) {
                validRecipient = true;
            }
        }

        // Message: loop until 250 characters or fewer
        String messageText = "";
        boolean validMessage = false;
        while (!validMessage) {
            System.out.print("Enter message (max 250 characters): ");
            messageText = scanner.nextLine();

            if (messageText.length() <= 250) {
                validMessage = true;
                System.out.println("Message sent");
            } else {
                System.out.println("Please enter a message of less than 250 characters.");
            }
        }

        // Constructor auto-generates the ID and hash
        Message message = new Message(recipient, messageText);

        System.out.println();
        System.out.println("Message ID: " + message.getMessageID());
        System.out.println("Message Hash: " + message.getMessageHash());
        System.out.println("Recipient: " + message.getRecipient());
        System.out.println("Message: " + message.getMessageText());
        System.out.println();

        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        int action = getIntInput("Choose an option: ");

        System.out.println(message.SentMessage(action));
        System.out.println();
    }

    System.out.println("----------------------------------------");
    System.out.println("Total messages sent: " + Message.getTotalMessagesSent());
    System.out.println("----------------------------------------");
    System.out.println();
    }
        private int getIntInput(String prompt) {
        int number = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                number = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        return number;
    }
}

