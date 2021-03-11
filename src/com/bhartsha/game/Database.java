package com.bhartsha.game;

import java.sql.*;
import java.time.LocalDateTime;

public class Database {
    private final Connection connection;
    public Database(Connection connection){
        this.connection = connection;
    }

    public void addTeamDetails(Team team){
        Statement stmt;
        String query;
        try{
            stmt = connection.createStatement();
            query = "insert into team values ("+team.getTeamId()+", "+"\"" + team.getTeamName()+ "\""+", "+team.getTotalPlayers()+", "+team.getBatsmen()+", "+team.getBowlers()+", "+team.getAllRounders()+")";
            stmt.executeUpdate(query);
            for (Player player : team.getPlayers()) {
                //add player in player table
                query = "insert into player values ("+player.getId()+", '"+ player.getFirstName()+"', '"+ player.getLastName()+"', '"+player.getCategory()+"', "+team.getTeamId()+")";
                stmt.executeUpdate(query);

                //add player in batsman table
                query = "insert into batsman values ("+player.getId()+", 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , "+player.getAsBatsman().getStrikeRate()+",0)";
                stmt.executeUpdate(query);

                //add player in bowler table
                query = "insert into bowler values ("+player.getId()+", 0 , 0 , 0 , 0 , 0 , 0 , 0 , "+player.getAsBowler().getStrikeRate()+",0)";
                stmt.executeUpdate(query);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public void updateTablesAfterInning(PlayGame inning){
        updateBatsmanTable(inning);
        updateBowlerTable(inning);
    }
    public void updateBatsmanTable(PlayGame inning){
        Statement stmt;
        String query;
        ResultSet rs;
        try{
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_UPDATABLE);
            for(Player player:inning.getBattingTeam().getPlayers()){
                query = "select * from batsman where batsman_id = "+player.getId();
                rs = stmt.executeQuery(query);
                while(rs.next()){
                    int timesOut = rs.getInt(11);
                    rs.updateInt(2,rs.getInt(2)+1);
                    Batsman batsman = player.getAsBatsman();
                    if(batsman.isBattingStatus()){
                        rs.updateInt(3,rs.getInt(3)+1);
                        rs.updateInt(4,rs.getInt(4)+batsman.getRuns());
                        rs.updateInt(5,rs.getInt(5)+batsman.getBalls());
                        rs.updateInt(6,rs.getInt(6)+batsman.getFours());
                        rs.updateInt(7,rs.getInt(7)+batsman.getSixes());
                        if(batsman.getRuns()>=100){
                            rs.updateInt(9,rs.getInt(9)+1);
                        }
                        else if(batsman.getRuns()>=50){
                            rs.updateInt(8,rs.getInt(8)+1);
                        }
                        if(batsman.getRuns()>rs.getInt(10)){
                            rs.updateInt(10,batsman.getRuns());
                        }
                        if(batsman.isOutStatus()){
                            rs.updateInt(11,timesOut+1);
                            timesOut++;
                        }
                        if(timesOut>0){
                            float runs = rs.getInt(4)+batsman.getRuns();
                            float battingAvg= runs/timesOut;
                            rs.updateFloat(13,battingAvg);
                        }
                    }
                    rs.updateRow();
                }
            }
        }
        catch (SQLException sqe){
            sqe.printStackTrace();
        }
    }
    public void updateBowlerTable(PlayGame inning){
        Statement stmt;
        String query;
        ResultSet rs;
        try{
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_UPDATABLE);
            for(Player player:inning.getBattingTeam().getPlayers()){
                query = "select * from bowler where bowler_id = "+player.getId();
                rs = stmt.executeQuery(query);
                while(rs.next()){
                    rs.updateInt(2,rs.getInt(2)+1);
                    Bowler bowler = player.getAsBowler();
                    if(bowler.isBowlingStatus()){
                        rs.updateInt(3,rs.getInt(3)+1);
                        rs.updateInt(4,rs.getInt(4)+bowler.getWickets());
                        rs.updateInt(5,rs.getInt(5)+bowler.getRuns());
                        rs.updateInt(6,rs.getInt(6)+bowler.getBalls());
                        if(bowler.getWickets()>=5){
                            rs.updateInt(7,rs.getInt(7)+1);
                        }
                        rs.updateInt(8,rs.getInt(8)+bowler.getMaidens());
                        float runs = (float) rs.getInt(5)+bowler.getRuns();
                        int wickets = rs.getInt(4)+ bowler.getWickets();
                        if(wickets>0){
                            float bowlingAvg= runs/wickets;
                            rs.updateFloat(10,bowlingAvg);
                        }

                    }
                    rs.updateRow();
                }
            }
        }
        catch (SQLException sqe){
            sqe.printStackTrace();
        }

    }
    public void updateMatchResultTable(PlayGame firstInning , PlayGame secondInning , int toss_id , String location){
        Statement stmt;
        String query;
        int winningTeamId = firstInning.getBattingTeam().getTeamId();
        if(firstInning.getCurrentScore()< secondInning.getCurrentScore()){
            winningTeamId = secondInning.getBattingTeam().getTeamId();
        }
        try {
            stmt = connection.createStatement();
            query = "insert into match_records (first_team_id , second_team_id , location , date , toss_id , first_inning_score , first_inning_wickets"+
                        " , second_inning_score , second_inning_wickets , winning_team_id) values ("+firstInning.getBattingTeam().getTeamId()+" , "+
                        secondInning.getBattingTeam().getTeamId()+" , '"+location+"' , CURRENT_TIMESTAMP , "+toss_id+" , "+
                        firstInning.getCurrentScore()+" , "+firstInning.getCurrentWicket()+" , "+secondInning.getCurrentScore()+" , "+
                        secondInning.getCurrentWicket()+" , "+winningTeamId+")";
            stmt.executeUpdate(query);
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
    }
    public Team getTeamObj(int teamId){
        Statement stmt , st;
        ResultSet rest;
        String query;
        String teamName;
        Team team = null;
        try{
           stmt = connection.createStatement();
           st = connection.createStatement();
           //adding team details
           query = "select * from team where team_id = "+teamId;
           rest = stmt.executeQuery(query);
           while(rest.next()){
               teamName = rest.getString(2);
               team = new Team(teamName , teamId);
           }
            stmt.getMoreResults();
           //adding player
            query = "select * from player where team_id = "+teamId;
           ResultSet res = stmt.executeQuery(query);
           while(res.next()){
               Player player = new Player(res.getInt(1) , res.getString(2), res.getString(3) , res.getString(4));

               //adding batting strike rate
               query = "select strike_rate from batsman where batsman_id = "+player.getId();
               ResultSet re = st.executeQuery(query);
               re.next();
               player.getAsBatsman().setStrikeRate(re.getFloat(1));
               st.getMoreResults();
               //re.close();

               //adding bowling strike rate
               query = "select strike_rate from bowler where bowler_id = "+player.getId();
               ResultSet resultSet = st.executeQuery(query);
               resultSet.next();
               player.getAsBowler().setStrikeRate(resultSet.getFloat(1));
               st.getMoreResults();
               //resultSet.close();

               assert team != null;
               team.addPlayer(player);
           }
            assert team != null;
            team.setCaptain(team.getPlayers().get(1));
           team.setWicketKeeper(team.getPlayers().get(5));

        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return  team;
    }
}


/*
 int matches = rs.getInt(1);
                    int innings = rs.getInt(2);
                    int runs = rs.getInt(3);
                    int ballsFaced = rs.getInt(4);
                    int fours = rs.getInt(5);
                    int sixes = rs.getInt(6);
                    int halfCenturies = rs.getInt(7);
                    int centuries = rs.getInt(8);
                    int highestScore = rs.getInt(9);
                    int timesOut = rs.getInt(10);
                    float battingAvg = rs.getFloat(12);
                    PreparedStatement update = connection.prepareStatement("update batsman set matches = ?, innings = ?, runs = ?, balls_faced = ?, fours = ?" +
                            ", sixes = ?, half_centuries = ?, centuries = ?, highest_score = ?, times_out = ?, batting_avg = ? where id = ?");
 */
