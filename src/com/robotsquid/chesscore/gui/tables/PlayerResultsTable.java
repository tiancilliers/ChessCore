package com.robotsquid.chesscore.gui.tables;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import java.util.ArrayList;

public class PlayerResultsTable extends DynamicTable
{
    public DefaultRowSorter sorter;

    public PlayerResultsTable()
    {
        super(new PlayerResultsTableModel());

        this.getColumn("Score").setMaxWidth(75);
        this.getColumn("Buchholz").setMaxWidth(75);
        this.getColumn("Present").setMaxWidth(50);
        this.getColumn("Rating").setMaxWidth(100);
        this.getColumn("Performance Ratio").setMaxWidth(75);

        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setCellSelectionEnabled(false);
        this.setRowSelectionAllowed(true);
        this.setColumnSelectionAllowed(false);

        sorter = ((DefaultRowSorter) this.getRowSorter());
    }

    public void defaultSort()
    {
        ArrayList sortList = new ArrayList();
        sortList.add(new RowSorter.SortKey(6, SortOrder.DESCENDING));
        sortList.add(new RowSorter.SortKey(8, SortOrder.DESCENDING));
        sortList.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortList);
        sorter.sort();
    }
}
