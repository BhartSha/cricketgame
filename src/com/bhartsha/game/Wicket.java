package com.bhartsha.game;

import lombok.Data;

@Data
public class Wicket {
    private Player bowler , batsman;
    private int overNumber ,ballNumber;

    public Wicket(Player bowler, Player batsman, int overNumber, int ballNumber) {
        this.bowler = bowler;
        this.batsman = batsman;
        this.overNumber = overNumber;
        this.ballNumber = ballNumber;
    }
}
