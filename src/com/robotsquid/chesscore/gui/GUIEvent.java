package com.robotsquid.chesscore.gui;

import javax.swing.*;
import java.util.EventObject;

public class GUIEvent extends EventObject
{
    private final JComponent componentChanged;
    private final GUIAction code;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public GUIEvent(Object source, JComponent component, GUIAction action)
    {
        super(source);
        componentChanged = component;
        code = action;
    }

    public JComponent getComponentChanged()
    {
        return componentChanged;
    }

    public GUIAction getCode()
    {
        return code;
    }
}
