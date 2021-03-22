package com.bhartsha.game;

import lombok.experimental.UtilityClass;

import java.util.Random;
@UtilityClass
public class Utils {
    private final int[] batsmanScoreFrequency={4,8,9,5,7,1,2,2};
    private final int[] allRounderScoreFrequency={3,5,8,4,9,1,4,4};
    private final int[] bowlerScoreFrequency={6,11,7,3,4,0,1,6};
    private final int[] typeOfBallFrequency={53,3,2,2};
    private final String[] coinFaces = {"head","tail"};
    public int findCeil(int[] prefix , int ele ,  int l , int r){
        int mid;
        while(l<r){
            mid = l+((r-l) >> 1);
            if(ele>prefix[mid]){
                l = mid+1;
            }
            else{
                r = mid;
            }
        }
        return (prefix[l]>=ele)?l:-1;
    }
    public int randomIndex(int[] freq , int n){
        int[] prefix = new int[n];
        prefix[0] = freq[0];
        for (int i = 1; i <n ; i++) {
            prefix[i] = prefix[i-1]+freq[i];
        }
        int ele =  (int)(Math.random()*prefix[n-1]+1);
        return findCeil(prefix , ele , 0 , n-1);
    }
    public MyEnumContainer.PossibleScore getRandomScore(String category){
        int rnd;
        if(category.equals("Batsman")){
            rnd = randomIndex(batsmanScoreFrequency , batsmanScoreFrequency.length);
        }
        else if(category.equals("AllRounder")){
            rnd = randomIndex(allRounderScoreFrequency , allRounderScoreFrequency.length);
        }
        else{
            rnd = randomIndex(bowlerScoreFrequency , bowlerScoreFrequency.length);
        }
        return MyEnumContainer.PossibleScore.values()[rnd];
    }

    public String getRandomCoinFace(){
        int rnd = new Random().nextInt(coinFaces.length);
        return coinFaces[rnd];
    }

    public MyEnumContainer.WicketType getRandomWicketType(){
        int pick = new Random().nextInt(MyEnumContainer.WicketType.values().length);
        return MyEnumContainer.WicketType.values()[pick];
    }

    public MyEnumContainer.TypeOfBall getRandomTypeOfBall(){
        int rnd = randomIndex(typeOfBallFrequency , typeOfBallFrequency.length);
        return MyEnumContainer.TypeOfBall.values()[rnd];
    }
    public MyEnumContainer.PossibleScore getRandomRunningScore(){
        return MyEnumContainer.PossibleScore.values()[new Random().nextInt(4)];
    }
    public int getRandomPlayerIndex(){
        return (new Random().nextInt(11));
    }
}