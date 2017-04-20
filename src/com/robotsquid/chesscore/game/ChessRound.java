package com.robotsquid.chesscore.game;

import com.robotsquid.chesscore.ChessCore;

import java.io.Serializable;
import java.util.ArrayList;

public class ChessRound implements Serializable, Comparable
{
    private static final long serialVersionUID = 1L;

    private int roundNum;

    private ArrayList<ChessGame> games;

    public ChessRound()
    {
        games = new ArrayList<ChessGame>();
    }

    public void addGame(ChessGame game)
    {
        games.add(game);
    }

    public ArrayList<ChessGame> getGames()
    {
        return games;
    }

    @Override
    public String toString()
    {
        return String.valueOf(roundNum);
    }

    @Override
    public int compareTo(Object o)
    {
        return this.roundNum < ((ChessRound)o).roundNum ? -1 : (this.roundNum > ((ChessRound) o).roundNum ? 1 : 0);
    }
}
