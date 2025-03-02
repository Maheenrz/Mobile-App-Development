package com.example.tictactoe;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoe.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;




public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = new int[9];
    private int playerTurn = 1;
    private int totalSelectedBox = 1;
    private boolean isVsAIMode = false;
    private final Handler aiHandler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initializeGame();
        setupClickListeners();
    }

    private void initializeGame() {
        // Initialize winning combinations
        combinationList.add(new int[]{0,1,2});
        combinationList.add(new int[]{3,4,5});
        combinationList.add(new int[]{6,7,8});
        combinationList.add(new int[]{0,3,6});
        combinationList.add(new int[]{1,4,7});
        combinationList.add(new int[]{2,5,8});
        combinationList.add(new int[]{2,4,6});
        combinationList.add(new int[]{0,4,8});

        // Get game parameters from intent
        String playerOneName = getIntent().getStringExtra("playerOne");
        String gameMode = getIntent().getStringExtra("gameMode");

        isVsAIMode = "vsAI".equals(gameMode);
        binding.playerOneName.setText(playerOneName);
        binding.playerTwoName.setText(isVsAIMode ? "AI" : getIntent().getStringExtra("playerTwo"));

        // If AI goes first
        if(isVsAIMode && playerTurn == 2) {
            makeAIMove();
        }
    }

    private void setupClickListeners() {
        int[] viewIds = {R.id.image1, R.id.image2, R.id.image3,
                R.id.image4, R.id.image5, R.id.image6,
                R.id.image7, R.id.image8, R.id.image9};

        for(int i = 0; i < viewIds.length; i++) {
            int position = i;
            binding.getRoot().findViewById(viewIds[i]).setOnClickListener(view -> {
                if(isBoxSelectable(position) && (!isVsAIMode || playerTurn == 1)) {
                    handlePlayerMove((ImageView) view, position);
                }
            });
        }
    }

    private void handlePlayerMove(ImageView imageView, int position) {
        boxPositions[position] = playerTurn;

        // Set symbol based on player turn
        imageView.setImageResource(playerTurn == 1 ? R.drawable.cross : R.drawable.zero);

        if (checkGameResult()) {
            String winnerName = (playerTurn == 1)
                    ? binding.playerOneName.getText().toString()
                    : binding.playerTwoName.getText().toString();
            showResultDialog(winnerName + " Wins!");
        } else if (totalSelectedBox == 9) {
            showResultDialog("Game Draw!");
        } else {
            totalSelectedBox++;

            if (isVsAIMode) {
                // AI's turn
                switchToAITurn();
            } else {
                // Switch to other player
                playerTurn = (playerTurn == 1) ? 2 : 1;
                updatePlayerHighlight();
            }
        }
    }

    private void switchToAITurn() {
        playerTurn = 2;
        updatePlayerHighlight();

        if(isVsAIMode) {
            makeAIMove();
        }
    }

    private void makeAIMove() {
        aiHandler.postDelayed(() -> {
            List<Integer> emptyPositions = new ArrayList<>();
            for(int i = 0; i < 9; i++) {
                if(boxPositions[i] == 0) emptyPositions.add(i);
            }

            if(!emptyPositions.isEmpty()) {
                int aiPosition = emptyPositions.get(new Random().nextInt(emptyPositions.size()));
                ImageView aiBox = getImageViewByPosition(aiPosition);

                if(aiBox != null) {
                    boxPositions[aiPosition] = 2;
                    aiBox.setImageResource(R.drawable.zero);

                    if(checkGameResult()) {
                        showResultDialog("AI Wins!");
                    } else if(totalSelectedBox == 9) {
                        showResultDialog("Game Draw!");
                    } else {
                        totalSelectedBox++;
                        switchToPlayerTurn();
                    }
                }
            }
        }, 1000);
    }

    private void switchToPlayerTurn() {
        playerTurn = 1;
        updatePlayerHighlight();
    }

    private void updatePlayerHighlight() {
        binding.playerOneLayout.setBackgroundResource(
                playerTurn == 1 ? R.drawable.black_border : R.drawable.white_box);
        binding.playerTwoLayout.setBackgroundResource(
                playerTurn == 2 ? R.drawable.black_border : R.drawable.white_box);
    }

    private ImageView getImageViewByPosition(int position) {
        switch(position) {
            case 0: return binding.image1;
            case 1: return binding.image2;
            case 2: return binding.image3;
            case 3: return binding.image4;
            case 4: return binding.image5;
            case 5: return binding.image6;
            case 6: return binding.image7;
            case 7: return binding.image8;
            case 8: return binding.image9;
            default: return null;
        }
    }

    private boolean checkGameResult() {
        for(int[] combination : combinationList) {
            if(boxPositions[combination[0]] == playerTurn &&
                    boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoxSelectable(int position) {
        return boxPositions[position] == 0;
    }

    private void showResultDialog(String message) {
        ResultDialog dialog = new ResultDialog(this, message, this);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void restartGame() {
        // Reset game state
        boxPositions = new int[9];
        playerTurn = 1;
        totalSelectedBox = 1;

        // Reset UI
        int[] viewIds = {R.id.image1, R.id.image2, R.id.image3,
                R.id.image4, R.id.image5, R.id.image6,
                R.id.image7, R.id.image8, R.id.image9};

        for(int id : viewIds) {
            ((ImageView) binding.getRoot().findViewById(id))
                    .setImageResource(R.drawable.white_box);
        }

        updatePlayerHighlight();

        // Restart AI if needed
        if(isVsAIMode && playerTurn == 2) {
            makeAIMove();
        }
    }
}