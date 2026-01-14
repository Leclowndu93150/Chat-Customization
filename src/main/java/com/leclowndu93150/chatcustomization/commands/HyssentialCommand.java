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
import com.leclowndu93150.chatcustomization.manager.ChatManager;
import javax.annotation.Nonnull;

public class HyssentialCommand extends AbstractPlayerCommand {
    private final ChatManager chatManager;

    private final RequiredArg<String> actionArg = this.withRequiredArg("action", "clear", ArgTypes.STRING);
    private final RequiredArg<String> targetArg = this.withRequiredArg("target", "nickname, prefix, suffix, pronouns, namecolor, msgcolor, prefixcolor, suffixcolor, pronounscolor, style, rainbow, gradient, all", ArgTypes.STRING);

    public HyssentialCommand(@Nonnull ChatManager chatManager) {
        super("hyssential", "Hyssential chat customization commands");
        this.chatManager = chatManager;
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
                          @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        String action = actionArg.get(context).toLowerCase();
        String target = targetArg.get(context).toLowerCase();

        if (!action.equals("clear")) {
            context.sendMessage(Message.raw("Unknown action. Use: /hyssential clear <target>").color("RED"));
            return;
        }

        var uuid = playerRef.getUuid();

        switch (target) {
            case "nickname", "nick", "name" -> {
                chatManager.setNickname(uuid, null);
                context.sendMessage(Message.raw("Your nickname has been cleared.").color("GREEN"));
            }
            case "prefix" -> {
                chatManager.setPrefix(uuid, null);
                context.sendMessage(Message.raw("Your prefix has been cleared.").color("GREEN"));
            }
            case "suffix" -> {
                chatManager.setSuffix(uuid, null);
                context.sendMessage(Message.raw("Your suffix has been cleared.").color("GREEN"));
            }
            case "pronouns" -> {
                chatManager.setPronouns(uuid, null);
                context.sendMessage(Message.raw("Your pronouns have been cleared.").color("GREEN"));
            }
            case "namecolor" -> {
                chatManager.setColor(uuid, "name", null);
                context.sendMessage(Message.raw("Your name color has been cleared.").color("GREEN"));
            }
            case "msgcolor", "messagecolor" -> {
                chatManager.setColor(uuid, "message", null);
                context.sendMessage(Message.raw("Your message color has been cleared.").color("GREEN"));
            }
            case "prefixcolor" -> {
                chatManager.setColor(uuid, "prefix", null);
                context.sendMessage(Message.raw("Your prefix color has been cleared.").color("GREEN"));
            }
            case "suffixcolor" -> {
                chatManager.setColor(uuid, "suffix", null);
                context.sendMessage(Message.raw("Your suffix color has been cleared.").color("GREEN"));
            }
            case "pronounscolor" -> {
                chatManager.setColor(uuid, "pronouns", null);
                context.sendMessage(Message.raw("Your pronouns color has been cleared.").color("GREEN"));
            }
            case "colors" -> {
                chatManager.setColor(uuid, "name", null);
                chatManager.setColor(uuid, "message", null);
                chatManager.setColor(uuid, "prefix", null);
                chatManager.setColor(uuid, "suffix", null);
                chatManager.setColor(uuid, "pronouns", null);
                context.sendMessage(Message.raw("All your colors have been cleared.").color("GREEN"));
            }
            case "style", "styles" -> {
                clearAllStyles(uuid);
                context.sendMessage(Message.raw("All your styles have been cleared.").color("GREEN"));
            }
            case "rainbow" -> {
                clearAllRainbow(uuid);
                context.sendMessage(Message.raw("All your rainbow effects have been cleared.").color("GREEN"));
            }
            case "gradient", "gradients" -> {
                clearAllGradients(uuid);
                context.sendMessage(Message.raw("All your gradients have been cleared.").color("GREEN"));
            }
            case "all" -> {
                chatManager.resetProfile(uuid);
                context.sendMessage(Message.raw("Your entire chat profile has been reset.").color("GREEN"));
            }
            default -> {
                context.sendMessage(Message.raw("Unknown target: " + target).color("RED"));
                context.sendMessage(Message.raw("Valid targets: nickname, prefix, suffix, pronouns, namecolor, msgcolor, prefixcolor, suffixcolor, pronounscolor, colors, style, rainbow, gradient, all").color("YELLOW"));
            }
        }
    }

    private void clearAllStyles(java.util.UUID uuid) {
        String[] targets = {"prefix", "name", "pronouns", "suffix", "message"};
        for (String t : targets) {
            chatManager.setBold(uuid, t, false);
            chatManager.setItalic(uuid, t, false);
            chatManager.setUnderline(uuid, t, false);
        }
    }

    private void clearAllRainbow(java.util.UUID uuid) {
        String[] targets = {"prefix", "name", "pronouns", "suffix", "message"};
        for (String t : targets) {
            chatManager.setRainbow(uuid, t, false);
        }
    }

    private void clearAllGradients(java.util.UUID uuid) {
        String[] targets = {"prefix", "name", "pronouns", "suffix", "message"};
        for (String t : targets) {
            chatManager.setGradient(uuid, t, null, null);
        }
    }
}
