package com.bhartsha.game.database;

import com.bhartsha.game.Player;
import com.bhartsha.game.Team;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TeamPlayerRepository {
    private final Connection connection;
    TeamPlayerRepository(Connection connection){
        this.connection = connection;
    }
    public void insertIntoTable(Team team){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            for (Player player : team.getPlayers()) {
                query = "insert into team_player values ("+team.getTeamId()+" , "+player.getId()+")";
                stmt.executeUpdate(query);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
