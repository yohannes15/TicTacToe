package com.example.yberh703.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicTacToeActivity extends AppCompatActivity {

    private Button myButtons[][] = new Button[3][3];
    private Button modeButtons[] = new Button[3];
    private TextView myResultTV;
    private String[] myOptions = {"O", "X"};
    private enum Mode{
        WITH_FRIEND, COMPUTER_FIRST, COMPUTER_SECOND
    }
    private enum Player{
        FIRST_PLAYER, SECOND_PLAYER
    }
    private boolean myGameIsOver;
    private Mode myMode = Mode.WITH_FRIEND;
    private Player myPlayer = Player.FIRST_PLAYER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        myButtons[0][0] = (Button) findViewById(R.id.upper_left_button);
        myButtons[0][1] = (Button) findViewById(R.id.upper_middle_button);
        myButtons[0][2] = (Button) findViewById(R.id.upper_right_button);

        myButtons[1][0] = (Button) findViewById(R.id.middle_left_button);
        myButtons[1][1] = (Button) findViewById(R.id.middle_middle_button);
        myButtons[1][2] = (Button) findViewById(R.id.middle_right_button);

        myButtons[2][0] = (Button) findViewById(R.id.lower_left_button);
        myButtons[2][1] = (Button) findViewById(R.id.lower_middle_button);
        myButtons[2][2] = (Button) findViewById(R.id.lower_right_button);

        modeButtons[0] = (Button) findViewById(R.id.with_friend);
        modeButtons[1] = (Button) findViewById(R.id.computer_first);
        modeButtons[2] = (Button) findViewById(R.id.computer_second);

        myResultTV = (TextView) findViewById(R.id.result);

        initGame();
        initButtons();
    }

    private void initGame(){
        for (int i = 0; i < 3; i++) {
            final int finalI = i;
            modeButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myGameIsOver = false;
                    myPlayer = Player.FIRST_PLAYER;
                    myResultTV.setText("");

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            myButtons[i][j].setText("");
                        }
                    }

                    switch (finalI) {
                        case 0:
                            myMode = Mode.WITH_FRIEND;
                            break;
                        case 1:
                            myMode = Mode.COMPUTER_FIRST;
                            computerPlay();
                            swapPlayer();
                            break;
                        case 2:
                            myMode = Mode.COMPUTER_SECOND;
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    private void initButtons() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int finalI = i;
                final int finalJ = j;
                myButtons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("".equals(myButtons[finalI][finalJ].getText().toString()) && !myGameIsOver) {
                            myButtons[finalI][finalJ].setText(signPlayer(myPlayer));
                            myGameIsOver = isGameOver();
                            if (myGameIsOver) {
                                decideWinner();
                            } else {
                                swapPlayer();
                                switch (myMode) {
                                    case COMPUTER_FIRST:
                                    case COMPUTER_SECOND:
                                        computerPlay();
                                        myGameIsOver = isGameOver();
                                        if (myGameIsOver) {
                                            decideWinner();
                                        } else {
                                            swapPlayer();
                                        }
                                        break;
                                }
                            }

                        }
                    }
                });
            }
        }
    }

    private String signPlayer(Player player) {
        if (player == Player.FIRST_PLAYER) {
            return myOptions[0];
        } else {
            return myOptions[1];
        }
    }

    private String signOpponent(Player player) {
        if (player == Player.FIRST_PLAYER) {
            return myOptions[1];
        } else {
            return myOptions[0];
        }
    }

    private void swapPlayer() {
        if (myPlayer == Player.FIRST_PLAYER) {
            myPlayer = Player.SECOND_PLAYER;
        } else {
            myPlayer = Player.FIRST_PLAYER;
        }
    }


    private boolean isGameOver() {

        if (hasWinner()) {
            return true;
        }

        return isTied();
    }

    private void computerPlay() {

        String[][] strings = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                strings[i][j] = myButtons[i][j].getText().toString();
            }
        }

        if (isWinPoint(strings, signPlayer(myPlayer))) {
            return;
        }

        if (isWinPoint(strings, signOpponent(myPlayer))) {
            return;
        }

        if ("".equals(strings[1][1])) {
            myButtons[1][1].setText(signPlayer(myPlayer));
            return;
        }

        for (int i = 0; i < 3; i += 2) {
            for (int j = 0; j < 3; j += 2) {
                if ("".equals(strings[i][j])) {
                    myButtons[i][j].setText(signPlayer(myPlayer));
                    return;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j ++) {
                if ("".equals(strings[i][j])) {
                    myButtons[i][j].setText(signPlayer(myPlayer));
                    return;
                }
            }
        }

    }


    private void decideWinner() {

        String winner = "";

        if (isTied()) {
            winner = "Nobody";
        } else {
            switch (myMode) {
                case WITH_FRIEND:
                    if (myPlayer == Player.FIRST_PLAYER) {
                        winner = "First Player";
                    } else {
                        winner = "Second Player";
                    }
                    break;
                case COMPUTER_FIRST:
                    if (myPlayer == Player.FIRST_PLAYER) {
                        winner = "Computer";
                    } else {
                        winner = "You";
                    }
                    break;
                case COMPUTER_SECOND:
                    if (myPlayer == Player.SECOND_PLAYER) {
                        winner = "Computer";
                    } else {
                        winner = "You";
                    }
                    break;
                default:
                    break;
            }
        }

        myResultTV.setText("The winner is " + winner);
    }

    private boolean isWinPoint(String[][] strings, String winSign) {
        for (int i = 0; i < 3; i++) {
            if (getWinSign(strings[i][0], strings[i][1], strings[i][2]).equals(winSign)) {
                for (int j = 0; i < 3; j++) {
                    if ("".equals(strings[i][j])) {
                        myButtons[i][j].setText(signPlayer(myPlayer));
                        return true;
                    }
                }
            }

            if (getWinSign(strings[0][i], strings[1][i], strings[2][i]).equals(winSign)) {
                for (int j = 0; i < 3; j++) {
                    if ("".equals(strings[j][i])) {
                        myButtons[j][i].setText(signPlayer(myPlayer));
                        return true;
                    }
                }
            }
        }

        if (getWinSign(strings[0][0], strings[1][1], strings[2][2]).equals(winSign)) {
            for (int i = 0; i < 3; i++) {
                if ("".equals(strings[i][i])) {
                    myButtons[i][i].setText(signPlayer(myPlayer));
                    return true;
                }
            }
        }

        if (getWinSign(strings[2][0], strings[1][1], strings[0][2]).equals(winSign)) {
            for (int i = 0; i < 3; i++) {
                if ("".equals(strings[2 - i][i])) {
                    myButtons[2 - i][i].setText(signPlayer(myPlayer));
                    return true;
                }
            }
        }

        return false;
    }

    private String getWinSign(String s1, String s2, String s3) {

        for (int i = 0; i < myOptions.length; i++) {
            if ((myOptions[i] + myOptions[i]).equals(s1 + s2 + s3)) {
                return myOptions[i];
            }
        }

        return "";

    }

    private boolean hasWinner() {
        String[][] strings = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                strings[i][j] = myButtons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (strings[i][0].equals(strings[i][1]) &&
                    strings[i][0].equals(strings[i][2]) &&
                    !"".equals(strings[i][0])) {
                return true;
            }

            if (strings[0][i].equals(strings[1][i]) &&
                    strings[0][i].equals(strings[2][i]) &&
                    !"".equals(strings[0][i])) {
                return true;
            }

        }

        if (strings[0][0].equals(strings[1][1]) &&
                strings[0][0].equals(strings[2][2]) &&
                !"".equals(strings[0][0])) {
            return true;
        }

        if (strings[2][0].equals(strings[1][1]) &&
                strings[2][0].equals(strings[0][2]) &&
                !"".equals(strings[2][0])) {
            return true;
        }

        return false;
    }

    private boolean isTied() {

        if (hasWinner()) {
            return false;
        }

        String[][] strings = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                strings[i][j] = myButtons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ("".equals(strings[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

}

