package com.leclowndu93150.chatcustomization.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.leclowndu93150.chatcustomization.data.MessagePreset;
import com.leclowndu93150.chatcustomization.data.PrefixPreset;
import com.leclowndu93150.chatcustomization.data.PronounPreset;
import com.leclowndu93150.chatcustomization.data.StylePreset;
import com.leclowndu93150.chatcustomization.util.Permissions;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class PresetManager {
    private static final String STYLE_PRESETS_FILE = "style_presets.json";
    private static final String PREFIX_PRESETS_FILE = "prefix_presets.json";
    private static final String PRONOUN_PRESETS_FILE = "pronoun_presets.json";
    private static final String MESSAGE_PRESETS_FILE = "message_presets.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path dataDirectory;
    private final HytaleLogger logger;
    private final List<StylePreset> stylePresets;
    private final List<PrefixPreset> prefixPresets;
    private final List<PronounPreset> pronounPresets;
    private final List<MessagePreset> messagePresets;

    public PresetManager(@Nonnull Path dataDirectory, @Nonnull HytaleLogger logger) {
        this.dataDirectory = dataDirectory;
        this.logger = logger;
        this.stylePresets = new CopyOnWriteArrayList<>();
        this.prefixPresets = new CopyOnWriteArrayList<>();
        this.pronounPresets = new CopyOnWriteArrayList<>();
        this.messagePresets = new CopyOnWriteArrayList<>();
        loadAllPresets();
    }

    private void loadAllPresets() {
        loadStylePresets();
        loadPrefixPresets();
        loadPronounPresets();
        loadMessagePresets();
    }

    private void loadStylePresets() {
        Path file = dataDirectory.resolve(STYLE_PRESETS_FILE);
        if (!Files.exists(file)) {
            logger.atInfo().log("No %s found, starting with empty list", STYLE_PRESETS_FILE);
            return;
        }

        try {
            String json = Files.readString(file);
            Type type = new TypeToken<StylePresetsWrapper>(){}.getType();
            StylePresetsWrapper wrapper = GSON.fromJson(json, type);

            stylePresets.clear();
            if (wrapper != null && wrapper.presets != null) {
                stylePresets.addAll(wrapper.presets);
            }

            stylePresets.sort(Comparator.comparingInt(StylePreset::getPriority).reversed());
            logger.atInfo().log("Loaded %d style presets from %s", stylePresets.size(), STYLE_PRESETS_FILE);
        } catch (IOException e) {
            logger.atSevere().log("Failed to load style presets: %s", e.getMessage());
        }
    }

    private void loadPrefixPresets() {
        Path file = dataDirectory.resolve(PREFIX_PRESETS_FILE);
        if (!Files.exists(file)) {
            logger.atInfo().log("No %s found, starting with empty list", PREFIX_PRESETS_FILE);
            return;
        }

        try {
            String json = Files.readString(file);
            Type type = new TypeToken<PrefixPresetsWrapper>(){}.getType();
            PrefixPresetsWrapper wrapper = GSON.fromJson(json, type);

            prefixPresets.clear();
            if (wrapper != null && wrapper.presets != null) {
                prefixPresets.addAll(wrapper.presets);
            }

            prefixPresets.sort(Comparator.comparingInt(PrefixPreset::getPriority).reversed());
            logger.atInfo().log("Loaded %d prefix presets from %s", prefixPresets.size(), PREFIX_PRESETS_FILE);
        } catch (IOException e) {
            logger.atSevere().log("Failed to load prefix presets: %s", e.getMessage());
        }
    }

    private void loadPronounPresets() {
        Path file = dataDirectory.resolve(PRONOUN_PRESETS_FILE);
        if (!Files.exists(file)) {
            logger.atInfo().log("No %s found, starting with empty list", PRONOUN_PRESETS_FILE);
            return;
        }

        try {
            String json = Files.readString(file);
            Type type = new TypeToken<PronounPresetsWrapper>(){}.getType();
            PronounPresetsWrapper wrapper = GSON.fromJson(json, type);

            pronounPresets.clear();
            if (wrapper != null && wrapper.presets != null) {
                pronounPresets.addAll(wrapper.presets);
            }

            pronounPresets.sort(Comparator.comparingInt(PronounPreset::getPriority).reversed());
            logger.atInfo().log("Loaded %d pronoun presets from %s", pronounPresets.size(), PRONOUN_PRESETS_FILE);
        } catch (IOException e) {
            logger.atSevere().log("Failed to load pronoun presets: %s", e.getMessage());
        }
    }

    private void loadMessagePresets() {
        Path file = dataDirectory.resolve(MESSAGE_PRESETS_FILE);
        if (!Files.exists(file)) {
            logger.atInfo().log("No %s found, starting with empty list", MESSAGE_PRESETS_FILE);
            return;
        }

        try {
            String json = Files.readString(file);
            Type type = new TypeToken<MessagePresetsWrapper>(){}.getType();
            MessagePresetsWrapper wrapper = GSON.fromJson(json, type);

            messagePresets.clear();
            if (wrapper != null && wrapper.presets != null) {
                messagePresets.addAll(wrapper.presets);
            }

            messagePresets.sort(Comparator.comparingInt(MessagePreset::getPriority).reversed());
            logger.atInfo().log("Loaded %d message presets from %s", messagePresets.size(), MESSAGE_PRESETS_FILE);
        } catch (IOException e) {
            logger.atSevere().log("Failed to load message presets: %s", e.getMessage());
        }
    }

    public void saveStylePresets() {
        try {
            if (!Files.exists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }

            StylePresetsWrapper wrapper = new StylePresetsWrapper();
            wrapper.presets = new ArrayList<>(stylePresets);

            Path file = dataDirectory.resolve(STYLE_PRESETS_FILE);
            Files.writeString(file, GSON.toJson(wrapper));
            logger.atInfo().log("Saved %d style presets to %s", stylePresets.size(), STYLE_PRESETS_FILE);
        } catch (IOException e) {
            logger.atSevere().log("Failed to save style presets: %s", e.getMessage());
        }
    }

    public void savePrefixPresets() {
        try {
            if (!Files.exists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }

            PrefixPresetsWrapper wrapper = new PrefixPresetsWrapper();
            wrapper.presets = new ArrayList<>(prefixPresets);

            Path file = dataDirectory.resolve(PREFIX_PRESETS_FILE);
            Files.writeString(file, GSON.toJson(wrapper));
            logger.atInfo().log("Saved %d prefix presets to %s", prefixPresets.size(), PREFIX_PRESETS_FILE);
        } catch (IOException e) {
            logger.atSevere().log("Failed to save prefix presets: %s", e.getMessage());
        }
    }

    public void savePronounPresets() {
        try {
            if (!Files.exists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }

            PronounPresetsWrapper wrapper = new PronounPresetsWrapper();
            wrapper.presets = new ArrayList<>(pronounPresets);

            Path file = dataDirectory.resolve(PRONOUN_PRESETS_FILE);
            Files.writeString(file, GSON.toJson(wrapper));
            logger.atInfo().log("Saved %d pronoun presets to %s", pronounPresets.size(), PRONOUN_PRESETS_FILE);
        } catch (IOException e) {
            logger.atSevere().log("Failed to save pronoun presets: %s", e.getMessage());
        }
    }

    public void saveMessagePresets() {
        try {
            if (!Files.exists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }

            MessagePresetsWrapper wrapper = new MessagePresetsWrapper();
            wrapper.presets = new ArrayList<>(messagePresets);

            Path file = dataDirectory.resolve(MESSAGE_PRESETS_FILE);
            Files.writeString(file, GSON.toJson(wrapper));
            logger.atInfo().log("Saved %d message presets to %s", messagePresets.size(), MESSAGE_PRESETS_FILE);
        } catch (IOException e) {
            logger.atSevere().log("Failed to save message presets: %s", e.getMessage());
        }
    }

    public void reload() {
        loadAllPresets();
    }

    @Nonnull
    public List<StylePreset> getAllStylePresets() {
        return new ArrayList<>(stylePresets);
    }

    @Nonnull
    public List<PrefixPreset> getAllPrefixPresets() {
        return new ArrayList<>(prefixPresets);
    }

    @Nonnull
    public List<PronounPreset> getAllPronounPresets() {
        return new ArrayList<>(pronounPresets);
    }

    @Nonnull
    public List<MessagePreset> getAllMessagePresets() {
        return new ArrayList<>(messagePresets);
    }

    @Nullable
    public StylePreset getStylePresetById(@Nonnull String id) {
        for (StylePreset preset : stylePresets) {
            if (preset.getId().equalsIgnoreCase(id)) {
                return preset;
            }
        }
        return null;
    }

    @Nullable
    public PrefixPreset getPrefixPresetById(@Nonnull String id) {
        for (PrefixPreset preset : prefixPresets) {
            if (preset.getId().equalsIgnoreCase(id)) {
                return preset;
            }
        }
        return null;
    }

    @Nullable
    public PronounPreset getPronounPresetById(@Nonnull String id) {
        for (PronounPreset preset : pronounPresets) {
            if (preset.getId().equalsIgnoreCase(id)) {
                return preset;
            }
        }
        return null;
    }

    @Nullable
    public MessagePreset getMessagePresetById(@Nonnull String id) {
        for (MessagePreset preset : messagePresets) {
            if (preset.getId().equalsIgnoreCase(id)) {
                return preset;
            }
        }
        return null;
    }

    @Nonnull
    public List<StylePreset> getPlayerStylePresets(@Nonnull PlayerRef player) {
        List<StylePreset> available = new ArrayList<>();
        for (StylePreset preset : stylePresets) {
            if (Permissions.hasStylePreset(player, preset.getId())) {
                available.add(preset);
            }
        }
        return available;
    }

    @Nonnull
    public List<PrefixPreset> getPlayerPrefixPresets(@Nonnull PlayerRef player) {
        List<PrefixPreset> available = new ArrayList<>();
        for (PrefixPreset preset : prefixPresets) {
            if (Permissions.hasPrefixPreset(player, preset.getId())) {
                available.add(preset);
            }
        }
        return available;
    }

    @Nonnull
    public List<PronounPreset> getPlayerPronounPresets(@Nonnull PlayerRef player) {
        List<PronounPreset> available = new ArrayList<>();
        for (PronounPreset preset : pronounPresets) {
            if (Permissions.hasPronounPreset(player, preset.getId())) {
                available.add(preset);
            }
        }
        return available;
    }

    @Nonnull
    public List<MessagePreset> getPlayerMessagePresets(@Nonnull PlayerRef player) {
        List<MessagePreset> available = new ArrayList<>();
        for (MessagePreset preset : messagePresets) {
            if (Permissions.hasMessagePreset(player, preset.getId())) {
                available.add(preset);
            }
        }
        return available;
    }

    public void addStylePreset(@Nonnull StylePreset preset) {
        stylePresets.removeIf(p -> p.getId().equalsIgnoreCase(preset.getId()));
        stylePresets.add(preset);
        stylePresets.sort(Comparator.comparingInt(StylePreset::getPriority).reversed());
        saveStylePresets();
    }

    public void addPrefixPreset(@Nonnull PrefixPreset preset) {
        prefixPresets.removeIf(p -> p.getId().equalsIgnoreCase(preset.getId()));
        prefixPresets.add(preset);
        prefixPresets.sort(Comparator.comparingInt(PrefixPreset::getPriority).reversed());
        savePrefixPresets();
    }

    public void addPronounPreset(@Nonnull PronounPreset preset) {
        pronounPresets.removeIf(p -> p.getId().equalsIgnoreCase(preset.getId()));
        pronounPresets.add(preset);
        pronounPresets.sort(Comparator.comparingInt(PronounPreset::getPriority).reversed());
        savePronounPresets();
    }

    public void addMessagePreset(@Nonnull MessagePreset preset) {
        messagePresets.removeIf(p -> p.getId().equalsIgnoreCase(preset.getId()));
        messagePresets.add(preset);
        messagePresets.sort(Comparator.comparingInt(MessagePreset::getPriority).reversed());
        saveMessagePresets();
    }

    public boolean removeStylePreset(@Nonnull String id) {
        boolean removed = stylePresets.removeIf(p -> p.getId().equalsIgnoreCase(id));
        if (removed) {
            saveStylePresets();
        }
        return removed;
    }

    public boolean removePrefixPreset(@Nonnull String id) {
        boolean removed = prefixPresets.removeIf(p -> p.getId().equalsIgnoreCase(id));
        if (removed) {
            savePrefixPresets();
        }
        return removed;
    }

    public boolean removePronounPreset(@Nonnull String id) {
        boolean removed = pronounPresets.removeIf(p -> p.getId().equalsIgnoreCase(id));
        if (removed) {
            savePronounPresets();
        }
        return removed;
    }

    public boolean removeMessagePreset(@Nonnull String id) {
        boolean removed = messagePresets.removeIf(p -> p.getId().equalsIgnoreCase(id));
        if (removed) {
            saveMessagePresets();
        }
        return removed;
    }

    public void updateStylePreset(@Nonnull StylePreset preset) {
        for (int i = 0; i < stylePresets.size(); i++) {
            if (stylePresets.get(i).getId().equalsIgnoreCase(preset.getId())) {
                stylePresets.set(i, preset);
                stylePresets.sort(Comparator.comparingInt(StylePreset::getPriority).reversed());
                saveStylePresets();
                return;
            }
        }
        addStylePreset(preset);
    }

    public void updatePrefixPreset(@Nonnull PrefixPreset preset) {
        for (int i = 0; i < prefixPresets.size(); i++) {
            if (prefixPresets.get(i).getId().equalsIgnoreCase(preset.getId())) {
                prefixPresets.set(i, preset);
                prefixPresets.sort(Comparator.comparingInt(PrefixPreset::getPriority).reversed());
                savePrefixPresets();
                return;
            }
        }
        addPrefixPreset(preset);
    }

    public void updatePronounPreset(@Nonnull PronounPreset preset) {
        for (int i = 0; i < pronounPresets.size(); i++) {
            if (pronounPresets.get(i).getId().equalsIgnoreCase(preset.getId())) {
                pronounPresets.set(i, preset);
                pronounPresets.sort(Comparator.comparingInt(PronounPreset::getPriority).reversed());
                savePronounPresets();
                return;
            }
        }
        addPronounPreset(preset);
    }

    public void updateMessagePreset(@Nonnull MessagePreset preset) {
        for (int i = 0; i < messagePresets.size(); i++) {
            if (messagePresets.get(i).getId().equalsIgnoreCase(preset.getId())) {
                messagePresets.set(i, preset);
                messagePresets.sort(Comparator.comparingInt(MessagePreset::getPriority).reversed());
                saveMessagePresets();
                return;
            }
        }
        addMessagePreset(preset);
    }

    public void grantStylePresetPermission(@Nonnull UUID playerUuid, @Nonnull String presetId) {
        StylePreset preset = getStylePresetById(presetId);
        if (preset != null) {
            String permission = preset.getPermission();
            logger.atInfo().log("Granting style preset permission '%s' to player %s", permission, playerUuid);
            PermissionsModule.get().addUserPermission(playerUuid, Set.of(permission));
        }
    }

    public void revokeStylePresetPermission(@Nonnull UUID playerUuid, @Nonnull String presetId) {
        StylePreset preset = getStylePresetById(presetId);
        if (preset != null) {
            String permission = preset.getPermission();
            logger.atInfo().log("Revoking style preset permission '%s' from player %s", permission, playerUuid);
            PermissionsModule.get().removeUserPermission(playerUuid, Set.of(permission));
        }
    }

    public void grantPrefixPresetPermission(@Nonnull UUID playerUuid, @Nonnull String presetId) {
        PrefixPreset preset = getPrefixPresetById(presetId);
        if (preset != null) {
            String permission = preset.getPermission();
            logger.atInfo().log("Granting prefix preset permission '%s' to player %s", permission, playerUuid);
            PermissionsModule.get().addUserPermission(playerUuid, Set.of(permission));
        }
    }

    public void revokePrefixPresetPermission(@Nonnull UUID playerUuid, @Nonnull String presetId) {
        PrefixPreset preset = getPrefixPresetById(presetId);
        if (preset != null) {
            String permission = preset.getPermission();
            logger.atInfo().log("Revoking prefix preset permission '%s' from player %s", permission, playerUuid);
            PermissionsModule.get().removeUserPermission(playerUuid, Set.of(permission));
        }
    }

    public void grantPronounPresetPermission(@Nonnull UUID playerUuid, @Nonnull String presetId) {
        PronounPreset preset = getPronounPresetById(presetId);
        if (preset != null) {
            String permission = preset.getPermission();
            logger.atInfo().log("Granting pronoun preset permission '%s' to player %s", permission, playerUuid);
            PermissionsModule.get().addUserPermission(playerUuid, Set.of(permission));
        }
    }

    public void revokePronounPresetPermission(@Nonnull UUID playerUuid, @Nonnull String presetId) {
        PronounPreset preset = getPronounPresetById(presetId);
        if (preset != null) {
            String permission = preset.getPermission();
            logger.atInfo().log("Revoking pronoun preset permission '%s' from player %s", permission, playerUuid);
            PermissionsModule.get().removeUserPermission(playerUuid, Set.of(permission));
        }
    }

    public void grantMessagePresetPermission(@Nonnull UUID playerUuid, @Nonnull String presetId) {
        MessagePreset preset = getMessagePresetById(presetId);
        if (preset != null) {
            String permission = preset.getPermission();
            logger.atInfo().log("Granting message preset permission '%s' to player %s", permission, playerUuid);
            PermissionsModule.get().addUserPermission(playerUuid, Set.of(permission));
        }
    }

    public void revokeMessagePresetPermission(@Nonnull UUID playerUuid, @Nonnull String presetId) {
        MessagePreset preset = getMessagePresetById(presetId);
        if (preset != null) {
            String permission = preset.getPermission();
            logger.atInfo().log("Revoking message preset permission '%s' from player %s", permission, playerUuid);
            PermissionsModule.get().removeUserPermission(playerUuid, Set.of(permission));
        }
    }

    private static class StylePresetsWrapper {
        List<StylePreset> presets;
    }

    private static class PrefixPresetsWrapper {
        List<PrefixPreset> presets;
    }

    private static class PronounPresetsWrapper {
        List<PronounPreset> presets;
    }

    private static class MessagePresetsWrapper {
        List<MessagePreset> presets;
    }
}
