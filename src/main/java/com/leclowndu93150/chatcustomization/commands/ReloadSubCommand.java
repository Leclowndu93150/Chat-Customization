package com.leclowndu93150.chatcustomization.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import com.leclowndu93150.chatcustomization.config.ChatCustomizationConfig;
import com.leclowndu93150.chatcustomization.manager.PresetManager;
import com.leclowndu93150.chatcustomization.util.Permissions;
import javax.annotation.Nonnull;

public class ReloadSubCommand extends AbstractPlayerCommand {
    private final PresetManager presetManager;
    private final Config<ChatCustomizationConfig> config;

    public ReloadSubCommand(@Nonnull PresetManager presetManager, @Nonnull Config<ChatCustomizationConfig> config) {
        super("reload", "Reload configuration and presets");
        this.presetManager = presetManager;
        this.config = config;
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
                          @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        if (!Permissions.canReload(playerRef)) {
            context.sendMessage(Message.raw("You don't have permission to reload configuration.").color("#FF5555"));
            return;
        }

        try {
            config.load().join();
            presetManager.reload();
            context.sendMessage(Message.raw("ChatCustomization configuration reloaded!").color("#55FF55"));
        } catch (Exception e) {
            context.sendMessage(Message.raw("Error reloading configuration: " + e.getMessage()).color("#FF5555"));
        }
    }
}
