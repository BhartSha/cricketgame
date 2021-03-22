package com.bhartsha.game.database;

import com.bhartsha.game.Batsman;
import com.bhartsha.game.Player;
import com.bhartsha.game.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerRepository {
    private final Connection connection;
    PlayerRepository(Connection connection){
        this.connection = connection;
    }

    public void insertIntoTable(Team team){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            for (Player player : team.getPlayers()) {
                query = "insert into player values ("+player.getId()+", '"+ player.getFirstName()+"', '"+ player.getLastName()+"', '"+player.getCategory()+"')";
                stmt.executeUpdate(query);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addPlayer(int playerId , String firstName , String lastName , String category , float battingStrikeRate , float bowlingStrikeRate){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "insert into player values ("+playerId+", '"+ firstName+"', '"+ lastName+"', '"+category+"')";
            stmt.executeUpdate(query);
        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        BatsmanRepository batsmanRepository = new BatsmanRepository(connection);
        batsmanRepository.insertIntoTable(playerId , battingStrikeRate);
        BowlerRepository bowlerRepository = new BowlerRepository(connection);
        bowlerRepository.insertIntoTable(playerId , bowlingStrikeRate);
    }

    public Player getPlayer(int id){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "select * from player where player_id = "+id;
            ResultSet res = stmt.executeQuery(query);
            res.next();
            return new Player(res.getInt(1) , res.getString(2), res.getString(3) , res.getString(4));

        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return null;
    }
}
