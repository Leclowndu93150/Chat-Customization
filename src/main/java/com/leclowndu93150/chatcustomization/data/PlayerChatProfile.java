package com.leclowndu93150.chatcustomization.data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public record PlayerChatProfile(
    @Nullable String prefix,
    @Nullable String suffix,
    @Nullable String nickname,
    @Nullable String pronouns,
    @Nonnull ElementStyle prefixStyle,
    @Nonnull ElementStyle nameStyle,
    @Nonnull ElementStyle pronounsStyle,
    @Nonnull ElementStyle suffixStyle,
    @Nonnull ElementStyle messageStyle,
    boolean hasSeenWelcome
) {
    public static PlayerChatProfile empty() {
        return new PlayerChatProfile(
            null, null, null, null,
            ElementStyle.EMPTY, ElementStyle.EMPTY, ElementStyle.EMPTY, ElementStyle.EMPTY, ElementStyle.EMPTY,
            false
        );
    }

    @Nonnull
    public PlayerChatProfile withPrefix(@Nullable String prefix) {
        return new PlayerChatProfile(prefix, suffix, nickname, pronouns, prefixStyle, nameStyle, pronounsStyle, suffixStyle, messageStyle, hasSeenWelcome);
    }

    @Nonnull
    public PlayerChatProfile withSuffix(@Nullable String suffix) {
        return new PlayerChatProfile(prefix, suffix, nickname, pronouns, prefixStyle, nameStyle, pronounsStyle, suffixStyle, messageStyle, hasSeenWelcome);
    }

    @Nonnull
    public PlayerChatProfile withNickname(@Nullable String nickname) {
        return new PlayerChatProfile(prefix, suffix, nickname, pronouns, prefixStyle, nameStyle, pronounsStyle, suffixStyle, messageStyle, hasSeenWelcome);
    }

    @Nonnull
    public PlayerChatProfile withPronouns(@Nullable String pronouns) {
        return new PlayerChatProfile(prefix, suffix, nickname, pronouns, prefixStyle, nameStyle, pronounsStyle, suffixStyle, messageStyle, hasSeenWelcome);
    }

    @Nonnull
    public PlayerChatProfile withPrefixStyle(@Nonnull ElementStyle style) {
        return new PlayerChatProfile(prefix, suffix, nickname, pronouns, style, nameStyle, pronounsStyle, suffixStyle, messageStyle, hasSeenWelcome);
    }

    @Nonnull
    public PlayerChatProfile withNameStyle(@Nonnull ElementStyle style) {
        return new PlayerChatProfile(prefix, suffix, nickname, pronouns, prefixStyle, style, pronounsStyle, suffixStyle, messageStyle, hasSeenWelcome);
    }

    @Nonnull
    public PlayerChatProfile withPronounsStyle(@Nonnull ElementStyle style) {
        return new PlayerChatProfile(prefix, suffix, nickname, pronouns, prefixStyle, nameStyle, style, suffixStyle, messageStyle, hasSeenWelcome);
    }

    @Nonnull
    public PlayerChatProfile withSuffixStyle(@Nonnull ElementStyle style) {
        return new PlayerChatProfile(prefix, suffix, nickname, pronouns, prefixStyle, nameStyle, pronounsStyle, style, messageStyle, hasSeenWelcome);
    }

    @Nonnull
    public PlayerChatProfile withMessageStyle(@Nonnull ElementStyle style) {
        return new PlayerChatProfile(prefix, suffix, nickname, pronouns, prefixStyle, nameStyle, pronounsStyle, suffixStyle, style, hasSeenWelcome);
    }

    @Nonnull
    public PlayerChatProfile withHasSeenWelcome(boolean hasSeenWelcome) {
        return new PlayerChatProfile(prefix, suffix, nickname, pronouns, prefixStyle, nameStyle, pronounsStyle, suffixStyle, messageStyle, hasSeenWelcome);
    }

    @Nonnull
    public ElementStyle getStyle(@Nonnull String target) {
        return switch (target.toLowerCase()) {
            case "prefix" -> prefixStyle;
            case "name" -> nameStyle;
            case "pronouns" -> pronounsStyle;
            case "suffix" -> suffixStyle;
            case "message" -> messageStyle;
            default -> ElementStyle.EMPTY;
        };
    }

    @Nonnull
    public PlayerChatProfile withStyle(@Nonnull String target, @Nonnull ElementStyle style) {
        return switch (target.toLowerCase()) {
            case "prefix" -> withPrefixStyle(style);
            case "name" -> withNameStyle(style);
            case "pronouns" -> withPronounsStyle(style);
            case "suffix" -> withSuffixStyle(style);
            case "message" -> withMessageStyle(style);
            default -> this;
        };
    }

    public boolean hasCustomizations() {
        return prefix != null || suffix != null || nickname != null || pronouns != null ||
               prefixStyle.hasCustomizations() || nameStyle.hasCustomizations() ||
               pronounsStyle.hasCustomizations() || suffixStyle.hasCustomizations() ||
               messageStyle.hasCustomizations();
    }

    // Convenience methods for backward compatibility
    @Nullable public String nameColor() { return nameStyle.color(); }
    @Nullable public String messageColor() { return messageStyle.color(); }
    @Nullable public String prefixColor() { return prefixStyle.color(); }
    @Nullable public String suffixColor() { return suffixStyle.color(); }
    @Nullable public String pronounsColor() { return pronounsStyle.color(); }

    public boolean prefixBold() { return prefixStyle.bold(); }
    public boolean prefixItalic() { return prefixStyle.italic(); }
    public boolean prefixUnderline() { return prefixStyle.underline(); }
    public boolean nameBold() { return nameStyle.bold(); }
    public boolean nameItalic() { return nameStyle.italic(); }
    public boolean nameUnderline() { return nameStyle.underline(); }
    public boolean pronounsBold() { return pronounsStyle.bold(); }
    public boolean pronounsItalic() { return pronounsStyle.italic(); }
    public boolean pronounsUnderline() { return pronounsStyle.underline(); }
    public boolean suffixBold() { return suffixStyle.bold(); }
    public boolean suffixItalic() { return suffixStyle.italic(); }
    public boolean suffixUnderline() { return suffixStyle.underline(); }
    public boolean messageBold() { return messageStyle.bold(); }
    public boolean messageItalic() { return messageStyle.italic(); }
    public boolean messageUnderline() { return messageStyle.underline(); }

    @Nullable public String nameGradientStart() { return nameStyle.gradientStart(); }
    @Nullable public String nameGradientEnd() { return nameStyle.gradientEnd(); }
    @Nullable public String messageGradientStart() { return messageStyle.gradientStart(); }
    @Nullable public String messageGradientEnd() { return messageStyle.gradientEnd(); }
    @Nullable public String prefixGradientStart() { return prefixStyle.gradientStart(); }
    @Nullable public String prefixGradientEnd() { return prefixStyle.gradientEnd(); }
    @Nullable public String suffixGradientStart() { return suffixStyle.gradientStart(); }
    @Nullable public String suffixGradientEnd() { return suffixStyle.gradientEnd(); }
    @Nullable public String pronounsGradientStart() { return pronounsStyle.gradientStart(); }
    @Nullable public String pronounsGradientEnd() { return pronounsStyle.gradientEnd(); }

    public boolean nameRainbow() { return nameStyle.rainbow(); }
    public boolean messageRainbow() { return messageStyle.rainbow(); }
    public boolean prefixRainbow() { return prefixStyle.rainbow(); }
    public boolean suffixRainbow() { return suffixStyle.rainbow(); }
    public boolean pronounsRainbow() { return pronounsStyle.rainbow(); }

    public boolean hasNameGradient() { return nameStyle.hasGradient(); }
    public boolean hasMessageGradient() { return messageStyle.hasGradient(); }
    public boolean hasPrefixGradient() { return prefixStyle.hasGradient(); }
    public boolean hasSuffixGradient() { return suffixStyle.hasGradient(); }
    public boolean hasPronounsGradient() { return pronounsStyle.hasGradient(); }
}
