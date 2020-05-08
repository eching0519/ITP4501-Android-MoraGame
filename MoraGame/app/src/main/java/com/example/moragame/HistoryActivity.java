package com.example.moragame;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;
    Button btnBarChart, btnReturn;
    GamesLog gamesLog = new GamesLog();
    ArrayList<Record> recordList;
    Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.listView);
        btnBarChart = findViewById(R.id.btnBarChart);
        btnReturn = findViewById(R.id.btnReturn);

        recordList = new ArrayList<Record>();
        Cursor data = gamesLog.getGamesLog();
        int numRows = data.getCount();
        if(numRows == 0) {
            Toast.makeText(HistoryActivity.this,"No game history exist.",Toast.LENGTH_LONG).show();
            finish();
        } else {
            while(data.moveToNext()) {
                String gameDate = data.getString(data.getColumnIndex("gameDate"));
                String gameTime = data.getString(data.getColumnIndex("gameTime"));
                String opponentName = data.getString(data.getColumnIndex("opponentName"));
                String winOrLost = data.getString(data.getColumnIndex("winOrLost"));
                record = new Record(gameDate,gameTime,opponentName,winOrLost);
                recordList.add(record);
            }
            History_ListAdapter adapter = new History_ListAdapter(this,R.layout.list_adapter_view,recordList);
            listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }
    }

    public void onClick(View view) {
        if(view.getId()==R.id.btnReturn)
            finish();

        if(view.getId()==R.id.btnBarChart) {
            Intent intent = new Intent(HistoryActivity.this, BarChartActivity.class);
            startActivity(intent);
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
