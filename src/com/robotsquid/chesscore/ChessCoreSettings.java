package com.robotsquid.chesscore;

import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.game.ChessRound;
import com.robotsquid.chesscore.game.PlayerParticipation;
import com.robotsquid.chesscore.matching.*;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChessCoreSettings implements Serializable
{
    private static final long serialVersionUID = 1L;

    public enum BuchholzOption
    {
        BUCHHOLZ,
        MEDIAN_BUCHHOLZ,
        SONNEBORN_BERGER;

        @Override
        public String toString()
        {
            switch (this)
            {
                case BUCHHOLZ:
                    return "Buchholz";
                case MEDIAN_BUCHHOLZ:
                    return "Median Buchholz";
                case SONNEBORN_BERGER:
                    return "Sonneborn-Berger";
            }
            return null;
        }

        public String title()
        {
            return this.toString();
        }

        public String description()
        {
            switch(this)
            {
                case BUCHHOLZ:
                    return "Sum of all opponents' current scores";
                case MEDIAN_BUCHHOLZ:
                    return "Sum of all opponents' current scores except best and worst opponent";
                case SONNEBORN_BERGER:
                    return "Sum of all opponents' current scores multiplied by result of the game";
            }
            return "DESCRIPTION MISSING";
        }

        public void updateBuchholz(PlayerEntry e)
        {
            int buchholz = 0;
            int ratingB = 0;
            ArrayList<PlayerParticipation> tempPlayed = new ArrayList<PlayerParticipation>();
            for (PlayerParticipation pp : e.gamesPlayed)
            {
                tempPlayed.add(pp);
            }
            Collections.sort(tempPlayed, new Comparator<PlayerParticipation>()
            {
                @Override
                public int compare(PlayerParticipation o1, PlayerParticipation o2)
                {
                    return o1.getMe().score > o2.getMe().score ? -1 : (o1.getMe().score < o2.getMe().score ? 1 : (o1.getMe().buchholz > o2.getMe().buchholz ? -1 : (o1.getMe().buchholz < o2.getMe().buchholz ? 1 : 0)));
                }
            });
            for (PlayerParticipation pp : e.gamesPlayed)
            {
                if (pp.getResult() != null)
                {
                    switch (this)
                    {
                        case BUCHHOLZ:
                            buchholz += pp.getOpponent().score;
                            ratingB += pp.getOpponent().getRating();
                            break;
                        case MEDIAN_BUCHHOLZ:
                            buchholz += (pp == tempPlayed.get(0)) || (pp == tempPlayed.get(tempPlayed.size() - 1)) ? 0 : pp.getOpponent().score;
                            ratingB += (pp == tempPlayed.get(0)) || (pp == tempPlayed.get(tempPlayed.size() - 1)) ? 0 : pp.getOpponent().getRating();
                            break;
                        case SONNEBORN_BERGER:
                            buchholz += pp.getOpponent().score * pp.getResult().toFloat();
                            ratingB += pp.getOpponent().getRating() * pp.getResult().toFloat();
                            break;
                    }
                }
            }

            e.buchholz = buchholz;
            e.ratingB = ratingB;
        }
    }

    public enum MatcherOption
    {
        FRIENDLY,
        LEAGUE,
        TEAM;


        @Override
        public String toString()
        {
            switch (this)
            {
                case FRIENDLY:
                    return "Automatic Matcher";
                case LEAGUE:
                    return "Manual Matcher";
                case TEAM:
                    return "Team Matcher";
            }
            return "MISSING NAME";
        }

        public String title()
        {
            return this.toString();
        }

        public String description()
        {
            switch (this)
            {
                case FRIENDLY:
                    return "Automatic matcher ranking according to score and buchholz.";
                case LEAGUE:
                    return "Basic manual matcher. Games can be chosen out of all players.";
                case TEAM:
                    return "Automatic matcher only matching players from opposing teams";
            }
            return "MISSING DESCRIPTION";
        }

        public ChessRound drawGames()
        {
            switch (this)
            {
                case FRIENDLY:
                    return FriendlyMatcher.drawGames();
                case LEAGUE:
                    return LeagueMatcher.drawGames();
                case TEAM:
                    return TeamMatcher.drawGames();
            }
            return new ChessRound();
        }
    }

    public BuchholzOption buchholzOption = BuchholzOption.BUCHHOLZ;
    public MatcherOption matcherOption = MatcherOption.FRIENDLY;

    public File saveFile;

    public boolean boardsLimited = false;
    public int boardsNum = 20;

    // FUTURE

    public boolean schoolEnabled = true;
    public boolean ratingEnabled = true;
    public boolean ageEnabled = true;
    public boolean genderEnabled = true;

    public ChessCoreSettings() {}
}
