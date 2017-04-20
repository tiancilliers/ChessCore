package com.robotsquid.chesscore.gui;

import java.util.EventListener;

public interface GUIEventListener extends EventListener
{
    public void guiEventOccured(GUIEvent e);
}
