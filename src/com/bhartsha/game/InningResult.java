package com.bhartsha.game;

public class InningResult {
    private final PlayGame inning;
    private int totalScore;
    public InningResult(PlayGame inning){
        this.inning = inning;
    }
    public void printOversStats(){
        for (int i = 0; i <inning.getOvers().size() ; i++) {
            inning.getOvers().get(i).showOverDetails();
        }
    }
    public void printBatsmenStats(){
        System.out.println("Player Name  R   B   4s   6s");
        for (Wicket wicket : inning.getWicketDetail()) {
            wicket.getBatsman().printBattingStats();
            System.out.print("  b "+wicket.getBowler().getPlayerName()+" "+wicket.getWicketType().getValue()+" ");
            if(wicket.getAssistPlayer()!= null){
                System.out.print("by "+wicket.getAssistPlayer().getPlayerName());
            }
            System.out.println();
        }
        for (Player player : inning.getBattingTeam().getPlayers()) {
            if(player.getAsBatsman().isBattingStatus()&&!player.getAsBatsman().isOutStatus()){
                player.printBattingStats();
                System.out.println("   Not Out");
            }
        }
        System.out.println();
        System.out.println();
    }
    public void printBowlerStats(){
        System.out.println("Player Name   Overs   R   W   M");
        for (Player player : inning.getBowlingTeam().getPlayers()) {
            if(player.getAsBowler().isBowlingStatus()){
                player.printBowlingStats();
            }
        }
        System.out.println();
        System.out.println();
    }
    public void printFinalStats(){
        totalScore = inning.getCurrentScore();
        int totalWicket = inning.getCurrentWicket();
        int totalOvers = inning.getOvers().size();
        int lastOverSize=0;
        for(Ball ball : inning.getOvers().get(totalOvers-1).getOverDetails()){
            if(ball.getTypeOfBall()==MyEnumContainer.TypeOfBall.NORMAL||ball.getTypeOfBall()==MyEnumContainer.TypeOfBall.BOUNCE){
                lastOverSize++;
            }
        }
        if(lastOverSize<6)
            System.out.println("Total Overs : "+(totalOvers-1)+"."+lastOverSize);
        else
            System.out.println("Total Overs : "+totalOvers+".0");

        int extras=0 , wideBall=0 , noBall=0;
        for(Over over : inning.getOvers()){
            for(Ball ball : over.getOverDetails()){
                if(ball.getTypeOfBall() == MyEnumContainer.TypeOfBall.NOBALL){
                    extras++;
                    noBall++;
                }
                if(ball.getTypeOfBall() == MyEnumContainer.TypeOfBall.WIDE){
                    extras++;
                    wideBall++;
                }
            }
        }
        System.out.println("Total:-   "+totalScore + " / "+ totalWicket);
        System.out.println("Extras: "+ extras +"  , Wides: "+wideBall+"  , No Balls: "+noBall);
    }
    public void printInningStats(){
        printOversStats();
        printBatsmenStats();
        printBowlerStats();
        printFinalStats();
        if(inning.isChasing()){
            printFinalResult();
        }
    }
    public void printFinalResult(){
        if(totalScore>inning.getTargetScore())
            System.out.println("Team "+inning.getBattingTeam().getTeamName()+" Win!!  by "+(10-inning.getCurrentWicket())+" wickets");
        else if(totalScore<inning.getTargetScore())
            System.out.println("Team "+inning.getBowlingTeam().getTeamName()+ " Win!!  by "+(inning.getTargetScore()-totalScore)+ " Runs");
        else
            System.out.println("Match Tie");
    }
}
