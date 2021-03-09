package com.bhartsha.game;

import lombok.Data;

@Data
public class Player {
    private String playerName , category;
    private int id;
    private Bowler asBowler;
    private Batsman asBatsman;
    public Player(int id , String playerName , String category){
        this.playerName = playerName;
        this.category = category;
        this.id = id;
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
