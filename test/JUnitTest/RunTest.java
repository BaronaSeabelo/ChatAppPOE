/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package JUnitTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class RunTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(LoginTest.class, MessageTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("FAILED: " + failure);
        }
        System.out.println("Tests run: " + result.getRunCount()
                + ", failed: " + result.getFailureCount());
    }
}