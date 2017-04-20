package com.robotsquid.chesscore.gui;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.entry.SchoolEntry;
import com.robotsquid.chesscore.game.PlayerParticipation;
import com.robotsquid.chesscore.gui.tables.DynamicTableModel;
import com.robotsquid.chesscore.gui.tables.PlayerEntryTable;
import com.robotsquid.chesscore.gui.tables.PlayerEntryTableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayerEntryPanel extends DynamicPanel
{
    final PlayerEntryTable entries;
    final JPanel buttons;

    public PlayerEntryPanel()
    {
        super();

        GridBagConstraints gbc = new GridBagConstraints();

        entries = new PlayerEntryTable();

        buttons = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        gbc2.fill = GridBagConstraints.BOTH;
        final JButton insert = new JButton("Add Player Entry");
        buttons.add(insert, gbc2);
        gbc2.gridx = 1;
        final JButton delete = new JButton("Delete Player Entry");
        buttons.add(delete, gbc2);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        this.add(new JScrollPane(entries), gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;

        this.add(buttons, gbc);

        insert.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ((PlayerEntryTableModel) entries.getModel()).addRow();
                entries.changeSelection(entries.convertRowIndexToView(((DynamicTableModel) entries.getModel()).getData().size() - 1), 0, false, false);
            }
        });

        delete.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ((PlayerEntryTableModel) entries.getModel()).removeRow(entries.convertRowIndexToModel(entries.getSelectedRow()));
            }
        });

        entries.getModel().addTableModelListener(new TableModelListener()
        {
            @Override
            public void tableChanged(TableModelEvent e)
            {
                if (e.getType() == TableModelEvent.UPDATE)
                {
                    ChessCore.data.playerEntries = ((ArrayList<PlayerEntry>) ((DynamicTableModel) entries.getModel()).getData());
                    fireGUIEvent(new GUIEvent(entries, PlayerEntryPanel.this, GUIAction.PLAYER_ENTRIES_CHANGED));
                }
            }
        });
    }

    @Override
    public void guiEventOccured(GUIEvent e)
    {
        if (e.getCode() == GUIAction.SCHOOL_ENTRIES_CHANGED)
        {
            entries.setEditorValues();

            SchoolEntry s;
            ArrayList<PlayerEntry> remove = new ArrayList();
            for (PlayerEntry p : ChessCore.data.playerEntries)
            {
                s = p.getSchoolEntry();
                if (!ChessCore.data.schoolEntries.contains(s))
                {
                    remove.add(p);
                    //((PlayerEntryTableModel) entries.getModel()).removeRow(((DynamicTableModel) entries.getModel()).getData().indexOf(p));
                    //p.setSchoolEntry(new SchoolEntry());
                }
            }
            for (PlayerEntry p : remove)
            {
                ((PlayerEntryTableModel) entries.getModel()).removeRow(((DynamicTableModel) entries.getModel()).getData().indexOf(p));
            }
        }
        if (e.getCode() == GUIAction.IMPORTED)
        {
            ((DynamicTableModel) entries.getModel()).setData(ChessCore.data.playerEntries);
        }
    }
}
