/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package JUnitTest;

import Java.Login;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestLogin {

    private Login login = new Login();

    @Test
    public void testUsernameValid() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameInvalid() {
        assertFalse(login.checkUserName("kyllllllllllllllllllllllllllllllllllllllllll"));
    }

    @Test
    public void testPasswordValid() {
        assertTrue(login.checkPasswordComplexity("Ch&sec@ke99l"));
    }

    @Test
    public void testPasswordInvalid() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testPhoneValid() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testPhoneInvalid() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}