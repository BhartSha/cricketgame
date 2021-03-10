package com.bhartsha.game;

import lombok.Data;

import java.io.File;
import java.sql.Connection;

@Data
public class Match {
    private int numberOfOver;
    private Team firstTeam , secondTeam;
    private FileToTeamObject teamA , teamB;
    private PlayGame firstInning , secondInning;
    private Toss toss;
    private Connection connection;
    public Match(File teamA, File teamB, int numberOfOver, Connection connection){
        this.teamA = new FileToTeamObject(teamA);
        this.teamB = new FileToTeamObject(teamB);
        this.numberOfOver = numberOfOver;
        this.connection = connection;
        convertFileDataToTeamObject();
    }
    public void convertFileDataToTeamObject(){
        firstTeam = teamA.returnTeamObject();
        secondTeam = teamB.returnTeamObject();
    }
    public void start(){
        toss = new Toss(firstTeam.getTeamName() , firstTeam.getCaptain().getPlayerName() , secondTeam.getTeamName() , secondTeam.getCaptain().getPlayerName());
        toss.flipCoin();

        if(toss.isFirstTeamBattingFirst()){
            firstInning = new PlayGame(firstTeam , secondTeam ,numberOfOver);
            firstInning.startInning();
            secondInning = new PlayGame(secondTeam , firstTeam ,numberOfOver , firstInning.getCurrentScore());
        }
        else{
            firstInning = new PlayGame(secondTeam , firstTeam , numberOfOver);
            firstInning.startInning();
            secondInning = new PlayGame(firstTeam , secondTeam , numberOfOver , firstInning.getCurrentScore());
        }
        secondInning.startInning();
        showResult();
    }
    public void showResult(){
        firstTeam.showTeamDetail();
        secondTeam.showTeamDetail();
        InningResult firstInningResult = new InningResult(firstInning);
        InningResult secondInningResult = new InningResult(secondInning);
        firstInningResult.printInningStats();
        System.out.println();
        System.out.println();
        secondInningResult.printInningStats();
    }
}
