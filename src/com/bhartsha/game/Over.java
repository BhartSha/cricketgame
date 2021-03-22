package com.bhartsha.game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Over {
    private int totalRuns , wickets , extras , overNumber , currentRunRate , RequiredRunRate , totalBounceBall , totalNoBall , totalWideBall;
    private Player bowler;
    private ArrayList<Ball> overDetails;
    public Over(){
        overDetails = new ArrayList<>();
    }
    public void addBall(Ball ball){
        overDetails.add(ball);
    }
    public void showOverDetails(){
        System.out.println("Over Number :   "+overNumber );
        System.out.println("Runs:  "+totalRuns+" , Total Wickets: "+wickets+" , Extras:"+extras);
        System.out.println("No Balls: "+totalNoBall+" , Wide Balls: "+totalWideBall+" , Bounce Balls: "+totalBounceBall);
        System.out.println("Bowler Name : "+bowler.getPlayerName());
        for (Ball overDetail : overDetails) {
            overDetail.printBall();
        }
        System.out.println();
        System.out.println();
    }
    public void addRuns(int run){
        totalRuns+=run;
    }
    public void incrementWicket(){
        wickets++;
    }
    public void incrementExtra(){
        extras++;
    }
    public void incrementNoBall(){
        totalNoBall++;
    }
    public void incrementWideBall(){
        totalWideBall++;
    }
    public void incrementBounceBall(){
        totalBounceBall++;
    }
}
