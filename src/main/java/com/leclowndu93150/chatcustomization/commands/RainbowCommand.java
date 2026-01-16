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
import com.leclowndu93150.chatcustomization.data.PlayerChatProfile;
import com.leclowndu93150.chatcustomization.manager.ChatManager;
import com.leclowndu93150.chatcustomization.util.ColorUtil;
import com.leclowndu93150.chatcustomization.util.Permissions;
import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Supplier;

public class RainbowCommand extends AbstractPlayerCommand {
    private static final Set<String> VALID_TARGETS = Set.of("prefix", "name", "pronouns", "suffix", "message");

    private final ChatManager chatManager;
    private final Supplier<ChatCustomizationConfig> configSupplier;
    private final RequiredArg<String> targetArg = this.withRequiredArg("target", "prefix, name, pronouns, suffix, or message", ArgTypes.STRING);
    private final DefaultArg<String> valueArg = this.withDefaultArg("value", "on or off (toggles if not specified)", ArgTypes.STRING, "", "");

    public RainbowCommand(@Nonnull ChatManager chatManager, @Nonnull Supplier<ChatCustomizationConfig> configSupplier) {
        super("rainbow", "Enable rainbow colors for any chat element");
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
        if (config.getRequirePermissionForRainbow() && !Permissions.hasPermission(playerRef, config.getPermissionRainbow())) {
            context.sendMessage(Message.raw("You don't have permission to use this command.").color("#FF5555"));
            return;
        }

        String target = targetArg.get(context).toLowerCase();
        String value = valueArg.get(context).toLowerCase();

        if (!VALID_TARGETS.contains(target)) {
            context.sendMessage(Message.raw("Target must be: prefix, name, pronouns, suffix, or message.").color("RED"));
            return;
        }

        PlayerChatProfile profile = chatManager.getProfile(playerRef.getUuid());
        boolean currentValue = profile.getStyle(target).rainbow();
        boolean newValue;

        if (value.isEmpty()) {
            newValue = !currentValue;
        } else if (value.equals("on") || value.equals("true") || value.equals("yes")) {
            newValue = true;
        } else if (value.equals("off") || value.equals("false") || value.equals("no")) {
            newValue = false;
        } else {
            context.sendMessage(Message.raw("Value must be 'on' or 'off'.").color("RED"));
            return;
        }

        chatManager.setRainbow(playerRef.getUuid(), target, newValue);

        String status = newValue ? "enabled" : "disabled";
        context.sendMessage(Message.raw("Rainbow " + status + " for your " + target + ".").color("GREEN"));

        if (newValue) {
            Message preview = ColorUtil.createRainbowText("Rainbow!", false, false, false);
            context.sendMessage(Message.raw("Preview: ").insert(preview));
        }
    }
}
