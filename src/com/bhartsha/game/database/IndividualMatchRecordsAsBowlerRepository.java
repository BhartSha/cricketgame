package com.bhartsha.game.database;

import com.bhartsha.game.Player;
import com.bhartsha.game.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IndividualMatchRecordsAsBowlerRepository {
    private final Connection connection;
    IndividualMatchRecordsAsBowlerRepository(Connection connection){
        this.connection = connection;
    }
    public void insertIntoTable(int matchId , Team team){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            for(Player player : team.getPlayers()){
                if(player.getAsBowler().isBowlingStatus()){
                    query = "insert into individual_match_records_as_bowler values(" + matchId + " , " + player.getId() +" , " + player.getAsBowler().getRuns()+
                            " , " + player.getAsBowler().getBalls() + " , " + player.getAsBowler().getWickets() + " , " + player.getAsBowler().getMaidens() +
                            " , "+player.getAsBowler().getOvers()+")";
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
                query = "select * from individual_match_records_as_bowler where match_id = "+matchId+" and player_id = "+player.getId();
                ResultSet res = stmt.executeQuery(query);
                if(res.next()){
                    player.getAsBowler().setBowlingStatus(true);
                    player.getAsBowler().setRuns(res.getInt(3));
                    player.getAsBowler().setBalls(res.getInt(4));
                    player.getAsBowler().setWickets(res.getInt(5));
                    player.getAsBowler().setMaidens(res.getInt(6));
                    player.getAsBowler().setOvers(res.getFloat(7));
                }
            }
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
    }
}
