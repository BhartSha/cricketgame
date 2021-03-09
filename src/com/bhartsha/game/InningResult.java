package com.bhartsha.game;

public class InningResult {
    private final PlayGame inning;
    private int totalWicket , totalScore;
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
        for (int i = 0; i <inning.getWicketDetail().size() ; i++) {
            inning.getWicketDetail().get(i).getBatsman().printBattingStats();
            System.out.println("  b "+inning.getWicketDetail().get(i).getBowler().getPlayerName());
        }
        for (Player player : inning.getBattingTeam().getPlayers()) {
            if(player.getAsBatsman().isBattingStatus()&&!player.getAsBatsman().isOutStatus()){
                player.printBattingStats();
                System.out.println("   Not Out");
            }
        }
    }
    public void printBowlerStats(){
        System.out.println("Player Name   Overs   R   W   M");
        for (Player player : inning.getBowlingTeam().getPlayers()) {
            if(player.getAsBowler().isBowlingStatus()){
                player.printBowlingStats();
            }
        }
    }
    public void printFinalStats(){
        totalScore = inning.getCurrentScore();
        totalWicket = inning.getCurrentWicket();
        int totalOvers = inning.getOvers().size();
        int lastOverSize = inning.getOvers().get(totalOvers-1).getOverDetails().size();
        if(lastOverSize<6)
            System.out.println("Total Overs : "+(totalOvers-1)+"."+lastOverSize);
        else
            System.out.println("Total Overs : "+totalOvers+".0");

        System.out.println("Total:-   "+totalScore + " / "+totalWicket);
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
            System.out.println("Team "+inning.getBattingTeam().getTeamName()+" Win!!");
        else if(totalScore<inning.getTargetScore())
            System.out.println("Team "+inning.getBowlingTeam().getTeamName()+ " Win!!");
        else
            System.out.println("Match Tie");
    }
}
