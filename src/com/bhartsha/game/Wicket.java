package com.bhartsha.game;

import lombok.Data;

@Data
public class Wicket {
    private MyEnumContainer.WicketType wicketType;
    private Player bowler , batsman , assistPlayer;
    private int overNumber ,ballNumber;

    public Wicket(Player bowler, Player batsman,Player assistPlayer , MyEnumContainer.WicketType wicketType ,int overNumber, int ballNumber) {
        this.bowler = bowler;
        this.batsman = batsman;
        this.assistPlayer = assistPlayer;
        this.wicketType = wicketType;
        this.overNumber = overNumber;
        this.ballNumber = ballNumber;
    }

}
