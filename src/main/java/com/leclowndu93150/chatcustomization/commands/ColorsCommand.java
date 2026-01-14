package com.leclowndu93150.chatcustomization.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.leclowndu93150.chatcustomization.util.ColorUtil;
import javax.annotation.Nonnull;

public class ColorsCommand extends AbstractPlayerCommand {

    public ColorsCommand() {
        super("colors", "Show all available colors");
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
                          @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        context.sendMessage(Message.raw("=== Available Colors ===").color("GOLD"));
        context.sendMessage(Message.raw(""));

        // Show named colors with their actual color
        String[] colorNames = ColorUtil.getColorNames();
        Message colorList = Message.empty();

        for (int i = 0; i < colorNames.length; i++) {
            String name = colorNames[i];
            String hex = ColorUtil.toHex(name);
            Message colorSample = Message.raw(name).color(hex);

            if (i > 0) {
                colorList.insert(Message.raw(", "));
            }
            colorList.insert(colorSample);
        }

        context.sendMessage(colorList);
        context.sendMessage(Message.raw(""));
        context.sendMessage(Message.raw("You can also use hex colors: #RRGGBB").color("GRAY"));
        context.sendMessage(Message.raw("Examples: ").color("GRAY")
            .insert(Message.raw("#FF0000").color("#FF0000"))
            .insert(Message.raw(", "))
            .insert(Message.raw("#00FF00").color("#00FF00"))
            .insert(Message.raw(", "))
            .insert(Message.raw("#0000FF").color("#0000FF"))
            .insert(Message.raw(", "))
            .insert(Message.raw("#FF69B4").color("#FF69B4")));
    }
}
