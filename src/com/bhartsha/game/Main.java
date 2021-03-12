package com.bhartsha.game;

import java.io.File;
import java.util.*;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        ResourceBundle reader = null;
        Connection connection = null;
        try {
            reader = ResourceBundle.getBundle("com.bhartsha.game.dbconfig");
            Class.forName(reader.getString("db.class"));
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(reader.getString("db.url") , reader.getString("db.username") , reader.getString("db.password"));
            System.out.println("Connected database successfully...");
            Scanner sc = new Scanner(System.in);

            File teamA = new File("src/Team-A.txt");
            File teamB = new File("src/Team-B.txt");

            System.out.println("Number of overs for this match");
            int numberOfOver = sc.nextInt();

            System.out.println("Enter location of the match:");
            String location = sc.next();

            Match newMatch = new Match(teamA , teamB, numberOfOver ,connection , location);
            //Match newMatch = new Match(1,2,numberOfOver ,connection ,location);
            newMatch.start();
        } catch (Exception e){
            e.printStackTrace();
        }//Handle errors for Class.forName
        finally{
            try{
                if(connection!=null) {
                    System.out.println("connection close!");
                    connection.close();
                }
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}