package com.robotsquid.chesscore.gui.tables;

public class SchoolEntryTable extends DynamicTable
{
    public SchoolEntryTable()
    {
        super(new SchoolEntryTableModel());

        this.getColumn("Acronym").setMaxWidth(100);
    }
}
