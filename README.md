# VoresKlimaplan app

The default of this app project, is set to never Android SDK versions.
This means for you, should you wish to run it:

## Works on
* Android Studio Emulator
* Never Android devices

### Issues on older devices 
* The app would not install or run on older phones, such as: Samsung Galaxy A51.

## How to fix it!
1. Open app/build.gradle
2. Find the line: 
minSDK = 35
3. Replace the number with something lower such as 22.
4. Sync the project and re-run the app.

## Firestore
* Please note, that this project have been set up with FireStore. That means for anyone wishing to download the code, some functions will not be able to work, such as updating the scoreboard.
* As an example should the future user set up their own Firestore for it, use their own: google service json firebase and so on, as this projects firebase have an experation date.
