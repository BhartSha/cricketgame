package com.bhartsha.game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Team {
    private ArrayList<Player> players;
    private Player captain , wicketKeeper;
    private String teamName;
    private int bowlers , batsmen,allRounders;
    public Team(String teamName){
        this.teamName = teamName;
        players = new ArrayList<>();
    }
    public void addPlayer(Player A){
        if(A.getCategory().equals("Batsman"))
            batsmen++;
        else if(A.getCategory().equals("Bowler"))
            bowlers++;
        else
            allRounders++;
        players.add(A);
    }

    public void showTeamDetail(){
        System.out.println("Team_Name: "+teamName);
        System.out.println("Players----");
        for (Player player : players) {
            System.out.println(player.getId() + "   " + player.getPlayerName() + "   " + player.getCategory());
        }
    }
}
