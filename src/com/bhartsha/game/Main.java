package com.bhartsha.game;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1st Team Name");
        String firstTeamName = sc.nextLine();
        System.out.println("Enter comma separated players name:");
        String s1 = sc.nextLine();

        System.out.println("Enter 2nd Team Name");
        String secondTeamName = sc.nextLine();
        System.out.println("Enter comma separated players name:");
        String s2 = sc.nextLine();

        System.out.println("Number of overs for this match");
        int numberOfOver = sc.nextInt();

        Match newMatch = new Match();
        newMatch.addTeamDetail(firstTeamName , s1 , secondTeamName , s2 , numberOfOver);
        newMatch.start();



	// write your code here
    }
}
/*
Mumbai Indians
A,B,C,D,E,F,G,H,I,J,K
Chennai Kings
A,B,C,D,E,F,G,H,I,J,K
 */