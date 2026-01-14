package com.leclowndu93150.chatcustomization.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.leclowndu93150.chatcustomization.config.ChatCustomizationConfig;
import com.leclowndu93150.chatcustomization.data.PlayerChatProfile;
import com.leclowndu93150.chatcustomization.manager.ChatManager;
import javax.annotation.Nonnull;
import java.util.UUID;

public class AdminChatCommand extends AbstractPlayerCommand {
    private final ChatManager chatManager;
    private final ChatCustomizationConfig config;

    private final RequiredArg<String> actionArg = this.withRequiredArg("action", "view, reset, setprefix, setsuffix, setnickname, setpronouns, setnamecolor, setmsgcolor, setprefixcolor, setsuffixcolor, setpronounscolor, clearnamecolor, clearmsgcolor, clearprefixcolor, clearsuffixcolor, clearpronounscolor, setstyle, setrainbow, setgradient, clearstyle, clearrainbow, cleargradient", ArgTypes.STRING);
    private final RequiredArg<String> playerArg = this.withRequiredArg("player", "Target player name", ArgTypes.STRING);
    private final DefaultArg<String> valueArg = this.withDefaultArg("value", "Value to set", ArgTypes.STRING, "", "");
    private final DefaultArg<String> value2Arg = this.withDefaultArg("value2", "Additional value", ArgTypes.STRING, "", "");

