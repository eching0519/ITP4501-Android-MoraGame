package com.example.moragame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GameStatisticActivity extends AppCompatActivity implements GameSetting {

    private int[] guessStat_p, guessStat_c, handStat_p, handStat_c;
    PieChart pcGuessC, pcGuessP, pcHandC, pcHandP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_statistic);
        Intent intent = getIntent();

        guessStat_p = intent.getIntArrayExtra("guessStat_p");
        guessStat_c = intent.getIntArrayExtra("guessStat_c");
        handStat_p = intent.getIntArrayExtra("handStat_p");
        handStat_c = intent.getIntArrayExtra("handStat_c");

        intent.getBundleExtra("guessStat");
        intent.getBundleExtra("handStat");

        // set chart
        pcGuessC = findViewById(R.id.pieChart_guessC);
        pcGuessC.setUsePercentValues(true);
        pcGuessP = findViewById(R.id.pieChart_guessP);
        pcGuessP.setUsePercentValues(true);
        pcHandC = findViewById(R.id.pieChart_handC);
        pcHandC.setUsePercentValues(true);
        pcHandP = findViewById(R.id.pieChart_handP);
        pcHandP.setUsePercentValues(true);

        setPieChart_guessC();
        setPieChart_guessP();
        setPieChart_handC();
        setPieChart_handP();
    }

    private void setPieChart_guessC() {
        List<PieEntry> value = new ArrayList<>();
        Description desc = new Description();
        desc.setText("Opponent Guess");
        desc.setTextSize(18f);
        pcGuessC.setDescription(desc);
        for(int i=0 ; i<GUESS_RANGE.length ; i++) {
            value.add(new PieEntry(guessStat_c[i],GUESS_RANGE[i]+""));
        }
        PieDataSet pieDataSet = new PieDataSet(value, "Guess");
        PieData pieData = new PieData(pieDataSet);
        pcGuessC.setData(pieData);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
    }

    private void setPieChart_guessP() {
        List<PieEntry> value = new ArrayList<>();
        Description desc = new Description();
        desc.setText("Your Guess");
        desc.setTextSize(18f);
        pcGuessP.setDescription(desc);
        for(int i=0 ; i<GUESS_RANGE.length ; i++) {
            value.add(new PieEntry(guessStat_p[i],GUESS_RANGE[i]+""));
        }
        PieDataSet pieDataSet = new PieDataSet(value, "Guess");
        PieData pieData = new PieData(pieDataSet);
        pcGuessP.setData(pieData);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
    }

    private void setPieChart_handC() {
        List<PieEntry> value = new ArrayList<>();
        Description desc = new Description();
        desc.setText("Opponent Hand Count");
        desc.setTextSize(18f);
        pcHandC.setDescription(desc);
        for(int i=0 ; i<HAND_STR.length ; i++) {
            value.add(new PieEntry(handStat_c[i],HAND_STR[i]+""));
        }
        PieDataSet pieDataSet = new PieDataSet(value, "Hand");
        PieData pieData = new PieData(pieDataSet);
        pcHandC.setData(pieData);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
    }

    private void setPieChart_handP() {
        List<PieEntry> value = new ArrayList<>();
        Description desc = new Description();
        desc.setText("Your Hand Count");
        desc.setTextSize(18f);
        pcHandP.setDescription(desc);
        for(int i=0 ; i<HAND_STR.length ; i++) {
            value.add(new PieEntry(handStat_p[i],HAND_STR[i]+""));
        }
        PieDataSet pieDataSet = new PieDataSet(value, "Hand");
        PieData pieData = new PieData(pieDataSet);
        pcHandP.setData(pieData);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOpponentGuess:
                pcGuessC.setVisibility(View.VISIBLE);
                pcGuessP.setVisibility(View.GONE);
                pcHandC.setVisibility(View.GONE);
                pcHandP.setVisibility(View.GONE);
                pcGuessC.animateXY(1400,1400);
                break;
            case R.id.btnPlayerGuess:
                pcGuessC.setVisibility(View.GONE);
                pcGuessP.setVisibility(View.VISIBLE);
                pcHandC.setVisibility(View.GONE);
                pcHandP.setVisibility(View.GONE);
                pcGuessP.animateXY(1400,1400);
                break;
            case R.id.btnOpponentHand:
                pcGuessC.setVisibility(View.GONE);
                pcGuessP.setVisibility(View.GONE);
                pcHandC.setVisibility(View.VISIBLE);
                pcHandP.setVisibility(View.GONE);
                pcHandC.animateXY(1400,1400);
                break;
            case R.id.btnPlayerHand:
                pcGuessC.setVisibility(View.GONE);
                pcGuessP.setVisibility(View.GONE);
                pcHandC.setVisibility(View.GONE);
                pcHandP.setVisibility(View.VISIBLE);
                pcHandP.animateXY(1400,1400);
                break;
        }
    }

    public void btnOnClick(View view) {
        if(view.getId()==R.id.btnQuit) {
            finish();
            GameActivity.game.finish();
        }

        if(view.getId()==R.id.btnRestart) {
            Intent intent = new Intent(GameStatisticActivity.this, StartGameActivity.class);
            startActivity(intent);
            finish();
            GameActivity.game.finish();
        }

        if(view.getId()==R.id.btnBarChart) {
            Intent intent = new Intent(GameStatisticActivity.this, BarChartActivity.class);
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
