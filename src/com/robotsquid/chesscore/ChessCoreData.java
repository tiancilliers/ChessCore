package com.robotsquid.chesscore;

import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.entry.SchoolEntry;
import com.robotsquid.chesscore.game.ChessGame;
import com.robotsquid.chesscore.game.ChessRound;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class ChessCoreData implements Serializable
{
    private static final long serialVersionUID = 1L;

    public ArrayList<SchoolEntry> schoolEntries;
    public ArrayList<PlayerEntry> playerEntries;
    public ArrayList<ChessRound> rounds;
    public ArrayList<ChessGame> historyGames;
    public int currentRound = 0;
    public ChessCoreSettings settings;

    public ChessCoreData()
    {
        schoolEntries = new ArrayList<SchoolEntry>();
        playerEntries = new ArrayList<PlayerEntry>();
        rounds = new ArrayList<ChessRound>();
        historyGames = new ArrayList<ChessGame>();
        currentRound = 0;
        settings = new ChessCoreSettings();
    }

    public int getMaxRating()
    {
        int max = 0;
        for (PlayerEntry p : playerEntries)
        {
            max = p.getRating() > max ? p.getRating() : max;
        }
        return max;
    }
}
