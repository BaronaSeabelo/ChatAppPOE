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

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static int totalMessagesSent = 0;
    private static ArrayList<Message> allMessages = new ArrayList<>();

    // messageNumber comes from the loop counter in ChatApp
    public Message(String recipient, String messageText, int messageNumber) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    public boolean checkMessageID() {
        return messageID != null && !messageID.isEmpty() && messageID.length() <= 10;
    }

    // Returns "Message ID generated: <Message ID>"
    public String messageIDGenerated() {
        return "Message ID generated: " + messageID;
    }

    // Regex reference: https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
    public String checkRecipientCell() {
        String error = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        if (recipient == null || recipient.isEmpty()) {
            return error;
        }
        if (recipient.matches("^\\+[0-9]{1,10}$")) {
            return "Cell phone number successfully captured.";
        }
        return error;
    }

    public String checkMessageLength() {
        if (messageText != null && messageText.length() > 250) {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0].toUpperCase().replaceAll("[^A-Z0-9]", "");
        String lastWord = words[words.length - 1].toUpperCase().replaceAll("[^A-Z0-9]", "");

        return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                allMessages.add(this);
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

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

    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    /**
     * Stores the message to messages.json using the json-simple library.
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

    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    // Lets tests fix the ID so the hash is predictable
    public void setMessageID(String messageID) {
        this.messageID = messageID;
        this.messageHash = createMessageHash();
    }

    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public static int getTotalMessagesSent() { return totalMessagesSent; }
}