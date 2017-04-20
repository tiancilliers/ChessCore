package com.robotsquid.chesscore.game;

import com.robotsquid.chesscore.entry.PlayerEntry;

import java.io.Serializable;

public class PlayerParticipation implements Serializable
{
    private static final long serialVersionUID = 1L;

    private PlayerResult result;
    private PlayerEntry me;
    private PlayerEntry opponent;
    private ChessSide side;

    public int getRound()
    {
        return round;
    }

    private int round;

    public PlayerParticipation(ChessSide side, int round)
    {
        this.setMe(new PlayerEntry(false));
        this.opponent = new PlayerEntry(false);
        this.side = side;
        this.round = round;
    }

    public PlayerParticipation(PlayerEntry player, int round)
    {
        this.setMe(player);
        this.opponent = new PlayerEntry(false);
        this.round = round;
    }

    public PlayerParticipation(PlayerEntry me, PlayerEntry opponent, ChessSide side, int round)
    {
        this.setMe(me);
        this.opponent = opponent;
        this.side = side;
        this.round = round;
    }

    public PlayerResult getResult()
    {
        return result;
    }

    public void setResult(PlayerResult result)
    {
        this.result = result;
    }

    public PlayerEntry getOpponent()
    {
        return opponent;
    }

    public ChessSide getSide()
    {
        return side;
    }

    public void setMe(PlayerEntry me)
    {
        this.me = me;
    }

    public void setOpponent(PlayerEntry opponent)
    {
        this.opponent = opponent;
    }

    public PlayerEntry getMe()
    {
        return me;
    }
}
