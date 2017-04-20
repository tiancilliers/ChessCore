package com.robotsquid.chesscore.gui.tables;

import com.robotsquid.chesscore.entry.SchoolEntry;

import java.util.ArrayList;

public class SchoolEntryTableModel extends DynamicTableModel
{
    public SchoolEntryTableModel()
    {
        super(new ArrayList<SchoolEntry>(), new String[] {"Acronym", "Full Name"}, new Class[] {String.class, String.class});
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
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        SchoolEntry row = ((SchoolEntry) data.get(rowIndex));
        switch (columnIndex)
        {
            case 0:
                row.setAcronym((String) aValue);
                break;
            case 1:
                row.setName((String) aValue);
                break;
        }
        fireTableDataChanged();
    }

    @Override
    public void addRow()
    {
        data.add(new SchoolEntry());
        fireTableDataChanged();
    }
}
