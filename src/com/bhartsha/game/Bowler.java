package com.bhartsha.game;

import lombok.Data;

@Data
public class Bowler {
    private int wickets , runs , maidens , balls;
    private float overs;
    private double strikeRate;
    private boolean bowlingStatus;
    public void showResult(String playerName){
        System.out.println(playerName+"  "+overs+"    "+runs+"  "+wickets+"  "+maidens);
    }
    public void addRuns(int run){
        runs+=run;
    }
    public void incrementBalls(){
        balls++;
    }
    public void addOvers(float o){
        overs+=o;
    }
    public void incrementMaidens(){
        maidens++;
    }
    public void incrementWickets(){
        wickets++;
    }
}
