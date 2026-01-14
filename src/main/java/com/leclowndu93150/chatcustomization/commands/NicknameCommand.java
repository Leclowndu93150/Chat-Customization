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

public class NicknameCommand extends AbstractPlayerCommand {
    private final ChatManager chatManager;
    private final int maxLength;
    private final DefaultArg<String> nicknameArg = this.withDefaultArg("nickname", "Your nickname (leave empty to use real name)", ArgTypes.STRING, "", "");

    public NicknameCommand(@Nonnull ChatManager chatManager, int maxLength) {
        super("nickname", "Set your chat nickname");
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
        String nickname = nicknameArg.get(context);

        if (nickname.isEmpty()) {
            chatManager.setNickname(playerRef.getUuid(), null);
            context.sendMessage(Message.raw("Your nickname has been cleared. Using your real name now."));
            return;
        }

        if (nickname.length() > maxLength) {
            context.sendMessage(Message.raw(String.format("Nickname cannot exceed %d characters.", maxLength)));
            return;
        }

        chatManager.setNickname(playerRef.getUuid(), nickname);
        context.sendMessage(Message.raw("Your nickname has been set to: " + nickname));
    }
}
