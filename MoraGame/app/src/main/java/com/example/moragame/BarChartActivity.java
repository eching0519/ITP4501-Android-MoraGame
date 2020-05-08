package com.example.moragame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class BarChartActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barChart = new BarChart(this);
        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setContentView(barChart);
    }

    class BarChart extends View {
        public BarChart(Context context) {
            super(context);
        }

        GamesLog gamesLog = new GamesLog();
        String title = "Win and Lose";
        String[] series = {"Win","Lose"};

        int[] data = { gamesLog.getWinTimes(),gamesLog.getLoseTimes() };

        public void onDraw(Canvas c) {
            super.onDraw(c);
            Log.d("GameLog",gamesLog.getWinTimes()+" "+gamesLog.getLoseTimes()+"");
            this.setBackgroundResource(R.drawable.wallpaper_light);

            // get display width and height
            int width = getWidth();
            int height = getHeight();

            // set paint
            Paint paint = new Paint();
            paint.setStrokeWidth(10);

            int xOffset = (int)(width/6);
            int yOffset = (int)(height/9);

            // draw series
            int textSize = 100;
            paint.setTypeface(Typeface.createFromAsset(getAssets(), "font/GoodDogPlain.ttf"));
            paint.setTextSize(textSize);
            paint.setTextAlign(Paint.Align.CENTER);
            // text shadow
            int shadow = 7;
            paint.setColor(Color.WHITE);
            c.drawText(series[0], (float)(xOffset*2)+shadow,(height-yOffset)+textSize+shadow,paint);
            c.drawText(series[1], (float)(xOffset*4)+shadow,(height-yOffset)+textSize+shadow,paint);
            // draw text
            paint.setColor(Color.parseColor("#f27752"));
            c.drawText(series[0], (float)(xOffset*2),(height-yOffset)+textSize,paint);
            paint.setColor(Color.parseColor("#5ebcf2"));
            c.drawText(series[1], (float)(xOffset*4),(height-yOffset)+textSize,paint);

            // draw title
            paint.setColor(Color.WHITE);
            c.drawText(title,width/2+shadow,yOffset+shadow,paint);
            paint.setColor(Color.parseColor("#F52549"));
            c.drawText(title,width/2,yOffset,paint);

            // draw bar
            int bar1Height;
            int bar2Height;
            if(data[0]>data[1]) {
                bar1Height = (int)((height - yOffset) * 0.25);
                bar2Height = (int)(height - yOffset - (height - yOffset - bar1Height) * data[1]/data[0]);
            } else {
                bar2Height = (int)((height - yOffset) * 0.25);
                bar1Height = (int)(height - yOffset - (height - yOffset - bar2Height) * data[0]/data[1]);
            }
            paint.setColor(Color.parseColor("#f27752"));
            c.drawRect(xOffset*3/2,bar1Height,xOffset*5/2,height-yOffset,paint);
            paint.setColor(Color.parseColor("#5ebcf2"));
            c.drawRect(xOffset*7/2,bar2Height,xOffset*9/2,height-yOffset,paint);

            // draw X Y axis
            paint.setColor(Color.GRAY);
            c.drawLine(xOffset/2,height-yOffset,width-xOffset/2,height-yOffset,paint);
            c.drawLine(xOffset,yOffset/2,xOffset,height-yOffset/2,paint);

            // draw value of the bars
            paint.setColor(Color.parseColor("#F52549"));
            paint.setTextSize(textSize);
            Rect textBounds = new Rect();
            if(data[0]!=data[1]) {
                paint.getTextBounds(data[0]+"",0,(data[0]+"").length(), textBounds);
                c.drawText(data[0] + "", xOffset - textSize, bar1Height + (textBounds.bottom-textBounds.top)/2, paint);
            }
            paint.getTextBounds(data[1]+"",0,(data[1]+"").length(), textBounds);
            c.drawText(data[1]+"",xOffset-textSize,bar2Height + (textBounds.bottom-textBounds.top)/2,paint);

            // draw value line
            paint.setColor(Color.parseColor("#3d2317"));
            if(data[0]!=data[1])
                c.drawLine(xOffset/5*4,bar1Height,xOffset*5/2,bar1Height,paint);
            c.drawLine(xOffset/5*4,bar2Height,xOffset*9/2,bar2Height,paint);

            // draw percentage
            textSize = 80;
            paint.setTextSize(textSize);
            String percentage1 = String.format("%.1f",(double)data[0]/(data[0]+data[1])*100)+"%";
            String percentage2 = String.format("%.1f",(double)data[1]/(data[0]+data[1])*100)+"%";
            // bar1 percentage
            paint.getTextBounds(percentage1,0,percentage1.length(),textBounds);
            paint.setColor(Color.WHITE);
            c.drawText(percentage1,xOffset*2-shadow,bar1Height-textBounds.bottom-10-shadow,paint);
            paint.setColor(Color.parseColor("#8AA822"));
            c.drawText(percentage1,xOffset*2,bar1Height-textBounds.bottom-10,paint);
            // bar2 percentage
            paint.getTextBounds(percentage2,0,percentage2.length(),textBounds);
            paint.setColor(Color.WHITE);
            c.drawText(percentage2,xOffset*4-shadow,bar2Height-textBounds.bottom-10-shadow,paint);
            paint.setColor(Color.parseColor("#8AA822"));
            c.drawText(percentage2,xOffset*4,bar2Height-textBounds.bottom-10,paint);
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
