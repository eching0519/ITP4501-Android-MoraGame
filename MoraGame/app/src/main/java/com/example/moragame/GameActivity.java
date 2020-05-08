package com.example.moragame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GameActivity extends AppCompatActivity implements GameSetting {

    public final String SERVER_URL = "https://4qm49vppc3.execute-api.us-east-1.amazonaws.com/Prod/itp4501_api/opponent/";
    ServerConnect serverConnect = null;

    // Opponent information
    private String opponentName = "";
    private int opponentId = 0;

    // Game initial setting
    public static Activity game;
    private int turn = OPPONENT;
    private int round = 0;
    private int guess_player = DEFAULT_GUESS_VALUE;
    private int guess_opponent = DEFAULT_GUESS_VALUE;
    private int[] hand_player = {0,0};
    private int[] hand_opponent = {0,0};
    private boolean decider = false;

    // Game statistic
    private int[][] stat_guess = new int[2][GUESS_RANGE.length];
    private int[][] stat_hand = new int[2][HAND.length];

    // View
    private TextView tvTurn,tvMessage, tvLoad;
    private ImageView imgLeft, imgRight, imgLeft_c, imgRight_c, opponentDialog,playerDialog;
    private RadioGroup handChoice;
    private Button btnGuess0,btnGuess5,btnGuess10,btnGuess15,btnGuess20, btnGo;
    private Button btnStatistic, btnQuit, btnRestart;

    MediaPlayer player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        game = this;
        Intent intent = getIntent();
        opponentId = Integer.parseInt(intent.getStringExtra("id"));
        opponentName = intent.getStringExtra("name");

        // Statistic data initialise
        for(int x = 0 ; x < stat_hand.length ; x++) {
            for(int y = 0 ; y < stat_hand[x].length ; y++) {
                stat_hand[x][y] = 0;
            }
        }
        for(int x = 0 ; x < stat_guess.length ; x++) {
            for(int y = 0 ; y < stat_guess[x].length ; y++) {
                stat_guess[x][y] = 0;
            }
        }

        // View definition
        tvTurn=findViewById(R.id.tvTurn);
        tvMessage=findViewById(R.id.tvMessage);
        tvLoad=findViewById(R.id.tvLoad);
        imgLeft=findViewById(R.id.imgLeft);
        imgRight=findViewById(R.id.imgRight);
        imgLeft_c=findViewById(R.id.imgLeft_c);
        imgRight_c=findViewById(R.id.imgRight_c);
        opponentDialog=findViewById(R.id.opponentDialog);
        playerDialog=findViewById(R.id.playerDialog);
        handChoice = findViewById(R.id.handChoice);
        btnGuess0=findViewById(R.id.btnGuess0);
        btnGuess5=findViewById(R.id.btnGuess5);
        btnGuess10=findViewById(R.id.btnGuess10);
        btnGuess15=findViewById(R.id.btnGuess15);
        btnGuess20=findViewById(R.id.btnGuess20);
        btnGo=findViewById(R.id.btnGo);
        btnStatistic=findViewById(R.id.btnStatistic);
        btnQuit=findViewById(R.id.btnQuit);
        btnRestart=findViewById(R.id.btnRestart);

        // swap to player turn
        swapTurn();

        // change value when user select different hand's options
        handChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.hand00:
                        imgLeft.setImageResource(R.drawable.stone139);
                        imgRight.setImageResource(R.drawable.stone139_right);
                        hand_player = HAND[0];
                        break;
                    case R.id.hand05:
                        imgLeft.setImageResource(R.drawable.stone139);
                        imgRight.setImageResource(R.drawable.paper139_right);
                        hand_player = HAND[1];
                        break;
                    case R.id.hand50:
                        imgLeft.setImageResource(R.drawable.paper139);
                        imgRight.setImageResource(R.drawable.stone139_right);
                        hand_player = HAND[2];
                        break;
                    case R.id.hand55:
                        imgLeft.setImageResource(R.drawable.paper139);
                        imgRight.setImageResource(R.drawable.paper139_right);
                        hand_player = HAND[3];
                        break;
                }
            }
        });

    }


    public void onClick(View view){
        playerDialog.setVisibility(View.INVISIBLE);
        opponentDialog.setVisibility(View.INVISIBLE);

        // get player guess number
        if(turn == PLAYER)
            switch (view.getId()) {
                case R.id.btnGuess0:
                    guess_player = 0;
                    break;
                case R.id.btnGuess5:
                    guess_player = 5;
                    break;
                case R.id.btnGuess10:
                    guess_player = 10;
                    break;
                case R.id.btnGuess15:
                    guess_player = 15;
                    break;
                case R.id.btnGuess20:
                    guess_player = 20;
                    break;
            }

        // get opponent's options
        getOpponentOptions();

        // freeze user action when getting opponent options by setting Views visibility
        handChoice.setVisibility(View.GONE);
        btnGuess0.setVisibility(View.GONE);
        btnGuess5.setVisibility(View.GONE);
        btnGuess10.setVisibility(View.GONE);
        btnGuess15.setVisibility(View.GONE);
        btnGuess20.setVisibility(View.GONE);
        btnGo.setVisibility(View.GONE);
        tvLoad.setVisibility(View.VISIBLE);
    }

    private void swapTurn() {
        decider = false;
        if(turn == OPPONENT) {
            // swap to player
            turn = PLAYER;
            round++;
            tvTurn.setText("Your Turn");
            tvMessage.setText("Round "+round);
        } else {
            // swap to opponent
            turn = OPPONENT;
            round++;
            tvTurn.setText(opponentName + "'s Turn");
            tvMessage.setText("Round "+round);
        }
        setButton();
    }

    private void setButton() {
        if(turn == PLAYER) {
            handChoice.setVisibility(View.VISIBLE);
            btnGuess0.setVisibility(View.VISIBLE);
            btnGuess5.setVisibility(View.VISIBLE);
            btnGuess10.setVisibility(View.VISIBLE);
            btnGuess15.setVisibility(View.VISIBLE);
            btnGuess20.setVisibility(View.VISIBLE);
            btnGo.setVisibility(View.GONE);
            tvLoad.setVisibility(View.GONE);
        } else {
            handChoice.setVisibility(View.VISIBLE);
            btnGuess0.setVisibility(View.GONE);
            btnGuess5.setVisibility(View.GONE);
            btnGuess10.setVisibility(View.GONE);
            btnGuess15.setVisibility(View.GONE);
            btnGuess20.setVisibility(View.GONE);
            btnGo.setVisibility(View.VISIBLE);
            tvLoad.setVisibility(View.GONE);
        }
    }

    // get opponent options from the server
    private void getOpponentOptions() {
        if(serverConnect==null || serverConnect.getStatus().equals(AsyncTask.Status.FINISHED)){
            serverConnect = new ServerConnect();
            serverConnect.execute(SERVER_URL+opponentId);
        }
    }

    // after connecting to the server, set opponent option
    private void setOpponentOptions(int left, int right, int guess) {
        if(left==0 && right==0)
            hand_opponent = HAND[0];
        else if(left==0 && right==5)
            hand_opponent = HAND[1];
        else if(left==5 && right==5)
            hand_opponent = HAND[2];
        else
            hand_opponent = HAND[3];

        if(hand_opponent[0] == STONE)
            imgLeft_c.setImageResource(R.drawable.stone139_r);
        else
            imgLeft_c.setImageResource(R.drawable.paper139_r);

        if(hand_opponent[1] == STONE)
            imgRight_c.setImageResource(R.drawable.stone139_r_right);
        else
            imgRight_c.setImageResource(R.drawable.paper139_r_right);

        if(turn == OPPONENT)
            guess_opponent = guess;
        else
            guess_opponent = DEFAULT_GUESS_VALUE;
    }

    // get the player & opponent hand's result
    private int getHandResult() {
        return hand_player[0]+hand_player[1]+hand_opponent[0]+hand_opponent[1];
    }

    // check result
    private void checkResult() {
        collectStatistic();
        if(turn == PLAYER){
            // Display guess dialog
            playerDialog.setVisibility(View.VISIBLE);
            switch (guess_player) {
                case 0:
                    playerDialog.setImageResource(R.drawable.dialog_p_0);
                    break;
                case 5:
                    playerDialog.setImageResource(R.drawable.dialog_p_5);
                    break;
                case 10:
                    playerDialog.setImageResource(R.drawable.dialog_p_10);
                    break;
                case 15:
                    playerDialog.setImageResource(R.drawable.dialog_p_15);
                    break;
                case 20:
                    playerDialog.setImageResource(R.drawable.dialog_p_20);
                    break;
            }
            // check result
            if(guess_player==getHandResult()){
                if(decider) {
                    winGame();
                }
                else {
                    decider = true;
                    tvTurn.setText(tvTurn.getText()+" - Decider");
                    setButton();
                }
            } else {
                swapTurn();
            }
        } else {
            // Display guess dialog
            opponentDialog.setVisibility(View.VISIBLE);
            switch (guess_opponent) {
                case 0:
                    opponentDialog.setImageResource(R.drawable.dialog_o_0);
                    break;
                case 5:
                    opponentDialog.setImageResource(R.drawable.dialog_o_5);
                    break;
                case 10:
                    opponentDialog.setImageResource(R.drawable.dialog_o_10);
                    break;
                case 15:
                    opponentDialog.setImageResource(R.drawable.dialog_o_15);
                    break;
                case 20:
                    opponentDialog.setImageResource(R.drawable.dialog_o_20);
                    break;
            }
            // check result
            if(guess_opponent==getHandResult()){
                if(decider) {
                    loseGame();
                } else {
                    decider = true;
                    tvTurn.setText(tvTurn.getText()+" - Decider");
                    setButton();
                }
            } else {
                swapTurn();
            }
        }
    }

    private void collectStatistic() {
        for(int i=0 ; i<HAND.length ; i++) {
            if(hand_player.equals(HAND[i])) {
                stat_hand[PLAYER][i]++;
            }
            if(hand_opponent.equals(HAND[i])) {
                stat_hand[OPPONENT][i]++;
            }
        }

        for(int i = 0 ; i<GUESS_RANGE.length ; i++) {
            if(guess_player==GUESS_RANGE[i]) {
                stat_guess[PLAYER][i]++;
            }
            if(guess_opponent==GUESS_RANGE[i]) {
                stat_guess[OPPONENT][i]++;
            }
        }
    }

    private void winGame() {
        player = MediaPlayer.create(this, R.raw.cheering);
        player.start();
        tvLoad.setText("You Win!");
        btnRestart.setVisibility(View.VISIBLE);
        btnQuit.setVisibility(View.VISIBLE);
        btnStatistic.setVisibility(View.VISIBLE);

        // Set Game Log
        GamesLog log = new GamesLog();
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        log.addLog(dateFormat.format(currentTime)+"", timeFormat.format(currentTime)+"",opponentName+"",true);
    }

    private void loseGame() {
        player = MediaPlayer.create(this, R.raw.sad_trombone);
        player.start();
        tvLoad.setText("You Lose...");
        btnRestart.setVisibility(View.VISIBLE);
        btnQuit.setVisibility(View.VISIBLE);
        btnStatistic.setVisibility(View.VISIBLE);

        // Set Game Log
        GamesLog log = new GamesLog();
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        log.addLog(dateFormat.format(currentTime)+"", timeFormat.format(currentTime)+"",opponentName+"",false);
    }

    public void btnOnClick(View view){
        if(view.getId()==R.id.btnRestart) {
            Intent intent = new Intent(GameActivity.this, StartGameActivity.class);
            startActivity(intent);
            finish();
        }

        if(view.getId()==R.id.btnQuit) {
            finish();
        }

        if(view.getId()==R.id.btnStatistic) {
            Intent intent = new Intent(GameActivity.this,GameStatisticActivity.class);
            intent.putExtra("guessStat_p",stat_guess[PLAYER]);
            intent.putExtra("guessStat_c",stat_guess[OPPONENT]);
            intent.putExtra("handStat_p", stat_hand[PLAYER]);
            intent.putExtra("handStat_c", stat_hand[OPPONENT]);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }

    private class ServerConnect extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                InputStream inputStream = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while((line=reader.readLine())!=null)
                    result += line;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int left = jsonObject.getInt("left");
                int right = jsonObject.getInt("right");
                int guess = jsonObject.getInt("guess");

                // set opponent options
                setOpponentOptions(left, right, guess);
                // check result
                checkResult();

            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
