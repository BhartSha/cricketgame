package com.bhartsha.game;

import lombok.Data;

@Data
public class Batsman {
    private int runs , balls , fours , sixes;
    private boolean outStatus , battingStatus;
    private double strikeRate;
    public void showResult(String playerName){
        System.out.println(playerName+"  "+runs+"  "+balls+"  "+fours+"  "+sixes);
    }
}
