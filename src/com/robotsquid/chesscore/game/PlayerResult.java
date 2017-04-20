package com.robotsquid.chesscore.game;

import java.io.Serializable;
import java.util.HashMap;

public enum PlayerResult implements Serializable
{
    WIN(1F, "Win"),
    DRAW(0.5F, "Draw"),
    LOSE(0F, "Lose");

    private static final long serialVersionUID = 1L;

    private final float score;
    private final String name;

    private static HashMap<Float, PlayerResult> scores = new HashMap<Float, PlayerResult>();

    static
    {
        for (PlayerResult p : PlayerResult.values())
        {
            scores.put(p.score, p);
        }
    }

    PlayerResult(float i, String name)
    {
        this.score = i;
        this.name = name;
    }

    public static PlayerResult valueOf(float score)
    {
        return scores.get(score);
    }

    public float toFloat()
    {
        return this.score;
    }

    public String stringName()
    {
        return this.name;
    }

    @Override
    public String toString()
    {
        return Float.toString(this.score);
    }

    public String comparisonString()
    {
        return this.score + " - " + (1-this.score);
    }
}
