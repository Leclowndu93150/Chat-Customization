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
import com.leclowndu93150.chatcustomization.util.ColorUtil;
import javax.annotation.Nonnull;

public class SuffixColorCommand extends AbstractPlayerCommand {
    private final ChatManager chatManager;
    private final DefaultArg<String> colorArg = this.withDefaultArg("color", "Color name or hex code (e.g. RED, #FF0000)", ArgTypes.STRING, "", "");

    public SuffixColorCommand(@Nonnull ChatManager chatManager) {
        super("suffixcolor", "Set the color of your suffix in chat");
        this.chatManager = chatManager;
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
                          @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        String color = colorArg.get(context);

        if (color.isEmpty() || color.equalsIgnoreCase("clear")) {
            chatManager.setColor(playerRef.getUuid(), "suffix", null);
            context.sendMessage(Message.raw("Your suffix color has been reset to default.").color("GREEN"));
            return;
        }

        if (!ColorUtil.isValidColor(color)) {
            context.sendMessage(Message.raw("Invalid color. Use a color name or hex code (#FF0000).").color("RED"));
            context.sendMessage(Message.raw("Use /colors to see available colors."));
            return;
        }

        chatManager.setColor(playerRef.getUuid(), "suffix", color.toUpperCase());
        String hex = ColorUtil.toHex(color);
        context.sendMessage(Message.raw("Your suffix color has been set to: ").color("GREEN").insert(Message.raw(color.toUpperCase()).color(hex)));
    }
}
