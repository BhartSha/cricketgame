package com.bhartsha.game.database;

import com.bhartsha.game.Player;
import com.bhartsha.game.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MatchTeamPlayerRepository {
    private final Connection connection;
    MatchTeamPlayerRepository(Connection connection){
        this.connection = connection;
    }

    public void insertIntoTable(int matchId , Team team){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            for (Player player : team.getPlayers()) {
                query = "insert into match_team_player values (" + matchId + " , " + team.getTeamId() + " , " + player.getId() + ")";
                stmt.executeUpdate(query);
            }
        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
    }

    public ArrayList<Integer> getParticularMatchPlayersList(int matchId , int teamId){
        Statement stmt;
        String query;
        ArrayList<Integer> players= new ArrayList<>();
        try{
            stmt = connection.createStatement();
            query = "select player_id from match_team_player where match_id = "+matchId+" and team_id = "+teamId;
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                players.add(res.getInt(1));
            }
        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return players;
    }
}
