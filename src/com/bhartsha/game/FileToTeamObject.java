package com.bhartsha.game;

import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
public class FileToTeamObject {
    private final File teamData;
    public FileToTeamObject(File teamData){
        this.teamData = teamData;
    }
    public Team returnTeamObject() {
        List<String> list = new ArrayList<>();
        String[] data = new String[0], lineData;
        String teamName, playerCategory;
        int playerId , captainId , wicketKeeperId,teamId;
        Player captainObj = null, wicketKeeperObj = null;
        try {
            Scanner myReader = new Scanner(teamData);
            while(myReader.hasNextLine()){
                list.add(myReader.nextLine());
            }
            data = list.toArray(new String[0]);
        } catch (FileNotFoundException e) {
            System.out.println("An Error occurred");
            e.printStackTrace();
        }
        //retrieve team name
        lineData = data[0].split(" ");
        teamName = lineData[1];

        //retrieve team id
        lineData = data[1].split(" ");
        teamId = Integer.parseInt(lineData[1]);

        //creating team object
        Team team = new Team(teamName,teamId);

        // retrieve captain id
        lineData = data[2].split(" ");
        captainId = Integer.parseInt(lineData[1]);

        //retrieve wicketKeeper id
        lineData = data[3].split(" ");
        wicketKeeperId = Integer.parseInt(lineData[1]);

        //retrieve players data
        for (int i = 5; i < data.length ; i++) {
            lineData = data[i].split("\\s+");
            playerId = Integer.parseInt(lineData[0]);
            playerCategory = lineData[3];
            Player obj = new Player(playerId,lineData[1],lineData[2],playerCategory);
            obj.getAsBatsman().setStrikeRate(Double.parseDouble(lineData[4]));
            obj.getAsBowler().setStrikeRate(Double.parseDouble(lineData[5]));
            if(playerId == captainId){
                captainObj = obj;
            }
            else if(playerId == wicketKeeperId){
                wicketKeeperObj = obj;
            }
            team.addPlayer(obj);
        }
        team.setCaptain(captainObj);
        team.setWicketKeeper(wicketKeeperObj);
        return team;
    }
}
