package com.bhartsha.game.database;

import com.bhartsha.game.MyEnumContainer;
import com.bhartsha.game.Team;
import com.bhartsha.game.Wicket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WicketRepository {
    private final Connection connection;
    WicketRepository(Connection connection){
        this.connection = connection;
    }
    public void insertIntoTable(int matchId , int inningNumber , ArrayList<Wicket> wickets){
        Statement stmt;
        String query;
        int wicketNumber = 1;
        try{
            stmt = connection.createStatement();
            for(Wicket wicket : wickets){
                query = "insert into wicket (match_id , inning_number , wicket_number , batsman_id , bowler_id , wicket_type) values (" + matchId + " , " + inningNumber +
                            " , " + wicketNumber + " , " + wicket.getBatsman().getId() + " , " + wicket.getBowler().getId() + " , '" + wicket.getWicketType().getValue() +"')";
                stmt.executeUpdate(query);
                if(wicket.getAssistPlayer() != null){
                    addAssistPlayer(matchId , inningNumber , wicketNumber , wicket.getAssistPlayer().getId());
                }
                wicketNumber++;
            }
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }

    }
    public void addAssistPlayer(int matchId , int inningNumber , int wicketNumber , int id){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "update wicket set assist_player_id = " + id +" where match_id = " +matchId+ " and inning_number = " +inningNumber+
                     " and wicket_number = " +wicketNumber;
            stmt.executeUpdate(query);
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
    }

    public ArrayList<Wicket> getParticularMatchInningWickets(int matchId , int inningId , Team A , Team B){
        Statement stmt;
        String query;
        ArrayList<Wicket> wickets = new ArrayList<>();
        try{
            stmt = connection.createStatement();
            /*
            select wicket.batsman_id , wicket.bowler_id , wicket.assist_player_id , wicket.wicket_type , ball.over_number ,ball.ball_number from wicket
            join ball on (wicket.match_id = ball.match_id) and (wicket.inning_number = ball.innining_number) and(wicket.batsman_id = ball.batsman_id)
            where ball.match_id = 1 and ball.innining_number = 1 and ball.score = "W";

             */
            query = "select wicket.batsman_id , wicket.bowler_id , wicket.assist_player_id , wicket.wicket_type , ball.over_number , ball.ball_number " +
                    "from wicket join ball on (wicket.match_id = ball.match_id) and (wicket.inning_number = ball.innining_number) and "+
                    "(wicket.batsman_id = ball.batsman_id) where wicket.match_id = "+matchId+" and wicket.inning_number = "+inningId+
                    " and ball.score = '"+"W"+"'";
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {
                Wicket wicket = new Wicket(B.getPlayerById(res.getInt(2)), A.getPlayerById(res.getInt(1)),
                        B.getPlayerById(res.getInt(3)), MyEnumContainer.WicketType.RUNOUT.fromString(res.getString(4)),
                        res.getInt(5), res.getInt(6));
                wickets.add(wicket);
            }
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
        return wickets;
    }
}
