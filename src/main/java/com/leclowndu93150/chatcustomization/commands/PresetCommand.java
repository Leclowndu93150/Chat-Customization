package com.leclowndu93150.chatcustomization.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.leclowndu93150.chatcustomization.gui.PlayerPresetSelectorGui;
import com.leclowndu93150.chatcustomization.manager.ChatManager;
import com.leclowndu93150.chatcustomization.manager.PresetManager;
import javax.annotation.Nonnull;

public class PresetCommand extends AbstractPlayerCommand {

    private final PresetManager presetManager;
    private final ChatManager chatManager;

    public PresetCommand(@Nonnull PresetManager presetManager, @Nonnull ChatManager chatManager) {
        super("preset", "View and apply chat presets");
        this.addAliases("presets");
        this.setPermissionGroup(GameMode.Adventure);
        this.presetManager = presetManager;
        this.chatManager = chatManager;
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
                          @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());
        player.getPageManager().openCustomPage(ref, store,
            new PlayerPresetSelectorGui(playerRef, presetManager, chatManager, CustomPageLifetime.CanDismiss));
    }
}
