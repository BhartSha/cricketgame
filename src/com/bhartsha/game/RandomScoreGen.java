package com.bhartsha.game;

import java.util.Random;

public class RandomScoreGen {
    private final char[] possibleScore = {'W' , '0' , '1' , '2' , '3' , '4' , '6'};
    public char randomScore(){
        int rnd = new Random().nextInt(possibleScore.length);
        return possibleScore[rnd];
    }
}
