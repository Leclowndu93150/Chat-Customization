package com.leclowndu93150.chatcustomization.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.leclowndu93150.chatcustomization.manager.ChatManager;
import javax.annotation.Nonnull;

public class PrefixCommand extends AbstractPlayerCommand {
    private final ChatManager chatManager;
    private final int maxLength;
    private final DefaultArg<String> prefixArg = this.withDefaultArg("prefix", "Your chat prefix (leave empty to clear)", ArgTypes.STRING, "", "");

    public PrefixCommand(@Nonnull ChatManager chatManager, int maxLength) {
        super("prefix", "Set your chat prefix");
        this.chatManager = chatManager;
        this.maxLength = maxLength;
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
                          @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        String prefix = prefixArg.get(context);

        if (prefix.isEmpty()) {
            chatManager.setPrefix(playerRef.getUuid(), null);
            context.sendMessage(Message.raw("Your prefix has been cleared."));
            return;
        }

        if (prefix.length() > maxLength) {
            context.sendMessage(Message.raw(String.format("Prefix cannot exceed %d characters.", maxLength)));
            return;
        }

        chatManager.setPrefix(playerRef.getUuid(), prefix);
        context.sendMessage(Message.raw("Your prefix has been set to: [" + prefix + "]"));
    }
}
