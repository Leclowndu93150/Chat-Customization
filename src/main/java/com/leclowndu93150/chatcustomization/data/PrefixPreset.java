package com.leclowndu93150.chatcustomization.data;

import com.leclowndu93150.chatcustomization.util.Permissions;
import javax.annotation.Nonnull;

public class PrefixPreset {
    private String id = "default";
    private String displayName = "Default";
    private int priority = 0;
    private String prefix = "";
    private ElementStyle prefixStyle = ElementStyle.EMPTY;

    public PrefixPreset() {
    }

    public PrefixPreset(String id, String displayName, int priority, String prefix, ElementStyle prefixStyle) {
        this.id = id;
        this.displayName = displayName;
        this.priority = priority;
        this.prefix = prefix != null ? prefix : "";
        this.prefixStyle = prefixStyle != null ? prefixStyle : ElementStyle.EMPTY;
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

    public ElementStyle getPrefixStyle() {
        return prefixStyle;
    }

    public void setPrefixStyle(ElementStyle prefixStyle) {
        this.prefixStyle = prefixStyle != null ? prefixStyle : ElementStyle.EMPTY;
    }

    @Nonnull
    public String getPermission() {
        return Permissions.PRESET_PREFIX_PREFIX + id;
    }

    @Nonnull
    public static PrefixPreset createEmpty(String id) {
        return new PrefixPreset(id, id, 0, "", ElementStyle.EMPTY);
    }

    public PrefixPreset copy() {
        return new PrefixPreset(id, displayName, priority, prefix, prefixStyle);
    }

    @Nonnull
    public PlayerChatProfile applyToProfile(@Nonnull PlayerChatProfile profile) {
        return profile.withPrefix(prefix).withPrefixStyle(prefixStyle);
    }
}
