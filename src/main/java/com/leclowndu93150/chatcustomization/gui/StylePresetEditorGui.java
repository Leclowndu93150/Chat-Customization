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
import com.leclowndu93150.chatcustomization.data.StylePreset;
import com.leclowndu93150.chatcustomization.manager.PresetManager;
import com.leclowndu93150.chatcustomization.util.ColorUtil;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StylePresetEditorGui extends InteractiveCustomUIPage<StylePresetEditorGui.EditorData> {

    private final PresetManager presetManager;
    private final boolean isNewPreset;

    private String id;
    private String displayName;
    private int priority;

    private String prefix;
    private String suffix;
    private String nickname;
    private String pronouns;

    private String prefixColor;
    private boolean prefixBold;
    private boolean prefixItalic;
    private boolean prefixUnderline;

    private String suffixColor;
    private boolean suffixBold;
    private boolean suffixItalic;
    private boolean suffixUnderline;

    private String nameColor;
    private boolean nameBold;
    private boolean nameItalic;
    private boolean nameUnderline;

    private String pronounsColor;
    private boolean pronounsBold;
    private boolean pronounsItalic;
    private boolean pronounsUnderline;

    private String messageColor;
    private boolean messageBold;
    private boolean messageItalic;
    private boolean messageUnderline;

    public StylePresetEditorGui(@Nonnull PlayerRef playerRef, @Nonnull PresetManager presetManager, @Nullable StylePreset existingPreset) {
        super(playerRef, CustomPageLifetime.CanDismiss, EditorData.CODEC);
        this.presetManager = presetManager;
        this.isNewPreset = existingPreset == null;

        if (existingPreset != null) {
            this.id = existingPreset.getId();
            this.displayName = existingPreset.getDisplayName();
            this.priority = existingPreset.getPriority();
            this.prefix = existingPreset.getPrefix();
            this.suffix = existingPreset.getSuffix();
            this.nickname = existingPreset.getNickname();
            this.pronouns = existingPreset.getPronouns();

            ElementStyle ps = existingPreset.getPrefixStyle();
            this.prefixColor = ps.color() != null ? ps.color() : "#FFFFFF";
            this.prefixBold = ps.bold();
            this.prefixItalic = ps.italic();
            this.prefixUnderline = ps.underline();

            ElementStyle ss = existingPreset.getSuffixStyle();
            this.suffixColor = ss.color() != null ? ss.color() : "#FFFFFF";
            this.suffixBold = ss.bold();
            this.suffixItalic = ss.italic();
            this.suffixUnderline = ss.underline();

            ElementStyle ns = existingPreset.getNameStyle();
            this.nameColor = ns.color() != null ? ns.color() : "#FFFFFF";
            this.nameBold = ns.bold();
            this.nameItalic = ns.italic();
            this.nameUnderline = ns.underline();

            ElementStyle prs = existingPreset.getPronounsStyle();
            this.pronounsColor = prs.color() != null ? prs.color() : "#AAAAAA";
            this.pronounsBold = prs.bold();
            this.pronounsItalic = prs.italic();
            this.pronounsUnderline = prs.underline();

            ElementStyle ms = existingPreset.getMessageStyle();
            this.messageColor = ms.color() != null ? ms.color() : "#FFFFFF";
            this.messageBold = ms.bold();
            this.messageItalic = ms.italic();
            this.messageUnderline = ms.underline();
        } else {
            this.id = "";
            this.displayName = "";
            this.priority = 0;
            this.prefix = "";
            this.suffix = "";
            this.nickname = "";
            this.pronouns = "";
            this.prefixColor = "#FFFFFF";
            this.suffixColor = "#FFFFFF";
            this.nameColor = "#FFFFFF";
            this.pronounsColor = "#AAAAAA";
            this.messageColor = "#FFFFFF";
        }
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder cmd,
                      @Nonnull UIEventBuilder events, @Nonnull Store<EntityStore> store) {
        cmd.append("Pages/ChatCustom_StylePresetEditor.ui");

        cmd.set("#TitleLabel.Text", isNewPreset ? "Create Style Preset" : "Edit Style Preset: " + displayName);

        cmd.set("#IdField.Value", id);
        cmd.set("#IdField.IsReadOnly", !isNewPreset);
        cmd.set("#DisplayNameField.Value", displayName);
        cmd.set("#PriorityField.Value", String.valueOf(priority));

        cmd.set("#PrefixField.Value", prefix);
        cmd.set("#SuffixField.Value", suffix);
        cmd.set("#NicknameField.Value", nickname);
        cmd.set("#PronounsField.Value", pronouns);

        cmd.set("#PrefixColorPicker.Value", prefixColor);
        cmd.set("#PrefixBold #CheckBox.Value", prefixBold);
        cmd.set("#PrefixItalic #CheckBox.Value", prefixItalic);
        cmd.set("#PrefixUnderline #CheckBox.Value", prefixUnderline);

        cmd.set("#SuffixColorPicker.Value", suffixColor);
        cmd.set("#SuffixBold #CheckBox.Value", suffixBold);
        cmd.set("#SuffixItalic #CheckBox.Value", suffixItalic);
        cmd.set("#SuffixUnderline #CheckBox.Value", suffixUnderline);

        cmd.set("#NameColorPicker.Value", nameColor);
        cmd.set("#NameBold #CheckBox.Value", nameBold);
        cmd.set("#NameItalic #CheckBox.Value", nameItalic);
        cmd.set("#NameUnderline #CheckBox.Value", nameUnderline);

        cmd.set("#PronounsColorPicker.Value", pronounsColor);
        cmd.set("#PronounsBold #CheckBox.Value", pronounsBold);
        cmd.set("#PronounsItalic #CheckBox.Value", pronounsItalic);
        cmd.set("#PronounsUnderline #CheckBox.Value", pronounsUnderline);

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

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixField",
            EventData.of("@Prefix", "#PrefixField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixField",
            EventData.of("@Suffix", "#SuffixField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NicknameField",
            EventData.of("@Nickname", "#NicknameField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsField",
            EventData.of("@Pronouns", "#PronounsField.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixColorPicker",
            EventData.of("@PrefixColor", "#PrefixColorPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixBold #CheckBox",
            EventData.of("@PrefixBold", "#PrefixBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixItalic #CheckBox",
            EventData.of("@PrefixItalic", "#PrefixItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixUnderline #CheckBox",
            EventData.of("@PrefixUnderline", "#PrefixUnderline #CheckBox.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixColorPicker",
            EventData.of("@SuffixColor", "#SuffixColorPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixBold #CheckBox",
            EventData.of("@SuffixBold", "#SuffixBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixItalic #CheckBox",
            EventData.of("@SuffixItalic", "#SuffixItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixUnderline #CheckBox",
            EventData.of("@SuffixUnderline", "#SuffixUnderline #CheckBox.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NameColorPicker",
            EventData.of("@NameColor", "#NameColorPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NameBold #CheckBox",
            EventData.of("@NameBold", "#NameBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NameItalic #CheckBox",
            EventData.of("@NameItalic", "#NameItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NameUnderline #CheckBox",
            EventData.of("@NameUnderline", "#NameUnderline #CheckBox.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsColorPicker",
            EventData.of("@PronounsColor", "#PronounsColorPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsBold #CheckBox",
            EventData.of("@PronounsBold", "#PronounsBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsItalic #CheckBox",
            EventData.of("@PronounsItalic", "#PronounsItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsUnderline #CheckBox",
            EventData.of("@PronounsUnderline", "#PronounsUnderline #CheckBox.Value"), false);

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
        String displayN = nickname.isEmpty() ? "PlayerName" : nickname;

        cmd.set("#PreviewPrefix.Text", prefix.isEmpty() ? "" : "[" + prefix + "] ");
        cmd.set("#PreviewPrefix.Style.TextColor", prefixColor);

        cmd.set("#PreviewName.Text", displayN);
        cmd.set("#PreviewName.Style.TextColor", nameColor);

        cmd.set("#PreviewPronouns.Text", pronouns.isEmpty() ? "" : " (" + pronouns + ")");
        cmd.set("#PreviewPronouns.Style.TextColor", pronounsColor);

        cmd.set("#PreviewSuffix.Text", suffix.isEmpty() ? "" : " [" + suffix + "]");
        cmd.set("#PreviewSuffix.Style.TextColor", suffixColor);

        cmd.set("#PreviewMessage.Style.TextColor", messageColor);
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store,
                                @Nonnull EditorData data) {
        super.handleDataEvent(ref, store, data);

        if (data.id != null && isNewPreset) this.id = data.id.toLowerCase().replaceAll("[^a-z0-9_]", "");
        if (data.displayName != null) this.displayName = data.displayName;
        if (data.priority != null) this.priority = parseIntSafe(data.priority, 0);

        if (data.prefix != null) this.prefix = data.prefix;
        if (data.suffix != null) this.suffix = data.suffix;
        if (data.nickname != null) this.nickname = data.nickname;
        if (data.pronouns != null) this.pronouns = data.pronouns;

        if (data.prefixColor != null) this.prefixColor = ColorUtil.toHex(data.prefixColor);
        if (data.prefixBold != null) this.prefixBold = data.prefixBold;
        if (data.prefixItalic != null) this.prefixItalic = data.prefixItalic;
        if (data.prefixUnderline != null) this.prefixUnderline = data.prefixUnderline;

        if (data.suffixColor != null) this.suffixColor = ColorUtil.toHex(data.suffixColor);
        if (data.suffixBold != null) this.suffixBold = data.suffixBold;
        if (data.suffixItalic != null) this.suffixItalic = data.suffixItalic;
        if (data.suffixUnderline != null) this.suffixUnderline = data.suffixUnderline;

        if (data.nameColor != null) this.nameColor = ColorUtil.toHex(data.nameColor);
        if (data.nameBold != null) this.nameBold = data.nameBold;
        if (data.nameItalic != null) this.nameItalic = data.nameItalic;
        if (data.nameUnderline != null) this.nameUnderline = data.nameUnderline;

        if (data.pronounsColor != null) this.pronounsColor = ColorUtil.toHex(data.pronounsColor);
        if (data.pronounsBold != null) this.pronounsBold = data.pronounsBold;
        if (data.pronounsItalic != null) this.pronounsItalic = data.pronounsItalic;
        if (data.pronounsUnderline != null) this.pronounsUnderline = data.pronounsUnderline;

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
        ElementStyle ps = new ElementStyle(
            prefixColor.equalsIgnoreCase("#FFFFFF") ? null : prefixColor,
            prefixBold, prefixItalic, prefixUnderline, null, null, false
        );
        ElementStyle ss = new ElementStyle(
            suffixColor.equalsIgnoreCase("#FFFFFF") ? null : suffixColor,
            suffixBold, suffixItalic, suffixUnderline, null, null, false
        );
        ElementStyle ns = new ElementStyle(
            nameColor.equalsIgnoreCase("#FFFFFF") ? null : nameColor,
            nameBold, nameItalic, nameUnderline, null, null, false
        );
        ElementStyle prs = new ElementStyle(
            pronounsColor.equalsIgnoreCase("#AAAAAA") ? null : pronounsColor,
            pronounsBold, pronounsItalic, pronounsUnderline, null, null, false
        );
        ElementStyle ms = new ElementStyle(
            messageColor.equalsIgnoreCase("#FFFFFF") ? null : messageColor,
            messageBold, messageItalic, messageUnderline, null, null, false
        );

        StylePreset preset = new StylePreset(
            id, displayName, priority,
            prefix, suffix, nickname, pronouns,
            ps, ss, ns, prs, ms
        );

        if (isNewPreset) {
            presetManager.addStylePreset(preset);
        } else {
            presetManager.updateStylePreset(preset);
        }
    }

    private void goBackToList(Ref<EntityStore> ref, Store<EntityStore> store) {
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        Player player = store.getComponent(ref, Player.getComponentType());
        player.getPageManager().openCustomPage(ref, store,
            new PresetListGui(playerRef, presetManager, CustomPageLifetime.CanDismiss, "style"));
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
            .addField(new KeyedCodec<>("@Suffix", Codec.STRING), (d, s) -> d.suffix = s, d -> d.suffix)
            .addField(new KeyedCodec<>("@Nickname", Codec.STRING), (d, s) -> d.nickname = s, d -> d.nickname)
            .addField(new KeyedCodec<>("@Pronouns", Codec.STRING), (d, s) -> d.pronouns = s, d -> d.pronouns)
            .addField(new KeyedCodec<>("@PrefixColor", Codec.STRING), (d, s) -> d.prefixColor = s, d -> d.prefixColor)
            .addField(new KeyedCodec<>("@PrefixBold", Codec.BOOLEAN), (d, b) -> d.prefixBold = b, d -> d.prefixBold)
            .addField(new KeyedCodec<>("@PrefixItalic", Codec.BOOLEAN), (d, b) -> d.prefixItalic = b, d -> d.prefixItalic)
            .addField(new KeyedCodec<>("@PrefixUnderline", Codec.BOOLEAN), (d, b) -> d.prefixUnderline = b, d -> d.prefixUnderline)
            .addField(new KeyedCodec<>("@SuffixColor", Codec.STRING), (d, s) -> d.suffixColor = s, d -> d.suffixColor)
            .addField(new KeyedCodec<>("@SuffixBold", Codec.BOOLEAN), (d, b) -> d.suffixBold = b, d -> d.suffixBold)
            .addField(new KeyedCodec<>("@SuffixItalic", Codec.BOOLEAN), (d, b) -> d.suffixItalic = b, d -> d.suffixItalic)
            .addField(new KeyedCodec<>("@SuffixUnderline", Codec.BOOLEAN), (d, b) -> d.suffixUnderline = b, d -> d.suffixUnderline)
            .addField(new KeyedCodec<>("@NameColor", Codec.STRING), (d, s) -> d.nameColor = s, d -> d.nameColor)
            .addField(new KeyedCodec<>("@NameBold", Codec.BOOLEAN), (d, b) -> d.nameBold = b, d -> d.nameBold)
            .addField(new KeyedCodec<>("@NameItalic", Codec.BOOLEAN), (d, b) -> d.nameItalic = b, d -> d.nameItalic)
            .addField(new KeyedCodec<>("@NameUnderline", Codec.BOOLEAN), (d, b) -> d.nameUnderline = b, d -> d.nameUnderline)
            .addField(new KeyedCodec<>("@PronounsColor", Codec.STRING), (d, s) -> d.pronounsColor = s, d -> d.pronounsColor)
            .addField(new KeyedCodec<>("@PronounsBold", Codec.BOOLEAN), (d, b) -> d.pronounsBold = b, d -> d.pronounsBold)
            .addField(new KeyedCodec<>("@PronounsItalic", Codec.BOOLEAN), (d, b) -> d.pronounsItalic = b, d -> d.pronounsItalic)
            .addField(new KeyedCodec<>("@PronounsUnderline", Codec.BOOLEAN), (d, b) -> d.pronounsUnderline = b, d -> d.pronounsUnderline)
            .addField(new KeyedCodec<>("@MessageColor", Codec.STRING), (d, s) -> d.messageColor = s, d -> d.messageColor)
            .addField(new KeyedCodec<>("@MessageBold", Codec.BOOLEAN), (d, b) -> d.messageBold = b, d -> d.messageBold)
            .addField(new KeyedCodec<>("@MessageItalic", Codec.BOOLEAN), (d, b) -> d.messageItalic = b, d -> d.messageItalic)
            .addField(new KeyedCodec<>("@MessageUnderline", Codec.BOOLEAN), (d, b) -> d.messageUnderline = b, d -> d.messageUnderline)
            .addField(new KeyedCodec<>("Action", Codec.STRING), (d, s) -> d.action = s, d -> d.action)
            .build();

        private String id;
        private String displayName;
        private String priority;
        private String prefix;
        private String suffix;
        private String nickname;
        private String pronouns;
        private String prefixColor;
        private Boolean prefixBold;
        private Boolean prefixItalic;
        private Boolean prefixUnderline;
        private String suffixColor;
        private Boolean suffixBold;
        private Boolean suffixItalic;
        private Boolean suffixUnderline;
        private String nameColor;
        private Boolean nameBold;
        private Boolean nameItalic;
        private Boolean nameUnderline;
        private String pronounsColor;
        private Boolean pronounsBold;
        private Boolean pronounsItalic;
        private Boolean pronounsUnderline;
        private String messageColor;
        private Boolean messageBold;
        private Boolean messageItalic;
        private Boolean messageUnderline;
        private String action;
    }
}
