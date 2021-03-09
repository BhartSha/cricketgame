package com.bhartsha.game;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Data
public class PlayGame {
    private int numberOfOver , targetScore , currentScore , currentWicket ,maxOverPerBowler;
    private Team battingTeam , bowlingTeam;
    private NextBatsman nextbatsman;
    private NextBowler nextBowler;
    private ArrayList<Player> players;
    private ArrayList<Over> overs;
    private boolean chasing;
    private ArrayList<Wicket> wicketDetail;
    public PlayGame(Team battingTeam , Team bowlingTeam ,int numberOfOver , int targetScore){
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.numberOfOver = numberOfOver;
        this.targetScore = targetScore;
        this.nextbatsman = new NextBatsman(battingTeam.getPlayers());
        this.nextBowler = new NextBowler(bowlingTeam.getPlayers());
        overs = new ArrayList<>();
        wicketDetail = new ArrayList<>();
        chasing = true;
    }
    public PlayGame(@NotNull Team battingTeam , @NotNull Team bowlingTeam , int numberOfOver){
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.numberOfOver = numberOfOver;
        this.nextbatsman = new NextBatsman(battingTeam.getPlayers());
        this.nextBowler = new NextBowler(bowlingTeam.getPlayers());
        overs = new ArrayList<>();
        wicketDetail = new ArrayList<>();
    }

    public void startInning(){
        boolean flag = true;
        maxOverPerBowler = (int) Math.ceil((float)numberOfOver/6);
        int run,id;
        Player bowler;
        char scoreOnCurrentBall;
        Player striker = nextbatsman.getAccordingToStrikeRate(),temp;
        striker.getAsBatsman().setBattingStatus(true);

        Player nonStriker = nextbatsman.getAccordingToStrikeRate();
        nonStriker.getAsBatsman().setBattingStatus(true);

        for (int i = 0; i <numberOfOver ; i++) {
            Over currentOver = new Over();
            id = i==0?-1:overs.get(i-1).getBowler().getId();
            bowler = nextBowler.getAccordingToStrikeRate(id,maxOverPerBowler);
            bowler.getAsBowler().setBowlingStatus(true);
            bowler.getAsBowler().setOvers(bowler.getAsBowler().getOvers()+1);
            currentOver.setBowler(bowler);
            flag = true;
            for (int j = 0; j<6 ; j++) {
                striker.getAsBatsman().setBalls(striker.getAsBatsman().getBalls()+1);
                scoreOnCurrentBall = Utils.getRandomScore(striker.getCategory());
                currentOver.addBall(Character.toString(scoreOnCurrentBall));
                if(scoreOnCurrentBall == 'W'){
                    wicketDetail.add(new Wicket(bowler , striker , i+1 , j+1));
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
                    flag = false;
                    break;
                }
                if(currentWicket == 10){
                    flag = false;
                    break;
                }
            }
            overs.add(currentOver);
            if(!flag){
                return;
            }
            if(currentOver.getTotalRuns()==0)
                bowler.getAsBowler().setMaidens(bowler.getAsBowler().getMaidens()+1);
            temp = striker;
            striker = nonStriker;
            nonStriker = temp;
        }
    }
}
