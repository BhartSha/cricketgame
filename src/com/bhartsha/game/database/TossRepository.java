package com.bhartsha.game.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TossRepository {
    private final Connection connection;
    TossRepository(Connection connection){
        this.connection = connection;
    }
    public int getCurrentTossId(){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "select max(toss_id) from toss";
            ResultSet res = stmt.executeQuery(query);
            res.next();
            return res.getInt(1);

        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return 0;
    }

    public void printParticularTossResult(int tossId){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "select * from toss where toss_id = "+tossId;
            ResultSet res = stmt.executeQuery(query);
            res.next();
            TeamRepository teamRepository = new TeamRepository(connection);
            System.out.println("Toss Result");
            System.out.println("First Team Name : "+teamRepository.getTeamName(res.getInt(2)));
            System.out.println("Second Team Name : "+teamRepository.getTeamName(res.getInt(3)));
            System.out.println("Winning Team Name : "+teamRepository.getTeamName(res.getInt(4)));
            System.out.println("Chosen Option : "+res.getString(5));
        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
    }
}
