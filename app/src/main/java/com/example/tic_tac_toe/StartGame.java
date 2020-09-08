package com.example.tic_tac_toe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class StartGame extends AppCompatActivity {
    CardView cardview;
    TextInputEditText player1,player2;
    String player1Name,player2Name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        cardview = (CardView) findViewById(R.id.card_view);

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);

        cardview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                player1Name = player1.getText().toString();
                player2Name = player2.getText().toString();

                Intent intent = new Intent(StartGame.this, PlayGame.class);
                intent.putExtra("player1Name",player1Name);
                intent.putExtra("player2Name",player2Name);
                startActivity(intent);
                finish();
            }
        });
    }
}
