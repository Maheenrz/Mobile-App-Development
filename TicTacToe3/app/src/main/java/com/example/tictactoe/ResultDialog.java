package com.example.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultDialog extends Dialog {

    private final String message;
    private final MainActivity mainActivity;


    public ResultDialog(@NonNull Context context, String message, MainActivity mainActivity){
        super(context);
        this.message=message;
        this.mainActivity=mainActivity;
    }

    // Default constructor (required for proper instantiation)
    public ResultDialog(@NonNull Context context) {
        super(context);
        this.message = "";
        this.mainActivity = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dialog);


        TextView messageText = findViewById(R.id.messageText);
        Button startAgainButton = findViewById(R.id.startAgainButton);

        messageText.setText(message);

        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity != null) {
                    mainActivity.restartGame();
                }
                dismiss();
            }
        });
    }
}