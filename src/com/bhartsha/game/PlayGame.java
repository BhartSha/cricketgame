package com.bhartsha.game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PlayGame {
    private int numberOfOver , targetScore , currentScore , currentWicket , finalScore , finalWicket,maxOverPerBowler;
    private Team battingTeam , bowlingTeam;
    private NextBatsman nextbatsman;
    private NextBowler nextBowler;
    private ArrayList<Player> players;
    private Over[] overs;
    Boolean chasing = false;
    public PlayGame(Team battingTeam , Team bowlingTeam ,int numberOfOver , int targetScore){
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.numberOfOver = numberOfOver;
        this.targetScore = targetScore;
        this.nextbatsman = new NextBatsman(battingTeam.getPlayers());
        this.nextBowler = new NextBowler(bowlingTeam.getPlayers());
        overs = new Over[numberOfOver];
        chasing = true;
    }
    public PlayGame(Team battingTeam , Team bowlingTeam ,int numberOfOver){
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.numberOfOver = numberOfOver;
        this.nextbatsman = new NextBatsman(battingTeam.getPlayers());
        this.nextBowler = new NextBowler(bowlingTeam.getPlayers());
        overs = new Over[numberOfOver];
    }

    public void startInning(){
        maxOverPerBowler = (int) Math.ceil((float)numberOfOver/6);
        int run,id;
        Player bowler=null;
        char scoreOnCurrentBall;
        Player striker = nextbatsman.getAccordingToStrikeRate(),temp;
        striker.getAsBatsman().setBattingStatus(true);

        Player nonStriker = nextbatsman.getAccordingToStrikeRate();
        nonStriker.getAsBatsman().setBattingStatus(true);

        for (int i = 0; i <numberOfOver ; i++) {
            Over currentOver = new Over();
            id = i==0?-1:overs[i-1].getBowler().getId();
            bowler = nextBowler.getAccordingToStrikeRate(id,maxOverPerBowler);
            bowler.getAsBowler().setBowlingStatus(true);
            bowler.getAsBowler().setOvers(bowler.getAsBowler().getOvers()+1);
            currentOver.setBowler(bowler);
            for (int j = 0; j<6 ; j++) {
                if(currentWicket == 10){
                    showScore();
                    if(chasing)
                        showResult();
                    return;
                }
                striker.getAsBatsman().setBalls(striker.getAsBatsman().getBalls()+1);
                scoreOnCurrentBall = Utils.getRandomScore(striker.getCategory());
                currentOver.addBall(Character.toString(scoreOnCurrentBall));
                if(scoreOnCurrentBall == 'W'){
                    currentOver.setWickets(currentOver.getWickets()+1);
                    bowler.getAsBowler().setWickets(bowler.getAsBowler().getWickets()+1);
                    striker.getAsBatsman().setOutStatus(true);
                    currentWicket++;
                    if(currentWicket!=10){
                        striker = nextbatsman.getAccordingToStrikeRate();
                        striker.getAsBatsman().setBattingStatus(true);
                    }
                }
                else{
                    run =  Character.getNumericValue(scoreOnCurrentBall);
                    currentOver.setTotalRuns(currentOver.getTotalRuns()+run);
                    bowler.getAsBowler().setRuns(bowler.getAsBowler().getRuns()+run);
                    currentScore += run;
                    striker.getAsBatsman().setRuns(striker.getAsBatsman().getRuns()+run);
                    if(run == 4){
                        striker.getAsBatsman().setFours(striker.getAsBatsman().getFours()+1);
                    }
                    if(run == 6){
                        striker.getAsBatsman().setSixes(striker.getAsBatsman().getSixes()+1);
                    }
                    if(run%2!=0){
                        temp = striker;
                        striker = nonStriker;
                        nonStriker = temp;
                    }
                }
                if(chasing && currentScore>targetScore){
                    showScore();
                    showResult();
                    return;
                }
            }
            overs[i] = currentOver;
            currentOver.showOverDetails();
            if(currentOver.getTotalRuns()==0)
                bowler.getAsBowler().setMaidens(bowler.getAsBowler().getMaidens()+1);
            temp = striker;
            striker = nonStriker;
            nonStriker = temp;
        }
        showScore();
        if(chasing)
            showResult();
    }

    public void showScore(){
        System.out.println("Player Name  R   B   4s   6s");
        for (Player player : battingTeam.getPlayers()) {
            if(player.getAsBatsman().isBattingStatus()){
                player.printBattingStats();
            }
        }
        System.out.println("Player Name   Overs   B   W   M");
        for (Player player : bowlingTeam.getPlayers()) {
            if(player.getAsBowler().isBowlingStatus()){
                player.printBowlingStats();
            }
        }
        finalScore = currentScore;
        finalWicket = currentWicket;
        System.out.println("Total:-   "+finalScore + " / "+finalWicket);
    }
    public void showResult(){
        if(currentScore>targetScore)
            System.out.println("Team "+battingTeam.getTeamName()+" Win!!");
        else if(currentScore<targetScore)
            System.out.println("Team "+bowlingTeam.getTeamName()+ " Win!!");
        else
            System.out.println("Match Tie");
    }
}
