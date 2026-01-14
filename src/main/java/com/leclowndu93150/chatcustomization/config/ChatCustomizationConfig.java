package com.leclowndu93150.chatcustomization.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class ChatCustomizationConfig {
    public static final BuilderCodec<ChatCustomizationConfig> CODEC = BuilderCodec
        .builder(ChatCustomizationConfig.class, ChatCustomizationConfig::new)
        .append(new KeyedCodec<>("_comment_colors", Codec.STRING), ChatCustomizationConfig::setCommentColors, ChatCustomizationConfig::getCommentColors).add()
        .append(new KeyedCodec<>("_comment_hex", Codec.STRING), ChatCustomizationConfig::setCommentHex, ChatCustomizationConfig::getCommentHex).add()
        .append(new KeyedCodec<>("_comment_styles", Codec.STRING), ChatCustomizationConfig::setCommentStyles, ChatCustomizationConfig::getCommentStyles).add()
        .append(new KeyedCodec<>("_comment_permissions", Codec.STRING), ChatCustomizationConfig::setCommentPermissions, ChatCustomizationConfig::getCommentPermissions).add()
        .append(new KeyedCodec<>("MaxPrefixLength", Codec.INTEGER), ChatCustomizationConfig::setMaxPrefixLength, ChatCustomizationConfig::getMaxPrefixLength).add()
        .append(new KeyedCodec<>("MaxSuffixLength", Codec.INTEGER), ChatCustomizationConfig::setMaxSuffixLength, ChatCustomizationConfig::getMaxSuffixLength).add()
        .append(new KeyedCodec<>("MaxNicknameLength", Codec.INTEGER), ChatCustomizationConfig::setMaxNicknameLength, ChatCustomizationConfig::getMaxNicknameLength).add()
        .append(new KeyedCodec<>("MaxPronounsLength", Codec.INTEGER), ChatCustomizationConfig::setMaxPronounsLength, ChatCustomizationConfig::getMaxPronounsLength).add()
        .append(new KeyedCodec<>("DefaultNameColor", Codec.STRING), ChatCustomizationConfig::setDefaultNameColor, ChatCustomizationConfig::getDefaultNameColor).add()
        .append(new KeyedCodec<>("DefaultMessageColor", Codec.STRING), ChatCustomizationConfig::setDefaultMessageColor, ChatCustomizationConfig::getDefaultMessageColor).add()
        .append(new KeyedCodec<>("ChatFormat", Codec.STRING), ChatCustomizationConfig::setChatFormat, ChatCustomizationConfig::getChatFormat).add()
        .append(new KeyedCodec<>("AllowGradients", Codec.BOOLEAN), ChatCustomizationConfig::setAllowGradients, ChatCustomizationConfig::getAllowGradients).add()
        .append(new KeyedCodec<>("AllowRainbow", Codec.BOOLEAN), ChatCustomizationConfig::setAllowRainbow, ChatCustomizationConfig::getAllowRainbow).add()
        .append(new KeyedCodec<>("AllowBold", Codec.BOOLEAN), ChatCustomizationConfig::setAllowBold, ChatCustomizationConfig::getAllowBold).add()
        .append(new KeyedCodec<>("AllowItalic", Codec.BOOLEAN), ChatCustomizationConfig::setAllowItalic, ChatCustomizationConfig::getAllowItalic).add()
        .append(new KeyedCodec<>("AllowUnderline", Codec.BOOLEAN), ChatCustomizationConfig::setAllowUnderline, ChatCustomizationConfig::getAllowUnderline).add()
        .append(new KeyedCodec<>("AllowLinks", Codec.BOOLEAN), ChatCustomizationConfig::setAllowLinks, ChatCustomizationConfig::getAllowLinks).add()
        .append(new KeyedCodec<>("EnableMentions", Codec.BOOLEAN), ChatCustomizationConfig::setEnableMentions, ChatCustomizationConfig::getEnableMentions).add()
        .append(new KeyedCodec<>("MentionColor", Codec.STRING), ChatCustomizationConfig::setMentionColor, ChatCustomizationConfig::getMentionColor).add()
        .append(new KeyedCodec<>("MentionSound", Codec.BOOLEAN), ChatCustomizationConfig::setMentionSound, ChatCustomizationConfig::getMentionSound).add()
        .append(new KeyedCodec<>("MentionSoundId", Codec.STRING), ChatCustomizationConfig::setMentionSoundId, ChatCustomizationConfig::getMentionSoundId).add()
        .append(new KeyedCodec<>("MentionSoundVolume", Codec.FLOAT), ChatCustomizationConfig::setMentionSoundVolume, ChatCustomizationConfig::getMentionSoundVolume).add()
        .append(new KeyedCodec<>("MentionSoundPitch", Codec.FLOAT), ChatCustomizationConfig::setMentionSoundPitch, ChatCustomizationConfig::getMentionSoundPitch).add()
        .append(new KeyedCodec<>("RequirePermissionForPrefix", Codec.BOOLEAN), ChatCustomizationConfig::setRequirePermissionForPrefix, ChatCustomizationConfig::getRequirePermissionForPrefix).add()
        .append(new KeyedCodec<>("RequirePermissionForSuffix", Codec.BOOLEAN), ChatCustomizationConfig::setRequirePermissionForSuffix, ChatCustomizationConfig::getRequirePermissionForSuffix).add()
        .append(new KeyedCodec<>("RequirePermissionForNickname", Codec.BOOLEAN), ChatCustomizationConfig::setRequirePermissionForNickname, ChatCustomizationConfig::getRequirePermissionForNickname).add()
        .append(new KeyedCodec<>("RequirePermissionForPronouns", Codec.BOOLEAN), ChatCustomizationConfig::setRequirePermissionForPronouns, ChatCustomizationConfig::getRequirePermissionForPronouns).add()
        .append(new KeyedCodec<>("RequirePermissionForColors", Codec.BOOLEAN), ChatCustomizationConfig::setRequirePermissionForColors, ChatCustomizationConfig::getRequirePermissionForColors).add()
        .append(new KeyedCodec<>("RequirePermissionForStyles", Codec.BOOLEAN), ChatCustomizationConfig::setRequirePermissionForStyles, ChatCustomizationConfig::getRequirePermissionForStyles).add()
        .append(new KeyedCodec<>("RequirePermissionForGradient", Codec.BOOLEAN), ChatCustomizationConfig::setRequirePermissionForGradient, ChatCustomizationConfig::getRequirePermissionForGradient).add()
        .append(new KeyedCodec<>("RequirePermissionForRainbow", Codec.BOOLEAN), ChatCustomizationConfig::setRequirePermissionForRainbow, ChatCustomizationConfig::getRequirePermissionForRainbow).add()
        .append(new KeyedCodec<>("PermissionPrefix", Codec.STRING), ChatCustomizationConfig::setPermissionPrefix, ChatCustomizationConfig::getPermissionPrefix).add()
        .append(new KeyedCodec<>("PermissionSuffix", Codec.STRING), ChatCustomizationConfig::setPermissionSuffix, ChatCustomizationConfig::getPermissionSuffix).add()
        .append(new KeyedCodec<>("PermissionNickname", Codec.STRING), ChatCustomizationConfig::setPermissionNickname, ChatCustomizationConfig::getPermissionNickname).add()
        .append(new KeyedCodec<>("PermissionPronouns", Codec.STRING), ChatCustomizationConfig::setPermissionPronouns, ChatCustomizationConfig::getPermissionPronouns).add()
        .append(new KeyedCodec<>("PermissionColors", Codec.STRING), ChatCustomizationConfig::setPermissionColors, ChatCustomizationConfig::getPermissionColors).add()
        .append(new KeyedCodec<>("PermissionStyles", Codec.STRING), ChatCustomizationConfig::setPermissionStyles, ChatCustomizationConfig::getPermissionStyles).add()
        .append(new KeyedCodec<>("PermissionGradient", Codec.STRING), ChatCustomizationConfig::setPermissionGradient, ChatCustomizationConfig::getPermissionGradient).add()
        .append(new KeyedCodec<>("PermissionRainbow", Codec.STRING), ChatCustomizationConfig::setPermissionRainbow, ChatCustomizationConfig::getPermissionRainbow).add()
        .append(new KeyedCodec<>("PermissionAdmin", Codec.STRING), ChatCustomizationConfig::setPermissionAdmin, ChatCustomizationConfig::getPermissionAdmin).add()
        .build();

    private String commentColors = "Available colors: BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE, PINK, ORANGE, CYAN, LIME";
    private String commentHex = "Hex colors: Use format #RRGGBB (e.g. #FF0000 for red, #00FF00 for green, #0000FF for blue)";
    private String commentStyles = "Available styles: bold, italic, underline. Use /style command to toggle";
    private String commentPermissions = "Permission nodes: chatcustom.prefix, chatcustom.suffix, chatcustom.nickname, chatcustom.pronouns, chatcustom.colors, chatcustom.styles, chatcustom.gradient, chatcustom.rainbow, chatcustom.admin";

    private int maxPrefixLength = 16;
    private int maxSuffixLength = 16;
    private int maxNicknameLength = 24;
    private int maxPronounsLength = 12;

    private String defaultNameColor = "YELLOW";
    private String defaultMessageColor = "WHITE";

    private String chatFormat = "{prefix}{name}{pronouns}{suffix}: {message}";

    private boolean allowGradients = true;
    private boolean allowRainbow = true;
    private boolean allowBold = true;
    private boolean allowItalic = true;
    private boolean allowUnderline = true;
    private boolean allowLinks = true;

    private boolean enableMentions = true;
    private String mentionColor = "#FFAA00";
    private boolean mentionSound = true;
    private String mentionSoundId = "SFX_Player_Pickup_Item";
    private float mentionSoundVolume = 1.0f;
    private float mentionSoundPitch = 1.0f;

    private boolean requirePermissionForPrefix = false;
    private boolean requirePermissionForSuffix = false;
    private boolean requirePermissionForNickname = false;
    private boolean requirePermissionForPronouns = false;
    private boolean requirePermissionForColors = false;
    private boolean requirePermissionForStyles = false;
    private boolean requirePermissionForGradient = true;
    private boolean requirePermissionForRainbow = true;

    private String permissionPrefix = "chatcustom.prefix";
    private String permissionSuffix = "chatcustom.suffix";
    private String permissionNickname = "chatcustom.nickname";
    private String permissionPronouns = "chatcustom.pronouns";
    private String permissionColors = "chatcustom.colors";
    private String permissionStyles = "chatcustom.styles";
    private String permissionGradient = "chatcustom.gradient";
    private String permissionRainbow = "chatcustom.rainbow";
    private String permissionAdmin = "chatcustom.admin";

    public ChatCustomizationConfig() {}

    public String getCommentColors() { return commentColors; }
    public void setCommentColors(String commentColors) { this.commentColors = commentColors; }
    public String getCommentHex() { return commentHex; }
    public void setCommentHex(String commentHex) { this.commentHex = commentHex; }
    public String getCommentStyles() { return commentStyles; }
    public void setCommentStyles(String commentStyles) { this.commentStyles = commentStyles; }
    public String getCommentPermissions() { return commentPermissions; }
    public void setCommentPermissions(String commentPermissions) { this.commentPermissions = commentPermissions; }

    public int getMaxPrefixLength() { return maxPrefixLength; }
    public void setMaxPrefixLength(int maxPrefixLength) { this.maxPrefixLength = maxPrefixLength; }
    public int getMaxSuffixLength() { return maxSuffixLength; }
    public void setMaxSuffixLength(int maxSuffixLength) { this.maxSuffixLength = maxSuffixLength; }
    public int getMaxNicknameLength() { return maxNicknameLength; }
    public void setMaxNicknameLength(int maxNicknameLength) { this.maxNicknameLength = maxNicknameLength; }
    public int getMaxPronounsLength() { return maxPronounsLength; }
    public void setMaxPronounsLength(int maxPronounsLength) { this.maxPronounsLength = maxPronounsLength; }

    public String getDefaultNameColor() { return defaultNameColor; }
    public void setDefaultNameColor(String defaultNameColor) { this.defaultNameColor = defaultNameColor; }
    public String getDefaultMessageColor() { return defaultMessageColor; }
    public void setDefaultMessageColor(String defaultMessageColor) { this.defaultMessageColor = defaultMessageColor; }

    public String getChatFormat() { return chatFormat; }
    public void setChatFormat(String chatFormat) { this.chatFormat = chatFormat; }

    public boolean getAllowGradients() { return allowGradients; }
    public void setAllowGradients(boolean allowGradients) { this.allowGradients = allowGradients; }
    public boolean getAllowRainbow() { return allowRainbow; }
    public void setAllowRainbow(boolean allowRainbow) { this.allowRainbow = allowRainbow; }
    public boolean getAllowBold() { return allowBold; }
    public void setAllowBold(boolean allowBold) { this.allowBold = allowBold; }
    public boolean getAllowItalic() { return allowItalic; }
    public void setAllowItalic(boolean allowItalic) { this.allowItalic = allowItalic; }
    public boolean getAllowUnderline() { return allowUnderline; }
    public void setAllowUnderline(boolean allowUnderline) { this.allowUnderline = allowUnderline; }
    public boolean getAllowLinks() { return allowLinks; }
    public void setAllowLinks(boolean allowLinks) { this.allowLinks = allowLinks; }

    public boolean getEnableMentions() { return enableMentions; }
    public void setEnableMentions(boolean enableMentions) { this.enableMentions = enableMentions; }
    public String getMentionColor() { return mentionColor; }
    public void setMentionColor(String mentionColor) { this.mentionColor = mentionColor; }
    public boolean getMentionSound() { return mentionSound; }
    public void setMentionSound(boolean mentionSound) { this.mentionSound = mentionSound; }
    public String getMentionSoundId() { return mentionSoundId; }
    public void setMentionSoundId(String mentionSoundId) { this.mentionSoundId = mentionSoundId; }
    public float getMentionSoundVolume() { return mentionSoundVolume; }
    public void setMentionSoundVolume(float mentionSoundVolume) { this.mentionSoundVolume = mentionSoundVolume; }
    public float getMentionSoundPitch() { return mentionSoundPitch; }
    public void setMentionSoundPitch(float mentionSoundPitch) { this.mentionSoundPitch = mentionSoundPitch; }

    public boolean getRequirePermissionForPrefix() { return requirePermissionForPrefix; }
    public void setRequirePermissionForPrefix(boolean v) { this.requirePermissionForPrefix = v; }
    public boolean getRequirePermissionForSuffix() { return requirePermissionForSuffix; }
    public void setRequirePermissionForSuffix(boolean v) { this.requirePermissionForSuffix = v; }
    public boolean getRequirePermissionForNickname() { return requirePermissionForNickname; }
    public void setRequirePermissionForNickname(boolean v) { this.requirePermissionForNickname = v; }
    public boolean getRequirePermissionForPronouns() { return requirePermissionForPronouns; }
    public void setRequirePermissionForPronouns(boolean v) { this.requirePermissionForPronouns = v; }
    public boolean getRequirePermissionForColors() { return requirePermissionForColors; }
    public void setRequirePermissionForColors(boolean v) { this.requirePermissionForColors = v; }
    public boolean getRequirePermissionForStyles() { return requirePermissionForStyles; }
    public void setRequirePermissionForStyles(boolean v) { this.requirePermissionForStyles = v; }
    public boolean getRequirePermissionForGradient() { return requirePermissionForGradient; }
    public void setRequirePermissionForGradient(boolean v) { this.requirePermissionForGradient = v; }
    public boolean getRequirePermissionForRainbow() { return requirePermissionForRainbow; }
    public void setRequirePermissionForRainbow(boolean v) { this.requirePermissionForRainbow = v; }

    public String getPermissionPrefix() { return permissionPrefix; }
    public void setPermissionPrefix(String v) { this.permissionPrefix = v; }
    public String getPermissionSuffix() { return permissionSuffix; }
    public void setPermissionSuffix(String v) { this.permissionSuffix = v; }
    public String getPermissionNickname() { return permissionNickname; }
    public void setPermissionNickname(String v) { this.permissionNickname = v; }
    public String getPermissionPronouns() { return permissionPronouns; }
    public void setPermissionPronouns(String v) { this.permissionPronouns = v; }
    public String getPermissionColors() { return permissionColors; }
    public void setPermissionColors(String v) { this.permissionColors = v; }
    public String getPermissionStyles() { return permissionStyles; }
    public void setPermissionStyles(String v) { this.permissionStyles = v; }
    public String getPermissionGradient() { return permissionGradient; }
    public void setPermissionGradient(String v) { this.permissionGradient = v; }
    public String getPermissionRainbow() { return permissionRainbow; }
    public void setPermissionRainbow(String v) { this.permissionRainbow = v; }
    public String getPermissionAdmin() { return permissionAdmin; }
    public void setPermissionAdmin(String v) { this.permissionAdmin = v; }
}
