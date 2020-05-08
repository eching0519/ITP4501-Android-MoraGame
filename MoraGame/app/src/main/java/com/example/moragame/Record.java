package com.example.moragame;

public class Record {
    private String gameDate;
    private String gameTime;
    private String opponentName;
    private String winOrLost;

    public Record(String gameDate, String gameTime, String opponentName, String winOrLost) {
        this.gameDate = gameDate;
        this.gameTime = gameTime;
        this.opponentName = opponentName;
        this.winOrLost = winOrLost;
    }

    public String getGameDate() {
        return gameDate;
    }

    public String getGameTime() {
        return gameTime;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public String getWinOrLost() {
        return winOrLost;
    }
}
