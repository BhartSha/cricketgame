package com.bhartsha.game;

import lombok.Data;

@Data
public class Player {
    private String playerName , category;
    private int id , runs , wickets , balls , fours , sixes;
    private boolean outStatus , battingStatus;
    public Player(int id , String playerName , String category){
        this.playerName = playerName;
        this.category = category;
        this.id = id;
        this.outStatus = false;
        this.battingStatus = false;
    }
}
