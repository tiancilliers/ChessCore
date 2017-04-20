package com.robotsquid.chesscore.gui.tables;

import com.robotsquid.chesscore.game.PlayerResult;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class TournamentTable extends DynamicTable
{
    public TournamentTable()
    {
        super(new TournamentTableModel());

        JComboBox<PlayerResult> values = new JComboBox<PlayerResult>(PlayerResult.values());
        this.getColumn("Result White").setCellEditor(new DefaultCellEditor(values));
        this.getColumn("Result Black").setCellEditor(new DefaultCellEditor(values));

        this.getColumn("Board").setMaxWidth(50);
        this.getColumn("Result White").setMaxWidth(100);
        this.getColumn("Result Black").setMaxWidth(100);

        this.getColumn("Score White").setMaxWidth(100);
        this.getColumn("Score Black").setMaxWidth(100);

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(JLabel.RIGHT);

        this.getColumn("Result White").setCellRenderer(render);
        this.getColumn("Result Black").setCellRenderer(render);
    }
}
