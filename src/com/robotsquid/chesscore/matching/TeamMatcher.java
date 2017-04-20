package com.robotsquid.chesscore.matching;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.game.ChessGame;
import com.robotsquid.chesscore.game.ChessRound;
import com.robotsquid.chesscore.game.PlayerParticipation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TeamMatcher
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
                return o1.gamesPlayed.size() < o2.gamesPlayed.size() ? -1 : (o1.gamesPlayed.size() > o2.gamesPlayed.size() ? 1 : (o1.score > o2.score ? -1 : (o1.score < o2.score ? 1 : (o1.buchholz > o2.buchholz ? -1 : (o1.buchholz < o2.buchholz ? 1 : 0)))));
            }
        });
        boolean done = noMatch.size() == 0;

        for (int board = 1; ChessCore.data.settings.boardsLimited ? (board <= ChessCore.data.settings.boardsNum && !done) : !done; board++)
        {
            boolean maybeDone = noMatch.size() <3;
            PlayerEntry p = noMatch.get(0);
            PlayerEntry o;
            noMatch.remove(p);
            ChessGame g;

            ArrayList<PlayerEntry> chooseList = new ArrayList<PlayerEntry>();
            for (PlayerEntry pe : noMatch)
            {
                if (pe.getSchoolEntry() != p.getSchoolEntry() && !p.playedAgainst(pe))
                {
                    chooseList.add(pe);
                }
            }
            if (chooseList.size() != 0)
            {
                o = chooseList.get(0);

                noMatch.remove(o);
                boolean p1w = true, p2w = false;
                if (thisRound.getGames().size() > 0)
                {
                    ChessGame prevGame = thisRound.getGames().get(thisRound.getGames().size() - 1);
                    p1w = prevGame.getBlackParticipation().getMe().getSchoolEntry() == p.getSchoolEntry();
                    p2w = prevGame.getBlackParticipation().getMe().getSchoolEntry() == o.getSchoolEntry();
                }
                g = new ChessGame(ChessCore.data.currentRound, board, p1w || !p2w ? p : o, p1w || !p2w ? o : p);
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
}
