package com.leclowndu93150.chatcustomization.gui;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.leclowndu93150.chatcustomization.data.MessagePreset;
import com.leclowndu93150.chatcustomization.data.PlayerChatProfile;
import com.leclowndu93150.chatcustomization.data.PrefixPreset;
import com.leclowndu93150.chatcustomization.data.PronounPreset;
import com.leclowndu93150.chatcustomization.data.StylePreset;
import com.leclowndu93150.chatcustomization.manager.ChatManager;
import com.leclowndu93150.chatcustomization.manager.PresetManager;
import javax.annotation.Nonnull;
import java.util.List;

public class PlayerPresetSelectorGui extends InteractiveCustomUIPage<PlayerPresetSelectorGui.SelectorData> {

    private final PresetManager presetManager;
    private final ChatManager chatManager;
    private final PlayerRef playerRef;
    private String currentTab = "style";

    public PlayerPresetSelectorGui(@Nonnull PlayerRef playerRef, @Nonnull PresetManager presetManager,
                                   @Nonnull ChatManager chatManager, @Nonnull CustomPageLifetime lifetime) {
        super(playerRef, lifetime, SelectorData.CODEC);
        this.playerRef = playerRef;
        this.presetManager = presetManager;
        this.chatManager = chatManager;
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder cmd,
                      @Nonnull UIEventBuilder events, @Nonnull Store<EntityStore> store) {
        cmd.append("Pages/ChatCustom_PlayerPresetSelector.ui");

        events.addEventBinding(CustomUIEventBindingType.Activating, "#TabStyle",
            EventData.of("Action", "TabStyle"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#TabPrefix",
            EventData.of("Action", "TabPrefix"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#TabPronoun",
            EventData.of("Action", "TabPronoun"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#TabMessage",
            EventData.of("Action", "TabMessage"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#CloseButton",
            EventData.of("Action", "Close"), false);

        buildPresetList(cmd, events);
    }

    private void buildPresetList(UICommandBuilder cmd, UIEventBuilder events) {
        cmd.clear("#PresetList");
        cmd.appendInline("#Content #PresetListContainer", "Group #PresetList { LayoutMode: Top; }");

        switch (currentTab) {
            case "style" -> buildStylePresetList(cmd, events);
            case "prefix" -> buildPrefixPresetList(cmd, events);
            case "pronoun" -> buildPronounPresetList(cmd, events);
            case "message" -> buildMessagePresetList(cmd, events);
        }
    }

    private void buildStylePresetList(UICommandBuilder cmd, UIEventBuilder events) {
        List<StylePreset> presets = presetManager.getPlayerStylePresets(playerRef);

        if (presets.isEmpty()) {
            cmd.appendInline("#PresetList", "Group { LayoutMode: Center; Anchor: (Height: 50); Label { Text: \"No style presets available\"; Style: (FontSize: 14, TextColor: #6e7da1); } }");
            return;
        }

        for (int i = 0; i < presets.size(); i++) {
            StylePreset preset = presets.get(i);

            cmd.append("#PresetList", "Pages/ChatCustom_PresetSelectorEntry.ui");

            cmd.set("#PresetList[" + i + "] #PresetName.Text", preset.getDisplayName());
            cmd.set("#PresetList[" + i + "] #PresetDescription.Text", buildStyleDescription(preset));

            events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #ApplyButton",
                EventData.of("Action", "ApplyStyle:" + preset.getId()), false);
        }
    }

    private void buildPrefixPresetList(UICommandBuilder cmd, UIEventBuilder events) {
        List<PrefixPreset> presets = presetManager.getPlayerPrefixPresets(playerRef);

        if (presets.isEmpty()) {
            cmd.appendInline("#PresetList", "Group { LayoutMode: Center; Anchor: (Height: 50); Label { Text: \"No prefix presets available\"; Style: (FontSize: 14, TextColor: #6e7da1); } }");
            return;
        }

        for (int i = 0; i < presets.size(); i++) {
            PrefixPreset preset = presets.get(i);

            cmd.append("#PresetList", "Pages/ChatCustom_PresetSelectorEntry.ui");

            cmd.set("#PresetList[" + i + "] #PresetName.Text", preset.getDisplayName());
            cmd.set("#PresetList[" + i + "] #PresetDescription.Text", "Prefix: [" + preset.getPrefix() + "]");

            events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #ApplyButton",
                EventData.of("Action", "ApplyPrefix:" + preset.getId()), false);
        }
    }

    private void buildPronounPresetList(UICommandBuilder cmd, UIEventBuilder events) {
        List<PronounPreset> presets = presetManager.getPlayerPronounPresets(playerRef);

        if (presets.isEmpty()) {
            cmd.appendInline("#PresetList", "Group { LayoutMode: Center; Anchor: (Height: 50); Label { Text: \"No pronoun presets available\"; Style: (FontSize: 14, TextColor: #6e7da1); } }");
            return;
        }

        for (int i = 0; i < presets.size(); i++) {
            PronounPreset preset = presets.get(i);

            cmd.append("#PresetList", "Pages/ChatCustom_PresetSelectorEntry.ui");

            cmd.set("#PresetList[" + i + "] #PresetName.Text", preset.getDisplayName());
            cmd.set("#PresetList[" + i + "] #PresetDescription.Text", "(" + preset.getPronouns() + ")");

            events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #ApplyButton",
                EventData.of("Action", "ApplyPronoun:" + preset.getId()), false);
        }
    }

    private void buildMessagePresetList(UICommandBuilder cmd, UIEventBuilder events) {
        List<MessagePreset> presets = presetManager.getPlayerMessagePresets(playerRef);

        if (presets.isEmpty()) {
            cmd.appendInline("#PresetList", "Group { LayoutMode: Center; Anchor: (Height: 50); Label { Text: \"No message presets available\"; Style: (FontSize: 14, TextColor: #6e7da1); } }");
            return;
        }

        for (int i = 0; i < presets.size(); i++) {
            MessagePreset preset = presets.get(i);

            cmd.append("#PresetList", "Pages/ChatCustom_PresetSelectorEntry.ui");

            cmd.set("#PresetList[" + i + "] #PresetName.Text", preset.getDisplayName());
            cmd.set("#PresetList[" + i + "] #PresetDescription.Text", "Message style preset");

            events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #ApplyButton",
                EventData.of("Action", "ApplyMessage:" + preset.getId()), false);
        }
    }

