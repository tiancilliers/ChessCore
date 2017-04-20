package com.robotsquid.chesscore.gui;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.entry.SchoolEntry;
import com.robotsquid.chesscore.gui.tables.SchoolResultsTable;
import com.robotsquid.chesscore.gui.tables.SchoolResultsTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

public class SchoolResultsPanel extends DynamicPanel
{
    final SchoolResultsTable schoolResultsTable;
    final JButton print;

    public SchoolResultsPanel()
    {
        super();

        GridBagConstraints gbc = new GridBagConstraints();

        schoolResultsTable = new SchoolResultsTable();

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        this.add(new JScrollPane(schoolResultsTable), gbc);

        print = new JButton("Print");

        gbc.weighty = 0;
        gbc.gridy = 1;

        this.add(print, gbc);

        print.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    schoolResultsTable.print();
                }
                catch (PrinterException e1) {}
            }
        });
    }

    @Override
    public void guiEventOccured(GUIEvent e)
    {
        switch (e.getCode())
        {
            case SCHOOL_ENTRIES_CHANGED:
            case IMPORTED:
                ((SchoolResultsTableModel) schoolResultsTable.getModel()).setData((ChessCore.data.schoolEntries));
                break;
            default:
                for (SchoolEntry s : ChessCore.data.schoolEntries)
                {
                    s.updateGames();
                }
        }
    }
}
