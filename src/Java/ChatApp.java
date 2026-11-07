/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Java;

/**
 *
 * @author user
 */
import java.util.ArrayList;
import java.util.Scanner;

public class ChatApp {
    private Login login;                    // Handles registration and login
    private MessageManager messageManager;  // Holds the message arrays and searches
    private Scanner scanner;                // For reading user input

    public ChatApp() {
        this.login = new Login();
        this.messageManager = new MessageManager();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        ChatApp app = new ChatApp();
        app.start();
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

        scanner.close();
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

        // Password: validate immediately, loop until valid
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

        // Phone number: validate immediately, loop until valid
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

        // First and last name: no formatting rules
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

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.println();
        System.out.println("----------------------------------------");

        boolean isAuthenticated = login.loginUser(username, password);

        if (isAuthenticated) {
            System.out.println("Welcome " + login.getFirstName() + " " + login.getLastName() +
                               " it is great to see you.");
            System.out.println();
            System.out.println("Login successful! You are now logged in.");
            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println();

            quickChatMenu();
        } else {
            System.out.println("Username or password incorrect, please try again.");
            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println();
        }
    }

    // QuickChat messaging menu (after login)
    public void quickChatMenu() {
        System.out.println("========================================");
        System.out.println("Welcome to QuickChat.");
        System.out.println("========================================");
        System.out.println();

        // Load any messages stored in earlier sessions into the arrays
        messageManager.readJsonFile();

        int choice = 0;

        // Keep showing the QuickChat menu until the user chooses Quit
        while (choice != 3) {
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
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
                case 4:
                    storedMessagesMenu();
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid choice! Please enter 1, 2, 3 or 4.");
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

                String check = new Message(recipient, "test", i).checkRecipientCell();
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

                String lengthCheck = new Message(recipient, messageText, i).checkMessageLength();
                System.out.println(lengthCheck);

                if (lengthCheck.equals("Message ready to send.")) {
                    validMessage = true;
                }
            }

            // Constructor auto-generates the ID and hash; i is the message number
            Message message = new Message(recipient, messageText, i);

            System.out.println();
            System.out.println(message.messageIDGenerated());
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

            // The message now has its flag, so file it in the right array
            messageManager.populateArrays(message);
            System.out.println();
        }

        System.out.println("----------------------------------------");
        System.out.println("Total messages sent: " + Message.getTotalMessagesSent());
        System.out.println("----------------------------------------");
        System.out.println();
    }

    // Option 4: sub-menu that uses the arrays in MessageManager
    private void storedMessagesMenu() {
        String choice = "";

        while (!choice.equals("g")) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("            STORED MESSAGES             ");
            System.out.println("========================================");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search for all messages for a recipient");
            System.out.println("e) Delete a message using its hash");
            System.out.println("f) Display a report of all stored messages");
            System.out.println("g) Back to QuickChat menu");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a":
                    String sender = login.getFirstName() + " " + login.getLastName();
                    System.out.println(messageManager.displaySenderAndRecipient(sender));
                    break;
                case "b":
                    System.out.println("Longest stored message: " + messageManager.getLongestMessage());
                    break;
                case "c":
                    searchByIDOption();
                    break;
                case "d":
                    searchByRecipientOption();
                    break;
                case "e":
                    deleteByHashOption();
                    break;
                case "f":
                    System.out.println(messageManager.displayReport());
                    break;
                case "g":
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a, b, c, d, e, f or g.");
            }
        }
    }

    private void searchByIDOption() {
        System.out.print("Enter the message ID: ");
        String id = scanner.nextLine().trim();

        Message found = messageManager.searchByMessageID(id);
        if (found == null) {
            System.out.println("No message with that ID was found.");
        } else {
            System.out.println("Recipient: " + found.getRecipient());
            System.out.println("Message: " + found.getMessageText());
        }
    }

    private void searchByRecipientOption() {
        System.out.print("Enter the recipient's cell number: ");
        String number = scanner.nextLine().trim();

        ArrayList<String> results = messageManager.searchByRecipient(number);
        if (results.isEmpty()) {
            System.out.println("No messages were found for that recipient.");
        } else {
            for (String text : results) {
                System.out.println(text);
            }
        }
    }

    private void deleteByHashOption() {
        System.out.print("Enter the message hash (shown in the report): ");
        String hash = scanner.nextLine().trim();

        String result = messageManager.deleteMessageByHash(hash);
        System.out.println(result);

        // Update the file too, so the deleted message does not come back
        if (result.startsWith("Message:")) {
            messageManager.saveStoredMessagesToFile();
        }
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