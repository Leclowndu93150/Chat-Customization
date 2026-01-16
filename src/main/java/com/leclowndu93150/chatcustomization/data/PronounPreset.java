package com.leclowndu93150.chatcustomization.data;

import com.leclowndu93150.chatcustomization.util.Permissions;
import javax.annotation.Nonnull;

public class PronounPreset {
    private String id = "default";
    private String displayName = "Default";
    private int priority = 0;
    private String pronouns = "";
    private ElementStyle pronounsStyle = ElementStyle.EMPTY;

    public PronounPreset() {
    }

    public PronounPreset(String id, String displayName, int priority, String pronouns, ElementStyle pronounsStyle) {
        this.id = id;
        this.displayName = displayName;
        this.priority = priority;
        this.pronouns = pronouns != null ? pronouns : "";
        this.pronounsStyle = pronounsStyle != null ? pronounsStyle : ElementStyle.EMPTY;
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

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns != null ? pronouns : "";
    }

    public ElementStyle getPronounsStyle() {
        return pronounsStyle;
    }

    public void setPronounsStyle(ElementStyle pronounsStyle) {
        this.pronounsStyle = pronounsStyle != null ? pronounsStyle : ElementStyle.EMPTY;
    }

    @Nonnull
    public String getPermission() {
        return Permissions.PRESET_PRONOUN_PREFIX + id;
    }

    @Nonnull
    public static PronounPreset createEmpty(String id) {
        return new PronounPreset(id, id, 0, "", ElementStyle.EMPTY);
    }

    public PronounPreset copy() {
        return new PronounPreset(id, displayName, priority, pronouns, pronounsStyle);
    }

    @Nonnull
    public PlayerChatProfile applyToProfile(@Nonnull PlayerChatProfile profile) {
        return profile.withPronouns(pronouns).withPronounsStyle(pronounsStyle);
    }
}
