package com.example.moragame;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.provider.ContactsContract.Intents.Insert.NOTES;

public class MainActivity extends AppCompatActivity {
    // activity_main.xml
    private TextView tvMessage;
    private Button btnRegInfo;
    private Button btnStartGame;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.tvMessage);
        btnRegInfo = findViewById(R.id.btnRegInfo);
        btnStartGame = findViewById(R.id.btnStartGame);

        SharedPreferences pref = getSharedPreferences(NOTES,MODE_PRIVATE);
        String name = pref.getString("name","");

        // auto change to register page
        if(name.equals("")) {
            intent = new Intent(MainActivity.this, RegisterActivity.class);
            intent.putExtra("view", false);
            startActivity(intent);
            finish();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegInfo:
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.putExtra("view", true);
                startActivity(intent);
                break;

            case R.id.btnStartGame:
                intent = new Intent(MainActivity.this, StartGameActivity.class);
                startActivity(intent);
                break;

            case R.id.btnHistory:
                intent = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);
                break;

            case R.id.btnClearHistory:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme)
                        .setCancelable(true)
                        .setTitle("Confirmation")
                        .setMessage("Would you want to remove all game records?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GamesLog gamesLog = new GamesLog();
                                gamesLog.clearLog();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(MusicService.serviceCount==0)
            startService(new Intent(getApplicationContext(),MusicService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getApplicationContext(),MusicService.class));
    }
}
