package com.bhartsha.game;

import lombok.Data;

@Data
public class Match {
    private int numberOfOver;
    private String firstTeamName , secondTeamName;
    private String[] firstTeamPlayersName , secondTeamPlayersName;
    private Team firstTeam , secondTeam;
    private PlayGame Inning1 , Inning2;
    public void addTeamDetail(String firstTeamName , String firstTeamPlayersNameString , String secondTeamName , String secondTeamPlayersNameString , int numberOfOver){
        //first team detail
        this.firstTeamName = firstTeamName;
        this.firstTeamPlayersName = firstTeamPlayersNameString.split(",");
        firstTeam = new Team(firstTeamName , firstTeamPlayersName);

        //second team detail
        this.secondTeamName = secondTeamName;
        this.secondTeamPlayersName = secondTeamPlayersNameString.split(",");
        secondTeam = new Team(secondTeamName , secondTeamPlayersName);

        this.numberOfOver = numberOfOver;
    }
    public void start(){
        Inning1 = new PlayGame(firstTeam , numberOfOver);
        Inning1.firstInning();

        Inning2 = new PlayGame(secondTeam , numberOfOver,Inning1.getFinalScore());
        Inning2.secondInning();
    }
}
