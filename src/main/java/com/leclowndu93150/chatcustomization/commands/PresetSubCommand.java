package com.leclowndu93150.chatcustomization.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.leclowndu93150.chatcustomization.gui.PresetListGui;
import com.leclowndu93150.chatcustomization.manager.PresetManager;
import com.leclowndu93150.chatcustomization.util.Permissions;
import javax.annotation.Nonnull;

public class PresetSubCommand extends AbstractPlayerCommand {
    private final PresetManager presetManager;

    public PresetSubCommand(@Nonnull PresetManager presetManager) {
        super("preset", "Open the preset management UI");
        this.presetManager = presetManager;
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
                          @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        if (!Permissions.canManagePresets(playerRef)) {
            context.sendMessage(Message.raw("You don't have permission to manage presets.").color("#FF5555"));
            return;
        }

        Player player = store.getComponent(ref, Player.getComponentType());
        player.getPageManager().openCustomPage(ref, store,
            new PresetListGui(playerRef, presetManager, CustomPageLifetime.CanDismiss));
    }
}
