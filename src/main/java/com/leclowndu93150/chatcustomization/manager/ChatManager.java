package com.leclowndu93150.chatcustomization.manager;

import com.leclowndu93150.chatcustomization.data.ElementStyle;
import com.leclowndu93150.chatcustomization.data.PlayerChatProfile;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChatManager {
    private final DataManager dataManager;
    private final Map<UUID, PlayerChatProfile> profiles;

    public ChatManager(@Nonnull DataManager dataManager) {
        this.dataManager = dataManager;
        this.profiles = dataManager.loadProfiles();
    }

    @Nonnull
    public PlayerChatProfile getProfile(@Nonnull UUID playerUuid) {
        return profiles.getOrDefault(playerUuid, PlayerChatProfile.empty());
    }

    public void setProfile(@Nonnull UUID playerUuid, @Nonnull PlayerChatProfile profile) {
        if (profile.hasCustomizations() || profile.hasSeenWelcome()) {
            profiles.put(playerUuid, profile);
        } else {
            profiles.remove(playerUuid);
        }
        dataManager.saveProfiles(profiles);
    }

    public void setPrefix(@Nonnull UUID playerUuid, @Nullable String prefix) {
        setProfile(playerUuid, getProfile(playerUuid).withPrefix(prefix));
    }

    public void setSuffix(@Nonnull UUID playerUuid, @Nullable String suffix) {
        setProfile(playerUuid, getProfile(playerUuid).withSuffix(suffix));
    }

    public void setNickname(@Nonnull UUID playerUuid, @Nullable String nickname) {
        setProfile(playerUuid, getProfile(playerUuid).withNickname(nickname));
    }

    public void setPronouns(@Nonnull UUID playerUuid, @Nullable String pronouns) {
        setProfile(playerUuid, getProfile(playerUuid).withPronouns(pronouns));
    }

    public void setColor(@Nonnull UUID playerUuid, @Nonnull String target, @Nullable String color) {
        PlayerChatProfile profile = getProfile(playerUuid);
        ElementStyle style = profile.getStyle(target).withColor(color);
        setProfile(playerUuid, profile.withStyle(target, style));
    }

    public void setBold(@Nonnull UUID playerUuid, @Nonnull String target, boolean value) {
        PlayerChatProfile profile = getProfile(playerUuid);
        ElementStyle style = profile.getStyle(target).withBold(value);
        setProfile(playerUuid, profile.withStyle(target, style));
    }

    public void setItalic(@Nonnull UUID playerUuid, @Nonnull String target, boolean value) {
        PlayerChatProfile profile = getProfile(playerUuid);
        ElementStyle style = profile.getStyle(target).withItalic(value);
        setProfile(playerUuid, profile.withStyle(target, style));
    }

    public void setUnderline(@Nonnull UUID playerUuid, @Nonnull String target, boolean value) {
        PlayerChatProfile profile = getProfile(playerUuid);
        ElementStyle style = profile.getStyle(target).withUnderline(value);
        setProfile(playerUuid, profile.withStyle(target, style));
    }

    public void setGradient(@Nonnull UUID playerUuid, @Nonnull String target, @Nullable String start, @Nullable String end) {
        PlayerChatProfile profile = getProfile(playerUuid);
        ElementStyle style = profile.getStyle(target).withGradient(start, end);
        setProfile(playerUuid, profile.withStyle(target, style));
    }

    public void setRainbow(@Nonnull UUID playerUuid, @Nonnull String target, boolean value) {
        PlayerChatProfile profile = getProfile(playerUuid);
        ElementStyle style = profile.getStyle(target).withRainbow(value);
        setProfile(playerUuid, profile.withStyle(target, style));
    }

    public void resetProfile(@Nonnull UUID playerUuid) {
        PlayerChatProfile profile = getProfile(playerUuid);
        if (profile.hasSeenWelcome()) {
            profiles.put(playerUuid, PlayerChatProfile.empty().withHasSeenWelcome(true));
        } else {
            profiles.remove(playerUuid);
        }
        dataManager.saveProfiles(profiles);
    }

    public void markWelcomeSeen(@Nonnull UUID playerUuid) {
        PlayerChatProfile profile = getProfile(playerUuid);
        profiles.put(playerUuid, profile.withHasSeenWelcome(true));
        dataManager.saveProfiles(profiles);
    }

    public void save() {
        dataManager.saveProfiles(profiles);
    }

    @Nonnull
    public PlayerChatProfile getOrCreateProfile(@Nonnull UUID playerUuid) {
        return profiles.computeIfAbsent(playerUuid, k -> PlayerChatProfile.empty());
    }
}
