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

public class PronounsCommand extends AbstractPlayerCommand {
    private final ChatManager chatManager;
    private final int maxLength;
    private final DefaultArg<String> pronounsArg = this.withDefaultArg("pronouns", "Your pronouns (e.g. he/him, she/her, they/them)", ArgTypes.STRING, "", "");

    public PronounsCommand(@Nonnull ChatManager chatManager, int maxLength) {
        super("pronouns", "Set your pronouns to display in chat");
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
        String pronouns = pronounsArg.get(context);

        if (pronouns.isEmpty()) {
            chatManager.setPronouns(playerRef.getUuid(), null);
            context.sendMessage(Message.raw("Your pronouns have been cleared."));
            return;
        }

        if (pronouns.length() > maxLength) {
            context.sendMessage(Message.raw(String.format("Pronouns cannot exceed %d characters.", maxLength)));
            return;
        }

        chatManager.setPronouns(playerRef.getUuid(), pronouns);
        context.sendMessage(Message.raw("Your pronouns have been set to: " + pronouns));
    }
}
