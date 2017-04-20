package com.robotsquid.chesscore.gui;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.entry.SchoolEntry;
import com.robotsquid.chesscore.gui.tables.DynamicTableModel;
import com.robotsquid.chesscore.gui.tables.PlayerEntryTableModel;
import com.robotsquid.chesscore.gui.tables.SchoolEntryTable;
import com.robotsquid.chesscore.gui.tables.SchoolEntryTableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SchoolEntryPanel extends DynamicPanel
{
    final SchoolEntryTable schools;

    public SchoolEntryPanel()
    {
        super();

        GridBagConstraints gbc = new GridBagConstraints();

        schools = new SchoolEntryTable();

        JPanel buttons = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        gbc2.fill = GridBagConstraints.BOTH;
        final JButton insert = new JButton("Add Team");
        buttons.add(insert, gbc2);
        gbc2.gridx = 1;
        final JButton delete = new JButton("Delete Team");
        buttons.add(delete, gbc2);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        this.add(new JScrollPane(schools), gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;

        this.add(buttons, gbc);

        insert.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ((SchoolEntryTableModel) schools.getModel()).addRow();
                schools.changeSelection(schools.convertRowIndexToView(((DynamicTableModel) schools.getModel()).getData().size() - 1), 0, false, false);
            }
        });

        delete.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ((SchoolEntryTableModel) schools.getModel()).removeRow(schools.getSelectedRow());
            }
        });

        schools.getModel().addTableModelListener(new TableModelListener()
        {
            @Override
            public void tableChanged(TableModelEvent e)
            {
                fireGUIEvent(new GUIEvent(schools, SchoolEntryPanel.this, GUIAction.SCHOOL_ENTRIES_CHANGED));

                ChessCore.data.schoolEntries = ((ArrayList<SchoolEntry>) ((DynamicTableModel) schools.getModel()).getData());
            }
        });
    }

    @Override
    public void guiEventOccured(GUIEvent e)
    {
        if (e.getCode() == GUIAction.IMPORTED)
        {
            ((DynamicTableModel) schools.getModel()).setData(ChessCore.data.schoolEntries);
        }
    }
}