    public AdminChatCommand(@Nonnull ChatManager chatManager, @Nonnull ChatCustomizationConfig config) {
        super("chatadmin", "Admin commands for managing player chat profiles");
        this.chatManager = chatManager;
        this.config = config;
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
                          @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {

        if (!context.sender().hasPermission(config.getPermissionAdmin())) {
            context.sendMessage(Message.raw("You don't have permission to use this command.").color("RED"));
            return;
        }

        String action = actionArg.get(context).toLowerCase();
        String targetName = playerArg.get(context);
        String value = valueArg.get(context);
        String value2 = value2Arg.get(context);

        PlayerRef targetPlayer = Universe.get().getPlayerByUsername(targetName, NameMatching.STARTS_WITH_IGNORE_CASE);
        if (targetPlayer == null) {
            context.sendMessage(Message.raw("Player '" + targetName + "' not found or offline.").color("RED"));
            return;
        }

        UUID targetUuid = targetPlayer.getUuid();
        PlayerChatProfile profile = chatManager.getProfile(targetUuid);

        switch (action) {
            case "view" -> viewProfile(context, targetName, profile);
            case "reset" -> resetProfile(context, targetName, targetUuid);
            case "setprefix" -> setPrefix(context, targetName, targetUuid, profile, value);
            case "setsuffix" -> setSuffix(context, targetName, targetUuid, profile, value);
            case "setnickname" -> setNickname(context, targetName, targetUuid, profile, value);
            case "setpronouns" -> setPronouns(context, targetName, targetUuid, profile, value);
            case "setnamecolor" -> setNameColor(context, targetName, targetUuid, profile, value);
            case "setmsgcolor" -> setMsgColor(context, targetName, targetUuid, profile, value);
            case "setprefixcolor" -> setPrefixColor(context, targetName, targetUuid, profile, value);
            case "setsuffixcolor" -> setSuffixColor(context, targetName, targetUuid, profile, value);
            case "setpronounscolor" -> setPronounsColor(context, targetName, targetUuid, profile, value);
            case "clearnamecolor" -> clearNameColor(context, targetName, targetUuid);
            case "clearmsgcolor" -> clearMsgColor(context, targetName, targetUuid);
            case "clearprefixcolor" -> clearPrefixColor(context, targetName, targetUuid);
            case "clearsuffixcolor" -> clearSuffixColor(context, targetName, targetUuid);
            case "clearpronounscolor" -> clearPronounsColor(context, targetName, targetUuid);
            case "setstyle" -> setStyle(context, targetName, targetUuid, profile, value, value2);
            case "setrainbow" -> setRainbow(context, targetName, targetUuid, profile, value, value2);
            case "setgradient" -> setGradient(context, targetName, targetUuid, profile, value, value2);
            case "clearstyle" -> clearStyle(context, targetName, targetUuid, profile, value);
            case "clearrainbow" -> clearRainbow(context, targetName, targetUuid, profile, value);
            case "cleargradient" -> clearGradient(context, targetName, targetUuid, profile, value);
            default -> showHelp(context);
        }
    }

    private void viewProfile(CommandContext context, String playerName, PlayerChatProfile profile) {
        context.sendMessage(Message.raw("=== Chat Profile for " + playerName + " ===").color("GOLD"));
        context.sendMessage(Message.raw("Prefix: " + (profile.prefix() != null ? profile.prefix() : "(none)")));
        context.sendMessage(Message.raw("Suffix: " + (profile.suffix() != null ? profile.suffix() : "(none)")));
        context.sendMessage(Message.raw("Nickname: " + (profile.nickname() != null ? profile.nickname() : "(none)")));
        context.sendMessage(Message.raw("Pronouns: " + (profile.pronouns() != null ? profile.pronouns() : "(none)")));
        context.sendMessage(Message.raw("Name Color: " + (profile.nameColor() != null ? profile.nameColor() : "(default)")));
        context.sendMessage(Message.raw("Message Color: " + (profile.messageColor() != null ? profile.messageColor() : "(default)")));
        context.sendMessage(Message.raw("Name Bold: " + profile.nameBold() + ", Italic: " + profile.nameItalic() + ", Underline: " + profile.nameUnderline()));
        context.sendMessage(Message.raw("Message Bold: " + profile.messageBold() + ", Italic: " + profile.messageItalic() + ", Underline: " + profile.messageUnderline()));
        context.sendMessage(Message.raw("Name Rainbow: " + profile.nameRainbow() + ", Gradient: " + profile.hasNameGradient()));
        context.sendMessage(Message.raw("Message Rainbow: " + profile.messageRainbow() + ", Gradient: " + profile.hasMessageGradient()));
    }

    private void resetProfile(CommandContext context, String playerName, UUID targetUuid) {
        chatManager.resetProfile(targetUuid);
        context.sendMessage(Message.raw("Reset chat profile for " + playerName + ".").color("GREEN"));
    }

    private void setPrefix(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String value) {
        if (value.isEmpty()) {
            chatManager.setProfile(targetUuid, profile.withPrefix(null));
            context.sendMessage(Message.raw("Cleared prefix for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setProfile(targetUuid, profile.withPrefix(value));
            context.sendMessage(Message.raw("Set prefix for " + playerName + " to: [" + value + "]").color("GREEN"));
        }
    }

    private void setSuffix(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String value) {
        if (value.isEmpty()) {
            chatManager.setProfile(targetUuid, profile.withSuffix(null));
            context.sendMessage(Message.raw("Cleared suffix for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setProfile(targetUuid, profile.withSuffix(value));
            context.sendMessage(Message.raw("Set suffix for " + playerName + " to: [" + value + "]").color("GREEN"));
        }
    }

    private void setNickname(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String value) {
        if (value.isEmpty()) {
            chatManager.setProfile(targetUuid, profile.withNickname(null));
            context.sendMessage(Message.raw("Cleared nickname for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setProfile(targetUuid, profile.withNickname(value));
            context.sendMessage(Message.raw("Set nickname for " + playerName + " to: " + value).color("GREEN"));
        }
    }

    private void setPronouns(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String value) {
        if (value.isEmpty()) {
            chatManager.setProfile(targetUuid, profile.withPronouns(null));
            context.sendMessage(Message.raw("Cleared pronouns for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setProfile(targetUuid, profile.withPronouns(value));
            context.sendMessage(Message.raw("Set pronouns for " + playerName + " to: " + value).color("GREEN"));
        }
    }

    private void setNameColor(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String value) {
        if (value.isEmpty()) {
            chatManager.setColor(targetUuid, "name", null);
            context.sendMessage(Message.raw("Cleared name color for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setColor(targetUuid, "name", value);
            context.sendMessage(Message.raw("Set name color for " + playerName + " to: " + value).color("GREEN"));
        }
    }

    private void setMsgColor(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String value) {
        if (value.isEmpty()) {
            chatManager.setColor(targetUuid, "message", null);
            context.sendMessage(Message.raw("Cleared message color for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setColor(targetUuid, "message", value);
            context.sendMessage(Message.raw("Set message color for " + playerName + " to: " + value).color("GREEN"));
        }
    }

    private void setPrefixColor(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String value) {
        if (value.isEmpty()) {
            chatManager.setColor(targetUuid, "prefix", null);
            context.sendMessage(Message.raw("Cleared prefix color for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setColor(targetUuid, "prefix", value);
            context.sendMessage(Message.raw("Set prefix color for " + playerName + " to: " + value).color("GREEN"));
        }
    }

    private void setSuffixColor(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String value) {
        if (value.isEmpty()) {
            chatManager.setColor(targetUuid, "suffix", null);
            context.sendMessage(Message.raw("Cleared suffix color for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setColor(targetUuid, "suffix", value);
            context.sendMessage(Message.raw("Set suffix color for " + playerName + " to: " + value).color("GREEN"));
        }
    }

    private void setPronounsColor(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String value) {
        if (value.isEmpty()) {
            chatManager.setColor(targetUuid, "pronouns", null);
            context.sendMessage(Message.raw("Cleared pronouns color for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setColor(targetUuid, "pronouns", value);
            context.sendMessage(Message.raw("Set pronouns color for " + playerName + " to: " + value).color("GREEN"));
        }
    }

    private void clearNameColor(CommandContext context, String playerName, UUID targetUuid) {
        chatManager.setColor(targetUuid, "name", null);
        context.sendMessage(Message.raw("Cleared name color for " + playerName + ".").color("GREEN"));
    }

    private void clearMsgColor(CommandContext context, String playerName, UUID targetUuid) {
        chatManager.setColor(targetUuid, "message", null);
        context.sendMessage(Message.raw("Cleared message color for " + playerName + ".").color("GREEN"));
    }

    private void clearPrefixColor(CommandContext context, String playerName, UUID targetUuid) {
        chatManager.setColor(targetUuid, "prefix", null);
        context.sendMessage(Message.raw("Cleared prefix color for " + playerName + ".").color("GREEN"));
    }

    private void clearSuffixColor(CommandContext context, String playerName, UUID targetUuid) {
        chatManager.setColor(targetUuid, "suffix", null);
        context.sendMessage(Message.raw("Cleared suffix color for " + playerName + ".").color("GREEN"));
    }

    private void clearPronounsColor(CommandContext context, String playerName, UUID targetUuid) {
        chatManager.setColor(targetUuid, "pronouns", null);
        context.sendMessage(Message.raw("Cleared pronouns color for " + playerName + ".").color("GREEN"));
    }

    private void setStyle(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String target, String style) {
        if (target.isEmpty() || style.isEmpty()) {
            context.sendMessage(Message.raw("Usage: /chatadmin setstyle <player> <target> <style> [on/off]").color("RED"));
            context.sendMessage(Message.raw("Target: prefix, name, pronouns, suffix, message").color("YELLOW"));
            context.sendMessage(Message.raw("Style: bold, italic, underline").color("YELLOW"));
            return;
        }

        boolean value = !style.equalsIgnoreCase("off");
        String styleLower = style.toLowerCase();

        switch (styleLower) {
            case "bold" -> chatManager.setBold(targetUuid, target, value);
            case "italic" -> chatManager.setItalic(targetUuid, target, value);
            case "underline" -> chatManager.setUnderline(targetUuid, target, value);
            default -> {
                context.sendMessage(Message.raw("Invalid style. Use: bold, italic, or underline").color("RED"));
                return;
            }
        }

        String status = value ? "enabled" : "disabled";
        context.sendMessage(Message.raw("Set " + styleLower + " " + status + " for " + playerName + "'s " + target + ".").color("GREEN"));
    }

    private void setRainbow(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String target, String value) {
        if (target.isEmpty()) {
            context.sendMessage(Message.raw("Usage: /chatadmin setrainbow <player> <target> [on/off]").color("RED"));
            context.sendMessage(Message.raw("Target: prefix, name, pronouns, suffix, message").color("YELLOW"));
            return;
        }

        boolean enabled = value.isEmpty() || !value.equalsIgnoreCase("off");
        chatManager.setRainbow(targetUuid, target, enabled);
        String status = enabled ? "enabled" : "disabled";
        context.sendMessage(Message.raw("Rainbow " + status + " for " + playerName + "'s " + target + ".").color("GREEN"));
    }

    private void setGradient(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String target, String colors) {
        if (target.isEmpty() || colors.isEmpty()) {
            context.sendMessage(Message.raw("Usage: /chatadmin setgradient <player> <target> <startColor> <endColor>").color("RED"));
            context.sendMessage(Message.raw("Target: prefix, name, pronouns, suffix, message").color("YELLOW"));
            return;
        }

        String[] colorParts = colors.split(" ");
        if (colorParts.length < 2) {
            context.sendMessage(Message.raw("You must specify both start and end colors.").color("RED"));
            return;
        }

        chatManager.setGradient(targetUuid, target, colorParts[0], colorParts[1]);
        context.sendMessage(Message.raw("Set gradient for " + playerName + "'s " + target + ".").color("GREEN"));
    }

    private void clearStyle(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String target) {
        if (target.isEmpty()) {
            chatManager.setBold(targetUuid, "prefix", false);
            chatManager.setBold(targetUuid, "name", false);
            chatManager.setBold(targetUuid, "pronouns", false);
            chatManager.setBold(targetUuid, "suffix", false);
            chatManager.setBold(targetUuid, "message", false);
            chatManager.setItalic(targetUuid, "prefix", false);
            chatManager.setItalic(targetUuid, "name", false);
            chatManager.setItalic(targetUuid, "pronouns", false);
            chatManager.setItalic(targetUuid, "suffix", false);
            chatManager.setItalic(targetUuid, "message", false);
            chatManager.setUnderline(targetUuid, "prefix", false);
            chatManager.setUnderline(targetUuid, "name", false);
            chatManager.setUnderline(targetUuid, "pronouns", false);
            chatManager.setUnderline(targetUuid, "suffix", false);
            chatManager.setUnderline(targetUuid, "message", false);
            context.sendMessage(Message.raw("Cleared all styles for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setBold(targetUuid, target, false);
            chatManager.setItalic(targetUuid, target, false);
            chatManager.setUnderline(targetUuid, target, false);
            context.sendMessage(Message.raw("Cleared styles for " + playerName + "'s " + target + ".").color("GREEN"));
        }
    }

    private void clearRainbow(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String target) {
        if (target.isEmpty()) {
            chatManager.setRainbow(targetUuid, "prefix", false);
            chatManager.setRainbow(targetUuid, "name", false);
            chatManager.setRainbow(targetUuid, "pronouns", false);
            chatManager.setRainbow(targetUuid, "suffix", false);
            chatManager.setRainbow(targetUuid, "message", false);
            context.sendMessage(Message.raw("Cleared rainbow for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setRainbow(targetUuid, target, false);
            context.sendMessage(Message.raw("Cleared rainbow for " + playerName + "'s " + target + ".").color("GREEN"));
        }
    }

    private void clearGradient(CommandContext context, String playerName, UUID targetUuid, PlayerChatProfile profile, String target) {
        if (target.isEmpty()) {
            chatManager.setGradient(targetUuid, "prefix", null, null);
            chatManager.setGradient(targetUuid, "name", null, null);
            chatManager.setGradient(targetUuid, "pronouns", null, null);
            chatManager.setGradient(targetUuid, "suffix", null, null);
            chatManager.setGradient(targetUuid, "message", null, null);
            context.sendMessage(Message.raw("Cleared gradient for " + playerName + ".").color("GREEN"));
        } else {
            chatManager.setGradient(targetUuid, target, null, null);
            context.sendMessage(Message.raw("Cleared gradient for " + playerName + "'s " + target + ".").color("GREEN"));
        }
    }

    private void showHelp(CommandContext context) {
        context.sendMessage(Message.raw("=== Chat Admin Commands ===").color("GOLD"));
        context.sendMessage(Message.raw("/chatadmin view <player> - View player's profile"));
        context.sendMessage(Message.raw("/chatadmin reset <player> - Reset player's profile"));
        context.sendMessage(Message.raw("/chatadmin setprefix <player> [value] - Set/clear prefix"));
        context.sendMessage(Message.raw("/chatadmin setsuffix <player> [value] - Set/clear suffix"));
        context.sendMessage(Message.raw("/chatadmin setnickname <player> [value] - Set/clear nickname"));
        context.sendMessage(Message.raw("/chatadmin setpronouns <player> [value] - Set/clear pronouns"));
        context.sendMessage(Message.raw("/chatadmin setnamecolor <player> [value] - Set name color"));
        context.sendMessage(Message.raw("/chatadmin setmsgcolor <player> [value] - Set message color"));
        context.sendMessage(Message.raw("/chatadmin setprefixcolor <player> [value] - Set prefix color"));
        context.sendMessage(Message.raw("/chatadmin setsuffixcolor <player> [value] - Set suffix color"));
        context.sendMessage(Message.raw("/chatadmin setpronounscolor <player> [value] - Set pronouns color"));
        context.sendMessage(Message.raw("/chatadmin clearnamecolor <player> - Clear name color"));
        context.sendMessage(Message.raw("/chatadmin clearmsgcolor <player> - Clear message color"));
        context.sendMessage(Message.raw("/chatadmin clearprefixcolor <player> - Clear prefix color"));
        context.sendMessage(Message.raw("/chatadmin clearsuffixcolor <player> - Clear suffix color"));
        context.sendMessage(Message.raw("/chatadmin clearpronounscolor <player> - Clear pronouns color"));
        context.sendMessage(Message.raw("/chatadmin setstyle <player> <target> <style> - Set style (bold/italic/underline)"));
        context.sendMessage(Message.raw("/chatadmin setrainbow <player> <target> [on/off] - Set rainbow effect"));
        context.sendMessage(Message.raw("/chatadmin setgradient <player> <target> <start> <end> - Set gradient"));
        context.sendMessage(Message.raw("/chatadmin clearstyle <player> [target] - Clear styles"));
        context.sendMessage(Message.raw("/chatadmin clearrainbow <player> [target] - Clear rainbow"));
        context.sendMessage(Message.raw("/chatadmin cleargradient <player> [target] - Clear gradient"));
    }
}
