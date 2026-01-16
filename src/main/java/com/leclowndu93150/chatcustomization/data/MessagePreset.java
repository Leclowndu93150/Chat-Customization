package com.leclowndu93150.chatcustomization.data;

import com.leclowndu93150.chatcustomization.util.Permissions;
import javax.annotation.Nonnull;

public class MessagePreset {
    private String id = "default";
    private String displayName = "Default";
    private int priority = 0;
    private ElementStyle messageStyle = ElementStyle.EMPTY;

    public MessagePreset() {
    }

    public MessagePreset(String id, String displayName, int priority, ElementStyle messageStyle) {
        this.id = id;
        this.displayName = displayName;
        this.priority = priority;
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

    public ElementStyle getMessageStyle() {
        return messageStyle;
    }

    public void setMessageStyle(ElementStyle messageStyle) {
        this.messageStyle = messageStyle != null ? messageStyle : ElementStyle.EMPTY;
    }

    @Nonnull
    public String getPermission() {
        return Permissions.PRESET_MESSAGE_PREFIX + id;
    }

    @Nonnull
    public static MessagePreset createEmpty(String id) {
        return new MessagePreset(id, id, 0, ElementStyle.EMPTY);
    }

    public MessagePreset copy() {
        return new MessagePreset(id, displayName, priority, messageStyle);
    }

    @Nonnull
    public PlayerChatProfile applyToProfile(@Nonnull PlayerChatProfile profile) {
        return profile.withMessageStyle(messageStyle);
    }
}
