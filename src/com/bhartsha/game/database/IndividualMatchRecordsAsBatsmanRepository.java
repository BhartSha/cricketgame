package com.bhartsha.game.database;

import com.bhartsha.game.Player;
import com.bhartsha.game.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IndividualMatchRecordsAsBatsmanRepository {
    private final Connection connection;
    IndividualMatchRecordsAsBatsmanRepository(Connection connection){
        this.connection = connection;
    }
    public void insertIntoTable(int matchId , Team team){
        Statement stmt;
        String query , outStatus;
        try{
            stmt = connection.createStatement();
            for(Player player : team.getPlayers()){
                outStatus = "Not Out";
                if(player.getAsBatsman().isBattingStatus()){
                    if(player.getAsBatsman().isOutStatus()){
                        outStatus = "Out";
                    }
                    query = "insert into individual_match_records_as_batsman values(" + matchId + " , " + player.getId() +" , " + player.getAsBatsman().getRuns()+
                            " , " + player.getAsBatsman().getBalls() + " , " + player.getAsBatsman().getFours() + " , " + player.getAsBatsman().getSixes() +
                            " , '" + outStatus + "')";
                    stmt.executeUpdate(query);
                }
            }
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
    }
    public void addRecordsIntoTeam(int matchId , Team team){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            for(Player player : team.getPlayers()){
                query = "select * from individual_match_records_as_batsman where match_id = "+matchId+" and player_id = "+player.getId();
                ResultSet res = stmt.executeQuery(query);
                if(res.next()){
                    player.getAsBatsman().setBattingStatus(true);
                    player.getAsBatsman().setRuns(res.getInt(3));
                    player.getAsBatsman().setBalls(res.getInt(4));
                    player.getAsBatsman().setFours(res.getInt(5));
                    player.getAsBatsman().setSixes(res.getInt(6));
                    if(res.getString(7).equals("Out")){
                        player.getAsBatsman().setOutStatus(true);
                    }
                }
            }
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
    }
}
