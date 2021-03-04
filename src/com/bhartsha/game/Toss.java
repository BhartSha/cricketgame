package com.bhartsha.game;

import lombok.Data;
import java.util.Scanner;
@Data
public class Toss {
    private boolean firstTeamBattingFirst;
    private final String captainNameTeamA;
    private final String captainNameTeamB;
    private final String teamNameA;
    private final String teamNameB;
    public Toss(String teamNameA , String captainNameTeamA , String teamNameB ,String captainNameTeamB ){
        this.teamNameA = teamNameA;
        this.captainNameTeamA = captainNameTeamA;
        this.teamNameB = teamNameB;
        this.captainNameTeamB = captainNameTeamB;
        this.firstTeamBattingFirst = false;
    }
    public void flipCoin(){
        System.out.println("Welcome "+captainNameTeamA+" from Team "+teamNameA+" and "+captainNameTeamB+" from team "+teamNameB);
        System.out.println(captainNameTeamB+" flip the coin and coin in the air !!!");
        System.out.println(captainNameTeamA+" choose your option Head or Tail");
        Scanner sc = new Scanner(System.in);
        String coinFaceChoose = sc.next();
        coinFaceChoose = coinFaceChoose.toLowerCase();
        if(coinFaceChoose.equals(Utils.getRandomCoinFace())){
            System.out.println(teamNameA + " won the toss!");
            System.out.println(captainNameTeamA + " what you choose batting or bowling?");
            String option = sc.next();
            option = option.toLowerCase();
            if(option.equals("batting")){
                firstTeamBattingFirst = true;
            }
        }
        else{
            System.out.println(teamNameB + " won the toss!");
            System.out.println(captainNameTeamB + " what you choose batting or bowling?");
            String option = sc.next();
            option = option.toLowerCase();
            if(option.equals("bowling")){
                firstTeamBattingFirst = true;
            }
        }
    }
}
