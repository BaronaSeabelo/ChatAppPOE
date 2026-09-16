/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Java;

/**
 *
 * @author user
 */
public class Login {
    // Instead of storing five separate fields, store one User object.
    // This is null until someone successfully registers.
    private User registeredUser;

    // initializes the Login object with no registered user yet
    public Login() {
        this.registeredUser = null;
    }

    // Convenience getters so the rest of the app (ChatApp) can keep
    // calling these the same way as before, without needing to know
    // about the User object underneath.
    public String getStoredUsername() {
        return registeredUser == null ? "" : registeredUser.getUsername();
    }

    public String getFirstName() {
        return registeredUser == null ? "" : registeredUser.getFirstName();
    }

    public String getLastName() {
        return registeredUser == null ? "" : registeredUser.getLastName();
    }

    // method 1 check the username
    public boolean checkUserName(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        // Condition 1 Contains underscore?
        boolean hasUnderscore = username.contains("_");

        // Condition 2 Length is 5 or less?
        boolean isShortEnough = username.length() <= 5;

        // BOTH conditions must be true
        return hasUnderscore && isShortEnough;
    }

    // method 2 to check the validation of the password
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        // Condition 1 At least 8 characters
        boolean isLongEnough = password.length() >= 8;

        // Condition 2 Contains atleast one capital letter (A-Z)
        boolean hasCapital = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                hasCapital = true;
                break;
            }
        }

        // Condition 3 Contains a number (0-9)
        boolean hasNumber = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (c >= '0' && c <= '9') {
                hasNumber = true;
                break;
            }
        }

        // Condition 4 Contains a special character
        boolean hasSpecial = false;
        String specialCharacters = "!@#$%^&*()_+-=[]{};:'\"\\|,<.>/?";
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (specialCharacters.indexOf(c) != -1) {
                hasSpecial = true;
                break;
            }
        }

        // ALL 4 conditions must be true for it to work
        return isLongEnough && hasCapital && hasNumber && hasSpecial;
    }

    // method 3 to check the cell phone number and ensure is typed correctly and the way it should 
    // https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
    public boolean checkCellPhoneNumber(String phoneNumber) {
        // Check if phone number is null or empty
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        String phoneRegex = "^\\+27[0-9]{9}$";

        // Check if the phone number matches the pattern
        return phoneNumber.matches(phoneRegex);
    }

    // method 4 to check the registration of the student
    public String registerUser(String username, String password,
                               String phoneNumber, String firstName,
                               String lastName) {
       
        // STEP 1 Check if username is valid Add username validation logic to Login class
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        // STEP 2 Check if password is valid password complexity validation to login class
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        // STEP 3 Check if phone number is valid
        if (!checkCellPhoneNumber(phoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // STEP 4 ALL valid - create and store the User object
        this.registeredUser = new User(username, password, phoneNumber, firstName, lastName);

        // STEP 5 Return success message
        return "Welcome " + firstName + " " + lastName + " it is great to see you.";
    }

    // method 5 of the user login
    public boolean loginUser(String username, String password) {
        // Check if a user has registered yet
        if (registeredUser == null) {
            return false;
        }

        // Check if username matches AND password matches
        return registeredUser.getUsername().equals(username)
            && registeredUser.getPassword().equals(password);
    }
}
