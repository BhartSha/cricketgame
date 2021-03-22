package com.bhartsha.game.database;

import com.bhartsha.game.Team;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeamRepository {
    private final Connection connection;
    TeamRepository(Connection connection){
        this.connection = connection;
    }
    public void insertIntoTable(Team team){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "insert into team values ("+team.getTeamId()+", "+"\"" + team.getTeamName()+ "\""+", "+team.getTotalPlayers()+", "+team.getBatsmen()+", "+team.getBowlers()+", "+team.getAllRounders()+")";
            stmt.executeUpdate(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Team getTeam(int id){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "select * from team where team_id = "+id;
            ResultSet res = stmt.executeQuery(query);
            res.next();
            return new Team(res.getString(2) , id);
        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return null;
    }
    public String getTeamName(int id){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "select team_name from team where team_id = "+id;
            ResultSet res = stmt.executeQuery(query);
            res.next();
            return res.getString(1);
        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return "";
    }
}
