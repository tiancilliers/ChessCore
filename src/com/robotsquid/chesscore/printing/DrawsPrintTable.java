package com.robotsquid.chesscore.printing;

import com.robotsquid.chesscore.gui.tables.DynamicTable;
import com.robotsquid.chesscore.gui.tables.DynamicTableModel;
import com.robotsquid.chesscore.gui.tables.TournamentTable;
import com.robotsquid.chesscore.gui.tables.TournamentTableModel;

import javax.swing.*;
import java.awt.*;

public class DrawsPrintTable extends TournamentTable
{
    public DrawsPrintTable(TournamentTable parent)
    {
        super();
        this.setModel(((TournamentTableModel) parent.getModel()));

        this.removeColumn(this.getColumn("Score White"));
        this.removeColumn(this.getColumn("Score Black"));
        this.removeColumn(this.getColumn("Result White"));
        this.removeColumn(this.getColumn("Result Black"));

        this.setSize(this.getMaximumSize());

        this.getColumn("Board").setMaxWidth(100);

        this.tableHeader.setBackground(Color.white);

        this.setFont(UIManager.getFont("Table.font").deriveFont(16f));
        this.tableHeader.setFont(UIManager.getFont("Table.font").deriveFont(18f));

        this.setRowHeight(20);

        this.gridColor = Color.GRAY;
    }

    public void hackyPrint()
    {
        try
        {
            JFrame frame = new JFrame();
            frame.setSize(800, 500);
            frame.add(new JScrollPane(this));
            frame.setVisible(true);

            this.print(JTable.PrintMode.FIT_WIDTH);

            frame.setVisible(false);
        }
        catch (Exception e)
        {

        }
    }
}
