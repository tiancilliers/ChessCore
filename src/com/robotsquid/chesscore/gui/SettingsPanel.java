package com.robotsquid.chesscore.gui;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.ChessCoreData;
import com.robotsquid.chesscore.ChessCoreSettings;
import com.robotsquid.chesscore.entry.PlayerEntry;
import com.robotsquid.chesscore.entry.SchoolEntry;
import com.robotsquid.chesscore.game.ChessGame;
import com.robotsquid.chesscore.gui.tables.DynamicTable;
import com.robotsquid.chesscore.gui.tables.DynamicTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SettingsPanel extends DynamicPanel
{
    final JButton newButton;
    final JButton saveButton;
    final JButton saveAsButton;
    final JButton openButton;

    final JFileChooser save;

    final JComboBox<ChessCoreSettings.BuchholzOption> buchholzOption;
    final JComboBox<ChessCoreSettings.MatcherOption> matcherOption;
    final JLabel labelDescription;
    final JLabel buchholzDescription;

    final JCheckBox resultsToggle;
    final JCheckBox limitToggle;
    final JSpinner limitSpinner;

    private ResultsFrame resultsWindow;

    public SettingsPanel()
    {
        super();

        GridBagConstraints gbc = new GridBagConstraints();

        final JPanel filePanel = new JPanel(new GridLayout(1, 4));
        filePanel.setBorder(new TitledBorder("Database"));

        newButton = new JButton("New Tournament");

        filePanel.add(newButton);

        saveButton = new JButton("Save");

        filePanel.add(saveButton);

        saveAsButton = new JButton("Save As");

        filePanel.add(saveAsButton);

        openButton = new JButton("Open");

        filePanel.add(openButton);

        save = new JFileChooser();
        save.setFileFilter(new FileNameExtensionFilter("Serialized Data File", "ser"));
        save.setMultiSelectionEnabled(false);
        save.setAcceptAllFileFilterUsed(false);

        newButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ChessCore.data = new ChessCoreData();
                fireGUIEvent(new GUIEvent(newButton, SettingsPanel.this, GUIAction.IMPORTED));
            }
        });

        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                try
                {
                    if (save.getSelectedFile() != null || save.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION)
                    {
                        File toSave = save.getSelectedFile();
                        if (!toSave.getAbsolutePath().endsWith(".ser"))
                        {
                            toSave = new File(save.getSelectedFile() + ".ser");
                        }
                        ChessCoreData tempData = ChessCore.data;
                        FileOutputStream fos = new FileOutputStream(toSave);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(tempData);
                        oos.close();
                        fos.close();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        saveAsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                if (save.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        File toSave = save.getSelectedFile();
                        if (!toSave.getAbsolutePath().endsWith(".ser"))
                        {
                            toSave = new File(save.getSelectedFile() + ".ser");
                        }
                        ChessCoreData tempData = ChessCore.data;
                        FileOutputStream fos = new FileOutputStream(toSave);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(tempData);
                        oos.close();
                        fos.close();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        openButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                try
                {
                    if (save.showOpenDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION)
                    {
                        FileInputStream fis = new FileInputStream(save.getSelectedFile());
                        ObjectInputStream oos = new ObjectInputStream(fis);
                        ChessCore.data = ((ChessCoreData) oos.readObject());
                        oos.close();
                        fis.close();

                        fireGUIEvent(new GUIEvent(SettingsPanel.this, openButton, GUIAction.IMPORTED));
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        final JPanel buchholzSettings = new JPanel(new GridBagLayout());
        buchholzSettings.setBorder(new TitledBorder("Buchholz Settings"));

        GridBagConstraints gbc1 = new GridBagConstraints();

        buchholzOption = new JComboBox<ChessCoreSettings.BuchholzOption>(ChessCoreSettings.BuchholzOption.values());

        buchholzDescription = new JLabel("<html><body style='width:200px'>" + ChessCore.data.settings.buchholzOption.description());
        buchholzDescription.setPreferredSize(new Dimension(260, 50));

        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.weighty = 0;
        gbc1.weightx = 0;
        gbc1.anchor = GridBagConstraints.WEST;
        gbc1.fill = GridBagConstraints.BOTH;

        buchholzSettings.add(buchholzOption, gbc1);

        gbc1.gridy = 1;

        buchholzSettings.add(buchholzDescription, gbc1);

        buchholzOption.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ChessCore.data.settings.buchholzOption = ((ChessCoreSettings.BuchholzOption) buchholzOption.getSelectedItem());
                for (PlayerEntry pe : ChessCore.data.playerEntries)
                {
                    ChessCore.data.settings.buchholzOption.updateBuchholz(pe);
                }

                buchholzDescription.setText("<html><body style='width:200px'>" + ChessCore.data.settings.buchholzOption.description());
                fireGUIEvent(new GUIEvent(SettingsPanel.this, buchholzOption, GUIAction.SCORES_CHANGED));
            }
        });

        final JPanel matcherSettings = new JPanel(new GridBagLayout());
        matcherSettings.setBorder(new TitledBorder("Matcher Settings"));

        gbc1 = new GridBagConstraints();

        matcherOption = new JComboBox<ChessCoreSettings.MatcherOption>(ChessCoreSettings.MatcherOption.values());

        labelDescription = new JLabel("<html><body style='width:200px'>" + ChessCore.data.settings.matcherOption.description());
        labelDescription.setPreferredSize(new Dimension(260, 50));

        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.weighty = 0;
        gbc1.weightx = 0;
        gbc1.anchor = GridBagConstraints.WEST;
        gbc1.fill = GridBagConstraints.BOTH;

        matcherSettings.add(matcherOption, gbc1);

        gbc1.gridy = 1;

        matcherSettings.add(labelDescription, gbc1);

        matcherOption.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ChessCore.data.settings.matcherOption = ((ChessCoreSettings.MatcherOption) matcherOption.getSelectedItem());
                labelDescription.setText("<html><body style='width:200px'>" + ChessCore.data.settings.matcherOption.description());
                //labelDescription.setMaximumSize(new Dimension(200, 200));
                fireGUIEvent(new GUIEvent(SettingsPanel.this, matcherOption, GUIAction.MATCHING_CHANGED));
            }
        });

        JPanel miscSettings = new JPanel(new GridLayout(0, 2));
        miscSettings.setBorder(new TitledBorder("Miscellaneous"));

        resultsToggle = new JCheckBox("Results Window");
        limitToggle = new JCheckBox("Board Limit");
        limitSpinner = new JSpinner(new SpinnerNumberModel(20, 1, 100, 1));
        limitSpinner.setEnabled(false);
        limitToggle.setSelected(false);

        miscSettings.add(resultsToggle);
        miscSettings.add(new JPanel());
        miscSettings.add(limitToggle);
        miscSettings.add(limitSpinner);

        resultsWindow = new ResultsFrame();

        resultsToggle.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resultsWindow.setVisible(resultsToggle.isSelected());
            }
        });

        limitToggle.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ChessCore.data.settings.boardsLimited = limitToggle.isSelected();
                limitSpinner.setEnabled(limitToggle.isSelected());
            }
        });

        limitSpinner.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                ChessCore.data.settings.boardsNum = ((Integer) limitSpinner.getValue());
            }
        });

        resultsWindow.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                resultsToggle.setSelected(false);
            }
        });


        // ADD ALL TO PANEL

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;

        this.add(filePanel, gbc);

        gbc.gridy = 1;

        this.add(buchholzSettings, gbc);

        gbc.gridy = 2;

        this.add(matcherSettings, gbc);

        gbc.gridy = 3;

        this.add(miscSettings, gbc);

        JLabel blank = new JLabel();

        gbc.weighty = 1;
        gbc.gridy = 4;

        this.add(blank, gbc);
    }

    @Override
    public void guiEventOccured(GUIEvent e)
    {
        switch (e.getCode())
        {
            case SCHOOL_ENTRIES_CHANGED:
                break;
            case GAMES_UPDATED:
                break;
            case PLAYER_ENTRIES_CHANGED:
                break;
            case SCORES_CHANGED:
                break;
            case MATCHING_CHANGED:
                break;
            case IMPORTED:
                save.setSelectedFile(ChessCore.data.settings.saveFile);

                buchholzOption.setSelectedItem(ChessCore.data.settings.buchholzOption);
                matcherOption.setSelectedItem(ChessCore.data.settings.matcherOption);

                limitToggle.setSelected(ChessCore.data.settings.boardsLimited);
                limitSpinner.setValue(ChessCore.data.settings.boardsNum);
                limitSpinner.setEnabled(limitToggle.isSelected());

                break;
        }

        resultsWindow.guiEventOccured(e);
    }

    private class ResultsFrame extends JFrame implements GUIEventListener
    {
        final SchoolTable scores;
        final HistoryTable table;

        public ResultsFrame()
        {
            super("Team Results");

            setSize(960, 480);
            setIconImage(new ImageIcon(ChessCoreApp.class.getResource("chesscore.png")).getImage());
            setLayout(new GridLayout(2,1));

            final JPanel tablePanel = new JPanel(new BorderLayout());

            scores = new SchoolTable();

            tablePanel.addComponentListener(new ComponentAdapter()
            {
                @Override
                public void componentResized(ComponentEvent e)
                {
                    super.componentResized(e);

                    scores.resizeText(tablePanel.getHeight());
                }
            });

            tablePanel.add(scores.getTableHeader(), BorderLayout.NORTH);
            tablePanel.add(scores, BorderLayout.CENTER);

            this.add(tablePanel);

            table = new HistoryTable();
            final JScrollPane tablePane = new JScrollPane(table);
            ((DynamicTableModel) table.getModel()).addTableModelListener(new TableModelListener()
            {
                @Override
                public void tableChanged(TableModelEvent e)
                {
                    tablePane.getVerticalScrollBar().setValue(tablePane.getVerticalScrollBar().getMaximum());
                }
            });
            /*tablePane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
            {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e)
                {
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                }
            });*/
            this.add(tablePane);

            setDefaultCloseOperation(HIDE_ON_CLOSE);
        }

        @Override
        public void guiEventOccured(GUIEvent e)
        {
            switch (e.getCode())
            {
                case SCHOOL_ENTRIES_CHANGED:
                    ((SchoolTableModel) scores.getModel()).update();
                    break;
                case GAMES_UPDATED:
                    break;
                case PLAYER_ENTRIES_CHANGED:
                    break;
                case SCORES_CHANGED:
                    ((DynamicTableModel) table.getModel()).fireTableDataChanged();
                    ((SchoolTableModel) scores.getModel()).fireTableDataChanged();
                    break;
                case MATCHING_CHANGED:
                    break;
                case IMPORTED:
                    ((DynamicTableModel) table.getModel()).setData(ChessCore.data.historyGames);
                    ((SchoolTableModel) scores.getModel()).update();
                    break;
            }
        }

        private class SchoolTable extends JTable
        {
            final ScoreCellRenderer cellRenderer;
            final ScoreCellRenderer headerRenderer;

            public SchoolTable()
            {
                super(new SchoolTableModel());
                this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                this.setFillsViewportHeight(true);
                this.setCellSelectionEnabled(false);
                this.setRowSelectionAllowed(false);
                this.setColumnSelectionAllowed(false);
                this.getTableHeader().setReorderingAllowed(false);
                this.getTableHeader().setResizingAllowed(false);
                this.gridColor = UIManager.getColor("Panel.background");

                cellRenderer = new ScoreCellRenderer(64f);
                this.setDefaultRenderer(Float.class, cellRenderer);

                headerRenderer = new ScoreCellRenderer(24f);
                this.tableHeader.setDefaultRenderer(headerRenderer);
            }

            public void resizeText(int height)
            {
                //tableHeader.setMinimumSize(new Dimension(tableHeader.getMinimumSize().width, ((int) (height * 0.25f))));
                setRowHeight(((int) ((height * 0.75f))));
                headerRenderer.setFontSize(((int) (height * 0.20f)));
                cellRenderer.setFontSize(getRowHeight());
            }

            private class ScoreCellRenderer extends DefaultTableCellRenderer
            {
                public void setFontSize(float fontSize)
                {
                    this.fontSize = fontSize;
                }

                private float fontSize;

                public ScoreCellRenderer()
                {
                    this(12f);
                }

                public ScoreCellRenderer(float fontSize)
                {
                    this.fontSize = fontSize;
                    this.setHorizontalAlignment(SwingConstants.CENTER);
                }

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
                {
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    if (fontSize == 64f)
                    {
                        setBackground(Color.white);
                    }
                    else
                    {
                        setBackground(UIManager.getColor("Panel.background"));
                    }
                    setFont(UIManager.getFont("Label.font").deriveFont(fontSize));

                    return this;
                }
            }
        }

        private class SchoolTableModel extends AbstractTableModel
        {
            public SchoolTableModel() {}

            @Override
            public String getColumnName(int column)
            {
                return ((SchoolEntry) ChessCore.data.schoolEntries.get(column)).getName();
            }

            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                return Float.class;
            }

            @Override
            public int getRowCount()
            {
                return 1;
            }

            @Override
            public int getColumnCount()
            {
                return ChessCore.data.schoolEntries.size();
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex)
            {
                return ((SchoolEntry) ChessCore.data.schoolEntries.get(columnIndex)).getTotalScore();
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return false;
            }

            public void update()
            {
                fireTableStructureChanged();
                fireTableDataChanged();
            }
        }

        private class HistoryTable extends DynamicTable
        {
            public HistoryTable()
            {
                super(new HistoryTableModel());

                this.getColumn("Board").setMaxWidth(50);
                this.getColumn("Team").setMaxWidth(75);
                this.getColumn("Result").setMaxWidth(100);
                this.getColumn("Team ").setMaxWidth(75);
            }
        }

        private class HistoryTableModel extends DynamicTableModel
        {
            public HistoryTableModel()
            {
                super(ChessCore.data.historyGames, new String[] {"Board", "Team", "White", "Result", "Black", "Team "}, new Class[] {Integer.class, String.class, PlayerEntry.class, String.class, PlayerEntry.class, String.class});
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex)
            {
                ChessGame row = ChessCore.data.historyGames.get(rowIndex);
                switch (columnIndex)
                {
                    case 0:
                        return row.getBoardNum();
                    case 1:
                        return row.getWhiteParticipation().getMe().getSchoolEntry() != null ? row.getWhiteParticipation().getMe().getSchoolEntry().getName() : "";
                    case 2:
                        return row.getWhiteParticipation().getMe().getFullName();
                    case 3:
                        return row.getWhiteParticipation().getResult().comparisonString();
                    case 4:
                        return row.getBlackParticipation().getMe().getFullName();
                    case 5:
                        return row.getBlackParticipation().getMe().getSchoolEntry() != null ? row.getBlackParticipation().getMe().getSchoolEntry().getName() : "";
                }
                return null;
            }
        }
    }
}
