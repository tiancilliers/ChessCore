package com.robotsquid.chesscore.gui.tables;

import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.entry.SchoolEntry;
import com.robotsquid.chesscore.entry.TableDate;

import java.util.ArrayList;

public class PlayerEntryTableModel extends DynamicTableModel
{
    public PlayerEntryTableModel()
    {
        super(new ArrayList<PlayerEntry>(), new String[] {"Surname", "Name", "Team", "Rating", "Born", "Gender", "Present"}, new Class[] {String.class, String.class, PlayerEntry.class, Integer.class, TableDate.class, PlayerEntry.Gender.class, Boolean.class});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        PlayerEntry row = ((PlayerEntry) data.get(rowIndex));
        switch (columnIndex)
        {
            case 0:
                return row.getSurname();
            case 1:
                return row.getName();
            case 2:
                return row.getSchoolEntry();
            case 3:
                return row.getRating();
            case 4:
                return new TableDate(row.getBirth());
            case 5:
                return row.getGender();
            case 6:
                return row.isPresent();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        PlayerEntry row = ((PlayerEntry) data.get(rowIndex));
        switch (columnIndex)
        {
            case 0:
                row.setSurname((String) aValue);
                break;
            case 1:
                row.setName((String) aValue);
                break;
            case 2:
                row.setSchoolEntry((SchoolEntry) aValue);
                break;
            case 3:
                row.setRating(((Integer) aValue));
                break;
            case 4:
                row.setBirth(((TableDate) aValue).getData());
                break;
            case 5:
                row.setGender(((PlayerEntry.Gender) aValue));
                break;
            case 6:
                row.setPresent((Boolean) aValue);
                break;
        }
        fireTableDataChanged();
    }

    @Override
    public void addRow()
    {
        PlayerEntry p = new PlayerEntry(false);
        p.setReal(true);
        data.add(p);
        fireTableDataChanged();
    }
}
