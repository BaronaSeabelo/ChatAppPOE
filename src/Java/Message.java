/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Java;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Message Class - Handles all message-related functionality for Part 2.
 * Creates, validates, hashes, and stores messages.
 * 
 * @author Barona Seabelo
 * @version 1.0
 * @since 2026
 */
public class Message {
    // INSTANCE VARIABLES
    
    private String messageID;          // 10-digit unique ID
    private int messageNumber;         // Position of this message (0, 1, 2...)
    private String recipient;          // Cell number
    private String messageText;        // Message content
    private String messageHash;        // Format: 00:0:HITHANKS

    // Static variables - shared across all Message objects
    private static int totalMessagesSent = 0;
    private static ArrayList<Message> allMessages = new ArrayList<>();

    
    
    /**
     * Creates a new Message with auto-generated ID and hash.
     * 
     * @param recipient The recipient's cell number
     * @param messageText The message content
     */
    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = totalMessagesSent;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    
    // METHOD 1 checkMessageID()
    
    /**
     *Checks if the message ID is not more than 10 characters.
     *@return true if valid, false otherwise
     */
    
    public boolean checkMessageID() {
        if (messageID == null || messageID.isEmpty()) {
            return false;
        }
        return messageID.length() <= 10;
    }
    
    
    // METHOD 2: checkRecipientCell()
    /**
     * Validates the recipient cell number.
     * Must be no more than 10 characters and start with international code.
     * 
     * Regex pattern researched and adapted from:
     * SOURCE: https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
     * RETRIEVED: October 2026
     * 
     * @return Success message if valid, error message if invalid
     */
    public String checkRecipientCell() {
        if (recipient == null || recipient.isEmpty()) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }

        // International code: + followed by up to 10 digits
        String cellRegex = "^\\+[0-9]{1,10}$";

        if (recipient.matches(cellRegex)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // METHOD 2b: checkMessageLength()
    /**
     * Validates that the message text is no longer than 250 characters.
     *
     * @return "Message sent" if valid, or an error message if too long
     */
    
    public String checkMessageLength() {
        if (messageText == null) {
            return "Please enter a message of less than 250 characters.";
        }
        if (messageText.length() > 250) {
            return "Please enter a message of less than 250 characters.";
        }
        return "Message sent";
    }

    // METHOD 3: createMessageHash()
    /**
     * Creates the message hash in format: 00:0:HITHANKS
     * - First 2 digits of Message ID
     * - Colon
     * - Message number
     * - Colon
     * - First word + Last word in ALL CAPS
     * 
     * @return The generated message hash
     */
    public String createMessageHash() {
        // First 2 digits of message ID
        String firstTwo = messageID.substring(0, 2);

        // Split message into words to get first and last
        String trimmed = messageText.trim();
        String[] words = trimmed.split("\\s+");

        String firstWord = "";
        String lastWord = "";

        if (words.length > 0) {
            firstWord = words[0].toUpperCase();
            lastWord = words[words.length - 1].toUpperCase();

            // Remove punctuation from last word
            lastWord = lastWord.replaceAll("[^A-Z0-9]", "");
            firstWord = firstWord.replaceAll("[^A-Z0-9]", "");
        }

        return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // METHOD 4: SentMessage()
    /**
     * Allows the user to choose what to do with the message.
     * 
     * @param choice 1 = Send, 2 = Disregard, 3 = Store
     * @return Appropriate confirmation message
     */
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                allMessages.add(this);
                return "Message successfully sent";
            case 2:
                return "Press 0 to delete the message";
            case 3:
                storeMessage();
                return "Message successfully stored";
            default:
                return "Invalid option.";
        }
    }

    // METHOD 5: printMessages()
    /**
     * Returns all messages sent while the program is running.
     * 
     * @return A string containing all message details
     */
    
    public String printMessages() {
        if (allMessages.isEmpty()) {
            return "No messages sent yet.";
        }

        StringBuilder sb = new StringBuilder();
        for (Message m : allMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n");
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n");
            sb.append("----------------------------------------\n");
        }
        return sb.toString();
    }

    
    // METHOD 6: returnTotalMessagess()
    /**
     * Returns the total number of messages sent.
     * 
     * @return Total messages sent
     */
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // METHOD 7: storeMessage() - Manual JSON
    /**
     * Stores the current message to a JSON file.
     * Uses manual string building (no external libraries).
     * 
     * Reference: 
     * SOURCE: https://stackoverflow.com/questions/4293586/how-to-write-json-in-java-without-any-library
     * RETRIEVED: October 2026
     */
    public void storeMessage() {
        String json = "{\n" +
                "  \"messageID\": \"" + messageID + "\",\n" +
                "  \"messageNumber\": " + messageNumber + ",\n" +
                "  \"recipient\": \"" + recipient + "\",\n" +
                "  \"message\": \"" + escapeJson(messageText) + "\",\n" +
                "  \"messageHash\": \"" + messageHash + "\"\n" +
                "}\n";

        try {
            // Append to messages.json
            FileWriter fw = new FileWriter("messages.json", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(json);
            pw.close();
            System.out.println("Message successfully stored");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    // HELPER: Generate Message ID
    /**
     * Generates a random 10-digit message ID.
     * @return The generated ID
     */
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    // HELPER Escape JSON special characters
    /**
     * Escapes quotes and backslashes for JSON output.
     * @param text The string to escape
     * @return The escaped string
     */
    private String escapeJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // GETTERS
    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public static int getTotalMessagesSent() {
        return totalMessagesSent;
    }


    // SETTERS
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
