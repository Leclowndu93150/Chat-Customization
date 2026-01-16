package com.leclowndu93150.chatcustomization.data;

import com.leclowndu93150.chatcustomization.util.Permissions;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StylePreset {
    private String id = "default";
    private String displayName = "Default";
    private int priority = 0;
    private String prefix = "";
    private String suffix = "";
    private String nickname = "";
    private String pronouns = "";
    private ElementStyle prefixStyle = ElementStyle.EMPTY;
    private ElementStyle suffixStyle = ElementStyle.EMPTY;
    private ElementStyle nameStyle = ElementStyle.EMPTY;
    private ElementStyle pronounsStyle = ElementStyle.EMPTY;
    private ElementStyle messageStyle = ElementStyle.EMPTY;

    public StylePreset() {
    }

    public StylePreset(String id, String displayName, int priority, String prefix, String suffix,
                       String nickname, String pronouns, ElementStyle prefixStyle, ElementStyle suffixStyle,
                       ElementStyle nameStyle, ElementStyle pronounsStyle, ElementStyle messageStyle) {
        this.id = id;
        this.displayName = displayName;
        this.priority = priority;
        this.prefix = prefix != null ? prefix : "";
        this.suffix = suffix != null ? suffix : "";
        this.nickname = nickname != null ? nickname : "";
        this.pronouns = pronouns != null ? pronouns : "";
        this.prefixStyle = prefixStyle != null ? prefixStyle : ElementStyle.EMPTY;
        this.suffixStyle = suffixStyle != null ? suffixStyle : ElementStyle.EMPTY;
        this.nameStyle = nameStyle != null ? nameStyle : ElementStyle.EMPTY;
        this.pronounsStyle = pronounsStyle != null ? pronounsStyle : ElementStyle.EMPTY;
        this.messageStyle = messageStyle != null ? messageStyle : ElementStyle.EMPTY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix != null ? prefix : "";
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix != null ? suffix : "";
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname != null ? nickname : "";
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns != null ? pronouns : "";
    }

    public ElementStyle getPrefixStyle() {
        return prefixStyle;
    }

    public void setPrefixStyle(ElementStyle prefixStyle) {
        this.prefixStyle = prefixStyle != null ? prefixStyle : ElementStyle.EMPTY;
    }

    public ElementStyle getSuffixStyle() {
        return suffixStyle;
    }

    public void setSuffixStyle(ElementStyle suffixStyle) {
        this.suffixStyle = suffixStyle != null ? suffixStyle : ElementStyle.EMPTY;
    }

    public ElementStyle getNameStyle() {
        return nameStyle;
    }

    public void setNameStyle(ElementStyle nameStyle) {
        this.nameStyle = nameStyle != null ? nameStyle : ElementStyle.EMPTY;
    }

    public ElementStyle getPronounsStyle() {
        return pronounsStyle;
    }

    public void setPronounsStyle(ElementStyle pronounsStyle) {
        this.pronounsStyle = pronounsStyle != null ? pronounsStyle : ElementStyle.EMPTY;
    }

    public ElementStyle getMessageStyle() {
        return messageStyle;
    }

    public void setMessageStyle(ElementStyle messageStyle) {
        this.messageStyle = messageStyle != null ? messageStyle : ElementStyle.EMPTY;
    }

    @Nonnull
    public String getPermission() {
        return Permissions.PRESET_STYLE_PREFIX + id;
    }

    @Nonnull
    public static StylePreset createEmpty(String id) {
        return new StylePreset(
            id,
            id,
            0,
            "",
            "",
            "",
            "",
            ElementStyle.EMPTY,
            ElementStyle.EMPTY,
            ElementStyle.EMPTY,
            ElementStyle.EMPTY,
            ElementStyle.EMPTY
        );
    }

    public StylePreset copy() {
        return new StylePreset(
            id,
            displayName,
            priority,
            prefix,
            suffix,
            nickname,
            pronouns,
            prefixStyle,
            suffixStyle,
            nameStyle,
            pronounsStyle,
            messageStyle
        );
    }

    @Nonnull
    public PlayerChatProfile applyToProfile(@Nonnull PlayerChatProfile profile) {
        PlayerChatProfile result = profile;
        if (!prefix.isEmpty()) {
            result = result.withPrefix(prefix).withPrefixStyle(prefixStyle);
        }
        if (!suffix.isEmpty()) {
            result = result.withSuffix(suffix).withSuffixStyle(suffixStyle);
        }
        if (!nickname.isEmpty()) {
            result = result.withNickname(nickname);
        }
        if (!pronouns.isEmpty()) {
            result = result.withPronouns(pronouns).withPronounsStyle(pronounsStyle);
        }
        if (nameStyle.hasCustomizations()) {
            result = result.withNameStyle(nameStyle);
        }
        if (messageStyle.hasCustomizations()) {
            result = result.withMessageStyle(messageStyle);
        }
        return result;
    }
}
