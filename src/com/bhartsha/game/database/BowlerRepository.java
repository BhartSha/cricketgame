package com.bhartsha.game.database;

import com.bhartsha.game.Bowler;
import com.bhartsha.game.Player;
import com.bhartsha.game.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BowlerRepository {
    private final Connection connection;
    public BowlerRepository(Connection connection){
        this.connection = connection;
    }

    public void insertIntoTable(int playerId , double strikeRate){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "insert into bowler values ("+playerId+", 0 , 0 , 0 , 0 , 0 , 0 , 0 , "+strikeRate+",0)";
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
                query = "select * from bowler where bowler_id = "+player.getId();
                rs = stmt.executeQuery(query);
                while(rs.next()){
                    rs.updateInt(2,rs.getInt(2)+1);
                    Bowler bowler = player.getAsBowler();
                    if(bowler.isBowlingStatus()){
                        rs.updateInt(3,rs.getInt(3)+1);
                        rs.updateInt(4,rs.getInt(4)+bowler.getWickets());
                        rs.updateInt(5,rs.getInt(5)+bowler.getRuns());
                        rs.updateInt(6,rs.getInt(6)+bowler.getBalls());
                        if(bowler.getWickets()>=5){
                            rs.updateInt(7,rs.getInt(7)+1);
                        }
                        rs.updateInt(8,rs.getInt(8)+bowler.getMaidens());
                        float runs = (float) rs.getInt(5)+bowler.getRuns();
                        int wickets = rs.getInt(4)+ bowler.getWickets();
                        if(wickets>0){
                            float bowlingAvg= runs/wickets;
                            rs.updateFloat(10,bowlingAvg);
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
            insertIntoTable(player.getId() , player.getAsBowler().getStrikeRate());
        }
    }
    public double getStrikeRate(int id){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "select strike_rate from bowler where bowler_id = "+id;
            ResultSet res = stmt.executeQuery(query);
            res.next();
            return res.getFloat(1);

        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return 0;
    }
}
