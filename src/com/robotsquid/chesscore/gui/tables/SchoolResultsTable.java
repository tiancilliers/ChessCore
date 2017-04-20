package com.robotsquid.chesscore.gui.tables;

public class SchoolResultsTable extends DynamicTable
{
    public SchoolResultsTable()
    {
        super(new SchoolResultsTableModel());

        this.getColumn("Acronym").setMaxWidth(100);
        this.getColumn("# Players").setMaxWidth(100);
        this.getColumn("Score").setMaxWidth(100);
    }
}
