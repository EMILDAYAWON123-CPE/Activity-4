package com.example.activity_3_dayawon;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login_sucessful extends AppCompatActivity  implements View.OnClickListener {

    TextView Score_player_one,Score_player_two,roundshow,drawcounter,player_return;
    Button button_reset;
    Button [] button = new Button[9];
    int playeronescorecount , playertwoscorecount, roundcount,round,draw;
    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};
    int [][]  winningStates= {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sucessful);

        Score_player_one = (TextView)findViewById(R.id.Score_one);
        Score_player_two = (TextView)findViewById(R.id.Score_two);
        button_reset = (Button)findViewById(R.id.btn_reset);
        roundshow = (TextView)findViewById(R.id.roundp);
        drawcounter = (TextView)findViewById(R.id.draw_p);

        for(int i=0;i<button.length; i++){
            String buttonID = "btn_" +i;
            int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
            button[i]= (Button) findViewById(resourceID);
            button[i].setOnClickListener(this);
        }
        roundcount = 0;
        playeronescorecount =0;
        playertwoscorecount =0;
        round = 1;
        draw = 0;
        activePlayer = true;

    }

    @Override
    public void onClick(View view) {
        Log.i("test","Button is pressed");
        if(!((Button)view).getText().toString().equals("")){
            return;
        }
        String buttonID =view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if(activePlayer){
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#ccc659"));

            gameState[gameStatePointer]=0;
        }
        else{
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#3bc3f7"));

            gameState[gameStatePointer]=1;
        }
        roundcount++;
        if(checkwinner()){
            if(activePlayer){
                Toast.makeText(this, "Player one Won", Toast.LENGTH_SHORT).show();
                playeronescorecount++;
                round++;
                updatePlayerScore();
                updateround();
                playAgain();

            }else{
                Toast.makeText(this, "Player two Won", Toast.LENGTH_SHORT).show();
                playertwoscorecount++;
                round++;
                updatePlayerScore();
                updateround();
                playAgain();
            }

        }else if(roundcount==9){
            round++;
            draw++;
            updateround();
            updatedraw();
            playAgain();
            Toast.makeText(this, "DRAW", Toast.LENGTH_SHORT).show();
        }else{
            activePlayer =!activePlayer;
        }
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playeronescorecount = 0;
                playertwoscorecount =0;
                draw = 0;
                round = 1;
                updatePlayerScore();
            }
        });
    }
    public boolean checkwinner(){
        boolean winnerResult = false;

        for(int []winningState:winningStates){
            if(gameState[winningState[0]]==gameState[winningState[1]]&&
                    gameState[winningState[1]]==gameState[winningState[2]] &&
                        gameState[winningState[0]] !=2){
                winnerResult = true;
            }

        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        Score_player_one.setText(Integer.toString(playeronescorecount));
        Score_player_two.setText(Integer.toString(playertwoscorecount));
    }
    public void playAgain(){
        roundcount = 0;
        activePlayer = true;

        for(int i = 0; i<button.length; i++){
            gameState[i] =2;
            button[i].setText("");
        }
    }
    public void updateround(){
        roundshow.setText(Integer.toString(round));
    }
    public void updatedraw(){
        drawcounter.setText(Integer.toString(draw));
    }

}