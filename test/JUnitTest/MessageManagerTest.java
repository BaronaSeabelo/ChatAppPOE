/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package JUnitTest;

import Java.Message;
import Java.MessageManager;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * MessageManagerTest Class - Unit tests for the arrays and the search,
 * delete and report logic, using the five test messages from the brief.
 *
 * @author Barona Seabelo
 * @version 1.0
 * @since 2026
 */
public class MessageManagerTest {

    private static final String TEXT_1 = "Did you get the cake?";
    private static final String TEXT_2 = "Where are you? You are late! I have asked you to be on time.";
    private static final String TEXT_3 = "Yohoooo, I am at your gate.";
    private static final String TEXT_4 = "It is dinner time!";
    private static final String TEXT_5 = "Ok, I am leaving without you.";

    private MessageManager manager;
    private Message message2;

    // Adds the five test messages to the arrays before every test.
    // setFlag() is used instead of SentMessage() so no file is written.
    @Before
    public void setUp() {
        manager = new MessageManager();

        Message message1 = new Message("+27834557896", TEXT_1, 0);
        message1.setFlag("Sent");

        message2 = new Message("+27838884567", TEXT_2, 1);
        message2.setFlag("Stored");

        Message message3 = new Message("+27834484567", TEXT_3, 2);
        message3.setFlag("Disregard");

        Message message4 = new Message("0838884567", TEXT_4, 3);
        message4.setMessageID("0838884567");
        message4.setFlag("Sent");

        Message message5 = new Message("+27838884567", TEXT_5, 4);
        message5.setFlag("Stored");

        manager.populateArrays(message1);
        manager.populateArrays(message2);
        manager.populateArrays(message3);
        manager.populateArrays(message4);
        manager.populateArrays(message5);
    }

    // Test 1: Sent Messages array is correctly populated
    @Test
    public void testSentMessagesArrayPopulated() {
        ArrayList<Message> sent = manager.getSentMessages();

        assertEquals(2, sent.size());
        assertEquals("Did you get the cake?", sent.get(0).getMessageText());
        assertEquals("It is dinner time!", sent.get(1).getMessageText());
    }

    // The other arrays are populated correctly too
    @Test
    public void testOtherArraysPopulated() {
        assertEquals(1, manager.getDisregardedMessages().size());
        assertEquals(TEXT_3, manager.getDisregardedMessages().get(0).getMessageText());
        assertEquals(2, manager.getStoredMessages().size());
        assertEquals(5, manager.getMessageHashes().size());
        assertEquals(5, manager.getMessageIDs().size());
    }

    // Test 2: Display the longest message
    @Test
    public void testLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                manager.getLongestMessage());
    }

    // Test 3: Search for a message ID
    @Test
    public void testSearchByMessageID() {
        Message found = manager.searchByMessageID("0838884567");

        assertNotNull(found);
        assertEquals("It is dinner time!", found.getMessageText());
    }

    // Search for an ID that does not exist
    @Test
    public void testSearchByMessageIDNotFound() {
        assertNull(manager.searchByMessageID("0000000000"));
    }

    // Test 4: Search all messages for one recipient
    @Test
    public void testSearchByRecipient() {
        ArrayList<String> results = manager.searchByRecipient("+27838884567");

        assertEquals(2, results.size());
        assertEquals("Where are you? You are late! I have asked you to be on time.", results.get(0));
        assertEquals("Ok, I am leaving without you.", results.get(1));
    }

    // Test 5: Delete a message using its hash (test message 2)
    @Test
    public void testDeleteMessageByHash() {
        String result = manager.deleteMessageByHash(message2.getMessageHash());

        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                result);
        assertEquals(1, manager.getStoredMessages().size());
        assertFalse(manager.getMessageHashes().contains(message2.getMessageHash()));
    }

    // Deleting a hash that does not exist
    @Test
    public void testDeleteMessageByHashNotFound() {
        assertEquals("No stored message with that hash was found.",
                manager.deleteMessageByHash("00:0:NOTHERE"));
    }

    // Test 6: Display report shows hash, recipient and message
    @Test
    public void testDisplayReport() {
        String report = manager.displayReport();

        assertTrue(report.contains(message2.getMessageHash()));
        assertTrue(report.contains("+27838884567"));
        assertTrue(report.contains(TEXT_2));
        assertTrue(report.contains(TEXT_5));
    }

    // The sent-messages report, since the brief's test mentions sent messages
    @Test
    public void testDisplaySentReport() {
        String report = manager.displaySentReport();

        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains(TEXT_1));
        assertTrue(report.contains(TEXT_4));
    }
}