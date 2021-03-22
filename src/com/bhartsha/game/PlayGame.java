package com.bhartsha.game;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Data
public class PlayGame {
    private int numberOfOver , targetScore , currentScore , currentWicket ,maxOverPerBowler , extras;
    private Team battingTeam , bowlingTeam;
    private NextBatsman nextbatsman;
    private NextBowler nextBowler;
   // private ArrayList<Player> players;
    private ArrayList<Over> overs;
    private Player striker , nonStriker , bowler;
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
    private void changeTurn(){
        Player temp;
        temp = striker;
        striker = nonStriker;
        nonStriker = temp;
    }
    private void updateRun(MyEnumContainer.PossibleScore score , Over currentOver , Ball ball){
        ball.setScore(score);
        int run =  Character.getNumericValue(score.getValue());
        currentOver.addRuns(run);
        bowler.getAsBowler().addRuns(run);
        currentScore += run;
        striker.getAsBatsman().addRuns(run);
        if(run == 4){
            striker.getAsBatsman().incrementFours();
        }
        if(run == 6){
            striker.getAsBatsman().incrementSixs();
        }
        if(run%2!=0){
            changeTurn();
        }
    }

    public void startInning(){
        boolean flag;
        maxOverPerBowler = (int) Math.ceil((float)numberOfOver/6);
        int id;
        MyEnumContainer.PossibleScore scoreOnCurrentBall;
        MyEnumContainer.WicketType wicketType;
        MyEnumContainer.TypeOfBall typeOfBall;
        striker = nextbatsman.getAccordingToStrikeRate();
        striker.getAsBatsman().setBattingStatus(true);

        nonStriker = nextbatsman.getAccordingToStrikeRate();
        nonStriker.getAsBatsman().setBattingStatus(true);

        for (int i = 0; i <numberOfOver ; i++) {
            Over currentOver = new Over();
            currentOver.setOverNumber(i+1);
            id = i==0?-1:overs.get(i-1).getBowler().getId();
            bowler = nextBowler.getAccordingToStrikeRate(id,maxOverPerBowler);
            bowler.getAsBowler().setBowlingStatus(true);
            currentOver.setBowler(bowler);
            flag = true;
            boolean noBall = false , freeHitBall = false;
            for (int j = 0; j<6 ; j++) {
                typeOfBall = Utils.getRandomTypeOfBall();
                if(typeOfBall == MyEnumContainer.TypeOfBall.WIDE){
                    currentOver.incrementExtra();
                    currentOver.incrementWideBall();
                    bowler.getAsBowler().incrementBalls();
                    bowler.getAsBowler().addRuns(1);
                    currentOver.addBall(new Ball(striker,bowler,MyEnumContainer.PossibleScore.ZERO,typeOfBall));
                    j--;
                    continue;
                }

                if(typeOfBall == MyEnumContainer.TypeOfBall.BOUNCE){
                    currentOver.incrementBounceBall();
                    if(currentOver.getTotalBounceBall()==2){
                        typeOfBall = MyEnumContainer.TypeOfBall.NOBALL;
                    }
                }
                if(typeOfBall == MyEnumContainer.TypeOfBall.NOBALL){
                    currentOver.incrementNoBall();
                    noBall = true;
                    currentOver.incrementExtra();
                    bowler.getAsBowler().addRuns(1);
                    j--;
                }
                bowler.getAsBowler().incrementBalls();
                striker.getAsBatsman().incrementBalls();
                scoreOnCurrentBall = Utils.getRandomScore(striker.getCategory());
                Ball ball = new Ball(striker,bowler,scoreOnCurrentBall,typeOfBall);
                currentOver.addBall(ball);
                if(scoreOnCurrentBall.getValue() == 'W'){
                    wicketType = Utils.getRandomWicketType();
                    if((noBall||freeHitBall)&&wicketType!=MyEnumContainer.WicketType.RUNOUT){
                        if(wicketType == MyEnumContainer.WicketType.Caught){
                            updateRun(Utils.getRandomRunningScore() , currentOver , ball);
                        }
                    }
                    else{
                        if(wicketType == MyEnumContainer.WicketType.RUNOUT){
                            updateRun(Utils.getRandomRunningScore() , currentOver , ball);
                        }
                        Player assistPlayer = null;
                        if(wicketType == MyEnumContainer.WicketType.Caught||wicketType == MyEnumContainer.WicketType.RUNOUT){
                            assistPlayer = bowlingTeam.getPlayers().get(Utils.getRandomPlayerIndex());
                        }
                        if(wicketType == MyEnumContainer.WicketType.STUMPED){
                            assistPlayer = bowlingTeam.getWicketKeeper();
                        }
                        Wicket wicket = new Wicket(bowler , striker ,assistPlayer, wicketType,i+1 , j+1);
                        ball.setWicketTaken(true);
                        ball.setWicket(wicket);
                        wicketDetail.add(wicket);
                        currentOver.setWickets(currentOver.getWickets()+1);
                        bowler.getAsBowler().incrementWickets();
                        striker.getAsBatsman().setOutStatus(true);
                        currentWicket++;
                        if(currentWicket!=10){
                            striker = nextbatsman.getAccordingToStrikeRate();
                            striker.getAsBatsman().setBattingStatus(true);
                        }
                    }
                }
                else{
                    updateRun(scoreOnCurrentBall , currentOver , ball);
                }
                if((chasing && currentScore>targetScore)|| currentWicket==10){
                    float currentOverBalls = j+1;
                    if(j!=5){
                        currentOverBalls /= 10;
                        bowler.getAsBowler().addOvers(currentOverBalls);
                    }
                    else{
                        bowler.getAsBowler().addOvers(1);
                    }
                    flag = false;
                    break;
                }

                if(noBall){
                    noBall = false;
                    freeHitBall = true;
                }
                if(freeHitBall&&typeOfBall!=MyEnumContainer.TypeOfBall.NOBALL){
                    freeHitBall = false;
                }
            }
            if(flag)
                bowler.getAsBowler().addOvers(1);
            currentOver.addRuns(currentOver.getExtras());
            extras += currentOver.getExtras();
            currentScore+=currentOver.getExtras();
            overs.add(currentOver);
            if(!flag){
                return;
            }
            if(currentOver.getTotalRuns()==0)
                bowler.getAsBowler().incrementMaidens();
            changeTurn();
        }
    }
}
