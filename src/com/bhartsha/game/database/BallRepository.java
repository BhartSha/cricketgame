package com.bhartsha.game.database;

import com.bhartsha.game.Ball;
import com.bhartsha.game.MyEnumContainer;
import com.bhartsha.game.Over;
import com.bhartsha.game.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BallRepository {
    private final Connection connection;
    BallRepository(Connection connection){
        this.connection = connection;
    }

    public void insertIntoTable(int matchId , int inningNumber , ArrayList<Over> overs){
        Statement stmt;
        String query;
        int ballNumber;
        try{
            stmt = connection.createStatement();
            for(Over over : overs){
                ballNumber = 1;
                for(Ball ball : over.getOverDetails()) {
                    query = "insert into ball values (" + matchId + " , " + inningNumber + " , " + over.getOverNumber() + " , " + ballNumber +
                            " , " + ball.getPlayedBy().getId() + " , " + ball.getBowledBy().getId() + " , '" + ball.getScore().getValue() +
                            "' , '" + ball.getTypeOfBall().getValue() + "')";
                    stmt.executeUpdate(query);
                    ballNumber++;
                }
            }
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
    }

    public ArrayList<Ball> getParticularMatchInningOverDetails(int matchId , int inningNumber , int overNumber , Team A , Team B){
        Statement stmt;
        String query;
        ArrayList<Ball> balls = new ArrayList<>();
        try{
            stmt = connection.createStatement();
            query = "select * from ball where match_id = "+matchId+" and innining_number = "+inningNumber+" and over_number = "+overNumber;
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                Ball ball = new Ball(A.getPlayerById(res.getInt(5)),B.getPlayerById(res.getInt(6)),
                        MyEnumContainer.PossibleScore.ZERO.fromString(res.getString(7)) ,
                        MyEnumContainer.TypeOfBall.BOUNCE.fromString(res.getString(8)));
                balls.add(ball);
            }
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
        return balls;
    }
}
