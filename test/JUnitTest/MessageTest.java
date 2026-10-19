/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package JUnitTest;

import Java.Message;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * MessageTest Class - Unit tests for the Message class.
 *
 * @author Barona Seabelo
 * @version 1.0
 * @since 2026
 */
public class MessageTest {

    // Message length: success
    @Test
    public void testMessageLengthSuccess() {
        Message m = new Message("+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("Message ready to send.", m.checkMessageLength());
    }

    // Message length: failure (260 characters, so the excess is 10)
    @Test
    public void testMessageLengthFailure() {
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longText.append("a");
        }
        Message m = new Message("+27718693002", longText.toString(), 0);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                m.checkMessageLength());
    }

    // Recipient: success
    @Test
    public void testRecipientCellSuccess() {
        Message m = new Message("+27718693002", "Hello", 0);
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    // Recipient: failure (no international code)
    @Test
    public void testRecipientCellFailure() {
        Message m = new Message("08575975889", "Hello", 0);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                m.checkRecipientCell());
    }

    // Hash: Test Case 1 gives 00:0:HITONIGHT
    @Test
    public void testMessageHashTestCase1() {
        Message m = new Message("+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        m.setMessageID("0012345678");
        assertEquals("00:0:HITONIGHT", m.getMessageHash());
    }

    // Hash: remaining messages tested in a loop.
    // Replace the second row with the second test case from your brief.
    @Test
    public void testMessageHashesInLoop() {
        String[] texts = {
            "Hi Mike, can you join us for dinner tonight?",
            "Okay, I'll send it"
        };
        String[] ids = {"0012345678", "1234567890"};
        String[] expected = {"00:0:HITONIGHT", "12:1:OKAYIT"};

        for (int i = 0; i < texts.length; i++) {
            Message m = new Message("+27718693002", texts[i], i);
            m.setMessageID(ids[i]);
            assertEquals(expected[i], m.getMessageHash());
        }
    }

    // Message ID is created
    @Test
    public void testMessageIDCreated() {
        Message m = new Message("+27718693002", "Hello", 0);
        assertTrue(m.checkMessageID());
        assertEquals("Message ID generated: " + m.getMessageID(), m.messageIDGenerated());
    }

    // Send option
    @Test
    public void testSendMessage() {
        Message m = new Message("+27718693002", "Hello there", 0);
        assertEquals("Message successfully sent.", m.SentMessage(1));
    }

    // Disregard option
    @Test
    public void testDisregardMessage() {
        Message m = new Message("+27718693002", "Hello there", 0);