package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ModeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);

        // Configure buttons
        Button btnTwoPlayer = findViewById(R.id.btnTwoPlayer);
        Button btnVsAI = findViewById(R.id.btnVsAI);

        // In ModeSelectionActivity
        btnTwoPlayer.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPlayers.class);
            intent.putExtra("gameMode", "twoPlayer");
            startActivity(intent);
        });

        btnVsAI.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPlayers.class);
            intent.putExtra("gameMode", "vsAI");
            startActivity(intent);
        });

    }
}