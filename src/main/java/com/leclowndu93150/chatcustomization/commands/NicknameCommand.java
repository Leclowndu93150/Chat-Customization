package com.leclowndu93150.chatcustomization.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.leclowndu93150.chatcustomization.config.ChatCustomizationConfig;
import com.leclowndu93150.chatcustomization.manager.ChatManager;
import com.leclowndu93150.chatcustomization.util.ArgumentParser;
import com.leclowndu93150.chatcustomization.util.Permissions;
import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class NicknameCommand extends AbstractPlayerCommand {
    private final ChatManager chatManager;
    private final Supplier<ChatCustomizationConfig> configSupplier;
    private final int maxLength;
    private final RequiredArg<String> nicknameArg = this.withRequiredArg("nickname", "Your nickname (use \"quotes\" for spaces)", ArgTypes.STRING);

    public NicknameCommand(@Nonnull ChatManager chatManager, @Nonnull Supplier<ChatCustomizationConfig> configSupplier, int maxLength) {
        super("nickname", "Set your chat nickname");
        this.chatManager = chatManager;
        this.configSupplier = configSupplier;
        this.maxLength = maxLength;
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
                          @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        ChatCustomizationConfig config = configSupplier.get();
        if (config.getRequirePermissionForNickname() && !Permissions.hasPermission(playerRef, config.getPermissionNickname())) {
            context.sendMessage(Message.raw("You don't have permission to use this command.").color("#FF5555"));
            return;
        }

        String nickname = ArgumentParser.stripQuotes(nicknameArg.get(context));

        if (nickname.length() > maxLength) {
            context.sendMessage(Message.raw(String.format("Nickname cannot exceed %d characters.", maxLength)));
            return;
        }

        chatManager.setNickname(playerRef.getUuid(), nickname);
        context.sendMessage(Message.raw("Your nickname has been set to: " + nickname));
    }
}
