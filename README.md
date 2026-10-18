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
- **Ant** - Build automation tool
- **JUnit 4** - Unit testing framework
- **GitHub** - Version control

## 📂 Project Structure

ChatAppPOE/
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
│   └── TestLogin.java
├── .gitignore
├── README.md               
├── build.xml
└── manifest.mf

## ✨ Features

- User registration with validation
- Username must contain underscore and be ≤ 5 characters
- Password must be ≥ 8 characters, contain a capital letter, number, and special character
- Cell phone number must start with +27 and have 9 digits
- Login authentication
- Console-based menu interface

## 🚀 How to Run

1. Clone the repository
2. Open the project in NetBeans
3. Run `ChatApp.java`
4. Follow the on-screen menu

## 🧪 Running Tests

Right-click the project in NetBeans and select **Test**.

## 📝 Disclaimer

AI was used to further elaborate and explain some concepts, and to support with debugging. However, the overall logic built is primarily my own work.
In my word document I reference my sources 
