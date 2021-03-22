package com.bhartsha.game.database;

import com.bhartsha.game.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WicketKeeperRepository {
    private final Connection connection;
    WicketKeeperRepository(Connection connection){
        this.connection = connection;
    }
    public void insertIntoTable(Team team){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "insert into wicket_keeper values ("+team.getTeamId()+" , "+team.getWicketKeeper().getId()+")";
            stmt.executeUpdate(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public int getWicketKeeperId(int teamId){
        Statement stmt;
        String query;
        ResultSet res;
        try{
            stmt = connection.createStatement();
            query = "select player_id from wicket_keeper where team_id = "+teamId;
            res = stmt.executeQuery(query);
            res.next();
            return res.getInt(1);
        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return 0;
    }
}
