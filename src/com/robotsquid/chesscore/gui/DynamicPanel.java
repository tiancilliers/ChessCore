package com.robotsquid.chesscore.gui;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;

public abstract class DynamicPanel extends JPanel implements GUIEventListener
{
    private EventListenerList listeners = new EventListenerList();

    public DynamicPanel()
    {
        super(new GridBagLayout());
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
}
