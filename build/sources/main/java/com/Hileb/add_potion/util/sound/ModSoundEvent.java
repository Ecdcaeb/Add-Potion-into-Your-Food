package com.Hileb.add_potion.util.sound;

import com.Hileb.add_potion.util.ModSoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class ModSoundEvent extends SoundEvent
{

    public ModSoundEvent(String path) {
        super(new ResourceLocation("add_potion", path));
        ModSoundHandler.SOUNDS.add(this);
        this.setRegistryName(path);
    }
}
