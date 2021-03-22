package com.bhartsha.game.database;

import com.bhartsha.game.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final Connection connection;
    private final int matchId;
    private final int tossId;
    private final BallRepository ballRepository;
    private final BatsmanRepository batsmanRepository;
    private final BowlerRepository bowlerRepository;
    private final CaptainRepository captainRepository;
    private final IndividualMatchRecordsAsBatsmanRepository individualMatchRecordsAsBatsmanRepository;
    private final IndividualMatchRecordsAsBowlerRepository individualMatchRecordsAsBowlerRepository;
    private final MatchRecordsRepository matchRecordsRepository;
    private final MatchTeamPlayerRepository matchTeamPlayerRepository;
    private final OverRecordsRepository overRecordsRepository;
    private final PlayerRepository playerRepository;
    private final TeamPlayerRepository teamPlayerRepository;
    private final TeamRepository teamRepository;
    private final TossRepository tossRepository;
    private final WicketKeeperRepository wicketKeeperRepository;
    private final WicketRepository wicketRepository;


    public Database(Connection connection){
        this.connection = connection;
        ballRepository = new BallRepository(connection);
        batsmanRepository = new BatsmanRepository(connection);
        bowlerRepository = new BowlerRepository(connection);
        captainRepository = new CaptainRepository(connection);
        individualMatchRecordsAsBatsmanRepository = new IndividualMatchRecordsAsBatsmanRepository(connection);
        individualMatchRecordsAsBowlerRepository = new IndividualMatchRecordsAsBowlerRepository(connection);
        matchRecordsRepository = new MatchRecordsRepository(connection);
        matchTeamPlayerRepository = new MatchTeamPlayerRepository(connection);
        overRecordsRepository = new OverRecordsRepository(connection);
        playerRepository = new PlayerRepository(connection);
        teamPlayerRepository = new TeamPlayerRepository(connection);
        teamRepository = new TeamRepository(connection);
        tossRepository = new TossRepository(connection);
        wicketKeeperRepository = new WicketKeeperRepository(connection);
        wicketRepository = new WicketRepository(connection);
        this.matchId = this.matchRecordsRepository.getCurrentMatchId()+1;
        this.tossId = tossRepository.getCurrentTossId()+1;
    }

    public void addTeamDetails(Team team){
        teamRepository.insertIntoTable(team);
        playerRepository.insertIntoTable(team);
        batsmanRepository.insertCompleteTeamIntoTable(team);
        bowlerRepository.insertCompleteTeamIntoTable(team);
        teamPlayerRepository.insertIntoTable(team);
        captainRepository.insertIntoTable(team);
        wicketKeeperRepository.insertIntoTable(team);
    }

    public void updateTableAfterMatch(PlayGame firstInning , PlayGame secondInning , int numberOfOvers , String location){
        matchRecordsRepository.insertIntoTable(firstInning,secondInning,numberOfOvers,tossId,location);
        //after first Inning
        batsmanRepository.updateTable(firstInning.getBattingTeam());
        bowlerRepository.updateTable(firstInning.getBowlingTeam());
        individualMatchRecordsAsBatsmanRepository.insertIntoTable(matchId , firstInning.getBattingTeam());
        individualMatchRecordsAsBowlerRepository.insertIntoTable(matchId , firstInning.getBowlingTeam());
        overRecordsRepository.insertIntoTable(matchId , 1 , firstInning.getOvers());
        wicketRepository.insertIntoTable(matchId , 1 , firstInning.getWicketDetail());
        ballRepository.insertIntoTable(matchId , 1 , firstInning.getOvers());
        matchTeamPlayerRepository.insertIntoTable(matchId , firstInning.getBattingTeam());

        //after second inning
        batsmanRepository.updateTable(secondInning.getBattingTeam());
        bowlerRepository.updateTable(secondInning.getBowlingTeam());
        individualMatchRecordsAsBatsmanRepository.insertIntoTable(matchId , secondInning.getBattingTeam());
        individualMatchRecordsAsBowlerRepository.insertIntoTable(matchId , secondInning.getBowlingTeam());
        overRecordsRepository.insertIntoTable(matchId , 2 , secondInning.getOvers());
        wicketRepository.insertIntoTable(matchId , 2 , secondInning.getWicketDetail());
        ballRepository.insertIntoTable(matchId , 2 , secondInning.getOvers());
        matchTeamPlayerRepository.insertIntoTable(matchId , secondInning.getBattingTeam());

    }

    public Team getTeamObj(int teamId){
        Statement stmt;
        String query;
        Team team = null;
        int captainId = captainRepository.getCaptainId(teamId);
        int wicketKeeperId = wicketKeeperRepository.getWicketKeeperId(teamId);
        try{
            stmt = connection.createStatement();
            team = teamRepository.getTeam(teamId);
           //adding player
            query = "select player.player_id , player.firstname , player.lastname , player.category from player join team_player on " +
                    "team_player.player_id = player.player_id where team_player.team_id = "+teamId;
           ResultSet res = stmt.executeQuery(query);
           while(res.next()){
               Player player = new Player(res.getInt(1) , res.getString(2), res.getString(3) , res.getString(4));
               if(res.getInt(1)==captainId){
                   assert team != null;
                   team.setCaptain(player);
               }
               if(res.getInt(1) == wicketKeeperId){
                   assert team != null;
                   team.setWicketKeeper(player);
               }

               player.getAsBatsman().setStrikeRate(batsmanRepository.getStrikeRate(player.getId()));
               player.getAsBowler().setStrikeRate(bowlerRepository.getStrikeRate(player.getId()));

               assert team != null;
               team.addPlayer(player);
           }
        }catch(SQLException sqe){
            sqe.printStackTrace();
        }
        return  team;
    }

    public Team getTeamObj2(int teamId , ArrayList<Integer> playersId){
        int captainId = captainRepository.getCaptainId(teamId);
        int wicketKeeperId = wicketKeeperRepository.getWicketKeeperId(teamId);
        Team team = teamRepository.getTeam(teamId);

        for(Integer id : playersId){
            Player player = playerRepository.getPlayer(id);
            if(id==captainId){
                assert team != null;
                    team.setCaptain(player);
            }
            if(id == wicketKeeperId){
                assert team != null;
                team.setWicketKeeper(player);
            }
            player.getAsBatsman().setStrikeRate(batsmanRepository.getStrikeRate(id));
            player.getAsBowler().setStrikeRate(bowlerRepository.getStrikeRate(id));

            assert team != null;
            team.addPlayer(player);
        }
        return  team;
    }

    public void printParticularMatchResult(int matchId){
        int tossId=1 ,firstTeamId=1 , secondTeamId=1 , firstInningScore=1 , secondInningScore=1 , firstInningWickets=1 , secondInningWickets=1;
        String location="";
        int overs=1;
        Team firstTeam , secondTeam;
        ArrayList<Integer> firstTeamPlayers , secondTeamPlayers;
        try{
            Statement stmt = connection.createStatement();
            String query = "select * from match_records where match_id = " + matchId;
            ResultSet res = stmt.executeQuery(query);
            res.next();
            firstTeamId = res.getInt(2);
            secondTeamId = res.getInt(3);
            overs = res.getInt(4);
            location = res.getString(5);
            tossId = res.getInt(7);
            firstInningScore = res.getInt(8);
            firstInningWickets = res.getInt(9);
            secondInningScore = res.getInt(10);
            secondInningWickets = res.getInt(11);
        }catch (SQLException sqe){
            sqe.printStackTrace();
        }
        System.out.println("Match Location: "+location);
        System.out.println("Number Of Overs: "+overs);
        tossRepository.printParticularTossResult(tossId);
        firstTeamPlayers = matchTeamPlayerRepository.getParticularMatchPlayersList(matchId , firstTeamId);
        secondTeamPlayers = matchTeamPlayerRepository.getParticularMatchPlayersList(matchId,secondTeamId);
        firstTeam = getTeamObj2(firstTeamId , firstTeamPlayers);
        secondTeam = getTeamObj2(secondTeamId , secondTeamPlayers);
        firstTeam.showTeamDetail();
        secondTeam.showTeamDetail();
        printInning(firstTeam,secondTeam,firstInningScore,firstInningWickets,0,matchId,false,1,overs);
        printInning(secondTeam,firstTeam,secondInningScore,secondInningWickets,firstInningScore,matchId,true,2,overs);
    }

    public void printInning(Team firstTeam , Team secondTeam , int inningScore , int inningWickets ,int chasingScore, int matchId,boolean chase,int inningNumber,int overs){
        PlayGame inning = new PlayGame(firstTeam,secondTeam,overs);
        inning.setCurrentScore(inningScore);
        inning.setCurrentWicket(inningWickets);
        inning.setChasing(chase);
        if(chase){
            inning.setTargetScore(chasingScore);
        }

        //inning.setOvers(overRecordsRepository.getParticularMatchInningOvers(matchId,inningNumber));
        inning.setWicketDetail(wicketRepository.getParticularMatchInningWickets(matchId,inningNumber,firstTeam,secondTeam));
        individualMatchRecordsAsBatsmanRepository.addRecordsIntoTeam(matchId,firstTeam);
        individualMatchRecordsAsBowlerRepository.addRecordsIntoTeam(matchId,secondTeam);
        inning.setOvers(overRecordsRepository.getParticularMatchInningOvers(matchId,inningNumber,firstTeam,secondTeam));
        InningResult inningResult = new InningResult(inning);
        inningResult.printInningStats();
    }
}
