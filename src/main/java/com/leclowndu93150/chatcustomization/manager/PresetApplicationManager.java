package com.leclowndu93150.chatcustomization.manager;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.leclowndu93150.chatcustomization.data.*;
import com.leclowndu93150.chatcustomization.util.Permissions;
import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PresetApplicationManager {
    private static final long CHECK_INTERVAL_MS = 60_000; // 1 minute

    private final PresetManager presetManager;
    private final ChatManager chatManager;
    private final Map<UUID, Long> lastCheckTime = new ConcurrentHashMap<>();

    public PresetApplicationManager(@Nonnull PresetManager presetManager, @Nonnull ChatManager chatManager) {
        this.presetManager = presetManager;
        this.chatManager = chatManager;
    }

    public void checkAndApplyPresets(@Nonnull PlayerRef playerRef) {
        UUID uuid = playerRef.getUuid();
        long now = System.currentTimeMillis();

        Long lastCheck = lastCheckTime.get(uuid);
        if (lastCheck != null && (now - lastCheck) < CHECK_INTERVAL_MS) {
            return;
        }

        lastCheckTime.put(uuid, now);

        PlayerChatProfile profile = chatManager.getOrCreateProfile(uuid);
        PlayerChatProfile updatedProfile = applyHighestPriorityPresets(playerRef, profile);

        if (updatedProfile != profile) {
            chatManager.setProfile(uuid, updatedProfile);
        }
    }

    private PlayerChatProfile applyHighestPriorityPresets(@Nonnull PlayerRef playerRef, @Nonnull PlayerChatProfile profile) {
        PlayerChatProfile result = profile;

        // Apply highest priority style preset (full profile)
        StylePreset stylePreset = findHighestPriorityStylePreset(playerRef);
        if (stylePreset != null) {
            result = stylePreset.applyToProfile(result);
        }

        // Apply highest priority prefix preset (only if no style preset or style has no prefix)
        if (stylePreset == null || stylePreset.getPrefix().isEmpty()) {
            PrefixPreset prefixPreset = findHighestPriorityPrefixPreset(playerRef);
            if (prefixPreset != null) {
                result = prefixPreset.applyToProfile(result);
            }
        }

        // Apply highest priority pronoun preset (only if no style preset or style has no pronouns)
        if (stylePreset == null || stylePreset.getPronouns().isEmpty()) {
            PronounPreset pronounPreset = findHighestPriorityPronounPreset(playerRef);
            if (pronounPreset != null) {
                result = pronounPreset.applyToProfile(result);
            }
        }

        // Apply highest priority message preset (only if no style preset)
        if (stylePreset == null) {
            MessagePreset messagePreset = findHighestPriorityMessagePreset(playerRef);
            if (messagePreset != null) {
                result = messagePreset.applyToProfile(result);
            }
        }

        return result;
    }

    private StylePreset findHighestPriorityStylePreset(@Nonnull PlayerRef playerRef) {
        return presetManager.getAllStylePresets().stream()
            .filter(p -> Permissions.hasStylePreset(playerRef, p.getId()))
            .max((a, b) -> Integer.compare(a.getPriority(), b.getPriority()))
            .orElse(null);
    }

    private PrefixPreset findHighestPriorityPrefixPreset(@Nonnull PlayerRef playerRef) {
        return presetManager.getAllPrefixPresets().stream()
            .filter(p -> Permissions.hasPrefixPreset(playerRef, p.getId()))
            .max((a, b) -> Integer.compare(a.getPriority(), b.getPriority()))
            .orElse(null);
    }

    private PronounPreset findHighestPriorityPronounPreset(@Nonnull PlayerRef playerRef) {
        return presetManager.getAllPronounPresets().stream()
            .filter(p -> Permissions.hasPronounPreset(playerRef, p.getId()))
            .max((a, b) -> Integer.compare(a.getPriority(), b.getPriority()))
            .orElse(null);
    }

    private MessagePreset findHighestPriorityMessagePreset(@Nonnull PlayerRef playerRef) {
        return presetManager.getAllMessagePresets().stream()
            .filter(p -> Permissions.hasMessagePreset(playerRef, p.getId()))
            .max((a, b) -> Integer.compare(a.getPriority(), b.getPriority()))
            .orElse(null);
    }

    public void invalidateCache(@Nonnull UUID uuid) {
        lastCheckTime.remove(uuid);
    }

    public void clearCache() {
        lastCheckTime.clear();
    }
}
