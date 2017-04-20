package com.robotsquid.chesscore;

import com.robotsquid.chesscore.gui.ChessCoreApp;

import javax.swing.*;

public class ChessCore
{
    public static JFrame mainGUI;

    public static ChessCoreData data = new ChessCoreData();

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {}
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                mainGUI = new ChessCoreApp();
            }
        });
    }
}
