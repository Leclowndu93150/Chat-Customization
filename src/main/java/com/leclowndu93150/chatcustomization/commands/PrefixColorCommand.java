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
import com.leclowndu93150.chatcustomization.util.ColorUtil;
import com.leclowndu93150.chatcustomization.util.Permissions;
import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class PrefixColorCommand extends AbstractPlayerCommand {
    private final ChatManager chatManager;
    private final Supplier<ChatCustomizationConfig> configSupplier;
    private final RequiredArg<String> colorArg = this.withRequiredArg("color", "Color name or hex code (e.g. RED, #FF0000)", ArgTypes.STRING);

    public PrefixColorCommand(@Nonnull ChatManager chatManager, @Nonnull Supplier<ChatCustomizationConfig> configSupplier) {
        super("prefixcolor", "Set the color of your prefix in chat");
        this.chatManager = chatManager;
        this.configSupplier = configSupplier;
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
                          @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        ChatCustomizationConfig config = configSupplier.get();
        if (config.getRequirePermissionForColors() && !Permissions.hasPermission(playerRef, config.getPermissionColors())) {
            context.sendMessage(Message.raw("You don't have permission to use this command.").color("#FF5555"));
            return;
        }

        String color = colorArg.get(context);

        if (!ColorUtil.isValidColor(color)) {
            context.sendMessage(Message.raw("Invalid color. Use a color name or hex code (#FF0000).").color("RED"));
            context.sendMessage(Message.raw("Use /colors to see available colors."));
            return;
        }

        chatManager.setColor(playerRef.getUuid(), "prefix", color.toUpperCase());
        String hex = ColorUtil.toHex(color);
        context.sendMessage(Message.raw("Your prefix color has been set to: ").color("GREEN").insert(Message.raw(color.toUpperCase()).color(hex)));
    }
}
