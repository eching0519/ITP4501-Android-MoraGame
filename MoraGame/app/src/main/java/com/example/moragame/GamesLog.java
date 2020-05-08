package com.example.moragame;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class GamesLog {
    SQLiteDatabase db;
    String sql = "";
    String[] columns = {"gameDate", "gameTime", "opponentName", "winOrLost"};
    Cursor cursor = null;

    final String TAG = "SQLite";

    public GamesLog() {
        initialDB();
    }

    public void initialDB() {
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.moragame/GamesLog",null,SQLiteDatabase.CREATE_IF_NECESSARY);
            //sql = "DROP TABLE IF EXISTS GamesLog;";

            sql="CREATE TABLE IF NOT EXISTS GamesLog (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                            "gameDate text, gameTime text,opponentName text, winOrLost text);";
            db.execSQL(sql);

        } catch (SQLiteException e) {
            Log.d(TAG,e.getMessage());
        }
    }

    public void addLog(String gameDate, String gameTime, String opponentName, boolean win) {
        try{
            String winOrLost = win? "Win" : "Lose";
            sql = String.format("INSERT INTO GamesLog(gameDate, gameTime,opponentName, winOrLost) VALUES ('%s','%s','%s','%s');",gameDate,gameTime,opponentName,winOrLost);
            db.execSQL(sql);

            cursor = db.rawQuery("SELECT * FROM GamesLog;",null);
            String dataStr = "\nData updated: \n";
            while(cursor.moveToNext()) {
                dataStr += cursor.getInt(cursor.getColumnIndex("id"))+"\t";
                dataStr += cursor.getString(cursor.getColumnIndex("gameDate"))+"\t";
                dataStr += cursor.getString(cursor.getColumnIndex("gameTime"))+"\t";
                dataStr += cursor.getString(cursor.getColumnIndex("opponentName"))+"\t";
                dataStr += cursor.getString(cursor.getColumnIndex("winOrLost"))+"\n";
            }
            Log.d(TAG,dataStr);
        } catch (SQLiteException e){
            Log.d(TAG,e.getMessage());
        }
    }

    public void clearLog() {
        sql = "DROP TABLE IF EXISTS GamesLog;";
        db.execSQL(sql);
    }

    public int getWinTimes() {
            sql = "SELECT * FROM GamesLog WHERE winOrLost = 'Win';";
            cursor = db.rawQuery(sql, null);

            if(cursor.getCount()<=0)
                return 0;
            else
                return cursor.getCount();
    }

    public int getLoseTimes() {
        sql = "SELECT * FROM GamesLog WHERE winOrLost='Lose';";
        cursor=db.rawQuery(sql,null);

        if(cursor.getCount()<=0)
            return 0;
        else
            return cursor.getCount();
    }

    public Cursor getGamesLog() {
        return db.rawQuery("SELECT * FROM GamesLog;",null);
    }
}
