package com.robotsquid.chesscore.gui;

import com.robotsquid.chesscore.ChessCore;

import javax.swing.*;
import javax.swing.event.EventListenerList;

public class ChessCoreApp extends JFrame implements GUIEventListener
{
    private EventListenerList listeners = new EventListenerList();
    private JTabbedPane tabs = new JTabbedPane();

    public ChessCoreApp()
    {
        //------------------INIT---------------------
        super("ChessCore");

        this.setSize(960, 480);
        this.setIconImage(new ImageIcon(ChessCoreApp.class.getResource("chesscore.png")).getImage());

        //------------SETTINGS-----------------------
        SettingsPanel settingsPanel = new SettingsPanel();
        tabs.addTab("Settings", settingsPanel);

        //------------------SCHOOLS------------------
        SchoolEntryPanel schoolEntryPanel = new SchoolEntryPanel();
        tabs.addTab("Team Entries", schoolEntryPanel);

        //------------------ENTRIES------------------
        PlayerEntryPanel playerEntryPanel = new PlayerEntryPanel();
        tabs.addTab("Player Entries", playerEntryPanel);

        //-------------TOURNAMENT--------------------
        TournamentPanel tournamentPanel = new TournamentPanel();
        tabs.addTab("Tournament", tournamentPanel);

        //-------------PLAYERS-----------------------
        PlayerResultsPanel playerResultsPanel = new PlayerResultsPanel();
        tabs.addTab("Player Results", playerResultsPanel);

        //-------------SCHOOLS-----------------------
        SchoolResultsPanel schoolResultsPanel = new SchoolResultsPanel();
        tabs.add("Team Results", schoolResultsPanel);

        //----------------DISABLE TABS---------------
        tabs.setEnabledAt(tabs.indexOfTab("Team Results"), false);
        tabs.setEnabledAt(tabs.indexOfTab("Player Results"), false);

        //---------------EVENT LISTNERS--------------

        settingsPanel.addGUIListener(this);
        schoolEntryPanel.addGUIListener(this);
        playerEntryPanel.addGUIListener(this);
        tournamentPanel.addGUIListener(this);
        playerResultsPanel.addGUIListener(this);
        schoolResultsPanel.addGUIListener(this);

        this.addGUIListener(settingsPanel);
        this.addGUIListener(schoolEntryPanel);
        this.addGUIListener(playerEntryPanel);
        this.addGUIListener(tournamentPanel);
        this.addGUIListener(playerResultsPanel);
        this.addGUIListener(schoolResultsPanel);

        //----------------FINISH---------------------
        this.add(tabs);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void fireGUIEvent(GUIEvent event)
    {
        Object[] objects = listeners.getListenerList();
        for (int i = 0; i < objects.length; i += 2)
        {
            if (objects[i] == GUIEventListener.class)
            {
                ((GUIEventListener)objects[i+1]).guiEventOccured(event);
            }
        }
    }

    public void addGUIListener(GUIEventListener listener)
    {
        listeners.add(GUIEventListener.class, listener);
    }

    public void removeGUIListener(GUIEventListener listener)
    {
        listeners.remove(GUIEventListener.class, listener);
    }

    @Override
    public void guiEventOccured(GUIEvent e)
    {
        if (e.getCode() == GUIAction.SCHOOL_ENTRIES_CHANGED)
        {
            tabs.setEnabledAt(tabs.indexOfTab("Team Results"), ChessCore.data.schoolEntries.size() > 1);
        }
        if (e.getCode() == GUIAction.PLAYER_ENTRIES_CHANGED)
        {
            tabs.setEnabledAt(tabs.indexOfTab("Player Results"), ChessCore.data.playerEntries.size() > 1);
        }
        fireGUIEvent(e);
    }
}
