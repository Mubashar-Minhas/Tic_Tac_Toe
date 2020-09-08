package com.example.tic_tac_toe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

class Player{
    TextView playerPoint,playerName;
    static TextView status;
    static  boolean turn= true;
    int playerPoints;
    String player_name;
    static int rounds;

    public void playerWins()
    {
        playerPoints++;
        status.setText(playerName.getText()+" Wins");
    }

    public void SetName(String defaultName){
        if (player_name.isEmpty())
            playerName.setText(defaultName);
        else
            playerName.setText(player_name);
    }

}

public class PlayGame extends AppCompatActivity implements View.OnClickListener{
    ImageButton home;
    ImageButton [][] imagebuttons = new ImageButton[3][3];
    int [][] buttons = new int[3][3];
//    boolean player1Turn = true;
    Player player1,player2;
    Animation blink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        player1 = new Player();
        player2 = new Player();

        player1.playerPoint = findViewById(R.id.player1_points);
        player2.playerPoint = findViewById(R.id.player2_points);
        home = findViewById(R.id.home);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        player1.player_name = bundle.getString("player1Name");
        player2.player_name = bundle.getString("player2Name");

        player1.playerName = findViewById(R.id.player1_name);
        player2.playerName = findViewById(R.id.player2_name);

        Player.status = findViewById(R.id.status);

        player1.SetName("Player 1");
        player2.SetName("Player 2");
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                imagebuttons[i][j] = findViewById(resID);
                imagebuttons[i][j].setOnClickListener(this);
            }
        }

        blink = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blinking);

        Player.status.setText(player1.playerName.getText()  +" turn");
        Player.status.startAnimation(blink);
        Player.status.setTextColor(getResources().getColor(R.color.red));

        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    @Override
    public void onClick(View v)
    {

        Drawable temp = v.getBackground();

        if(temp != null)
        {
            Toast.makeText(this, "Moves Over !!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Player.turn == true)
        {
            Player.status.setVisibility(View.VISIBLE);

            Player.status.setText(player2.playerName.getText()+" turn");
            Player.status.setTextColor(getResources().getColor(R.color.green));

            Player.status.startAnimation(blink);
            ((ImageButton) v).setImageResource(R.drawable.tictactoe_x);
        }
        else
        {
            Player.status.setVisibility(View.VISIBLE);
            Player.status.setText(player1.playerName.getText()+" turn");
            Player.status.startAnimation(blink);
            Player.status.setTextColor(getResources().getColor(R.color.red));

            ((ImageButton) v).setImageResource(R.drawable.tictactoe_o);
        }

        ((ImageButton) v).setEnabled(false);

        Player.rounds++;

        if(checkForWin())
        {
            if (Player.turn == true){
                player1.playerWins();
            Resetting(R.color.red);}

            else{
            player2.playerWins();
            Resetting(R.color.green);
        }
        }
        else if(Player.rounds == 9)
            draw();
        else
            Player.turn = !Player.turn;


    }
    private void Resetting(int color){
        Player.status.setTextColor(getResources().getColor(color));
        updatePointsText();
        resetBoard();

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                imagebuttons[i][j].setEnabled(false);
    }

    private boolean checkForWin()
    {
        String[][] field = new String[3][3];

        Drawable x = getResources().getDrawable(R.drawable.tictactoe_x);
        Drawable o = getResources().getDrawable(R.drawable.tictactoe_o);
        Drawable temp;

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                temp = imagebuttons[i][j].getDrawable();

                if(temp == null)
                    field[i][j] = "";
                else if(temp.getConstantState() == (x.getConstantState()))
                    field[i][j] = "X";
                else
                    field[i][j] = "O";
                //field[i][j] = imagebuttons[i][j].getDrawable();
            }
        }


        for(int i=0;i<3;i++)
            if (field[i][0].equals(field[i][1]) && field[i][1].equals(field[i][2]) && !field[i][0].equals(""))
                return true;

        for(int i=0;i<3;i++)
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(""))
                return true;

        if(field[0][0].equals(field[1][1]) && field[1][1].equals(field[2][2]) && !field[0][0].equals(""))
            return true;

        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals(""))
            return true;


        return false;
    }


    private void draw()
    {
        Player.status.setText("Draw !!");
        resetBoard();

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                imagebuttons[i][j].setEnabled(false);
    }

    private void updatePointsText()
    {
        player1.playerPoint.setText(player1.playerPoints+"");
        player2.playerPoint.setText(player2.playerPoints+"");
    }

    private void resetBoard()
    {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                imagebuttons[i][j].setBackground(null);

        Player.rounds=0;
        Player.turn=true;

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                imagebuttons[i][j].setEnabled(true);
    }

    private void resetGame()
    {
        Player.status.setText(player1.playerName.getText()+" Turn");
        Player.status.setTextColor(getResources().getColor(R.color.red));
        updatePointsText();
        resetBoard();
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                imagebuttons[i][j].setImageResource(0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("roundCount",Player.rounds);
        outState.putInt("player1Points",player1.playerPoints);
        outState.putInt("player2Points",player2.playerPoints);
        outState.putBoolean("playerTurn",Player.turn);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Player.rounds=savedInstanceState.getInt("roundCount");
        player1.playerPoints=savedInstanceState.getInt("player1Points");
        player2.playerPoints=savedInstanceState.getInt("player2Points");
        Player.turn=savedInstanceState.getBoolean("playerTurn");
    }

}
