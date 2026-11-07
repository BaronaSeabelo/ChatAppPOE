/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Java;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import org.json.simple.JSONObject;

/**
 * Message Class - Creates, validates, hashes, flags and stores messages.
 *
 * @author Barona Seabelo
 * @version 3.0
 * @since 2026
 */
public class Message {
    // INSTANCE VARIABLES
    private String messageID;          // 10-digit unique ID
    private int messageNumber;         // Loop counter from ChatApp (0, 1, 2...)
    private String recipient;          // Cell number
    private String messageText;        // Message content
    private String messageHash;        // Format: 00:0:HITHANKS
    private String flag;               // "Sent", "Disregard" or "Stored"

    // Static variables - shared across all Message objects
    private static int totalMessagesSent = 0;
    private static ArrayList<Message> allMessages = new ArrayList<>();

    /**
     * Creates a new Message with an auto-generated ID and hash.
     *
     * @param recipient The recipient's cell number
     * @param messageText The message content
     * @param messageNumber The loop counter used as the message number
     */
    public Message(String recipient, String messageText, int messageNumber) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // METHOD 1: checkMessageID()
    /**
     * Checks that the message ID is not more than 10 characters.
     * @return true if valid, false otherwise
     */
    public boolean checkMessageID() {
        return messageID != null && !messageID.isEmpty() && messageID.length() <= 10;
    }

    /**
     * Returns the "Message ID generated" confirmation text.
     * @return "Message ID generated: <Message ID>"
     */
    public String messageIDGenerated() {
        return "Message ID generated: " + messageID;
    }

    // METHOD 2: checkRecipientCell()
    /**
     * Validates the recipient cell number: international code, then digits.
     *
     * Regex syntax reference:
     * SOURCE: https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
     * RETRIEVED: October 2026
     *
     * @return Success message if valid, error message if invalid
     */
    public String checkRecipientCell() {
        String error = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";

        if (recipient == null || recipient.isEmpty()) {
            return error;
        }
        if (recipient.matches("^\\+[0-9]{1,3}[0-9]{1,10}$")) {
            return "Cell phone number successfully captured.";
        }
        return error;
    }

    // METHOD 2b: checkMessageLength()
    /**
     * Validates that the message is no longer than 250 characters.
     * @return Success message, or an error stating how many characters too long
     */
    public String checkMessageLength() {
        if (messageText != null && messageText.length() > 250) {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    // METHOD 3: createMessageHash()
    /**
     * Creates the message hash in the format 00:0:HITHANKS
     * (first 2 digits of the ID, message number, first and last words in capitals).
     *
     * @return The generated message hash
     */
    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0].toUpperCase().replaceAll("[^A-Z0-9]", "");
        String lastWord = words[words.length - 1].toUpperCase().replaceAll("[^A-Z0-9]", "");

        return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // METHOD 4: SentMessage()
    /**
     * Lets the user send, disregard or store the message, and records the
     * choice in the flag so the message can be sorted into the right array.
     *
     * @param choice 1 = Send, 2 = Disregard, 3 = Store
     * @return Confirmation message
     */
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                flag = "Sent";
                totalMessagesSent++;
                allMessages.add(this);
                return "Message successfully sent.";
            case 2:
                flag = "Disregard";
                return "Press 0 to delete the message.";
            case 3:
                flag = "Stored";
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // METHOD 5: printMessages()
    /**
     * Returns all messages sent while the program is running.
     * @return A string containing all sent message details
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
     * @return Total messages sent
     */
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // METHOD 7: storeMessage() - JSON
    /**
     * Stores the message to messages.json using the json-simple library.
     *
     * SOURCE: json-simple 1.1 library (org.json.simple.JSONObject),
     * provided by my tutor and lecturer for Programming 1A.
     * RETRIEVED: October 2026
     */
    @SuppressWarnings("unchecked")
    public void storeMessage() {
        JSONObject messageDetails = new JSONObject();
        messageDetails.put("MessageID", messageID);
        messageDetails.put("MessageNumber", messageNumber);
        messageDetails.put("Recipient", recipient);
        messageDetails.put("Message", messageText);
        messageDetails.put("MessageHash", messageHash);

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(messageDetails.toJSONString());
            file.write("\n");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    // HELPER: Generate Message ID
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    // GETTERS
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getFlag() { return flag; }
    public static int getTotalMessagesSent() { return totalMessagesSent; }

    // SETTERS
    public void setFlag(String flag) { this.flag = flag; }

    /** Lets tests fix the ID so the hash is predictable. */
    public void setMessageID(String messageID) {
        this.messageID = messageID;
        this.messageHash = createMessageHash();
    }
}