    private String buildStyleDescription(StylePreset preset) {
        StringBuilder sb = new StringBuilder();
        if (!preset.getPrefix().isEmpty()) {
            sb.append("[").append(preset.getPrefix()).append("] ");
        }
        if (!preset.getNickname().isEmpty()) {
            sb.append(preset.getNickname());
        } else {
            sb.append("Name");
        }
        if (!preset.getPronouns().isEmpty()) {
            sb.append(" (").append(preset.getPronouns()).append(")");
        }
        if (!preset.getSuffix().isEmpty()) {
            sb.append(" [").append(preset.getSuffix()).append("]");
        }
        return sb.toString();
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store,
                                @Nonnull SelectorData data) {
        super.handleDataEvent(ref, store, data);

        if (data.action != null) {
            if (data.action.equals("Close")) {
                this.close();
                return;
            }

            if (data.action.equals("TabStyle")) {
                currentTab = "style";
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.equals("TabPrefix")) {
                currentTab = "prefix";
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.equals("TabPronoun")) {
                currentTab = "pronoun";
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.equals("TabMessage")) {
                currentTab = "message";
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.startsWith("ApplyStyle:")) {
                String presetId = data.action.substring(11);
                StylePreset preset = presetManager.getStylePresetById(presetId);
                if (preset != null) {
                    PlayerChatProfile profile = chatManager.getOrCreateProfile(playerRef.getUuid());
                    chatManager.setProfile(playerRef.getUuid(), preset.applyToProfile(profile));
                }
                this.close();
                return;
            }

            if (data.action.startsWith("ApplyPrefix:")) {
                String presetId = data.action.substring(12);
                PrefixPreset preset = presetManager.getPrefixPresetById(presetId);
                if (preset != null) {
                    PlayerChatProfile profile = chatManager.getOrCreateProfile(playerRef.getUuid());
                    chatManager.setProfile(playerRef.getUuid(), preset.applyToProfile(profile));
                }
                this.close();
                return;
            }

            if (data.action.startsWith("ApplyPronoun:")) {
                String presetId = data.action.substring(13);
                PronounPreset preset = presetManager.getPronounPresetById(presetId);
                if (preset != null) {
                    PlayerChatProfile profile = chatManager.getOrCreateProfile(playerRef.getUuid());
                    chatManager.setProfile(playerRef.getUuid(), preset.applyToProfile(profile));
                }
                this.close();
                return;
            }

            if (data.action.startsWith("ApplyMessage:")) {
                String presetId = data.action.substring(13);
                MessagePreset preset = presetManager.getMessagePresetById(presetId);
                if (preset != null) {
                    PlayerChatProfile profile = chatManager.getOrCreateProfile(playerRef.getUuid());
                    chatManager.setProfile(playerRef.getUuid(), preset.applyToProfile(profile));
                }
                this.close();
                return;
            }
        }
    }

    public static class SelectorData {
        public static final BuilderCodec<SelectorData> CODEC = BuilderCodec.<SelectorData>builder(SelectorData.class, SelectorData::new)
            .addField(new KeyedCodec<>("Action", Codec.STRING), (d, s) -> d.action = s, d -> d.action)
            .build();

        private String action;
    }
}
