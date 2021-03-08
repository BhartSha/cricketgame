package com.bhartsha.game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Over {
    private int totalRuns , wickets , extras;
    private Player bowler;
    private ArrayList<String> overDetails;
    public Over(){
        overDetails = new ArrayList<>();
    }
    public void addBall(String ball){
        overDetails.add(ball);
    }
    public void showOverDetails(){
        System.out.println("Runs:  "+totalRuns+" , Total Wickets: "+wickets+" , Extras:"+extras);
        System.out.println("Bowler Name : "+bowler.getPlayerName());
        for (int i = 0; i <overDetails.size() ; i++) {
            System.out.print(overDetails.get(i));
            if(i!=overDetails.size()-1){
                System.out.print(" , ");
            }
        }
        System.out.println();
    }
}
