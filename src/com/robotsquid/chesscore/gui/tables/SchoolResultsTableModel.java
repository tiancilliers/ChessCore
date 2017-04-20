package com.robotsquid.chesscore.gui.tables;

import com.robotsquid.chesscore.entry.SchoolEntry;

import java.util.ArrayList;

public class SchoolResultsTableModel extends DynamicTableModel
{
    public SchoolResultsTableModel()
    {
        super(new ArrayList<SchoolEntry>(), new String[] {"Acronym", "Full Name", "# Players", "Score"}, new Class[] {String.class, String.class, Integer.class, Float.class});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        SchoolEntry row = ((SchoolEntry) data.get(rowIndex));
        switch (columnIndex)
        {
            case 0:
                return row.getAcronym();
            case 1:
                return row.getName();
            case 2:
                return row.getNumPlayers();
            case 3:
                return row.getTotalScore();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }
}
