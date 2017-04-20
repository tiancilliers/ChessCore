package com.robotsquid.chesscore.gui.tables;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.entry.SchoolEntry;

import javax.swing.*;

public class PlayerEntryTable extends DynamicTable
{
    private JComboBox<SchoolEntry> editor = new JComboBox<SchoolEntry>();

    public PlayerEntryTable()
    {
        super(new PlayerEntryTableModel());

        setEditorValues();

        this.getColumn("Team").setCellEditor(new DefaultCellEditor(editor));
        this.getColumn("Gender").setCellEditor(new DefaultCellEditor(new JComboBox<PlayerEntry.Gender>(PlayerEntry.Gender.values())));

        this.getColumn("Present").setMaxWidth(50);
        this.getColumn("Born").setMaxWidth(100);
        this.getColumn("Gender").setMaxWidth(100);
        this.getColumn("Rating").setMaxWidth(100);

    }

    public void setEditorValues()
    {
        editor.setModel(new DefaultComboBoxModel<SchoolEntry>(ChessCore.data.schoolEntries.toArray(new SchoolEntry[ChessCore.data.schoolEntries.size()])));
    }
}
