package com.leclowndu93150.chatcustomization.gui;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.leclowndu93150.chatcustomization.data.MessagePreset;
import com.leclowndu93150.chatcustomization.data.PrefixPreset;
import com.leclowndu93150.chatcustomization.data.PronounPreset;
import com.leclowndu93150.chatcustomization.data.StylePreset;
import com.leclowndu93150.chatcustomization.manager.PresetManager;
import javax.annotation.Nonnull;
import java.util.List;

public class PresetListGui extends InteractiveCustomUIPage<PresetListGui.PresetListData> {

    private final PresetManager presetManager;
    private String currentTab;
    private int deleteConfirmIndex = -1;

    public PresetListGui(@Nonnull PlayerRef playerRef, @Nonnull PresetManager presetManager, @Nonnull CustomPageLifetime lifetime) {
        this(playerRef, presetManager, lifetime, "style");
    }

    public PresetListGui(@Nonnull PlayerRef playerRef, @Nonnull PresetManager presetManager, @Nonnull CustomPageLifetime lifetime, @Nonnull String initialTab) {
        super(playerRef, lifetime, PresetListData.CODEC);
        this.presetManager = presetManager;
        this.currentTab = initialTab;
    }

    public String getCurrentTab() {
        return currentTab;
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder cmd,
                      @Nonnull UIEventBuilder events, @Nonnull Store<EntityStore> store) {
        cmd.append("Pages/ChatCustom_PresetList.ui");

        events.addEventBinding(CustomUIEventBindingType.Activating, "#TabStyle",
            EventData.of("Action", "TabStyle"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#TabPrefix",
            EventData.of("Action", "TabPrefix"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#TabPronoun",
            EventData.of("Action", "TabPronoun"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#TabMessage",
            EventData.of("Action", "TabMessage"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#CreateButton",
            EventData.of("Action", "Create"), false);
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
        List<StylePreset> presets = presetManager.getAllStylePresets();
        if (presets.isEmpty()) {
            cmd.appendInline("#PresetList", "Group { LayoutMode: Center; Anchor: (Height: 80); Label { Text: \"No style presets created yet\"; Style: (FontSize: 15, TextColor: #6e7da1); } }");
            return;
        }
        for (int i = 0; i < presets.size(); i++) {
            StylePreset preset = presets.get(i);

            cmd.append("#PresetList", "Pages/ChatCustom_PresetEntry.ui");

            cmd.set("#PresetList[" + i + "] #PresetName.Text", preset.getDisplayName());
            cmd.set("#PresetList[" + i + "] #PresetId.Text", "ID: " + preset.getId());
            cmd.set("#PresetList[" + i + "] #PresetPriority.Text", "Priority: " + preset.getPriority());
            cmd.set("#PresetList[" + i + "] #PresetPermission.Text", preset.getPermission());

            events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #EditButton",
                EventData.of("Action", "EditStyle:" + preset.getId()), false);

            if (deleteConfirmIndex == i) {
                cmd.set("#PresetList[" + i + "] #DeleteButton.Text", "?");
                events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "ConfirmDeleteStyle:" + preset.getId()), false);
                events.addEventBinding(CustomUIEventBindingType.MouseExited, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "CancelDelete"), false);
            } else {
                events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "DeleteStyle:" + i), false);
            }
        }
    }

    private void buildPrefixPresetList(UICommandBuilder cmd, UIEventBuilder events) {
        List<PrefixPreset> presets = presetManager.getAllPrefixPresets();
        if (presets.isEmpty()) {
            cmd.appendInline("#PresetList", "Group { LayoutMode: Center; Anchor: (Height: 80); Label { Text: \"No prefix presets created yet\"; Style: (FontSize: 15, TextColor: #6e7da1); } }");
            return;
        }
        for (int i = 0; i < presets.size(); i++) {
            PrefixPreset preset = presets.get(i);

            cmd.append("#PresetList", "Pages/ChatCustom_PresetEntry.ui");

            cmd.set("#PresetList[" + i + "] #PresetName.Text", preset.getDisplayName());
            cmd.set("#PresetList[" + i + "] #PresetId.Text", "ID: " + preset.getId());
            cmd.set("#PresetList[" + i + "] #PresetPriority.Text", "Priority: " + preset.getPriority());
            cmd.set("#PresetList[" + i + "] #PresetPermission.Text", preset.getPermission());

            events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #EditButton",
                EventData.of("Action", "EditPrefix:" + preset.getId()), false);

            if (deleteConfirmIndex == i) {
                cmd.set("#PresetList[" + i + "] #DeleteButton.Text", "?");
                events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "ConfirmDeletePrefix:" + preset.getId()), false);
                events.addEventBinding(CustomUIEventBindingType.MouseExited, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "CancelDelete"), false);
            } else {
                events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "DeletePrefix:" + i), false);
            }
        }
    }

    private void buildPronounPresetList(UICommandBuilder cmd, UIEventBuilder events) {
        List<PronounPreset> presets = presetManager.getAllPronounPresets();
        if (presets.isEmpty()) {
            cmd.appendInline("#PresetList", "Group { LayoutMode: Center; Anchor: (Height: 80); Label { Text: \"No pronoun presets created yet\"; Style: (FontSize: 15, TextColor: #6e7da1); } }");
            return;
        }
        for (int i = 0; i < presets.size(); i++) {
            PronounPreset preset = presets.get(i);

            cmd.append("#PresetList", "Pages/ChatCustom_PresetEntry.ui");

            cmd.set("#PresetList[" + i + "] #PresetName.Text", preset.getDisplayName());
            cmd.set("#PresetList[" + i + "] #PresetId.Text", "ID: " + preset.getId());
            cmd.set("#PresetList[" + i + "] #PresetPriority.Text", "Priority: " + preset.getPriority());
            cmd.set("#PresetList[" + i + "] #PresetPermission.Text", preset.getPermission());

            events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #EditButton",
                EventData.of("Action", "EditPronoun:" + preset.getId()), false);

            if (deleteConfirmIndex == i) {
                cmd.set("#PresetList[" + i + "] #DeleteButton.Text", "?");
                events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "ConfirmDeletePronoun:" + preset.getId()), false);
                events.addEventBinding(CustomUIEventBindingType.MouseExited, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "CancelDelete"), false);
            } else {
                events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "DeletePronoun:" + i), false);
            }
        }
    }

    private void buildMessagePresetList(UICommandBuilder cmd, UIEventBuilder events) {
        List<MessagePreset> presets = presetManager.getAllMessagePresets();
        if (presets.isEmpty()) {
            cmd.appendInline("#PresetList", "Group { LayoutMode: Center; Anchor: (Height: 80); Label { Text: \"No message presets created yet\"; Style: (FontSize: 15, TextColor: #6e7da1); } }");
            return;
        }
        for (int i = 0; i < presets.size(); i++) {
            MessagePreset preset = presets.get(i);

            cmd.append("#PresetList", "Pages/ChatCustom_PresetEntry.ui");

            cmd.set("#PresetList[" + i + "] #PresetName.Text", preset.getDisplayName());
            cmd.set("#PresetList[" + i + "] #PresetId.Text", "ID: " + preset.getId());
            cmd.set("#PresetList[" + i + "] #PresetPriority.Text", "Priority: " + preset.getPriority());
            cmd.set("#PresetList[" + i + "] #PresetPermission.Text", preset.getPermission());

            events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #EditButton",
                EventData.of("Action", "EditMessage:" + preset.getId()), false);

            if (deleteConfirmIndex == i) {
                cmd.set("#PresetList[" + i + "] #DeleteButton.Text", "?");
                events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "ConfirmDeleteMessage:" + preset.getId()), false);
                events.addEventBinding(CustomUIEventBindingType.MouseExited, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "CancelDelete"), false);
            } else {
                events.addEventBinding(CustomUIEventBindingType.Activating, "#PresetList[" + i + "] #DeleteButton",
                    EventData.of("Action", "DeleteMessage:" + i), false);
            }
        }
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store,
                                @Nonnull PresetListData data) {
        super.handleDataEvent(ref, store, data);

        if (data.action != null) {
            if (data.action.equals("Close")) {
                this.close();
                return;
            }

            if (data.action.equals("TabStyle")) {
                currentTab = "style";
                deleteConfirmIndex = -1;
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.equals("TabPrefix")) {
                currentTab = "prefix";
                deleteConfirmIndex = -1;
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.equals("TabPronoun")) {
                currentTab = "pronoun";
                deleteConfirmIndex = -1;
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.equals("TabMessage")) {
                currentTab = "message";
                deleteConfirmIndex = -1;
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.equals("Create")) {
                PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
                Player player = store.getComponent(ref, Player.getComponentType());
                switch (currentTab) {
                    case "style" -> player.getPageManager().openCustomPage(ref, store,
                        new StylePresetEditorGui(playerRef, presetManager, null));
                    case "prefix" -> player.getPageManager().openCustomPage(ref, store,
                        new PrefixPresetEditorGui(playerRef, presetManager, null));
                    case "pronoun" -> player.getPageManager().openCustomPage(ref, store,
                        new PronounPresetEditorGui(playerRef, presetManager, null));
                    case "message" -> player.getPageManager().openCustomPage(ref, store,
                        new MessagePresetEditorGui(playerRef, presetManager, null));
                }
                return;
            }

            if (data.action.startsWith("EditStyle:")) {
                String presetId = data.action.substring(10);
                StylePreset preset = presetManager.getStylePresetById(presetId);
                if (preset != null) {
                    PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
                    Player player = store.getComponent(ref, Player.getComponentType());
                    player.getPageManager().openCustomPage(ref, store,
                        new StylePresetEditorGui(playerRef, presetManager, preset));
                }
                return;
            }

            if (data.action.startsWith("EditPrefix:")) {
                String presetId = data.action.substring(11);
                PrefixPreset preset = presetManager.getPrefixPresetById(presetId);
                if (preset != null) {
                    PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
                    Player player = store.getComponent(ref, Player.getComponentType());
                    player.getPageManager().openCustomPage(ref, store,
                        new PrefixPresetEditorGui(playerRef, presetManager, preset));
                }
                return;
            }

            if (data.action.startsWith("EditPronoun:")) {
                String presetId = data.action.substring(12);
                PronounPreset preset = presetManager.getPronounPresetById(presetId);
                if (preset != null) {
                    PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
                    Player player = store.getComponent(ref, Player.getComponentType());
                    player.getPageManager().openCustomPage(ref, store,
                        new PronounPresetEditorGui(playerRef, presetManager, preset));
                }
                return;
            }

            if (data.action.startsWith("EditMessage:")) {
                String presetId = data.action.substring(12);
                MessagePreset preset = presetManager.getMessagePresetById(presetId);
                if (preset != null) {
                    PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
                    Player player = store.getComponent(ref, Player.getComponentType());
                    player.getPageManager().openCustomPage(ref, store,
                        new MessagePresetEditorGui(playerRef, presetManager, preset));
                }
                return;
            }

            if (data.action.startsWith("DeleteStyle:") || data.action.startsWith("DeletePrefix:") || data.action.startsWith("DeletePronoun:") || data.action.startsWith("DeleteMessage:")) {
                int colonIndex = data.action.indexOf(':');
                int index = Integer.parseInt(data.action.substring(colonIndex + 1));
                deleteConfirmIndex = index;
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.equals("CancelDelete")) {
                deleteConfirmIndex = -1;
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.startsWith("ConfirmDeleteStyle:")) {
                String presetId = data.action.substring(19);
                presetManager.removeStylePreset(presetId);
                deleteConfirmIndex = -1;
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.startsWith("ConfirmDeletePrefix:")) {
                String presetId = data.action.substring(20);
                presetManager.removePrefixPreset(presetId);
                deleteConfirmIndex = -1;
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.startsWith("ConfirmDeletePronoun:")) {
                String presetId = data.action.substring(21);
                presetManager.removePronounPreset(presetId);
                deleteConfirmIndex = -1;
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }

            if (data.action.startsWith("ConfirmDeleteMessage:")) {
                String presetId = data.action.substring(21);
                presetManager.removeMessagePreset(presetId);
                deleteConfirmIndex = -1;
                UICommandBuilder cmd = new UICommandBuilder();
                UIEventBuilder events = new UIEventBuilder();
                buildPresetList(cmd, events);
                this.sendUpdate(cmd, events, false);
                return;
            }
        }
    }

    public static class PresetListData {
        public static final BuilderCodec<PresetListData> CODEC = BuilderCodec.<PresetListData>builder(PresetListData.class, PresetListData::new)
            .addField(new KeyedCodec<>("Action", Codec.STRING), (d, s) -> d.action = s, d -> d.action)
            .build();

        private String action;
    }
}
