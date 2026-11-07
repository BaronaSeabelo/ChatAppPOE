/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * MessageManager Class - Holds the message arrays and the search, delete
 * and report logic so that ChatApp stays simple.
 *
 * @author Barona Seabelo
 * @version 1.0
 * @since 2026
 */
public class MessageManager {
    private static final String JSON_FILE = "messages.json";

    // The five arrays. The hash and ID lists stay in the same order as each other.
    private ArrayList<Message> sentMessages = new ArrayList<>();
    private ArrayList<Message> disregardedMessages = new ArrayList<>();
    private ArrayList<Message> storedMessages = new ArrayList<>();
    private ArrayList<String> messageHashes = new ArrayList<>();
    private ArrayList<String> messageIDs = new ArrayList<>();

    /**
     * Adds a message to the array that matches its flag, and records
     * its hash and ID.
     *
     * @param msg The message to add
     */
    public void populateArrays(Message msg) {
        String flag = msg.getFlag();

        if ("Sent".equals(flag)) {
            sentMessages.add(msg);
        } else if ("Disregard".equals(flag)) {
            disregardedMessages.add(msg);
        } else if ("Stored".equals(flag)) {
            storedMessages.add(msg);
        } else {
            return; // no valid flag, so nothing to add
        }

        messageHashes.add(msg.getMessageHash());
        messageIDs.add(msg.getMessageID());
    }

    /**
     * Reads messages.json into the stored messages array.
     * Messages whose ID is already loaded are skipped, so a message stored
     * in this session is not added twice.
     *
     * SOURCE: json-simple 1.1 library (JSONParser), provided by my tutor
     * and lecturer for Programming 1A.
     * ADD HERE: the online source you used to read the file line by line,
     * and the date you retrieved it.
     */
    public void readJsonFile() {
        JSONParser parser = new JSONParser();

        try (BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                JSONObject details = (JSONObject) parser.parse(line);
                String id = (String) details.get("MessageID");
                if (messageIDs.contains(id)) {
                    continue;
                }

                int number = ((Long) details.get("MessageNumber")).intValue();
                Message msg = new Message((String) details.get("Recipient"),
                                          (String) details.get("Message"), number);
                msg.setMessageID(id);
                msg.setFlag("Stored");
                populateArrays(msg);
            }
        } catch (FileNotFoundException e) {
            // No file yet means no stored messages, which is fine.
        } catch (IOException | ParseException e) {
            System.out.println("Error reading " + JSON_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Rewrites messages.json from the stored array. ChatApp calls this
     * after a delete so that the deleted message does not come back.
     */
    @SuppressWarnings("unchecked")
    public void saveStoredMessagesToFile() {
        try (FileWriter file = new FileWriter(JSON_FILE, false)) {
            for (Message msg : storedMessages) {
                JSONObject details = new JSONObject();
                details.put("MessageID", msg.getMessageID());
                details.put("MessageNumber", msg.getMessageNumber());
                details.put("Recipient", msg.getRecipient());
                details.put("Message", msg.getMessageText());
                details.put("MessageHash", msg.getMessageHash());

                file.write(details.toJSONString());
                file.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving " + JSON_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Lists the sender and recipient of every stored message.
     *
     * @param sender The logged-in user, who is the sender of every message
     * @return The list, or a message saying nothing is stored
     */
    public String displaySenderAndRecipient(String sender) {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }

        StringBuilder sb = new StringBuilder();
        for (Message msg : storedMessages) {
            sb.append("Sender: ").append(sender)
              .append(" | Recipient: ").append(msg.getRecipient()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Finds the longest stored message.
     *
     * @return The text of the longest stored message
     */
    public String getLongestMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }

        Message longest = storedMessages.get(0);
        for (Message msg : storedMessages) {
            if (msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        return longest.getMessageText();
    }

    /**
     * Finds a message by its ID, searching sent, stored and disregarded messages.
     *
     * @param id The message ID
     * @return The matching message, or null if there is none
     */
    public Message searchByMessageID(String id) {
        Message found = findByID(sentMessages, id);
        if (found == null) {
            found = findByID(storedMessages, id);
        }
        if (found == null) {
            found = findByID(disregardedMessages, id);
        }
        return found;
    }

    /**
     * Finds all sent and stored messages for one recipient.
     *
     * @param number The recipient's cell number
     * @return The text of every matching message
     */
    public ArrayList<String> searchByRecipient(String number) {
        ArrayList<String> results = new ArrayList<>();
        addMatchingTexts(sentMessages, number, results);
        addMatchingTexts(storedMessages, number, results);
        return results;
    }

    /**
     * Deletes a stored message using its hash.
     *
     * @param hash The message hash
     * @return A confirmation, or a message saying the hash was not found
     */
    public String deleteMessageByHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            Message msg = storedMessages.get(i);

            if (msg.getMessageHash().equals(hash)) {
                storedMessages.remove(i);
                messageHashes.remove(msg.getMessageHash());
                messageIDs.remove(msg.getMessageID());
                return "Message: \"" + msg.getMessageText() + "\" successfully deleted.";
            }
        }
        return "No stored message with that hash was found.";
    }

    /**
     * Builds a report of all stored messages (hash, recipient, message).
     *
     * @return The report text
     */
    public String displayReport() {
        return buildReport(storedMessages, "stored");
    }

    /**
     * Builds a report of all sent messages (hash, recipient, message).
     * The brief's report test refers to sent messages, so both reports exist.
     *
     * @return The report text
     */
    public String displaySentReport() {
        return buildReport(sentMessages, "sent");
    }

    // HELPERS

    private Message findByID(ArrayList<Message> list, String id) {
        for (Message msg : list) {
            if (msg.getMessageID().equals(id)) {
                return msg;
            }
        }
        return null;
    }

    private void addMatchingTexts(ArrayList<Message> list, String number,
                                  ArrayList<String> results) {
        for (Message msg : list) {
            if (msg.getRecipient().equals(number)) {
                results.add(msg.getMessageText());
            }
        }
    }

    private String buildReport(ArrayList<Message> list, String type) {
        if (list.isEmpty()) {
            return "No " + type + " messages.";
        }

        StringBuilder sb = new StringBuilder();
        for (Message msg : list) {
            sb.append("Message Hash: ").append(msg.getMessageHash()).append("\n");
            sb.append("Recipient: ").append(msg.getRecipient()).append("\n");
            sb.append("Message: ").append(msg.getMessageText()).append("\n");
            sb.append("----------------------------------------\n");
        }
        return sb.toString();
    }

    // GETTERS

    public ArrayList<Message> getSentMessages() { return sentMessages; }
    public ArrayList<Message> getDisregardedMessages() { return disregardedMessages; }
    public ArrayList<Message> getStoredMessages() { return storedMessages; }
    public ArrayList<String> getMessageHashes() { return messageHashes; }
    public ArrayList<String> getMessageIDs() { return messageIDs; }
}

