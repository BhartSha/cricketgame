package com.bhartsha.game;

import lombok.Data;

import java.io.File;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;

@Data
public class Match {
    private int numberOfOver;
    private Team firstTeam , secondTeam;
    private FileToTeamObject teamA , teamB;
    private PlayGame firstInning , secondInning;
    private Toss toss;
    private Connection connection;
    private Database db;
    private String location;
    public Match(File teamA, File teamB, int numberOfOver, Connection connection , String location){
        this.teamA = new FileToTeamObject(teamA);
        this.teamB = new FileToTeamObject(teamB);
        this.numberOfOver = numberOfOver;
        this.connection = connection;
        this.location = location;
        db = new Database(this.connection);
        convertFileDataToTeamObject();
    }
    public Match(int firstTeamId , int secondTeamId , int numberOfOver , Connection connection , String location){
        this.numberOfOver = numberOfOver;
        this.connection = connection;
        this.location = location;
        db = new Database(this.connection);
        this.firstTeam = db.getTeamObj(firstTeamId);
        this.secondTeam = db.getTeamObj(secondTeamId);
    }
    public void convertFileDataToTeamObject(){
        firstTeam = teamA.returnTeamObject();
        secondTeam = teamB.returnTeamObject();
        db.addTeamDetails(firstTeam);
        db.addTeamDetails(secondTeam);
    }
    public void start(){
        toss = new Toss(firstTeam , secondTeam);
        toss.flipCoin();
        int toss_id = toss.addTossDetailInDatabase(connection);

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
        db.updateTablesAfterInning(firstInning);
        db.updateTablesAfterInning(secondInning);
        db.updateMatchResultTable(firstInning , secondInning , toss_id , location);
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
