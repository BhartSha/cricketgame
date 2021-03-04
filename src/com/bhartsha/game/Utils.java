package com.bhartsha.game;

import lombok.experimental.UtilityClass;

import java.util.Random;
@UtilityClass
public class Utils {
    private final char[] possibleScore = {'W' , '0' , '1' , '2' , '3' , '4' ,'5', '6'};
    private final String[] coinFaces = {"head","tail"};
    public char getRandomScore(){
        int rnd = new Random().nextInt(possibleScore.length);
        return possibleScore[rnd];
    }
    public String getRandomCoinFace(){
        int rnd = new Random().nextInt(coinFaces.length);
        return coinFaces[rnd];
    }
}