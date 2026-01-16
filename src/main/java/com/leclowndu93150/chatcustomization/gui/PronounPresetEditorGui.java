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
import com.leclowndu93150.chatcustomization.data.PronounPreset;
import com.leclowndu93150.chatcustomization.manager.PresetManager;
import com.leclowndu93150.chatcustomization.util.ColorUtil;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PronounPresetEditorGui extends InteractiveCustomUIPage<PronounPresetEditorGui.EditorData> {

    private final PresetManager presetManager;
    private final boolean isNewPreset;

    private String id;
    private String displayName;
    private int priority;
    private String pronouns;
    private String pronounsColor;
    private boolean pronounsBold;
    private boolean pronounsItalic;
    private boolean pronounsUnderline;

    public PronounPresetEditorGui(@Nonnull PlayerRef playerRef, @Nonnull PresetManager presetManager, @Nullable PronounPreset existingPreset) {
        super(playerRef, CustomPageLifetime.CanDismiss, EditorData.CODEC);
        this.presetManager = presetManager;
        this.isNewPreset = existingPreset == null;

        if (existingPreset != null) {
            this.id = existingPreset.getId();
            this.displayName = existingPreset.getDisplayName();
            this.priority = existingPreset.getPriority();
            this.pronouns = existingPreset.getPronouns();

            ElementStyle ps = existingPreset.getPronounsStyle();
            this.pronounsColor = ps.color() != null ? ps.color() : "#AAAAAA";
            this.pronounsBold = ps.bold();
            this.pronounsItalic = ps.italic();
            this.pronounsUnderline = ps.underline();
        } else {
            this.id = "";
            this.displayName = "";
            this.priority = 0;
            this.pronouns = "";
            this.pronounsColor = "#AAAAAA";
        }
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder cmd,
                      @Nonnull UIEventBuilder events, @Nonnull Store<EntityStore> store) {
        cmd.append("Pages/ChatCustom_PronounPresetEditor.ui");

        cmd.set("#TitleLabel.Text", isNewPreset ? "Create Pronoun Preset" : "Edit Pronoun Preset: " + displayName);

        cmd.set("#IdField.Value", id);
        cmd.set("#IdField.IsReadOnly", !isNewPreset);
        cmd.set("#DisplayNameField.Value", displayName);
        cmd.set("#PriorityField.Value", String.valueOf(priority));

        cmd.set("#PronounsField.Value", pronouns);
        cmd.set("#PronounsColorPicker.Value", pronounsColor);
        cmd.set("#PronounsBold #CheckBox.Value", pronounsBold);
        cmd.set("#PronounsItalic #CheckBox.Value", pronounsItalic);
        cmd.set("#PronounsUnderline #CheckBox.Value", pronounsUnderline);

        buildPreview(cmd);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#IdField",
            EventData.of("@Id", "#IdField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#DisplayNameField",
            EventData.of("@DisplayName", "#DisplayNameField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PriorityField",
            EventData.of("@Priority", "#PriorityField.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsField",
            EventData.of("@Pronouns", "#PronounsField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsColorPicker",
            EventData.of("@PronounsColor", "#PronounsColorPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsBold #CheckBox",
            EventData.of("@PronounsBold", "#PronounsBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsItalic #CheckBox",
            EventData.of("@PronounsItalic", "#PronounsItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsUnderline #CheckBox",
            EventData.of("@PronounsUnderline", "#PronounsUnderline #CheckBox.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.Activating, "#SaveButton",
            EventData.of("Action", "Save"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#CancelButton",
            EventData.of("Action", "Cancel"), false);
    }

    private void buildPreview(UICommandBuilder cmd) {
        cmd.set("#PreviewPronouns.Text", pronouns.isEmpty() ? "" : " (" + pronouns + ")");
        cmd.set("#PreviewPronouns.Style.TextColor", pronounsColor);
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store,
                                @Nonnull EditorData data) {
        super.handleDataEvent(ref, store, data);

        if (data.id != null && isNewPreset) this.id = data.id.toLowerCase().replaceAll("[^a-z0-9_]", "");
        if (data.displayName != null) this.displayName = data.displayName;
        if (data.priority != null) this.priority = parseIntSafe(data.priority, 0);

        if (data.pronouns != null) this.pronouns = data.pronouns;
        if (data.pronounsColor != null) this.pronounsColor = ColorUtil.toHex(data.pronounsColor);
        if (data.pronounsBold != null) this.pronounsBold = data.pronounsBold;
        if (data.pronounsItalic != null) this.pronounsItalic = data.pronounsItalic;
        if (data.pronounsUnderline != null) this.pronounsUnderline = data.pronounsUnderline;

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
            pronounsColor.equalsIgnoreCase("#AAAAAA") ? null : pronounsColor,
            pronounsBold, pronounsItalic, pronounsUnderline, null, null, false
        );

        PronounPreset preset = new PronounPreset(id, displayName, priority, pronouns, ps);

        if (isNewPreset) {
            presetManager.addPronounPreset(preset);
        } else {
            presetManager.updatePronounPreset(preset);
        }
    }

    private void goBackToList(Ref<EntityStore> ref, Store<EntityStore> store) {
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        Player player = store.getComponent(ref, Player.getComponentType());
        player.getPageManager().openCustomPage(ref, store,
            new PresetListGui(playerRef, presetManager, CustomPageLifetime.CanDismiss, "pronoun"));
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
            .addField(new KeyedCodec<>("@Pronouns", Codec.STRING), (d, s) -> d.pronouns = s, d -> d.pronouns)
            .addField(new KeyedCodec<>("@PronounsColor", Codec.STRING), (d, s) -> d.pronounsColor = s, d -> d.pronounsColor)
            .addField(new KeyedCodec<>("@PronounsBold", Codec.BOOLEAN), (d, b) -> d.pronounsBold = b, d -> d.pronounsBold)
            .addField(new KeyedCodec<>("@PronounsItalic", Codec.BOOLEAN), (d, b) -> d.pronounsItalic = b, d -> d.pronounsItalic)
            .addField(new KeyedCodec<>("@PronounsUnderline", Codec.BOOLEAN), (d, b) -> d.pronounsUnderline = b, d -> d.pronounsUnderline)
            .addField(new KeyedCodec<>("Action", Codec.STRING), (d, s) -> d.action = s, d -> d.action)
            .build();

        private String id;
        private String displayName;
        private String priority;
        private String pronouns;
        private String pronounsColor;
        private Boolean pronounsBold;
        private Boolean pronounsItalic;
        private Boolean pronounsUnderline;
        private String action;
    }
}
