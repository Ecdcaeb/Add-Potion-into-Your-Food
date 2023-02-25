package com.Hileb.add_potion.gui.potion.expOne;

import net.minecraftforge.fml.common.eventhandler.Event;

public class ProcessEvent extends Event {
    ProcessMessage message;
    @Override
    public boolean isCancelable() {
        return false;
    }
    public ProcessEvent(ProcessMessage messageIn){message=messageIn;}
}
