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

public class SuffixCommand extends AbstractPlayerCommand {
    private final ChatManager chatManager;
    private final Supplier<ChatCustomizationConfig> configSupplier;
    private final int maxLength;
    private final RequiredArg<String> suffixArg = this.withRequiredArg("suffix", "Your chat suffix (use \"quotes\" for spaces)", ArgTypes.STRING);

    public SuffixCommand(@Nonnull ChatManager chatManager, @Nonnull Supplier<ChatCustomizationConfig> configSupplier, int maxLength) {
        super("suffix", "Set your chat suffix");
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
        if (config.getRequirePermissionForSuffix() && !Permissions.hasPermission(playerRef, config.getPermissionSuffix())) {
            context.sendMessage(Message.raw("You don't have permission to use this command.").color("#FF5555"));
            return;
        }

        String suffix = ArgumentParser.stripQuotes(suffixArg.get(context));

        if (suffix.length() > maxLength) {
            context.sendMessage(Message.raw(String.format("Suffix cannot exceed %d characters.", maxLength)));
            return;
        }

        chatManager.setSuffix(playerRef.getUuid(), suffix);
        context.sendMessage(Message.raw("Your suffix has been set to: [" + suffix + "]"));
    }
}
