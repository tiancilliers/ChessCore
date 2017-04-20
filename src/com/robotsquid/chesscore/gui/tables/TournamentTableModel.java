package com.robotsquid.chesscore.gui.tables;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.game.ChessGame;
import com.robotsquid.chesscore.game.PlayerResult;

import java.util.ArrayList;

public class TournamentTableModel extends DynamicTableModel
{
    public TournamentTableModel()
    {
        super(new ArrayList<ChessGame>(), new String[] {"Board", "Player White", "Score White", "Result White", "Result Black", "Score Black", "Player Black"}, new Class[] {Integer.class, PlayerEntry.class, Float.class, PlayerResult.class, PlayerResult.class, Float.class, PlayerEntry.class});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ChessGame row = ((ChessGame) data.get(rowIndex));
        switch (columnIndex)
        {
            case 0:
                return row.getBoardNum();
            case 1:
                return row.getWhiteParticipation().getMe();
            case 2:
                return row.getWhiteParticipation().getMe().scoreBeforeCurrentRound;
            case 3:
                return row.getWhiteParticipation().getResult();
            case 4:
                return row.getBlackParticipation().getResult();
            case 5:
                return row.isBye() ? null : row.getBlackParticipation().getMe().scoreBeforeCurrentRound;
            case 6:
                return row.getBlackParticipation().getMe();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        if (aValue != null)
        {
            ChessGame row = ((ChessGame) data.get(rowIndex));
            switch (columnIndex)
            {
                case 3:
                    row.setWhiteScore((PlayerResult) aValue);
                    fireTableCellUpdated(rowIndex, columnIndex + 1);
                    break;
                case 4:
                    row.setWhiteScore(PlayerResult.valueOf(1F - ((PlayerResult) aValue).toFloat()));
                    fireTableCellUpdated(rowIndex, columnIndex - 1);
                    break;
            }
            fireTableDataChanged();
        }
    }

    @Override
    public void addRow()
    {
        data.add(new ChessGame(ChessCore.data.currentRound));
        fireTableDataChanged();
    }

    public void addRow(Object o)
    {
        data.add(o);
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return (columnIndex == 3 || columnIndex == 4) && !((ChessGame) data.get(rowIndex)).getBlackParticipation().getMe().getName().equals("BYE");
    }
}
