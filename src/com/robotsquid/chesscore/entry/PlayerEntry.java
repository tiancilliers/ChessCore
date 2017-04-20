package com.robotsquid.chesscore.entry;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.game.PlayerParticipation;

import java.io.Serializable;
import java.util.*;

public class PlayerEntry implements Serializable
{
    private static final long serialVersionUID = 1L;

    private SchoolEntry schoolEntry;

    private String name;
    private String surname;
    private Date birth;
    private Gender gender;

    private boolean present = false;

    private int rating = 0;
    public float score = 0;
    public float scoreBeforeCurrentRound = 0;
    public float buchholz = 0;
    public float perf = 0;
    public float ratingB;
    public String gameScores = "";

    public boolean isReal()
    {
        return real;
    }

    public void setReal(boolean real)
    {
        this.real = real;
        for (int i = 0; i < ChessCore.data.rounds.size(); i++)
        {
            this.gamesPlayed.add(new PlayerParticipation(this, i));
        }
    }

    public boolean real = true;

    public ArrayList<PlayerParticipation> gamesPlayed = new ArrayList<PlayerParticipation>();

    public PlayerEntry(String name, String surname, SchoolEntry schoolEntry, Date date, Gender gender)
    {
        this.name = name;
        this.surname = surname;
        this.schoolEntry = schoolEntry;
        this.birth = date;
        this.gender = gender;
        this.real = true;
    }

    public PlayerEntry(boolean bye)
    {
        this.real = false;
        if (bye)
        {
            this.name = "BYE";
        }
        else
        {
            this.name = "";
            this.surname = "";
        }
    }

    public void setSchoolEntry(SchoolEntry schoolEntry)
    {
        this.schoolEntry = schoolEntry;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getName()
    {
        return name;
    }

    public SchoolEntry getSchoolEntry()
    {
        return schoolEntry;
    }

    @Deprecated
    public ArrayList<PlayerParticipation> getGamesPlayed()
    {
        return gamesPlayed;
    }

    @Override
    public String toString()
    {
        return this.name.equalsIgnoreCase("bye") ? "BYE" : (this.isReal() ? (this.surname + ", " + this.name) : "");
    }

    public void updateScores()
    {
        score = 0;
        for (PlayerParticipation pp : gamesPlayed)
        {
            if (pp.getResult() != null)
            {
                score += pp.getResult().toFloat();
            }
        }
    }

    public boolean isPresent()
    {
        return present;
    }

    public void setPresent(boolean present)
    {
        this.present = present;
    }

    public void updateGameString()
    {
        this.gameScores = "";
        for (PlayerParticipation pp : gamesPlayed)
        {
            if (gameScores != "")
            {
                gameScores += ", ";
            }
            gameScores += pp.getResult() != null ? (Objects.equals(pp.getOpponent().name, "BYE") ? "BYE" : pp.getResult()) : (!Objects.equals(pp.getOpponent().getName(), "") ? "BUSY" : "NP");
        }
    }

    public Date getBirth()
    {
        return birth;
    }

    public void setBirth(Date birth)
    {
        this.birth = birth;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public float getRatingB()
    {
        return ratingB;
    }

    public void setRatingB(float ratingB)
    {
        this.ratingB = ratingB;
    }

    public void updatePerf()
    {
        ArrayList ratio = new ArrayList<Float>();
        for (PlayerEntry pe : ChessCore.data.playerEntries)
        {
            float wins = 0, loss = 0;
            for (PlayerParticipation pp : pe.gamesPlayed)
            {
                if (pp.getResult() != null)
                {
                    wins += pp.getResult().toFloat();
                    loss += 1 - pp.getResult().toFloat();
                }
            }
            ratio.add(loss == 0 ? 1 : wins / loss);
        }

        float wins = 0, loss = 0;
        for (PlayerParticipation pp : gamesPlayed)
        {
            if (pp.getResult() != null)
            {
                wins += pp.getResult().toFloat();
                loss += 1 - pp.getResult().toFloat();
            }
        }

        perf = loss == 0 ? 1 : (wins / loss) / ((float) Collections.max(ratio));
    }


    public static enum Gender implements Serializable
    {
        MALE,
        FEMALE,
        OTHER;

        private static final long serialVersionUID = 1L;

        public String toShortString()
        {
            switch (this)
            {
                case MALE:
                    return "M";
                case FEMALE:
                    return "F";
                case OTHER:
                    return "O";
            }
            return "";
        }

        @Override
        public String toString()
        {
            switch (this)
            {
                case MALE:
                    return "Male";
                case FEMALE:
                    return "Female";
                case OTHER:
                    return "Other";
            }
            return "";
        }


    }

    public String getFullName()
    {
        return isReal() ? name + " " + surname : name;
    }

    public boolean playedAgainst(PlayerEntry pe)
    {
        boolean out = false;
        for (PlayerParticipation pp : gamesPlayed)
        {
            out = out || pp.getOpponent() == pe;
        }
        return out;
    }
}
