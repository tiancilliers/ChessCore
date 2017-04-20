package com.robotsquid.chesscore.matching;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.game.ChessGame;
import com.robotsquid.chesscore.game.ChessRound;
import com.robotsquid.chesscore.game.PlayerParticipation;

import javax.swing.table.TableCellEditor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FriendlyMatcher implements Serializable
{
    public static ChessRound drawGames()
    {
        ChessCore.data.currentRound++;
        ChessRound thisRound = new ChessRound();

        ArrayList<PlayerEntry> noMatch = new ArrayList<PlayerEntry>();
        for (PlayerEntry pe : ChessCore.data.playerEntries)
        {
            if (pe.isPresent())
            {
                noMatch.add(pe);
            }
        }

        Collections.sort(noMatch, new Comparator<PlayerEntry>()
        {
            @Override
            public int compare(PlayerEntry o1, PlayerEntry o2)
            {
                return o1.score > o2.score ? -1 : (o1.score < o2.score ? 1 : (o1.buchholz > o2.buchholz ? -1 : (o1.buchholz < o2.buchholz ? 1 : 0)));
            }
        });
        boolean done = noMatch.size() == 0;

        for (int board = 1; ChessCore.data.settings.boardsLimited ? (board <= ChessCore.data.settings.boardsNum && !done) : !done; board++)
        {
            boolean maybeDone = noMatch.size() < 3;
            PlayerEntry p = noMatch.get(0);
            PlayerEntry o;
            noMatch.remove(p);
            ChessGame g;

            if (noMatch.size() == 0)
            {
                o = new PlayerEntry(true);
                g = new ChessGame(ChessCore.data.currentRound, board, p, o);
                g.setBye(true);
                thisRound.addGame(g);
                /*p.scoreBeforeCurrentRound = p.score;*/
            }
            else
            {
                ArrayList<PlayerEntry> chooseList = new ArrayList<PlayerEntry>();
                for (PlayerEntry pe : noMatch)
                {
                    chooseList.add(pe);
                }
                for (PlayerParticipation pp : p.gamesPlayed)
                {
                    if (chooseList.contains(pp.getOpponent()))
                    {
                        chooseList.remove(pp.getOpponent());
                    }
                }
                if (chooseList.size() != 0)
                {
                    o = chooseList.get(0);
                }
                else
                {
                    o = noMatch.get(0);
                }
                noMatch.remove(o);
                g = new ChessGame(ChessCore.data.currentRound, board, p, o);
                thisRound.addGame(g);

                /*p.scoreBeforeCurrentRound = p.score;
                o.scoreBeforeCurrentRound = o.score;*/
            }

            done = maybeDone;
        }

        for (ChessGame g : thisRound.getGames())
        {
            g.startGame();
        }

        return thisRound;
    }

    /*@Override
    public String description()
    {
        return "A matching algorithm for friendly matches between schools. Ranked according to score and buchholz, doesn't take school in account";
    }

    @Override
    public String toString()
    {
        return "Friendly Matcher";
    }*/
}
