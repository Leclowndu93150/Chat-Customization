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
import com.leclowndu93150.chatcustomization.data.ElementStyle;
import com.leclowndu93150.chatcustomization.data.MessagePreset;
import com.leclowndu93150.chatcustomization.manager.PresetManager;
import com.leclowndu93150.chatcustomization.util.ColorUtil;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MessagePresetEditorGui extends InteractiveCustomUIPage<MessagePresetEditorGui.EditorData> {

    private final PresetManager presetManager;
    private final boolean isNewPreset;

    private String id;
    private String displayName;
    private int priority;
    private String messageColor;
    private boolean messageBold;
    private boolean messageItalic;
    private boolean messageUnderline;

    public MessagePresetEditorGui(@Nonnull PlayerRef playerRef, @Nonnull PresetManager presetManager, @Nullable MessagePreset existingPreset) {
        super(playerRef, CustomPageLifetime.CanDismiss, EditorData.CODEC);
        this.presetManager = presetManager;
        this.isNewPreset = existingPreset == null;

        if (existingPreset != null) {
            this.id = existingPreset.getId();
            this.displayName = existingPreset.getDisplayName();
            this.priority = existingPreset.getPriority();

            ElementStyle ms = existingPreset.getMessageStyle();
            this.messageColor = ms.color() != null ? ms.color() : "#FFFFFF";
            this.messageBold = ms.bold();
            this.messageItalic = ms.italic();
            this.messageUnderline = ms.underline();
        } else {
            this.id = "";
            this.displayName = "";
            this.priority = 0;
            this.messageColor = "#FFFFFF";
        }
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder cmd,
                      @Nonnull UIEventBuilder events, @Nonnull Store<EntityStore> store) {
        cmd.append("Pages/ChatCustom_MessagePresetEditor.ui");

        cmd.set("#TitleLabel.Text", isNewPreset ? "Create Message Preset" : "Edit Message Preset: " + displayName);

        cmd.set("#IdField.Value", id);
        cmd.set("#IdField.IsReadOnly", !isNewPreset);
        cmd.set("#DisplayNameField.Value", displayName);
        cmd.set("#PriorityField.Value", String.valueOf(priority));

        cmd.set("#MessageColorPicker.Value", messageColor);
        cmd.set("#MessageBold #CheckBox.Value", messageBold);
        cmd.set("#MessageItalic #CheckBox.Value", messageItalic);
        cmd.set("#MessageUnderline #CheckBox.Value", messageUnderline);

        buildPreview(cmd);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#IdField",
            EventData.of("@Id", "#IdField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#DisplayNameField",
            EventData.of("@DisplayName", "#DisplayNameField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PriorityField",
            EventData.of("@Priority", "#PriorityField.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#MessageColorPicker",
            EventData.of("@MessageColor", "#MessageColorPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#MessageBold #CheckBox",
            EventData.of("@MessageBold", "#MessageBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#MessageItalic #CheckBox",
            EventData.of("@MessageItalic", "#MessageItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#MessageUnderline #CheckBox",
            EventData.of("@MessageUnderline", "#MessageUnderline #CheckBox.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.Activating, "#SaveButton",
            EventData.of("Action", "Save"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#CancelButton",
            EventData.of("Action", "Cancel"), false);
    }

    private void buildPreview(UICommandBuilder cmd) {
        cmd.set("#PreviewMessage.Text", "Hello, World!");
        cmd.set("#PreviewMessage.Style.TextColor", messageColor);
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store,
                                @Nonnull EditorData data) {
        super.handleDataEvent(ref, store, data);

        if (data.id != null && isNewPreset) this.id = data.id.toLowerCase().replaceAll("[^a-z0-9_]", "");
        if (data.displayName != null) this.displayName = data.displayName;
        if (data.priority != null) this.priority = parseIntSafe(data.priority, 0);

        if (data.messageColor != null) this.messageColor = ColorUtil.toHex(data.messageColor);
        if (data.messageBold != null) this.messageBold = data.messageBold;
        if (data.messageItalic != null) this.messageItalic = data.messageItalic;
        if (data.messageUnderline != null) this.messageUnderline = data.messageUnderline;

        if (data.action != null) {
            if (data.action.equals("Save")) {
                if (id.isEmpty() || displayName.isEmpty()) {
                    return;
                }
                savePreset();
                goBackToList(ref, store);
                return;
            }

            if (data.action.equals("Cancel")) {
                goBackToList(ref, store);
                return;
            }
        }

        UICommandBuilder cmd = new UICommandBuilder();
        buildPreview(cmd);
        this.sendUpdate(cmd);
    }

    private void savePreset() {
        ElementStyle ms = new ElementStyle(
            messageColor.equalsIgnoreCase("#FFFFFF") ? null : messageColor,
            messageBold, messageItalic, messageUnderline, null, null, false
        );

        MessagePreset preset = new MessagePreset(id, displayName, priority, ms);

        if (isNewPreset) {
            presetManager.addMessagePreset(preset);
        } else {
            presetManager.updateMessagePreset(preset);
        }
    }

    private void goBackToList(Ref<EntityStore> ref, Store<EntityStore> store) {
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        Player player = store.getComponent(ref, Player.getComponentType());
        player.getPageManager().openCustomPage(ref, store,
            new PresetListGui(playerRef, presetManager, CustomPageLifetime.CanDismiss, "message"));
    }

    private int parseIntSafe(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static class EditorData {
        public static final BuilderCodec<EditorData> CODEC = BuilderCodec.<EditorData>builder(EditorData.class, EditorData::new)
            .addField(new KeyedCodec<>("@Id", Codec.STRING), (d, s) -> d.id = s, d -> d.id)
            .addField(new KeyedCodec<>("@DisplayName", Codec.STRING), (d, s) -> d.displayName = s, d -> d.displayName)
            .addField(new KeyedCodec<>("@Priority", Codec.STRING), (d, s) -> d.priority = s, d -> d.priority)
            .addField(new KeyedCodec<>("@MessageColor", Codec.STRING), (d, s) -> d.messageColor = s, d -> d.messageColor)
            .addField(new KeyedCodec<>("@MessageBold", Codec.BOOLEAN), (d, b) -> d.messageBold = b, d -> d.messageBold)
            .addField(new KeyedCodec<>("@MessageItalic", Codec.BOOLEAN), (d, b) -> d.messageItalic = b, d -> d.messageItalic)
            .addField(new KeyedCodec<>("@MessageUnderline", Codec.BOOLEAN), (d, b) -> d.messageUnderline = b, d -> d.messageUnderline)
            .addField(new KeyedCodec<>("Action", Codec.STRING), (d, s) -> d.action = s, d -> d.action)
            .build();

        private String id;
        private String displayName;
        private String priority;
        private String messageColor;
        private Boolean messageBold;
        private Boolean messageItalic;
        private Boolean messageUnderline;
        private String action;
    }
}
