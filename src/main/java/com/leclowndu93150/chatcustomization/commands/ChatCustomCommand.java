package com.leclowndu93150.chatcustomization.commands;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import com.hypixel.hytale.server.core.util.Config;
import com.leclowndu93150.chatcustomization.config.ChatCustomizationConfig;
import com.leclowndu93150.chatcustomization.manager.PresetManager;
import javax.annotation.Nonnull;

public class ChatCustomCommand extends AbstractCommandCollection {
    public ChatCustomCommand(@Nonnull PresetManager presetManager, @Nonnull Config<ChatCustomizationConfig> config) {
        super("chatcustom", "ChatCustomization admin commands");
        this.addAliases("cc");
        this.addSubCommand(new PresetSubCommand(presetManager));
        this.addSubCommand(new ReloadSubCommand(presetManager, config));
    }
}
