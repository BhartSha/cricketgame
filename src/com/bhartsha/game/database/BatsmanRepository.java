package com.bhartsha.game.database;

import com.bhartsha.game.Batsman;
import com.bhartsha.game.Player;
import com.bhartsha.game.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BatsmanRepository {
    private final Connection connection;

    public BatsmanRepository(Connection connection){
        this.connection = connection;
    }

    public void insertIntoTable(int playerId , double strikeRate){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "insert into batsman values ("+playerId+", 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , "+strikeRate+",0)";
            stmt.executeUpdate(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateTable(Team team){
        Statement stmt;
        String query;
        ResultSet rs;
        try{
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_UPDATABLE);
            for(Player player:team.getPlayers()){
                query = "select * from batsman where batsman_id = "+player.getId();
                rs = stmt.executeQuery(query);
                while(rs.next()){
                    int timesOut = rs.getInt(11);
                    rs.updateInt(2,rs.getInt(2)+1);
                    Batsman batsman = player.getAsBatsman();
                    if(batsman.isBattingStatus()){
                        rs.updateInt(3,rs.getInt(3)+1);
                        rs.updateInt(4,rs.getInt(4)+batsman.getRuns());
                        rs.updateInt(5,rs.getInt(5)+batsman.getBalls());
                        rs.updateInt(6,rs.getInt(6)+batsman.getFours());
                        rs.updateInt(7,rs.getInt(7)+batsman.getSixes());
                        if(batsman.getRuns()>=100){
                            rs.updateInt(9,rs.getInt(9)+1);
                        }
                        else if(batsman.getRuns()>=50){
                            rs.updateInt(8,rs.getInt(8)+1);
                        }
                        if(batsman.getRuns()>rs.getInt(10)){
                            rs.updateInt(10,batsman.getRuns());
                        }
                        if(batsman.isOutStatus()){
                            rs.updateInt(11,timesOut+1);
                            timesOut++;
                        }
                        if(timesOut>0){
                            float runs = rs.getInt(4)+batsman.getRuns();
                            float battingAvg= runs/timesOut;
                            rs.updateFloat(13,battingAvg);
                        }
                    }
                    rs.updateRow();
                }
            }
        }
        catch (SQLException sqe){
            sqe.printStackTrace();
        }
    }
    public void insertCompleteTeamIntoTable(Team team){
        for (Player player : team.getPlayers()) {
            insertIntoTable(player.getId() , player.getAsBatsman().getStrikeRate());
        }
    }

    public double getStrikeRate(int id){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "select strike_rate from batsman where batsman_id = "+id;
            ResultSet res = stmt.executeQuery(query);
            res.next();
            return res.getFloat(1);

        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return 0;
    }
}
