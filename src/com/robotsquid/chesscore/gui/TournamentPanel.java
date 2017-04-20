package com.robotsquid.chesscore.gui;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.game.ChessGame;
import com.robotsquid.chesscore.gui.tables.DynamicTableModel;
import com.robotsquid.chesscore.gui.tables.TournamentTable;
import com.robotsquid.chesscore.gui.tables.TournamentTableModel;
import com.robotsquid.chesscore.printing.DrawsPrintTable;
import com.robotsquid.chesscore.printing.ResultsPrintTable;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TournamentPanel extends DynamicPanel
{
    final JLabel round;
    final JButton redraw;
    final TournamentTable games;
    final JButton draws;
    final JButton results;

    public TournamentPanel()
    {
        super();

        GridBagConstraints gbc = new GridBagConstraints();

        round = new JLabel("TOURNAMENT NOT STARTED");
        round.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;

        this.add(round, gbc);

        redraw = new JButton("Redraw Games");

        gbc.gridy = 1;

        this.add(redraw, gbc);

        games = new TournamentTable();

        gbc.gridy = 2;
        gbc.weighty = 1;

        this.add(new JScrollPane(games), gbc);

        draws = new JButton("Print Draws");
        results = new JButton("Print Result Sheets");

        final JPanel print = new JPanel(new GridLayout(1, 2));
        print.add(draws);
        print.add(results);

        gbc.gridy = 3;
        gbc.weighty = 0;

        this.add(print, gbc);

        games.getModel().addTableModelListener(new TableModelListener()
        {
            @Override
            public void tableChanged(TableModelEvent e)
            {
                fireGUIEvent(new GUIEvent(games, TournamentPanel.this, GUIAction.SCORES_CHANGED));
            }
        });

        redraw.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (JOptionPane.showConfirmDialog(TournamentPanel.this, ChessCore.data.currentRound == 0 ? "Are you sure you want to begin the tournament?" : "Are you sure you would like to end the current round and draw the next round?", "Start round?", JOptionPane.YES_NO_OPTION) == 0)
                {
                    ChessCore.data.rounds.add(ChessCore.data.settings.matcherOption.drawGames());

                    fireGUIEvent(new GUIEvent(TournamentPanel.this, redraw, GUIAction.GAMES_UPDATED));
                };
            }
        });

        draws.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                new DrawsPrintTable(games).hackyPrint();
            }
        });

        results.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                new ResultsPrintTable(games).hackyPrint();
            }
        });
    }

    @Override
    public void guiEventOccured(GUIEvent e)
    {
        switch(e.getCode())
        {
            case GAMES_UPDATED:
                round.setText("Round: " + ChessCore.data.currentRound);
                ((DynamicTableModel) games.getModel()).removeAllRows();
                for (ChessGame g : ChessCore.data.rounds.get(ChessCore.data.rounds.size() - 1).getGames())
                {
                    ((TournamentTableModel) games.getModel()).addRow(g);
                }
                break;
            case IMPORTED:
                round.setText(ChessCore.data.currentRound == 0 ? "TOURNAMENT NOT STARTED" : ("Round: " + ChessCore.data.currentRound));
                if (ChessCore.data.rounds.size() > 0)
                {
                    ((DynamicTableModel) games.getModel()).setData(ChessCore.data.rounds.get(ChessCore.data.rounds.size() - 1).getGames());
                }
                else
                {
                    ((DynamicTableModel) games.getModel()).setData(new ArrayList<ChessGame>());
                }
                break;
        }
    }
}
