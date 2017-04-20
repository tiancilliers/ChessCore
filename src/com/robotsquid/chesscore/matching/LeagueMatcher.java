package com.robotsquid.chesscore.matching;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.game.ChessGame;
import com.robotsquid.chesscore.game.ChessRound;
import com.robotsquid.chesscore.game.PlayerParticipation;
import com.robotsquid.chesscore.gui.tables.DynamicTable;
import com.robotsquid.chesscore.gui.tables.DynamicTableModel;
import com.robotsquid.chesscore.gui.tables.TournamentTableModel;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class LeagueMatcher implements Serializable
{
    public static ChessRound drawGames()
    {
        ++ChessCore.data.currentRound;
        ChessRound round = new ChessRound();
        LeagueChooser lc = new LeagueChooser();
        JOptionPane.showMessageDialog(ChessCore.mainGUI, lc, "Choose Players", JOptionPane.PLAIN_MESSAGE);
        ArrayList games = ((ArrayList<ChessGame>) ((DynamicTableModel) lc.ct.getModel()).getData());
        for (int i = 0; i < games.size(); i++)
        {
            ChessGame g = ((ChessGame) games.get(i));
            PlayerEntry pw = (g.getWhiteParticipation().getMe() != null && g.getWhiteParticipation().getMe().isReal()) ? g.getWhiteParticipation().getMe() : new PlayerEntry(true);
            PlayerEntry pb = (g.getBlackParticipation().getMe() != null && g.getBlackParticipation().getMe().isReal()) ? g.getBlackParticipation().getMe() : new PlayerEntry(true);
            if (pw.isReal() || pb.isReal())
            {
                ChessGame ng = new ChessGame(ChessCore.data.currentRound, round.getGames().size()+1, pw, pb);
                if (!pw.isReal() || !pb.isReal())
                {
                    ng.setBye(true);
                }
                ng.startGame();
                round.addGame(ng);
            }
        }

        return round;
    }

    private static class LeagueChooser extends JPanel
    {
        final ChooseTable ct;

        final LeftEditor chooser;

        public LeagueChooser()
        {
            super(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();

            ct = new ChooseTable();
            chooser = new LeftEditor(((ChooseTableModel) ct.getModel()));

            ct.getColumn("White").setCellEditor(chooser);
            ct.getColumn("Black").setCellEditor(chooser);

            gbc.gridy = 0;
            gbc.gridx = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.BOTH;

            this.add(new JScrollPane(ct), gbc);

            JPanel buttons = new JPanel(new GridBagLayout());
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridx = 0;
            gbc2.gridy = 0;
            gbc2.weightx = 1;
            gbc2.weighty = 1;
            gbc2.fill = GridBagConstraints.BOTH;
            final JButton insert = new JButton("Add Game");
            buttons.add(insert, gbc2);
            gbc2.gridx = 1;
            final JButton delete = new JButton("Delete Game");
            buttons.add(delete, gbc2);

            gbc.gridy = 1;
            gbc.weighty = 0;

            this.add(buttons, gbc);

            insert.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (ct.getRowCount() < ChessCore.data.settings.boardsNum)
                    {
                        ((ChooseTableModel) ct.getModel()).addRow();
                        ct.changeSelection(ct.convertRowIndexToView(((DynamicTableModel) ct.getModel()).getData().size() - 1), 0, false, false);
                    }
                }
            });

            delete.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    ((ChooseTableModel) ct.getModel()).removeRow(ct.convertRowIndexToModel(ct.getSelectedRow()));
                }
            });
        }
    }

    private static class LeftEditor extends DefaultCellEditor
    {
        ChooseTableModel ctm;

        public LeftEditor(ChooseTableModel ctm)
        {
            super(new JComboBox());
            this.ctm = ctm;
        }


        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            ArrayList chosenPlayers = new ArrayList<PlayerEntry>();
            for (ChessGame g : ((ArrayList<ChessGame>) ctm.getData()))
            {
                if (g != null)
                {
                    if (g.getWhiteParticipation().getMe() != null && g.getWhiteParticipation().getMe().isReal())
                    {
                        chosenPlayers.add(g.getWhiteParticipation().getMe());
                    }
                    if (g.getBlackParticipation().getMe() != null && g.getBlackParticipation().getMe().isReal())
                    {
                        chosenPlayers.add(g.getBlackParticipation().getMe());
                    }
                }
            }
            ((JComboBox) editorComponent).removeAllItems();
            for (PlayerEntry pe : ChessCore.data.playerEntries)
            {
                if (!(chosenPlayers.contains(pe)) && pe.isPresent())
                {
                    ((JComboBox) editorComponent).addItem(pe);
                }
            }

            if (((PlayerEntry) ctm.getValueAt(row, column)).isReal())
            {
                ((JComboBox) editorComponent).addItem(ctm.getValueAt(row, column));
            }

            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
        }
    }

    private static class ChooseTable extends DynamicTable
    {
        public ChooseTable()
        {
            super(new ChooseTableModel());

            this.getColumn("Board").setMaxWidth(50);
        }
    }

    private static class ChooseTableModel extends DynamicTableModel
    {
        public ChooseTableModel()
        {
            super(new ArrayList<ChessGame>(), new String[] {"Board", "White", "Black"}, new Class[] {Integer.class, PlayerEntry.class, PlayerEntry.class});
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            ChessGame row = ((ChessGame) data.get(rowIndex));
            switch (columnIndex)
            {
                case 0:
                    return rowIndex + 1;
                case 1:
                    return row.getWhiteParticipation().getMe();
                case 2:
                    return row.getBlackParticipation().getMe();
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return columnIndex != 0;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        {
            ChessGame row = ((ChessGame) data.get(rowIndex));
            switch (columnIndex)
            {
                case 1:
                    row.setWhitePlayer(((PlayerEntry) aValue));
                    break;
                case 2:
                    row.setBlackPlayer(((PlayerEntry) aValue));
                    break;
            }
            fireTableDataChanged();
        }

        @Override
        public void addRow()
        {
            data.add(new ChessGame(ChessCore.data.currentRound));
            fireTableDataChanged();
        }
    }
}
