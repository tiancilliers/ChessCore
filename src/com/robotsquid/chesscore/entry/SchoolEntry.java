package com.robotsquid.chesscore.entry;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.game.PlayerParticipation;

import java.io.Serializable;
import java.util.ArrayList;

public class SchoolEntry implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;
    private String acronym;
    private float totalScore = 0;
    private int numPlayers = 0;

    public SchoolEntry()
    {
        this.name = "";
        this.acronym = "";
    }

    public SchoolEntry(String acronym, String name)
    {
        this.name = name;
        this.acronym = acronym;
    }

    public String getAcronym()
    {
        return acronym;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAcronym(String acronym)
    {
        this.acronym = acronym;
    }

    @Override
    public String toString()
    {
        return this.getName();
    }

    public float getTotalScore()
    {
        return totalScore;
    }

    public void updateGames()
    {
        totalScore = 0;
        numPlayers = 0;
        for (PlayerEntry e : ChessCore.data.playerEntries)
        {
            if (e.getSchoolEntry() == this)
            {
                numPlayers++;
                for (PlayerParticipation p : e.gamesPlayed)
                {
                    if (p.getResult() != null)
                    {
                        totalScore += p.getResult().toFloat();
                    }
                }
            }
        }
    }

    public int getNumPlayers()
    {
        return numPlayers;
    }
}
