package com.bhartsha.game;

import lombok.Data;

@Data
public class Batsman {
    private int runs , balls , fours , sixes;
    private boolean outStatus , battingStatus;
    private double strikeRate;
    public void showResult(String playerName){
        System.out.print(playerName+"  "+runs+"  "+balls+"  "+fours+"  "+sixes);
    }
    public void addRuns(int run){
        runs+=run;
    }
    public void incrementBalls(){
        balls++;
    }
    public void incrementFours(){
        fours++;
    }
    public void incrementSixs(){
        sixes++;
    }
}
