package com.leclowndu93150.chatcustomization.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hypixel.hytale.logger.HytaleLogger;
import com.leclowndu93150.chatcustomization.data.ElementStyle;
import com.leclowndu93150.chatcustomization.data.PlayerChatProfile;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DataManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String PROFILES_FILE = "chat_profiles.json";

    private final Path dataDirectory;
    private final HytaleLogger logger;

    public DataManager(@Nonnull Path dataDirectory, @Nonnull HytaleLogger logger) {
        this.dataDirectory = dataDirectory;
        this.logger = logger;
    }

    public Map<UUID, PlayerChatProfile> loadProfiles() {
        Path file = dataDirectory.resolve(PROFILES_FILE);

        if (!Files.exists(file, new LinkOption[0])) {
            logger.atInfo().log("No existing %s file found, starting fresh.", PROFILES_FILE);
            return new ConcurrentHashMap<>();
        }

        try {
            String json = Files.readString(file);
            JsonObject root = GSON.fromJson(json, JsonObject.class);
            Map<UUID, PlayerChatProfile> result = new ConcurrentHashMap<>();

            if (root != null) {
                boolean needsMigration = false;
                for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                    try {
                        UUID uuid = UUID.fromString(entry.getKey());
                        JsonObject profileJson = entry.getValue().getAsJsonObject();
                        PlayerChatProfile profile = migrateProfile(profileJson);
                        if (!profileJson.has("prefixStyle")) {
                            needsMigration = true;
                        }
                        result.put(uuid, profile);
                    } catch (IllegalArgumentException e) {
                        logger.atWarning().log("Invalid UUID in profiles file: %s", entry.getKey());
                    }
                }
                logger.atInfo().log("Loaded %d chat profiles from %s", result.size(), PROFILES_FILE);
                if (needsMigration) {
                    logger.atInfo().log("Migrated old profile format to new ElementStyle format");
                }
            }
            return result;
        } catch (IOException e) {
            logger.atSevere().log("Failed to load %s: %s", PROFILES_FILE, e.getMessage());
        }
        return new ConcurrentHashMap<>();
    }

    private PlayerChatProfile migrateProfile(JsonObject json) {
        if (json.has("prefixStyle")) {
            PlayerChatProfile profile = GSON.fromJson(json, PlayerChatProfile.class);
            return sanitizeProfile(profile);
        }

        String prefix = getStringOrNull(json, "prefix");
        String suffix = getStringOrNull(json, "suffix");
        String nickname = getStringOrNull(json, "nickname");
        String pronouns = getStringOrNull(json, "pronouns");

        ElementStyle prefixStyle = new ElementStyle(
            getStringOrNull(json, "prefixColor"),
            getBoolOrFalse(json, "prefixBold"),
            getBoolOrFalse(json, "prefixItalic"),
            getBoolOrFalse(json, "prefixUnderline"),
            getStringOrNull(json, "prefixGradientStart"),
            getStringOrNull(json, "prefixGradientEnd"),
            getBoolOrFalse(json, "prefixRainbow")
        );

        ElementStyle nameStyle = new ElementStyle(
            getStringOrNull(json, "nameColor"),
            getBoolOrFalse(json, "nameBold"),
            getBoolOrFalse(json, "nameItalic"),
            getBoolOrFalse(json, "nameUnderline"),
            getStringOrNull(json, "nameGradientStart"),
            getStringOrNull(json, "nameGradientEnd"),
            getBoolOrFalse(json, "nameRainbow")
        );

        ElementStyle pronounsStyle = new ElementStyle(
            getStringOrNull(json, "pronounsColor"),
            getBoolOrFalse(json, "pronounsBold"),
            getBoolOrFalse(json, "pronounsItalic"),
            getBoolOrFalse(json, "pronounsUnderline"),
            getStringOrNull(json, "pronounsGradientStart"),
            getStringOrNull(json, "pronounsGradientEnd"),
            getBoolOrFalse(json, "pronounsRainbow")
        );

        ElementStyle suffixStyle = new ElementStyle(
            getStringOrNull(json, "suffixColor"),
            getBoolOrFalse(json, "suffixBold"),
            getBoolOrFalse(json, "suffixItalic"),
            getBoolOrFalse(json, "suffixUnderline"),
            getStringOrNull(json, "suffixGradientStart"),
            getStringOrNull(json, "suffixGradientEnd"),
            getBoolOrFalse(json, "suffixRainbow")
        );

        ElementStyle messageStyle = new ElementStyle(
            getStringOrNull(json, "messageColor"),
            getBoolOrFalse(json, "messageBold"),
            getBoolOrFalse(json, "messageItalic"),
            getBoolOrFalse(json, "messageUnderline"),
            getStringOrNull(json, "messageGradientStart"),
            getStringOrNull(json, "messageGradientEnd"),
            getBoolOrFalse(json, "messageRainbow")
        );

        boolean hasSeenWelcome = getBoolOrFalse(json, "hasSeenWelcome");

        return new PlayerChatProfile(prefix, suffix, nickname, pronouns,
            prefixStyle, nameStyle, pronounsStyle, suffixStyle, messageStyle, hasSeenWelcome);
    }

    private PlayerChatProfile sanitizeProfile(PlayerChatProfile profile) {
        if (profile == null) {
            return PlayerChatProfile.empty();
        }
        return new PlayerChatProfile(
            profile.prefix(),
            profile.suffix(),
            profile.nickname(),
            profile.pronouns(),
            profile.prefixStyle() != null ? profile.prefixStyle() : ElementStyle.EMPTY,
            profile.nameStyle() != null ? profile.nameStyle() : ElementStyle.EMPTY,
            profile.pronounsStyle() != null ? profile.pronounsStyle() : ElementStyle.EMPTY,
            profile.suffixStyle() != null ? profile.suffixStyle() : ElementStyle.EMPTY,
            profile.messageStyle() != null ? profile.messageStyle() : ElementStyle.EMPTY,
            profile.hasSeenWelcome()
        );
    }

    @Nullable
    private String getStringOrNull(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return null;
    }

    private boolean getBoolOrFalse(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsBoolean();
        }
        return false;
    }

    public void saveProfiles(Map<UUID, PlayerChatProfile> profiles) {
        try {
            if (!Files.exists(dataDirectory, new LinkOption[0])) {
                Files.createDirectories(dataDirectory);
            }

            Type type = new TypeToken<Map<String, PlayerChatProfile>>(){}.getType();
            Map<String, PlayerChatProfile> stringMap = new ConcurrentHashMap<>();

            for (Map.Entry<UUID, PlayerChatProfile> entry : profiles.entrySet()) {
                stringMap.put(entry.getKey().toString(), entry.getValue());
            }

            Path file = dataDirectory.resolve(PROFILES_FILE);
            String json = GSON.toJson(stringMap, type);
            Files.writeString(file, json);
            logger.atFine().log("Saved %d chat profiles to %s", profiles.size(), PROFILES_FILE);
        } catch (IOException e) {
            logger.atSevere().log("Failed to save %s: %s", PROFILES_FILE, e.getMessage());
        }
    }
}
