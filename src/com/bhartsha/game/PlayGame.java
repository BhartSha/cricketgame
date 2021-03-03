package com.bhartsha.game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PlayGame {
    private int numberOfOver , targetScore , currentScore=0 , currentWicket=0 , finalScore=0 , finalWicket=0;
    private Team team;
    private ArrayList<Player> players;
    private char[][] ballResult;
    Boolean chasing = false;
    private RandomScoreGen randomObj = new RandomScoreGen();
    public PlayGame(Team team , int numberOfOver , int targetScore){
        this.team = team;
        this.numberOfOver = numberOfOver;
        this.targetScore = targetScore;
        this.players = this.team.getPlayers();
        ballResult = new char[numberOfOver][6];
    }
    public PlayGame(Team team , int numberOfOver){
        this.team = team;
        this.numberOfOver = numberOfOver;
        this.players = this.team.getPlayers();
        ballResult = new char[numberOfOver][6];
    }

    public void firstInning(){
        for (int i = 0; i <numberOfOver ; i++) {
            for (int j = 0; j <6 ; j++) {
                if(currentWicket == 10){
                    showScore();
                    return;
                }
                ballResult[i][j] = randomObj.randomScore();
                if(ballResult[i][j] == 'W'){
                    currentWicket++;
                }
                else{
                    currentScore += Character.getNumericValue(ballResult[i][j]);
                }
            }
        }
        showScore();

    }

    public void secondInning(){
        for (int i = 0; i <numberOfOver ; i++) {
            for (int j = 0; j <6 ; j++) {
                if(currentWicket == 10){
                    finalScore = currentScore;
                    finalWicket = currentWicket;
                    showScore();
                    showResult();
                    return;
                }
                ballResult[i][j] = randomObj.randomScore();
                if(ballResult[i][j] == 'W'){
                    currentWicket++;
                }
                else{
                    currentScore += Character.getNumericValue(ballResult[i][j]);
                }
                if(currentScore>targetScore){
                    showScore();
                    showResult();
                    return;
                }
            }
        }
        showScore();
        showResult();
    }

    public void showScore(){
        finalScore = currentScore;
        finalWicket = currentWicket;
        System.out.println(finalScore + " / "+finalWicket);
    }
    public void showResult(){
        if(currentScore>targetScore)
            System.out.println("Second Team Win!!");
        else if(currentScore<targetScore)
            System.out.println("First Team Win!!");
        else
            System.out.println("Match Tie");
    }
}
