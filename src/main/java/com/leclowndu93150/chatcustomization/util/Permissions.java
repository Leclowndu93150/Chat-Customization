package com.leclowndu93150.chatcustomization.util;

import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import javax.annotation.Nonnull;
import java.util.UUID;

public final class Permissions {
    public static final String ADMIN_PRESETS = "chatcustom.admin.presets";
    public static final String ADMIN_RELOAD = "chatcustom.admin.reload";
    public static final String ADMIN_MANAGE_PLAYERS = "chatcustom.admin.players";

    public static final String CHAT_EDITOR = "chatcustom.editor";

    public static final String CMD_PREFIX = "chatcustom.command.prefix";
    public static final String CMD_SUFFIX = "chatcustom.command.suffix";
    public static final String CMD_NICKNAME = "chatcustom.command.nickname";
    public static final String CMD_PRONOUNS = "chatcustom.command.pronouns";
    public static final String CMD_COLOR = "chatcustom.command.color";
    public static final String CMD_STYLE = "chatcustom.command.style";
    public static final String CMD_GRADIENT = "chatcustom.command.gradient";
    public static final String CMD_RAINBOW = "chatcustom.command.rainbow";
    public static final String CMD_RESET = "chatcustom.command.reset";

    public static final String STYLE_BOLD = "chatcustom.style.bold";
    public static final String STYLE_ITALIC = "chatcustom.style.italic";
    public static final String STYLE_UNDERLINE = "chatcustom.style.underline";
    public static final String STYLE_GRADIENT = "chatcustom.style.gradient";
    public static final String STYLE_RAINBOW = "chatcustom.style.rainbow";

    public static final String PRESET_STYLE_PREFIX = "chatcustom.preset.style.";
    public static final String PRESET_PREFIX_PREFIX = "chatcustom.preset.prefix.";
    public static final String PRESET_PRONOUN_PREFIX = "chatcustom.preset.pronoun.";
    public static final String PRESET_MESSAGE_PREFIX = "chatcustom.preset.message.";

    private Permissions() {
    }

    public static boolean hasPermission(@Nonnull PlayerRef player, @Nonnull String permission) {
        return PermissionsModule.get().hasPermission(player.getUuid(), permission);
    }

    public static boolean hasPermission(@Nonnull UUID playerUuid, @Nonnull String permission) {
        return PermissionsModule.get().hasPermission(playerUuid, permission);
    }

    public static boolean canManagePresets(@Nonnull PlayerRef player) {
        return hasPermission(player, ADMIN_PRESETS);
    }

    public static boolean canReload(@Nonnull PlayerRef player) {
        return hasPermission(player, ADMIN_RELOAD);
    }

    public static boolean canManagePlayers(@Nonnull PlayerRef player) {
        return hasPermission(player, ADMIN_MANAGE_PLAYERS);
    }

    public static boolean canOpenEditor(@Nonnull PlayerRef player) {
        return hasPermission(player, CHAT_EDITOR);
    }

    public static boolean canUsePrefix(@Nonnull PlayerRef player) {
        return hasPermission(player, CMD_PREFIX);
    }

    public static boolean canUseSuffix(@Nonnull PlayerRef player) {
        return hasPermission(player, CMD_SUFFIX);
    }

    public static boolean canUseNickname(@Nonnull PlayerRef player) {
        return hasPermission(player, CMD_NICKNAME);
    }

    public static boolean canUsePronouns(@Nonnull PlayerRef player) {
        return hasPermission(player, CMD_PRONOUNS);
    }

    public static boolean canUseColors(@Nonnull PlayerRef player) {
        return hasPermission(player, CMD_COLOR);
    }

    public static boolean canUseStyle(@Nonnull PlayerRef player) {
        return hasPermission(player, CMD_STYLE);
    }

    public static boolean canUseGradient(@Nonnull PlayerRef player) {
        return hasPermission(player, CMD_GRADIENT) && hasPermission(player, STYLE_GRADIENT);
    }

    public static boolean canUseRainbow(@Nonnull PlayerRef player) {
        return hasPermission(player, CMD_RAINBOW) && hasPermission(player, STYLE_RAINBOW);
    }

    public static boolean canUseBold(@Nonnull PlayerRef player) {
        return hasPermission(player, STYLE_BOLD);
    }

    public static boolean canUseItalic(@Nonnull PlayerRef player) {
        return hasPermission(player, STYLE_ITALIC);
    }

    public static boolean canUseUnderline(@Nonnull PlayerRef player) {
        return hasPermission(player, STYLE_UNDERLINE);
    }

    public static boolean hasStylePreset(@Nonnull PlayerRef player, @Nonnull String presetId) {
        return hasPermission(player, PRESET_STYLE_PREFIX + presetId);
    }

    public static boolean hasPrefixPreset(@Nonnull PlayerRef player, @Nonnull String presetId) {
        return hasPermission(player, PRESET_PREFIX_PREFIX + presetId);
    }

    public static boolean hasPronounPreset(@Nonnull PlayerRef player, @Nonnull String presetId) {
        return hasPermission(player, PRESET_PRONOUN_PREFIX + presetId);
    }

    public static boolean hasMessagePreset(@Nonnull PlayerRef player, @Nonnull String presetId) {
        return hasPermission(player, PRESET_MESSAGE_PREFIX + presetId);
    }
}
