package com.bhartsha.game;

import java.util.ArrayList;

public class NextBatsman {
    private final ArrayList<Player> players;
    public NextBatsman(ArrayList<Player> players){
        this.players = players;
    }
    public Player getAccordingToStrikeRate(){
        Player player=null;
        double maxStrikeRate = -1;
        for (Player value : players) {
            if (!value.getAsBatsman().isBattingStatus()&& maxStrikeRate < value.getAsBatsman().getStrikeRate()) {
                player = value;
                maxStrikeRate = value.getAsBatsman().getStrikeRate();
            }
        }
        return player;
    }
    public Player getSequentially(){
        for (Player value : players) {
            if (!value.getAsBatsman().isBattingStatus()) {
                return value;
            }
        }
        return null;
    }
    public Player getRandomly(){
        ArrayList<Integer> bowlersIndex = new ArrayList<>();
        for (int i = 0; i <players.size() ; i++) {
            if (!players.get(i).getAsBatsman().isBattingStatus()) {
                bowlersIndex.add(i);
            }
        }
        int randomIndex = (int)(Math.random()*bowlersIndex.size());
        return players.get(randomIndex);
    }
}
