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
import com.leclowndu93150.chatcustomization.data.PrefixPreset;
import com.leclowndu93150.chatcustomization.manager.PresetManager;
import com.leclowndu93150.chatcustomization.util.ColorUtil;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PrefixPresetEditorGui extends InteractiveCustomUIPage<PrefixPresetEditorGui.EditorData> {

    private final PresetManager presetManager;
    private final boolean isNewPreset;

    private String id;
    private String displayName;
    private int priority;
    private String prefix;
    private String prefixColor;
    private boolean prefixBold;
    private boolean prefixItalic;
    private boolean prefixUnderline;

    public PrefixPresetEditorGui(@Nonnull PlayerRef playerRef, @Nonnull PresetManager presetManager, @Nullable PrefixPreset existingPreset) {
        super(playerRef, CustomPageLifetime.CanDismiss, EditorData.CODEC);
        this.presetManager = presetManager;
        this.isNewPreset = existingPreset == null;

        if (existingPreset != null) {
            this.id = existingPreset.getId();
            this.displayName = existingPreset.getDisplayName();
            this.priority = existingPreset.getPriority();
            this.prefix = existingPreset.getPrefix();

            ElementStyle ps = existingPreset.getPrefixStyle();
            this.prefixColor = ps.color() != null ? ps.color() : "#FFFFFF";
            this.prefixBold = ps.bold();
            this.prefixItalic = ps.italic();
            this.prefixUnderline = ps.underline();
        } else {
            this.id = "";
            this.displayName = "";
            this.priority = 0;
            this.prefix = "";
            this.prefixColor = "#FFFFFF";
        }
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder cmd,
                      @Nonnull UIEventBuilder events, @Nonnull Store<EntityStore> store) {
        cmd.append("Pages/ChatCustom_PrefixPresetEditor.ui");

        cmd.set("#TitleLabel.Text", isNewPreset ? "Create Prefix Preset" : "Edit Prefix Preset: " + displayName);

        cmd.set("#IdField.Value", id);
        cmd.set("#IdField.IsReadOnly", !isNewPreset);
        cmd.set("#DisplayNameField.Value", displayName);
        cmd.set("#PriorityField.Value", String.valueOf(priority));

        cmd.set("#PrefixField.Value", prefix);
        cmd.set("#PrefixColorPicker.Value", prefixColor);
        cmd.set("#PrefixBold #CheckBox.Value", prefixBold);
        cmd.set("#PrefixItalic #CheckBox.Value", prefixItalic);
        cmd.set("#PrefixUnderline #CheckBox.Value", prefixUnderline);

        buildPreview(cmd);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#IdField",
            EventData.of("@Id", "#IdField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#DisplayNameField",
            EventData.of("@DisplayName", "#DisplayNameField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PriorityField",
            EventData.of("@Priority", "#PriorityField.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixField",
            EventData.of("@Prefix", "#PrefixField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixColorPicker",
            EventData.of("@PrefixColor", "#PrefixColorPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixBold #CheckBox",
            EventData.of("@PrefixBold", "#PrefixBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixItalic #CheckBox",
            EventData.of("@PrefixItalic", "#PrefixItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixUnderline #CheckBox",
            EventData.of("@PrefixUnderline", "#PrefixUnderline #CheckBox.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.Activating, "#SaveButton",
            EventData.of("Action", "Save"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#CancelButton",
            EventData.of("Action", "Cancel"), false);
    }

    private void buildPreview(UICommandBuilder cmd) {
        cmd.set("#PreviewPrefix.Text", prefix.isEmpty() ? "" : "[" + prefix + "] ");
        cmd.set("#PreviewPrefix.Style.TextColor", prefixColor);
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store,
                                @Nonnull EditorData data) {
        super.handleDataEvent(ref, store, data);

        if (data.id != null && isNewPreset) this.id = data.id.toLowerCase().replaceAll("[^a-z0-9_]", "");
        if (data.displayName != null) this.displayName = data.displayName;
        if (data.priority != null) this.priority = parseIntSafe(data.priority, 0);

        if (data.prefix != null) this.prefix = data.prefix;
        if (data.prefixColor != null) this.prefixColor = ColorUtil.toHex(data.prefixColor);
        if (data.prefixBold != null) this.prefixBold = data.prefixBold;
        if (data.prefixItalic != null) this.prefixItalic = data.prefixItalic;
        if (data.prefixUnderline != null) this.prefixUnderline = data.prefixUnderline;

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
        ElementStyle ps = new ElementStyle(
            prefixColor.equalsIgnoreCase("#FFFFFF") ? null : prefixColor,
            prefixBold, prefixItalic, prefixUnderline, null, null, false
        );

        PrefixPreset preset = new PrefixPreset(id, displayName, priority, prefix, ps);

        if (isNewPreset) {
            presetManager.addPrefixPreset(preset);
        } else {
            presetManager.updatePrefixPreset(preset);
        }
    }

    private void goBackToList(Ref<EntityStore> ref, Store<EntityStore> store) {
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        Player player = store.getComponent(ref, Player.getComponentType());
        player.getPageManager().openCustomPage(ref, store,
            new PresetListGui(playerRef, presetManager, CustomPageLifetime.CanDismiss, "prefix"));
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
            .addField(new KeyedCodec<>("@Prefix", Codec.STRING), (d, s) -> d.prefix = s, d -> d.prefix)
            .addField(new KeyedCodec<>("@PrefixColor", Codec.STRING), (d, s) -> d.prefixColor = s, d -> d.prefixColor)
            .addField(new KeyedCodec<>("@PrefixBold", Codec.BOOLEAN), (d, b) -> d.prefixBold = b, d -> d.prefixBold)
            .addField(new KeyedCodec<>("@PrefixItalic", Codec.BOOLEAN), (d, b) -> d.prefixItalic = b, d -> d.prefixItalic)
            .addField(new KeyedCodec<>("@PrefixUnderline", Codec.BOOLEAN), (d, b) -> d.prefixUnderline = b, d -> d.prefixUnderline)
            .addField(new KeyedCodec<>("Action", Codec.STRING), (d, s) -> d.action = s, d -> d.action)
            .build();

        private String id;
        private String displayName;
        private String priority;
        private String prefix;
        private String prefixColor;
        private Boolean prefixBold;
        private Boolean prefixItalic;
        private Boolean prefixUnderline;
        private String action;
    }
}
