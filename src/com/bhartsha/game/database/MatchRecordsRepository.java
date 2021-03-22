package com.bhartsha.game.database;

import com.bhartsha.game.PlayGame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MatchRecordsRepository {
    private final Connection connection;
    MatchRecordsRepository(Connection connection){
        this.connection = connection;
    }
    public void insertIntoTable(PlayGame firstInning , PlayGame secondInning , int numberOfOvers , int toss_id , String location){
        Statement stmt;
        String query;
        int winningTeamId = firstInning.getBattingTeam().getTeamId();
        if(firstInning.getCurrentScore()< secondInning.getCurrentScore()){
            winningTeamId = secondInning.getBattingTeam().getTeamId();
        }
        try {
            stmt = connection.createStatement();
            query = "insert into match_records (first_team_id , second_team_id , number_of_overs , location , date , toss_id , first_inning_score , first_inning_wickets"+
                    " , second_inning_score , second_inning_wickets , winning_team_id) values ("+firstInning.getBattingTeam().getTeamId()+" , "+
                    secondInning.getBattingTeam().getTeamId()+" , "+numberOfOvers+" , '"+location+"' , CURRENT_TIMESTAMP , "+toss_id+" , "+
                    firstInning.getCurrentScore()+" , "+firstInning.getCurrentWicket()+" , "+secondInning.getCurrentScore()+" , "+
                    secondInning.getCurrentWicket()+" , "+winningTeamId+")";
            stmt.executeUpdate(query);
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
    }
    public int getCurrentMatchId(){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "select max(match_id) from match_records";
            ResultSet res = stmt.executeQuery(query);
            res.next();
            return res.getInt(1);

        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return 0;
    }

    public int getTossId(int matchId){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "select toss_id from match_records where match_id = "+matchId;
            ResultSet res = stmt.executeQuery(query);
            res.next();
            return res.getInt(1);
        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return 0;
    }

    public ResultSet getMatchRecord(int matchId){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "select * from match_records where match_id = "+matchId;
            ResultSet res = stmt.executeQuery(query);
            res.next();
            return res;
        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return null;
    }
}
