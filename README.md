# Android-Matching-Game
This Android application is a letter matching game consisting of 16 unmarked buttons with eight matching pairs of letters. The user has unlimited guesses to match each pair of letters. The game includes features such as tracking the number of guesses and a restart button that appears once all matches are found.

## Features
- **16 Buttons** arranged in a 4x4 grid.
- **Unlimited Guesses** to match the pairs.
- **Real-time Guess Count** displayed on the screen.
- **Restart Button** appears after all pairs are matched.
- **Smooth UI** with hidden buttons on successful matches and delayed flip on unsuccessful attempts.
- **Edge-to-Edge Layout** for a seamless visual experience.

## Installation
1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the project on an Android device or emulator.

## How to Play
1. **Launch the app**: The game starts with 16 unmarked buttons.
2. **Click a button**: Reveals a letter.
3. **Match pairs**: Click another button to find the matching letter. If they match, both buttons are hidden. If not, both buttons are flipped back.
4. **Track guesses**: The number of guesses is displayed at the top.
5. **Restart game**: Once all pairs are matched, a restart button appears to reset the game.

## Code Structure
- **MainActivity.java**: Contains the game logic and UI handling.
- **activity_main.xml**: Defines the UI layout of the game.
- **strings.xml**: Contains all user-visible strings.

## Technologies Used
- **Java**: Primary programming language used.
- **Android SDK**: Core libraries for Android development.
- **Android Studio**: IDE for Android development.
- **XML**: Used for UI layout files.
- **Collections**: Java utility class used for shuffling the letters.

### You can contribute by:
- Reporting bugs
- Suggesting new features
- Submitting pull requests to improve the codebase
