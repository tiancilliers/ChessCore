package com.robotsquid.chesscore.gui.tables;

import com.robotsquid.chesscore.game.ChessGame;
import com.robotsquid.chesscore.game.PlayerResult;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class DynamicTableModel extends AbstractTableModel
{
    protected ArrayList data;
    private final String[] columnNames;
    private final Class[] columnClass;

    public DynamicTableModel(ArrayList data, String[] columnNames, Class[] columnClass)
    {
        this.data = data;
        this.columnNames = columnNames;
        this.columnClass = columnClass;
    }

    @Override
    public String getColumnName(int i)
    {
        return columnNames[i];
    }

    @Override
    public Class getColumnClass(int i)
    {
        return columnClass[i];
    }

    @Override
    public int getRowCount()
    {
        return data.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    public void addRow()
    {

    }

    public void removeRow(int i)
    {
        data.remove(i);
        fireTableDataChanged();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        super.setValueAt(aValue, rowIndex, columnIndex);
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;
    }

    public ArrayList<?> getData()
    {
        return data;
    }

    public void removeAllRows()
    {
        this.data.clear();
        fireTableDataChanged();
    }

    public void setData(ArrayList data)
    {
        this.data = data;
        fireTableDataChanged();
    }

    public void update()
    {
        fireTableDataChanged();
    }
}
