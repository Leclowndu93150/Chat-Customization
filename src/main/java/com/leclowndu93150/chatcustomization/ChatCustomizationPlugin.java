package com.leclowndu93150.chatcustomization;

import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.protocol.MaybeBool;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.util.TempAssetIdUtil;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.util.Config;
import com.leclowndu93150.chatcustomization.api.ChatFormatterAPI;
import com.leclowndu93150.chatcustomization.commands.*;
import com.leclowndu93150.chatcustomization.config.ChatCustomizationConfig;
import com.leclowndu93150.chatcustomization.data.ElementStyle;
import com.leclowndu93150.chatcustomization.data.PlayerChatProfile;
import com.leclowndu93150.chatcustomization.manager.ChatManager;
import com.leclowndu93150.chatcustomization.manager.DataManager;
import com.leclowndu93150.chatcustomization.util.ColorUtil;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChatCustomizationPlugin extends JavaPlugin {
    private final Config<ChatCustomizationConfig> config = this.withConfig(ChatCustomizationConfig.CODEC);
    private DataManager dataManager;
    private ChatManager chatManager;

    public ChatCustomizationPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        ChatCustomizationConfig cfg = this.config.get();
        this.dataManager = new DataManager(this.getDataDirectory(), this.getLogger());
        this.chatManager = new ChatManager(this.dataManager);

        EventRegistry eventRegistry = this.getEventRegistry();
        eventRegistry.registerGlobal(PlayerChatEvent.class, this::onPlayerChat);
        eventRegistry.registerGlobal(PlayerConnectEvent.class, this::onPlayerConnect);
    }

    @Override
    protected void start() {
        ChatCustomizationConfig cfg = this.config.get();
        this.config.save();
        this.getCommandRegistry().registerCommand(new PrefixCommand(this.chatManager, cfg.getMaxPrefixLength()));
        this.getCommandRegistry().registerCommand(new SuffixCommand(this.chatManager, cfg.getMaxSuffixLength()));
        this.getCommandRegistry().registerCommand(new NicknameCommand(this.chatManager, cfg.getMaxNicknameLength()));
        this.getCommandRegistry().registerCommand(new PronounsCommand(this.chatManager, cfg.getMaxPronounsLength()));
        this.getCommandRegistry().registerCommand(new NameColorCommand(this.chatManager));
        this.getCommandRegistry().registerCommand(new MsgColorCommand(this.chatManager));
        this.getCommandRegistry().registerCommand(new PrefixColorCommand(this.chatManager));
        this.getCommandRegistry().registerCommand(new SuffixColorCommand(this.chatManager));
        this.getCommandRegistry().registerCommand(new PronounsColorCommand(this.chatManager));
        this.getCommandRegistry().registerCommand(new ResetFormatCommand(this.chatManager));
        this.getCommandRegistry().registerCommand(new ChatProfileCommand(this.chatManager));

        this.getCommandRegistry().registerCommand(new StyleCommand(this.chatManager));
        this.getCommandRegistry().registerCommand(new GradientCommand(this.chatManager));
        this.getCommandRegistry().registerCommand(new RainbowCommand(this.chatManager));
        this.getCommandRegistry().registerCommand(new ColorsCommand());
        this.getCommandRegistry().registerCommand(new AdminChatCommand(this.chatManager, cfg));
        this.getCommandRegistry().registerCommand(new ChatEditorCommand(this.chatManager));
        this.getCommandRegistry().registerCommand(new HyssentialCommand(this.chatManager));

        this.getLogger().at(Level.INFO).log("ChatCustomization loaded!");
    }

    @Override
    protected void shutdown() {
        if (this.chatManager != null) {
            this.chatManager.save();
        }
    }

    private void onPlayerConnect(@Nonnull PlayerConnectEvent event) {
        PlayerRef player = event.getPlayerRef();
        PlayerChatProfile profile = chatManager.getProfile(player.getUuid());

        if (!profile.hasSeenWelcome()) {
            player.sendMessage(Message.empty()
                .insert(Message.raw("[ChatCustomization] ").color("#FFD700").bold(true))
                .insert(Message.raw("This server has chat customization! Use ").color("#AAAAAA"))
                .insert(Message.raw("/chateditor").color("#7ecc7e"))
                .insert(Message.raw(" to customize your chat appearance.").color("#AAAAAA")));
            chatManager.markWelcomeSeen(player.getUuid());
        }
    }

    private void onPlayerChat(@Nonnull PlayerChatEvent event) {
        PlayerRef sender = event.getSender();
        PlayerChatProfile profile = chatManager.getProfile(sender.getUuid());
        ChatCustomizationConfig cfg = this.config.get();

        if (cfg.getEnableMentions()) {
            processMentions(event.getContent(), cfg);
        }

        event.setFormatter((playerRef, message) -> {
            Message baseFormatted = buildFormattedMessage(playerRef, message, profile, cfg);
            return ChatFormatterAPI.processMessage(playerRef, message, baseFormatted);
        });
    }

    private Message buildFormattedMessage(PlayerRef playerRef, String message, PlayerChatProfile profile, ChatCustomizationConfig cfg) {
        Message result = Message.empty();

        if (profile.prefix() != null) {
            Message prefixMsg = buildStyledText("[" + profile.prefix() + "] ", profile.prefixStyle(), null, cfg);
            result.insert(prefixMsg);
        }

        String displayName = profile.nickname() != null ? profile.nickname() : playerRef.getUsername();
        Message nameMsg = buildStyledText(displayName, profile.nameStyle(), cfg.getDefaultNameColor(), cfg);
        result.insert(nameMsg);

        if (profile.pronouns() != null) {
            Message pronounsMsg = buildStyledText(" (" + profile.pronouns() + ")", profile.pronounsStyle(), "GRAY", cfg);
            result.insert(pronounsMsg);
        }

        if (profile.suffix() != null) {
            Message suffixMsg = buildStyledText(" [" + profile.suffix() + "]", profile.suffixStyle(), null, cfg);
            result.insert(suffixMsg);
        }

        result.insert(Message.raw(": "));

        Message contentMsg = buildStyledText(message, profile.messageStyle(), cfg.getDefaultMessageColor(), cfg);
        result.insert(contentMsg);

        return result;
    }

    private Message buildStyledText(String text, ElementStyle style, @Nullable String defaultColor, ChatCustomizationConfig cfg) {
        boolean useBold = style.bold() && cfg.getAllowBold();
        boolean useItalic = style.italic() && cfg.getAllowItalic();
        boolean useUnderline = style.underline() && cfg.getAllowUnderline();

        if (style.rainbow() && cfg.getAllowRainbow()) {
            return ColorUtil.createRainbowText(text, useBold, useItalic, useUnderline);
        }

        if (style.hasGradient() && cfg.getAllowGradients()) {
            return ColorUtil.createGradientText(text, style.gradientStart(), style.gradientEnd(),
                useBold, useItalic, useUnderline);
        }

        String color = style.color() != null ? style.color() : (defaultColor != null ? defaultColor : "WHITE");
        Message msg = Message.raw(text).color(ColorUtil.toHex(color));

        if (useBold) msg.bold(true);
        if (useItalic) msg.italic(true);
        if (useUnderline) applyUnderline(msg);

        return msg;
    }

    private void applyUnderline(Message msg) {
        msg.getFormattedMessage().underlined = MaybeBool.True;
    }

    private void processMentions(String message, ChatCustomizationConfig cfg) {
        String messageLower = message.toLowerCase();

        for (PlayerRef player : Universe.get().getPlayers()) {
            PlayerChatProfile profile = chatManager.getProfile(player.getUuid());
            String username = player.getUsername().toLowerCase();
            String nickname = profile.nickname() != null ? profile.nickname().toLowerCase() : null;

            if (messageLower.contains(username) || (nickname != null && messageLower.contains(nickname))) {
                player.sendMessage(Message.raw("You were mentioned in chat!").color(cfg.getMentionColor()).bold(true));

                if (cfg.getMentionSound()) {
                    playMentionSound(player, cfg);
                }
            }
        }
    }

    private void playMentionSound(PlayerRef player, ChatCustomizationConfig cfg) {
        try {
            String soundId = cfg.getMentionSoundId();
            if (soundId == null || soundId.isEmpty()) {
                return;
            }

            int soundEventIndex = TempAssetIdUtil.getSoundEventIndex(soundId);
            if (soundEventIndex == 0) {
                this.getLogger().atWarning().log("Mention sound '%s' not found in asset map", soundId);
                return;
            }

            SoundUtil.playSoundEvent2dToPlayer(
                player,
                soundEventIndex,
                SoundCategory.UI,
                cfg.getMentionSoundVolume(),
                cfg.getMentionSoundPitch()
            );
        } catch (Exception e) {
            this.getLogger().atWarning().log("Failed to play mention sound: %s", e.getMessage());
        }
    }
}
