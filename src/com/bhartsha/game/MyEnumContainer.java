package com.bhartsha.game;

public final class MyEnumContainer {
    public enum WicketType{
        BOLD("Bold"),
        Caught("Catch Out"),
        LBW("Lbw"),
        STUMPED("Stumped"),
        RUNOUT("Run Out"),
        HITWICKET("Hit Wicket");
        private final String value;
        WicketType(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }
        public WicketType fromString(String text){
            for(WicketType wicketType : WicketType.values()){
                if(wicketType.getValue().equals(text)){
                    return wicketType;
                }
            }
            return null;
        }

    }

    public enum TypeOfBall{
        NORMAL("Normal"),
        WIDE("Wide"),
        BOUNCE("Bounce"),
        NOBALL("No Ball");

        private final String value;
        TypeOfBall(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }
        public TypeOfBall fromString(String text){
            for(TypeOfBall typeOfBall : TypeOfBall.values()){
                if(typeOfBall.getValue().equals(text)){
                    return typeOfBall;
                }
            }
            return null;
        }
    }

    public  enum PossibleScore{
        ZERO('0'),
        ONE('1'),
        TWO('2'),
        THREE('3'),
        FOUR('4'),
        FIVE('5'),
        SIX('6'),
        WICKET('W');

        private final char value;
        PossibleScore(char c) {
            this.value = c;
        }
        public char getValue(){
            return value;
        }
        public  PossibleScore fromString(String text){
            for(PossibleScore possibleScore : PossibleScore.values()){
                if(text.equals(Character.toString(possibleScore.getValue()))){
                    return possibleScore;
                }
            }
            return null;
        }
    }
}
