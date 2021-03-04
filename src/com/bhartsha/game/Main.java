package com.bhartsha.game;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        File teamA = new File("src/Team-A.txt");
        File teamB = new File("src/Team-B.txt");

        System.out.println("Number of overs for this match");
        int numberOfOver = sc.nextInt();

        Match newMatch = new Match(teamA , teamB, numberOfOver);
    }
}
