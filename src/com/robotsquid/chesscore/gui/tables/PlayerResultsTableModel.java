package com.robotsquid.chesscore.gui.tables;

import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.entry.SchoolEntry;
import com.robotsquid.chesscore.entry.TableDate;

import java.util.ArrayList;

public class PlayerResultsTableModel extends DynamicTableModel
{
    public PlayerResultsTableModel()
    {
        super(new ArrayList<PlayerEntry>(), new String[] {"Name", "Team", "Present", "Rating", "Born", "Gender", "Score", "Buchholz", "Performance Ratio"}, new Class[] {String.class, SchoolEntry.class, Boolean.class, Integer.class, TableDate.class, PlayerEntry.Gender.class, Float.class, Float.class, Float.class});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        PlayerEntry row = ((PlayerEntry) data.get(rowIndex));
        switch (columnIndex)
        {
            case 0:
                return row.toString();
            case 1:
                return row.getSchoolEntry();
            case 2:
                return row.isPresent();
            case 3:
                return row.getRating();
            case 4:
                return new TableDate(row.getBirth());
            case 5:
                return row.getGender();
            case 6:
                return row.score;
            case 7:
                return row.buchholz;
            case 8:
                return row.perf;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }
}
