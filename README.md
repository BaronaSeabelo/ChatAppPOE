# Programming 1A POE - Chat Application

## 📋 Project Overview

A console-based chat application built in Java that allows users to register an account and log in. The application validates usernames, passwords, and South African cell phone numbers before storing user data.

## 👤 Author Information

- **Name:** Barona Seabelo
- **Student Number:** ST10521727
- **Module:** Programming 1A
- **Group:** Group 2
- **Institution:** Rosebank International
- **Year:** 2026

## 🛠️ Technologies Used

- **Java** - Programming language
- **Ant** - Build automation tool (NetBeans project)
- **JUnit 4** - Unit testing framework
- **GitHub** - Version control
- **GitHub Actions** - Continuous Integration (automated tests)

## 📂 Project Structure

```
ChatAppPOE/
├── .github/
│   └── workflows/
│       └── TestJava.yml    ← GitHub Actions workflow (runs the tests)
├── nbproject/              ← NetBeans project files
│   ├── build-impl.xml
│   ├── genfiles.properties
│   ├── project.properties
│   └── project.xml
├── src/Java/               ← My source code
│   ├── ChatApp.java
│   ├── Login.java
│   └── User.java
├── test/JUnitTest/         ← My unit tests
│   └── LoginTest.java
├── .gitignore
├── README.md
├── build.xml
└── manifest.mf
```

## ✨ Features

- User registration with validation
- Username must contain an underscore and be ≤ 5 characters
- Password must be ≥ 8 characters, and contain a capital letter, a number, and a special character
- Cell phone number must start with +27 followed by 9 digits
- Login authentication
- Console-based menu interface

## 🚀 How to Run

1. Clone the repository
2. Open the project in NetBeans
3. Run `ChatApp.java`
4. Follow the on-screen menu

## 🧪 Running Tests

**In NetBeans:** right-click the project and select **Test**. Test classes must end in `Test` (for example `LoginTest`) for NetBeans to find them. JUnit 4 and Hamcrest must be added under **Test Libraries**.

**On GitHub:** the tests run automatically on every push through GitHub Actions. Open the **Actions** tab of the repository to see the latest result (a green tick means all tests passed).

## ⚙️ Continuous Integration

The workflow in `.github/workflows/TestJava.yml` runs on every push and pull request. It:

1. Checks out the code
2. Installs Java 26
3. Downloads JUnit 4 and Hamcrest
4. Compiles the source and test code
5. Runs every class whose name ends in `Test`

## 📝 Disclaimer

AI was used to further elaborate and explain some concepts, and to support with debugging. However, the overall logic built is primarily my own work.

In my Word document I reference my sources.
