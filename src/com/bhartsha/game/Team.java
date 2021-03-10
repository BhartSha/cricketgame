package com.bhartsha.game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Team {
    private ArrayList<Player> players;
    private Player captain , wicketKeeper;
    private String teamName;
    private int bowlers , batsmen,allRounders,totalPlayers,teamId;
    public Team(String teamName , int teamId){
        this.teamName = teamName;
        this.teamId = teamId;
        players = new ArrayList<>();
    }
    public void addPlayer(Player A){
        if(A.getCategory().equals("Batsman"))
            batsmen++;
        else if(A.getCategory().equals("Bowler"))
            bowlers++;
        else
            allRounders++;
        totalPlayers++;
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
