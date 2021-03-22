package com.bhartsha.game;

import com.bhartsha.game.database.*;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;

@Data
public class Match {
    private int numberOfOver;
    private Team firstTeam , secondTeam;
    private FileToTeamObject teamA , teamB;
    private PlayGame firstInning , secondInning;
    private Toss toss;
    private Database db;
    private DBConnection dbConnection;
    private String location;
    public Match(File teamA, File teamB, int numberOfOver , String location){
        this.teamA = new FileToTeamObject(teamA);
        this.teamB = new FileToTeamObject(teamB);
        this.numberOfOver = numberOfOver;
        this.location = location;
        dbConnection = new DBConnection();
        db = new Database(dbConnection.getConnection());
        convertFileDataToTeamObject();
    }
    public Match(int firstTeamId , int secondTeamId , int numberOfOver , String location){
        this.numberOfOver = numberOfOver;
        this.location = location;
        dbConnection = new DBConnection();
        db = new Database(dbConnection.getConnection());
        this.firstTeam = db.getTeamObj(firstTeamId);
        this.secondTeam = db.getTeamObj(secondTeamId);
    }
    private Match(int firstTeamId , int secondTeamId , int numberOfOver , String location , ArrayList<Integer> firstTeamPlayers , ArrayList<Integer> secondTeamPlayers){
        this.numberOfOver = numberOfOver;
        this.location = location;
        dbConnection = new DBConnection();
        db = new Database(dbConnection.getConnection());
        this.firstTeam = db.getTeamObj2(firstTeamId , firstTeamPlayers);
        this.secondTeam = db.getTeamObj2(secondTeamId , secondTeamPlayers);
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
       toss.addTossDetailInDatabase(dbConnection.getConnection());

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
        db.updateTableAfterMatch(firstInning , secondInning , numberOfOver,location);
        dbConnection.closeConnection();
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
