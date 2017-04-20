package com.robotsquid.chesscore.game;

import java.io.Serializable;

public enum ChessSide implements Serializable
{
    WHITE(0),
    BLACK(1);

    private static final long serialVersionUID = 1L;

    private final int sideInt;

    ChessSide(int i)
    {
        this.sideInt = i;
    }

    public int toInt()
    {
        return this.sideInt;
    }


    @Override
    public String toString()
    {
        switch (this)
        {
            case WHITE:
                return "White";
            case BLACK:
                return "Black";
        }
        return null;
    }
}
