package com.bhartsha.game;

import lombok.Data;

@Data
public class Player {
    private String playerName , category ,firstName , lastName;
    private int id;
    private Bowler asBowler;
    private Batsman asBatsman;
    public Player(int id ,String firstName , String lastName, String category){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.category = category;
        this.playerName = firstName+" "+lastName;
        asBatsman = new Batsman();
        asBowler = new Bowler();
    }
    public void printBattingStats(){
        asBatsman.showResult(playerName);
    }
    public void printBowlingStats(){
        asBowler.showResult(playerName);
    }
}
