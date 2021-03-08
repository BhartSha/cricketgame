package com.bhartsha.game;

import java.util.ArrayList;

public class NextBowler {
    private final ArrayList<Player> players;
    public NextBowler(ArrayList<Player> players){
        this.players = players;
    }
    public Player getAccordingToStrikeRate(int id ,int maxOver){
        Player player=null;
        double maxStrikeRate = Double.MAX_VALUE;
        for (Player value : players) {
            if(value.getId()!=id&&maxOver>value.getAsBowler().getOvers()&&maxStrikeRate>value.getAsBowler().getStrikeRate()){
                player = value;
                maxStrikeRate = value.getAsBowler().getStrikeRate();
            }
        }
        return player;
    }
    public Player getSequentially(int id , int maxOver){
        for (Player value : players) {
            if (value.getId()!=id&&maxOver>value.getAsBowler().getOvers()&&(value.getCategory().equals("Bowler")||value.getCategory().equals("AllRounder"))) {
                return value;
            }
        }
        return null;
    }
    public Player getRandomly(int id , int maxOver){
        ArrayList<Integer> bowlersIndex = new ArrayList<>();
        for (int i = 0; i <players.size() ; i++) {
            if (players.get(i).getId()!=id&&maxOver>players.get(i).getAsBowler().getOvers()&&(players.get(i).getCategory().equals("Bowler")||players.get(i).getCategory().equals("AllRounder"))) {
                bowlersIndex.add(i);
            }
        }
        int randomIndex = (int)(Math.random()*bowlersIndex.size());
        return players.get(randomIndex);
    }
}
