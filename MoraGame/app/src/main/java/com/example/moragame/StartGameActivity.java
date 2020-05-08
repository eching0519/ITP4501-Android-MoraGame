package com.example.moragame;

import android.content.Intent;
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
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StartGameActivity extends AppCompatActivity implements GameSetting {

    public final String SERVER_URL = "https://4qm49vppc3.execute-api.us-east-1.amazonaws.com/Prod/itp4501_api/opponent/0";

    ServerConnect serverConnect = null;
    String jsonStr;
    JSONObject json = null;
    int id;
    String name, country;

    private TextView tvStatus,tvMessage;
    private Button btnStart;
    private ImageView imageView;

    private final String TAG = "Text Watcher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        tvStatus = findViewById(R.id.tvStatus);
        tvMessage = findViewById(R.id.tvMessage);
        btnStart = findViewById(R.id.btnStart);
        imageView = findViewById(R.id.imageView);

        // connect to server and get opponent information
        if(serverConnect==null || serverConnect.getStatus().equals(AsyncTask.Status.FINISHED)) {
            serverConnect = new ServerConnect();
            serverConnect.execute(SERVER_URL);
        }
    }

    public void onClick(View view){
        Intent intent = new Intent(StartGameActivity.this, GameActivity.class);
        intent.putExtra("id",id+"");
        intent.putExtra("name",name);
        startActivity(intent);
        finish();
    }

    private class ServerConnect extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
                id = jsonObject.getInt("id");
                name = jsonObject.getString("name");
                country = jsonObject.getString("country");

                tvStatus.setText("Your Opponent is Ready");
                String msg = String.format("%-20s%-15s\n%-15s%-15s\n%-15s%-15s","ID:",id,"Name:",name,"Country:",country);
                tvMessage.setText(msg);
                imageView.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.VISIBLE);
            } catch (Exception e){
                tvMessage.setText(e.getMessage());
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
