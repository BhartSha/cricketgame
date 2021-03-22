package com.bhartsha.game;

import com.bhartsha.game.database.DBConnection;
import com.bhartsha.game.database.Database;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args){
//        Scanner sc = new Scanner(System.in);
//
//        File teamA = new File("src/Team-A.txt");
//        File teamB = new File("src/Team-B.txt");
//
//        System.out.println("Number of overs for this match");
//        int numberOfOver = sc.nextInt();
//
//        System.out.println("Enter location of the match:");
//        String location = sc.next();
//
//        //Match newMatch = new Match(teamA , teamB, numberOfOver , location);
//        Match newMatch = new Match(1,2,numberOfOver  ,location);
//        newMatch.start();
        DBConnection dbConnection = new DBConnection();
        Database database = new Database(dbConnection.getConnection());
        database.printParticularMatchResult(2);
        dbConnection.closeConnection();
    }
}