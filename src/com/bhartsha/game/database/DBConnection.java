package com.bhartsha.game.database;

import lombok.Data;


import java.sql.*;
import java.sql.SQLException;
import java.util.ResourceBundle;


@Data
public class DBConnection {
    private  Connection connection;
    public DBConnection(){
        try {
            ResourceBundle reader = ResourceBundle.getBundle("com.bhartsha.game.config.dbconfig");
            Class.forName(reader.getString("db.class"));
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(reader.getString("db.url") , reader.getString("db.username") , reader.getString("db.password"));
            System.out.println("Connected database successfully...");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void closeConnection(){
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
