package com.robotsquid.chesscore.gui.tables;

import com.robotsquid.chesscore.game.PlayerParticipation;

import static com.robotsquid.chesscore.ChessCore.data;

public class GamesTable extends DynamicTable
{

    public GamesTable()
    {
        super(new GamesTableModel());

        this.getColumn("Round").setMaxWidth(50);
        this.getColumn("Result").setMaxWidth(100);
    }
}
