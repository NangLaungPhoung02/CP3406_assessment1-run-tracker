# Utility App Starter – CP3406 / CP5307

This is a basic Android app template for **Assessment 1: Utility App** in CP3406/CP5603..  
It provides the structure for a simple tabular UI using **Jetpack Compose** and **Material Design 3**.

---

## Getting Started

### How to Run
1. Clone or download this repo  
2. Open in Android Studio  
3. Run on an emulator or physical device (API 26+ recommended)  

---
# Run Tracker App

## Project Description

Run Tracker App is an Android mobile application designed to help users track their running activities. The app allows users to start, pause, resume, and stop a running session while viewing useful running information such as time, distance, pace, calories, steps, and weather information.

The app also includes a records page where users can view their previous running history and a settings page where users can control different running preferences such as auto pause, audio cues, pace display, cadence, calories, and steps.

This project was developed for CP3406 Mobile Computing using Android Studio and Jetpack Compose.

---

## Features

### Home Page
- Displays a professional running app home screen
- Shows useful running information
- Includes weather condition display
- Provides quick access to the main running features

### Run Page
- Start running session
- Pause running session
- Resume running session
- End running session
- Display running timer
- Show running details such as:
  - Distance
  - Pace
  - Calories
  - Steps
  - Weather condition

### Records Page
- Displays previous running records
- Shows information such as:
  - Date
  - Time
  - Distance
  - Pace
  - Calories burned
- Includes goal setting feature
- Goal page allows users to set:
  - Target weight
  - Target number of days
- Premium upgrade page is included as part of the goal feature

### Settings Page
- Allows users to turn running preferences on or off
- Settings include:
  - Auto Pause
  - Audio Cues
  - Current Pace
  - Cadence
  - Calories
  - Steps
- Includes user account section
- Includes profile and logout option

---

## Technologies Used

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Android Navigation
- Location Services
- GitHub for version control

---

## App Screens

The app contains the following main screens:

1. Home Screen
2. Run Screen
3. Records Screen
4. Goal Setting Screen
5. Upgrade to Premium Screen
6. Settings Screen
7. User Account Screen

---

## Project Structure

```text
app/
 └── src/
     └── main/
         └── java/
             └── au/edu/jcu/cp3406_cp5307_utilityappstartertemplate/
                 ├── MainActivity.kt
                 ├── LocationUtils.kt
                 └── ui/theme/
                     ├── Color.kt
                     ├── Theme.kt
                     └── Type.kt

## 📚 License
This template is provided for educational use in CP3406.  
