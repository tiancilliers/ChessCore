package com.robotsquid.chesscore.gui.tables;

import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.awt.*;

public class DynamicTable extends JTable
{
    public DynamicTable(DynamicTableModel model)
    {
        super(model);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setColumnSelectionAllowed(false);
        this.getTableHeader().setReorderingAllowed(false);
        this.getTableHeader().setResizingAllowed(false);
        this.setAutoCreateRowSorter(true);

        this.gridColor = Color.white;
    }
}
