package com.bhartsha.game;

import lombok.Data;

import java.io.File;

@Data
public class Match {
    private int numberOfOver;
    private Team firstTeam , secondTeam;
    private FileToTeamObject teamA , teamB;
    private PlayGame Inning1 , Inning2;
    private Toss toss;
    public Match(File teamA , File teamB , int numberOfOver){
        this.teamA = new FileToTeamObject(teamA);
        this.teamB = new FileToTeamObject(teamB);
        this.numberOfOver = numberOfOver;

        convertFileDataToTeamObject();
        start();
    }
    public void convertFileDataToTeamObject(){
        firstTeam = teamA.returnTeamObject();
        secondTeam = teamB.returnTeamObject();

        firstTeam.showTeamDetail();
        secondTeam.showTeamDetail();
    }
    public void start(){
        toss = new Toss(firstTeam.getTeamName() , firstTeam.getCaptain().getPlayerName() , secondTeam.getTeamName() , secondTeam.getCaptain().getPlayerName());
        toss.flipCoin();

        if(toss.isFirstTeamBattingFirst()){
            Inning1 = new PlayGame(firstTeam , secondTeam.getTeamName() ,numberOfOver);
            Inning1.startInning();

            Inning2 = new PlayGame(secondTeam , firstTeam.getTeamName() ,numberOfOver,Inning1.getFinalScore());
        }
        else{
            Inning1 = new PlayGame(secondTeam , firstTeam.getTeamName() ,numberOfOver);
            Inning1.startInning();

            Inning2 = new PlayGame(firstTeam , secondTeam.getTeamName() ,numberOfOver,Inning1.getFinalScore());
        }
        Inning2.startInning();

    }
}
