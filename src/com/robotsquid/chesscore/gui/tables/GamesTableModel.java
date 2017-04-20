package com.robotsquid.chesscore.gui.tables;

import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.game.PlayerParticipation;
import com.robotsquid.chesscore.game.PlayerResult;

import java.util.ArrayList;

public class GamesTableModel extends DynamicTableModel
{

    public GamesTableModel()
    {
        super(new ArrayList<PlayerParticipation>(), new String[] {"Round", "Opponent", "Result"}, new Class[] {Integer.class, PlayerEntry.class, PlayerResult.class});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        PlayerParticipation row = ((PlayerParticipation) data.get(rowIndex));
        switch (columnIndex)
        {
            case 0:
                return row.getRound();
            case 1:
                return row.getOpponent();
            case 2:
                return row.getResult();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }
}
