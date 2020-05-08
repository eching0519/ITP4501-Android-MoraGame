package com.example.moragame;

public interface GameSetting {
    public final int PLAYER = 0;
    public final int OPPONENT = 1;
    public final int[][] HAND = {{0,0},{0,5},{5,0},{5,5}};
    public final String[] HAND_STR = {"(0,0)","(0,5)","(5,0)","(5,5)"};
    public final int[] GUESS_RANGE = {0,5,10,15,20};
    public final int DEFAULT_GUESS_VALUE = -1;
    public final int STONE = 0;
    public final int PAPER = 5;
}
