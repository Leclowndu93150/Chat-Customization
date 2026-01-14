package com.leclowndu93150.chatcustomization.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.leclowndu93150.chatcustomization.gui.ChatEditorGui;
import com.leclowndu93150.chatcustomization.manager.ChatManager;
import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ChatEditorCommand extends AbstractAsyncCommand {

    private final ChatManager chatManager;

    public ChatEditorCommand(@Nonnull ChatManager chatManager) {
        super("chateditor", "Open the visual chat customization editor");
        this.addAliases("chatgui", "ce");
        this.setPermissionGroup(GameMode.Adventure);
        this.chatManager = chatManager;
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> executeAsync(CommandContext commandContext) {
        CommandSender sender = commandContext.sender();

        if (sender instanceof Player player) {
            Ref<EntityStore> ref = player.getReference();
            if (ref != null && ref.isValid()) {
                Store<EntityStore> store = ref.getStore();
                World world = store.getExternalData().getWorld();

                return CompletableFuture.runAsync(() -> {
                    PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
                    if (playerRef != null) {
                        player.getPageManager().openCustomPage(ref, store,
                            new ChatEditorGui(playerRef, chatManager));
                    }
                }, world);
            } else {
                commandContext.sendMessage(Message.raw("You must be in a world to use this command.").color("RED"));
                return CompletableFuture.completedFuture(null);
            }
        } else {
            commandContext.sendMessage(Message.raw("This command can only be used by players.").color("RED"));
            return CompletableFuture.completedFuture(null);
        }
    }
}
