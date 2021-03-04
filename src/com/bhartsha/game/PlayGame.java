package com.bhartsha.game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PlayGame {
    private int numberOfOver , targetScore , currentScore , currentWicket , finalScore , finalWicket;
    private Team team;
    private ArrayList<Player> players;
    private char[][] ballResult;
    Boolean chasing = false;
    private String oppositionTeam;
    public PlayGame(Team team , String oppositionTeam ,int numberOfOver , int targetScore){
        this.team = team;
        this.numberOfOver = numberOfOver;
        this.targetScore = targetScore;
        this.players = this.team.getPlayers();
        this.oppositionTeam = oppositionTeam;
        ballResult = new char[numberOfOver][6];
        chasing = true;
    }
    public PlayGame(Team team , String oppositionTeam , int numberOfOver){
        this.team = team;
        this.numberOfOver = numberOfOver;
        this.players = this.team.getPlayers();
        this.oppositionTeam = oppositionTeam;
        ballResult = new char[numberOfOver][6];
    }

    public void startInning(){
        int index=2,run;
        Player striker = players.get(0),temp = null;
        Player nonStriker = players.get(1);
        striker.setBattingStatus(true);
        nonStriker.setBattingStatus(true);
        for (int i = 0; i <numberOfOver ; i++) {
            for (int j = 0; j <6 ; j++) {
                if(currentWicket == 10){
                    showScore();
                    if(chasing)
                        showResult();
                    return;
                }
                striker.setBalls(striker.getBalls()+1);
                ballResult[i][j] = Utils.getRandomScore();
                if(ballResult[i][j] == 'W'){
                    striker.setOutStatus(true);
                    currentWicket++;
                    if(currentWicket!=10){
                        striker = players.get(index);
                        striker.setBattingStatus(true);
                        index++;
                    }
                }
                else{
                    run =  Character.getNumericValue(ballResult[i][j]);
                    currentScore += run;
                    striker.setRuns(striker.getRuns()+run);
                    if(run == 4){
                        striker.setFours(striker.getFours()+1);
                    }
                    if(run == 6){
                        striker.setSixes(striker.getSixes()+1);
                    }
                    if(run%2!=0){
                        temp = striker;
                        striker = nonStriker;
                        nonStriker = temp;
                    }
                }
                if(chasing && currentScore>targetScore){
                    showScore();
                    showResult();
                    return;
                }
            }
            temp = striker;
            striker = nonStriker;
            nonStriker = temp;
        }
        showScore();
        if(chasing)
            showResult();
    }

    public void showScore(){
        System.out.println("Player Name   R   B   4s   6s");
        for (Player player : players) {
            if(player.isBattingStatus())
                System.out.println(player.getPlayerName() + "    " + player.getRuns() +"   "+player.getBalls()+"   "+player.getFours()+"   "+player.getSixes());
        }
        finalScore = currentScore;
        finalWicket = currentWicket;
        System.out.println("Total:-   "+finalScore + " / "+finalWicket);
    }
    public void showResult(){
        if(currentScore>targetScore)
            System.out.println("Team "+team.getTeamName()+" Win!!");
        else if(currentScore<targetScore)
            System.out.println("Team "+oppositionTeam+ " Win!!");
        else
            System.out.println("Match Tie");
    }
}
