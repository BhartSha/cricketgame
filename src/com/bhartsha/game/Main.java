package com.bhartsha.game;

import java.io.File;
import java.util.*;
import java.sql.*;

public class Main {
    static final String DB_URL = "jdbc:mysql://localhost:3306/cricketdb";
    static final String uname = "root";
    static final String password = "Mysql5859?";
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(DB_URL, uname, password);
            System.out.println("Connected database successfully...");
            stmt = connection.createStatement();
            Scanner sc = new Scanner(System.in);

            File teamA = new File("src/Team-A.txt");
            File teamB = new File("src/Team-B.txt");

            System.out.println("Number of overs for this match");
            int numberOfOver = sc.nextInt();

            Match newMatch = new Match(teamA , teamB, numberOfOver ,connection);
            newMatch.start();
        } catch (Exception e){
            e.printStackTrace();
        }//Handle errors for Class.forName
        finally{
            try{
                if(stmt!=null)
                    connection.close();
            }catch(SQLException se){
            }
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