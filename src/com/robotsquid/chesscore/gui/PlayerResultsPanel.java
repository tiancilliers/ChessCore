package com.robotsquid.chesscore.gui;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.entry.SchoolEntry;
import com.robotsquid.chesscore.entry.TableDate;
import com.robotsquid.chesscore.game.PlayerParticipation;
import com.robotsquid.chesscore.gui.tables.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PlayerResultsPanel extends DynamicPanel
{
    final JButton filter;
    final PlayerResultsTable players;
    final JButton sort;
    final JButton print;
    final GamesTable games;

    public static HashMap<Present, Boolean> presentMap = new HashMap() {{put(Present.YES, true);put(Present.NO, true);}};
    public static HashMap<Integer, Boolean> yearBornMap = new HashMap<>();
    public static HashMap<PlayerEntry.Gender, Boolean> genderMap = new HashMap() {{put(PlayerEntry.Gender.MALE, true); put(PlayerEntry.Gender.FEMALE, true); put(PlayerEntry.Gender.OTHER, true);}};
    public static HashMap<SchoolEntry, Boolean> schoolMap = new HashMap<>();

    public PlayerResultsPanel()
    {
        super();

        GridBagConstraints gbc = new GridBagConstraints();

        filter = new JButton("Filter Players");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;

        this.add(filter, gbc);

        players = new PlayerResultsTable();

        gbc.gridy = 1;
        gbc.weighty = 1;

        this.add(new JScrollPane(players), gbc);

        sort = new JButton("Default Sort");

        gbc.weighty = 0;
        gbc.gridy = 2;

        this.add(sort, gbc);

        print = new JButton("Print");

        gbc.gridy = 3;

        this.add(print, gbc);

        games = new GamesTable();

        gbc.weighty = 1;
        gbc.gridy = 4;

        this.add(new JScrollPane(games), gbc);

        filter.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FilterPanel fp = new FilterPanel();
                JOptionPane.showMessageDialog(PlayerResultsPanel.this, fp, "Filter Players", JOptionPane.PLAIN_MESSAGE);
                ((PlayerResultsTableModel) players.getModel()).update();

            }
        });

        sort.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                players.defaultSort();
            }
        });

        print.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    players.print();
                }
                catch (PrinterException e1) {}
            }
        });

        ((DefaultRowSorter) players.getRowSorter()).setRowFilter(new javax.swing.RowFilter()
        {
            @Override
            public boolean include(Entry entry)
            {
                boolean present = presentMap.get(Present.fromBoolean((Boolean) entry.getValue(2)));
                boolean year = (((TableDate) entry.getValue(4)).getData() == null || !yearBornMap.containsKey(dateToCalendar(((TableDate) entry.getValue(4)).getData()).get(Calendar.YEAR))) ? true : yearBornMap.get(dateToCalendar(((TableDate) entry.getValue(4)).getData()).get(Calendar.YEAR));
                boolean gender = entry.getValue(5) == null ? true : genderMap.get(((PlayerEntry.Gender) entry.getValue(5)));
                boolean school = (entry.getValue(1) == null || !schoolMap.containsKey((SchoolEntry) entry.getValue(1))) ? true : schoolMap.get(((SchoolEntry) entry.getValue(1)));

                return present && year && gender && school;
            }
        });

        players.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                ArrayList data = new ArrayList();
                if (players.getSelectedRow() != -1)
                {
                    for (PlayerParticipation pp : ((PlayerEntry) ((DynamicTableModel) players.getModel()).getData().get(players.convertRowIndexToModel(players.getSelectedRow()))).gamesPlayed)
                    {
                        data.add(pp);
                    }
                    ((DynamicTableModel) games.getModel()).setData(data);
                }
                else
                {
                    ((DynamicTableModel) games.getModel()).setData(data);
                }
                ((DynamicTableModel) games.getModel()).fireTableDataChanged();
            }
        });
    }

    private Calendar dateToCalendar(Date d)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c;
    }

    @Override
    public void guiEventOccured(GUIEvent e)
    {
        //update stuff
        if (e.getCode() == GUIAction.PLAYER_ENTRIES_CHANGED)
        {
            ((DynamicTableModel) players.getModel()).setData(ChessCore.data.playerEntries);

            yearBornMap.clear();

            Calendar c = Calendar.getInstance();
            for (PlayerEntry pe : ChessCore.data.playerEntries)
            {
                if (pe.getBirth() != null)
                {
                    c.setTime(pe.getBirth());
                    if (!yearBornMap.containsKey(c.get(Calendar.YEAR)))
                    {
                        yearBornMap.put(c.get(Calendar.YEAR), true);
                    }
                }
            }
        }
        if (e.getCode() == GUIAction.IMPORTED)
        {
            ((DynamicTableModel) players.getModel()).setData(ChessCore.data.playerEntries);
        }
        if (e.getCode() == GUIAction.GAMES_UPDATED)
        {
            ArrayList data = new ArrayList();
            if (players.getSelectedRow() != -1)
            {
                for (PlayerParticipation pp : ((PlayerEntry) ((DynamicTableModel) players.getModel()).getData().get(players.convertRowIndexToModel(players.getSelectedRow()))).gamesPlayed)
                {
                    if (!pp.getOpponent().getName().equals(""))
                    {
                        data.add(pp);
                    }
                }
                ((DynamicTableModel) games.getModel()).setData(data);
            }
            else
            {
                ((DynamicTableModel) games.getModel()).setData(data);
            }
            ((DynamicTableModel) games.getModel()).fireTableDataChanged();
        }
        if (e.getCode() == GUIAction.SCHOOL_ENTRIES_CHANGED)
        {
            schoolMap.clear();
            for (SchoolEntry school : ChessCore.data.schoolEntries)
            {
                if (!schoolMap.containsKey(school))
                {
                    schoolMap.put(school, true);
                }
            }
        }
    }

    public class FilterPanel extends JPanel
    {
        final JMultipleSelector school;
        final JMultipleSelector participating;
        final JMultipleSelector yearBorn;
        final JMultipleSelector gender;

        public FilterPanel()
        {
            super();

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridy = 0;
            gbc.gridx = 0;
            gbc.weighty = 0;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.BOTH;

            school = new JMultipleSelector("Team", PlayerResultsPanel.schoolMap);

            this.add(new JScrollPane(school), gbc);

            gbc.gridx = 1;

            participating = new JMultipleSelector("Present", PlayerResultsPanel.presentMap);

            this.add(new JScrollPane(participating), gbc);

            gbc.gridx = 2;

            yearBorn = new JMultipleSelector("Year Born", PlayerResultsPanel.yearBornMap);

            this.add(new JScrollPane(yearBorn), gbc);

            gbc.gridx = 3;

            gender = new JMultipleSelector("Gender", PlayerResultsPanel.genderMap);

            this.add(new JScrollPane(gender), gbc);
        }
    }

    public class JMultipleSelector extends JTable
    {
        public JMultipleSelector(String title, HashMap data)
        {
            super(new SelectorTableModel(title, data));

            this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            this.setColumnSelectionAllowed(false);
            this.getTableHeader().setReorderingAllowed(false);
            this.getTableHeader().setResizingAllowed(false);

            this.gridColor = Color.white;

            this.getColumn(title).setPreferredWidth(175);
            this.getColumn("").setPreferredWidth(50);

            this.setPreferredScrollableViewportSize(new Dimension(225, 250));
        }
    }

    public class SelectorTableModel extends AbstractTableModel
    {
        protected HashMap data;
        private String title;

        private ArrayList rows;

        public SelectorTableModel(String title, HashMap data)
        {
            this.data = data;
            this.title = title;

            rows = new ArrayList(data.keySet());
        }

        @Override
        public int getRowCount()
        {
            return data.size();
        }

        @Override
        public int getColumnCount()
        {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            switch (columnIndex)
            {
                case 0:
                    return rows.get(rowIndex);
                case 1:
                    return data.get(rows.get(rowIndex));
            }
            return null;
        }

        @Override
        public String getColumnName(int column)
        {
            return column == 0 ? title : "";
        }

        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            return columnIndex == 0 ? String.class : Boolean.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return columnIndex != 0;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        {
            if (columnIndex == 1)
            {
                data.put(rows.get(rowIndex), !((boolean) data.get(rows.get(rowIndex))));
                fireTableDataChanged();
            }
        }
    }

    public enum Present
    {
        YES,
        NO;


        @Override
        public String toString()
        {
            switch (this)
            {
                case YES:
                    return "Yes";
                case NO:
                    return "No";
            }
            return "";
        }

        public boolean value()
        {
            switch (this)
            {
                case YES:
                    return true;
                case NO:
                    return false;
            }
            return false;
        }

        public static Present fromBoolean(boolean b)
        {
            return b ? Present.YES : Present.NO;
        }
    }
}
