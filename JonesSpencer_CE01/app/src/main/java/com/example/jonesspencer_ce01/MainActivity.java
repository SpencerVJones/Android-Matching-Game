// Spencer Jones
// MDV3832-0 - 062024
// MainActivity.java

package com.example.jonesspencer_ce01;

// Imports
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity"; // Tag for logging
    private final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "A", "B", "C", "D", "E", "F", "G", "H"}; // Array of letters
    private String firstLetterClicked = null;
    private Button firstClickedButton = null;
    private TextView guessCount;
    private Button restartButton;

    private int guessCountNum = 0; // Counts number of guesses
    private int matchesFound = 0; // Counts number of matches found
    private boolean isProcessing = false; // Indicates if match/guess is in process

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initializing
        guessCount = findViewById(R.id.guessCount);
        restartButton = findViewById(R.id.restartButton);

        restartButton.setOnClickListener(restartButtonListener); // Setting click listener

        // Initially hiding restart button
        restartButton.setVisibility(View.GONE);

        // Shuffling letters array
        Collections.shuffle(Arrays.asList(letters));

        // Looping through button1 to button16
        for (int i = 1; i <= 16; i++) {
            int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName()); // Getting button id's
            Button button = findViewById(buttonId); // Finding buttons by id's
            button.setOnClickListener(this); // Setting click listener
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Click Listener for restart button
    private final View.OnClickListener restartButtonListener = view -> resetGame();

    // Method to reset game
    private void resetGame() {
        // Reset variables back to original game state
        guessCountNum = 0;
        matchesFound = 0;
        firstLetterClicked = null;
        firstClickedButton = null;
        isProcessing = false;
        updateGuessCount();
        restartButton.setVisibility(View.GONE);  // Hide restart button

        // Looping through button1 to button16
        for (int i = 1; i <= 16; i++) {
            int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName()); // Getting button id's
            Button button = findViewById(buttonId); // Finding buttons by id's
            button.setVisibility(View.VISIBLE); // Making buttons visible
            button.setText(""); // Clear buttons text
            button.setOnClickListener(this); // Setting click listener
        }

        // Shuffling letters array
        Collections.shuffle(Arrays.asList(letters));
    }

    // Handles click events for buttons game play buttons
    @Override
    public void onClick(View view) {
        // If a match is in progress
        if (isProcessing) {
            return;
        }

        Log.i(TAG, "Button Clicked: " + view.getId());

        // If no tag, log error
        if (view.getTag() == null) {
            Log.e(TAG, "Tag is null for view: " + view.getId());
            return;
        }

        int index = Integer.parseInt(view.getTag().toString()); // Get index from tag

        // If valid index
        if (index >= 0 && index < letters.length) {
            String letter = letters[index]; // Get letter from letters array
            Log.i(TAG, "Button " + (index + 1) + " clicked. Letter: " + letter);

            if (firstLetterClicked == null) {
                firstLetterClicked = letter; // Set first letter clicked
                firstClickedButton = (Button) view; // Set first clicked button
                firstClickedButton.setText(letter); // Set button text to letter

            } else if (view != firstClickedButton) {  // Ensure same button not clicked twice
                String secondLetter = letter; // Set second letter
                Button secondClickedButton = (Button) view; // Set second click button
                secondClickedButton.setText(letter); // Set button text to letter

                guessCountNum++;
                updateGuessCount();
                restartButton.setVisibility(View.VISIBLE);  // Show restart button after first guess

                isProcessing = true;

                // Creating handler to delay "flipping" or "disappearing"
                Handler handler = new Handler();
                handler.postDelayed(() -> {

                    // If letters match
                    if (firstLetterClicked.equals(secondLetter)) {
                        Log.i(TAG, "Match found");
                        // Hide the matched buttons
                        firstClickedButton.setVisibility(View.GONE);
                        secondClickedButton.setVisibility(View.GONE);
                        matchesFound++;

                        // If all matches found, show Toast alert
                        if (matchesFound == letters.length / 2) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); // Create alert

                            builder.setTitle("Congratulations!"); // Set alert title
                            builder.setMessage("All matches found"); // Set alert message

                            // Set button text to "Restart" and have it restart the game
                            builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    resetGame();
                                }
                            });
                            builder.setCancelable(false); // Make user restart the game
                            builder.show(); // Show alert
                        }
                    } else {
                        Log.i(TAG, "No match");
                        // "Flip" the buttons by clearing the text
                        firstClickedButton.setText("");
                        secondClickedButton.setText("");
                    }
                    resetClicks();
                }, 500);  // Delay to show letter before "flipping"/"disappearing"
            }
        }
    }

    // Method to clears firstLetterClicked and secondLetterClicked
    private void resetClicks() {
        firstLetterClicked = null;
        firstClickedButton = null;
        isProcessing = false; // No longer making a match attempt
    }

    // Method to update guess count on UI
    private void updateGuessCount() {
        guessCount.setText("Guess Count: " + guessCountNum);
    }
}