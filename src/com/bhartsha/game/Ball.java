package com.bhartsha.game;

import lombok.Data;

@Data
public class Ball {
    private Player playedBy;
    private Player bowledBy;
    private MyEnumContainer.PossibleScore score;
    private MyEnumContainer.TypeOfBall typeOfBall;
    private boolean wicketTaken;
    private Wicket wicket;

    public Ball(Player playedBy, Player bowledBy, MyEnumContainer.PossibleScore score, MyEnumContainer.TypeOfBall typeOfBall) {
        this.playedBy = playedBy;
        this.bowledBy = bowledBy;
        this.score = score;
        this.typeOfBall = typeOfBall;
    }

    public void printBall(){
        System.out.println(typeOfBall.getValue()+" "+score.getValue()+" "+playedBy.getPlayerName()+" "+bowledBy.getPlayerName());
    }

}
