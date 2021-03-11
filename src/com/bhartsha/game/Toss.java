package com.bhartsha.game;

import lombok.Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
@Data
public class Toss {
    private boolean firstTeamBattingFirst;
    private final Team firstTeam;
    private final Team secondTeam;
    private Team tossWinningTeam;
    private String chooseOption;
    public Toss(Team firstTeam , Team secondTeam){
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.firstTeamBattingFirst = false;
    }
    public void flipCoin(){
        System.out.println("Welcome "+firstTeam.getCaptain().getPlayerName()+" from Team "+firstTeam.getTeamName()+" and "+secondTeam.getCaptain().getPlayerName()+" from team "+secondTeam.getTeamName());
        System.out.println(secondTeam.getCaptain().getPlayerName()+" flip the coin and coin in the air !!!");
        System.out.println(firstTeam.getCaptain().getPlayerName()+" choose your option Head or Tail");
        Scanner sc = new Scanner(System.in);
        String coinFaceChoose = sc.next();
        coinFaceChoose = coinFaceChoose.toLowerCase();
        if(coinFaceChoose.equals(Utils.getRandomCoinFace())){
            tossWinningTeam = firstTeam;
            System.out.println(firstTeam.getTeamName()+ " won the toss!");
            System.out.println(firstTeam.getCaptain().getPlayerName()+ " what you choose batting or bowling?");
            String option = sc.next();
            option = option.toLowerCase();
            if(option.equals("batting")){
                firstTeamBattingFirst = true;
                chooseOption = "Batting";
            }
            else{
                chooseOption = "Bowling";
            }
        }
        else{
            tossWinningTeam = secondTeam;
            System.out.println(secondTeam.getTeamName() + " won the toss!");
            System.out.println(secondTeam.getCaptain().getPlayerName()+ " what you choose batting or bowling?");
            String option = sc.next();
            option = option.toLowerCase();
            if(option.equals("bowling")){
                firstTeamBattingFirst = true;
                chooseOption = "Bowling";
            }
            else{
                chooseOption = "Batting";
            }
        }
    }
    public int addTossDetailInDatabase(Connection connection){
        Statement stmt;
        String query;
        int toss_id = 0;
        ResultSet res;
        try{
            query = "Insert into toss (first_team_id , second_team_id , winning_team_id , option_choose) Values ("+firstTeam.getTeamId()+" , "+
                    secondTeam.getTeamId()+" , "+tossWinningTeam.getTeamId()+" , " + "\"" + chooseOption +"\""+")";
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            query =  "select count(*) from toss";
            res = stmt.executeQuery(query);
            res.next();
            toss_id = res.getInt(1);
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
        return toss_id;
    }
}
