package com.leclowndu93150.chatcustomization.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
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
import java.util.Set;
import java.util.function.Supplier;

public class GradientCommand extends AbstractPlayerCommand {
    private static final Set<String> VALID_TARGETS = Set.of("prefix", "name", "pronouns", "suffix", "message");

    private final ChatManager chatManager;
    private final Supplier<ChatCustomizationConfig> configSupplier;
    private final RequiredArg<String> targetArg = this.withRequiredArg("target", "prefix, name, pronouns, suffix, or message", ArgTypes.STRING);
    private final DefaultArg<String> startColorArg = this.withDefaultArg("start", "Start color (or 'clear')", ArgTypes.STRING, "", "");
    private final DefaultArg<String> endColorArg = this.withDefaultArg("end", "End color", ArgTypes.STRING, "", "");

    public GradientCommand(@Nonnull ChatManager chatManager, @Nonnull Supplier<ChatCustomizationConfig> configSupplier) {
        super("gradient", "Set a color gradient for any chat element");
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
        if (config.getRequirePermissionForGradient() && !Permissions.hasPermission(playerRef, config.getPermissionGradient())) {
            context.sendMessage(Message.raw("You don't have permission to use this command.").color("#FF5555"));
            return;
        }

        String target = targetArg.get(context).toLowerCase();
        String startColor = startColorArg.get(context);
        String endColor = endColorArg.get(context);

        if (!VALID_TARGETS.contains(target)) {
            context.sendMessage(Message.raw("Target must be: prefix, name, pronouns, suffix, or message.").color("RED"));
            return;
        }

        if (startColor.isEmpty() || startColor.equalsIgnoreCase("clear")) {
            chatManager.setGradient(playerRef.getUuid(), target, null, null);
            context.sendMessage(Message.raw("Gradient cleared for your " + target + ".").color("GREEN"));
            return;
        }

        if (!ColorUtil.isValidColor(startColor)) {
            context.sendMessage(Message.raw("Invalid start color. Use a color name or hex code (#RRGGBB).").color("RED"));
            return;
        }

        if (endColor.isEmpty()) {
            context.sendMessage(Message.raw("You must specify both start and end colors for a gradient.").color("RED"));
            return;
        }

        if (!ColorUtil.isValidColor(endColor)) {
            context.sendMessage(Message.raw("Invalid end color. Use a color name or hex code (#RRGGBB).").color("RED"));
            return;
        }

        String startHex = ColorUtil.toHex(startColor);
        String endHex = ColorUtil.toHex(endColor);

        chatManager.setGradient(playerRef.getUuid(), target, startHex, endHex);

        Message preview = ColorUtil.createGradientText("Sample", startHex, endHex, false, false, false);
        context.sendMessage(Message.raw("Gradient set for your " + target + ": ").color("GREEN").insert(preview));
    }
}
