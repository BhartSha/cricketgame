package com.bhartsha.game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Team {
    private ArrayList<Player> players;
    private String teamName;
    public Team(String teamName , String[] playersName){
        this.teamName = teamName;
        players = new ArrayList<>();
        for (String s : playersName) {
            Player obj = new Player(s);
            players.add(obj);
        }
    }
}
