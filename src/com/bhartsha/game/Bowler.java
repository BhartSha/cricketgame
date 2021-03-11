package com.bhartsha.game;

import lombok.Data;

@Data
public class Bowler {
    private int wickets , overs , runs , maidens , balls;
    private double strikeRate;
    private boolean bowlingStatus;
    public void showResult(String playerName){
        System.out.println(playerName+"  "+overs+"    "+runs+"  "+wickets+"  "+maidens);
    }
}
