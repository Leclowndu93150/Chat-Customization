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

public class SuffixCommand extends AbstractPlayerCommand {
    private final ChatManager chatManager;
    private final int maxLength;
    private final DefaultArg<String> suffixArg = this.withDefaultArg("suffix", "Your chat suffix (leave empty to clear)", ArgTypes.STRING, "", "");

    public SuffixCommand(@Nonnull ChatManager chatManager, int maxLength) {
        super("suffix", "Set your chat suffix");
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
        String suffix = suffixArg.get(context);

        if (suffix.isEmpty()) {
            chatManager.setSuffix(playerRef.getUuid(), null);
            context.sendMessage(Message.raw("Your suffix has been cleared."));
            return;
        }

        if (suffix.length() > maxLength) {
            context.sendMessage(Message.raw(String.format("Suffix cannot exceed %d characters.", maxLength)));
            return;
        }

        chatManager.setSuffix(playerRef.getUuid(), suffix);
        context.sendMessage(Message.raw("Your suffix has been set to: [" + suffix + "]"));
    }
}
