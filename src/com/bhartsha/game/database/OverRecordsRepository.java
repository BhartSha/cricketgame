package com.bhartsha.game.database;

import com.bhartsha.game.Over;
import com.bhartsha.game.Team;
import com.bhartsha.game.Wicket;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OverRecordsRepository {
    private final Connection connection;
    OverRecordsRepository(Connection connection){
        this.connection = connection;
    }
    public void insertIntoTable(int matchId , int inningNumber , ArrayList<Over> overs){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            for(Over over : overs){
                query = "insert into over_records values ("+ matchId + " , " + inningNumber + " , " + over.getOverNumber() + " , " + over.getBowler().getId() +
                        " , " + over.getTotalRuns() + " , " + over.getWickets() + " , " + over.getExtras()+")";
                stmt.executeUpdate(query);
            }
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
    }

    public ArrayList<Over> getParticularMatchInningOvers(int matchId , int inningId , Team A , Team B){
        Statement stmt;
        String query;
        ArrayList<Over> overs = new ArrayList<>();
        try{
            stmt = connection.createStatement();
            query = "select * from over_records where match_id = "+matchId+" and innining_number = "+inningId;
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                Over over = new Over();
                over.setOverNumber(res.getInt(3));
                over.setBowler(B.getPlayerById(res.getInt(4)));
                over.setTotalRuns(res.getInt(5));
                over.setWickets(res.getInt(6));
                over.setExtras(res.getInt(7));
                over.setOverDetails(new BallRepository(connection).getParticularMatchInningOverDetails(matchId,inningId,over.getOverNumber(),A,B));
                overs.add(over);
            }
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
        return overs;
    }
}
