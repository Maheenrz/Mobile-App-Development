package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPlayers extends AppCompatActivity {

    private String gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        gameMode = getIntent().getStringExtra("gameMode");

        EditText playerOne = findViewById(R.id.playerOne);
        EditText playerTwo = findViewById(R.id.playerTwo);
        Button startGameButton = findViewById(R.id.StartGameButton);

        if ("vsAI".equals(gameMode)) {
            // Hide only the second player input field
            playerTwo.setVisibility(View.GONE);

            // Change hint for player one
            playerOne.setHint("Enter your name");
        }

        startGameButton.setOnClickListener(v -> {
            String getPlayerOneName = playerOne.getText().toString().trim();
            String getPlayerTwoName = "vsAI".equals(gameMode) ? "Computer" : playerTwo.getText().toString().trim();

            if (getPlayerOneName.isEmpty()) {
                Toast.makeText(AddPlayers.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            if ("twoPlayer".equals(gameMode) && getPlayerTwoName.isEmpty()) {
                Toast.makeText(AddPlayers.this, "Please enter second player's name", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(AddPlayers.this, MainActivity.class);
            intent.putExtra("playerOne", getPlayerOneName);
            intent.putExtra("playerTwo", getPlayerTwoName);
            intent.putExtra("gameMode", gameMode);
            startActivity(intent);
        });
    }
}