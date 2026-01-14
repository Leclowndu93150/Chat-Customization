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
import com.leclowndu93150.chatcustomization.data.ElementStyle;
import com.leclowndu93150.chatcustomization.data.PlayerChatProfile;
import com.leclowndu93150.chatcustomization.manager.ChatManager;
import com.leclowndu93150.chatcustomization.util.ColorUtil;
import javax.annotation.Nonnull;

public class ChatEditorGui extends InteractiveCustomUIPage<ChatEditorGui.EditorData> {

    private final ChatManager chatManager;
    private final PlayerRef playerRef;

    private String prefix;
    private String nickname;
    private String pronouns;
    private String suffix;

    private String prefixColor;
    private String nameColor;
    private String pronounsColor;
    private String suffixColor;
    private String messageColor;

    private boolean prefixBold;
    private boolean prefixItalic;
    private boolean prefixUnderline;

    private boolean nameBold;
    private boolean nameItalic;
    private boolean nameUnderline;

    private boolean pronounsBold;
    private boolean pronounsItalic;
    private boolean pronounsUnderline;

    private boolean suffixBold;
    private boolean suffixItalic;
    private boolean suffixUnderline;

    private boolean messageBold;
    private boolean messageItalic;
    private boolean messageUnderline;

    private String prefixGradientStart;
    private String prefixGradientEnd;
    private boolean prefixRainbow;

    private String nameGradientStart;
    private String nameGradientEnd;
    private boolean nameRainbow;

    private String pronounsGradientStart;
    private String pronounsGradientEnd;
    private boolean pronounsRainbow;

    private String suffixGradientStart;
    private String suffixGradientEnd;
    private boolean suffixRainbow;

    private String messageGradientStart;
    private String messageGradientEnd;
    private boolean messageRainbow;

    private String currentTab = "editor";

    public ChatEditorGui(@Nonnull PlayerRef playerRef, @Nonnull ChatManager chatManager) {
        super(playerRef, CustomPageLifetime.CanDismiss, EditorData.CODEC);
        this.playerRef = playerRef;
        this.chatManager = chatManager;

        PlayerChatProfile profile = chatManager.getProfile(playerRef.getUuid());

        this.prefix = profile.prefix() != null ? profile.prefix() : "";
        this.nickname = profile.nickname() != null ? profile.nickname() : "";
        this.pronouns = profile.pronouns() != null ? profile.pronouns() : "";
        this.suffix = profile.suffix() != null ? profile.suffix() : "";

        this.prefixColor = ColorUtil.toHex(profile.prefixColor() != null ? profile.prefixColor() : "WHITE");
        this.nameColor = ColorUtil.toHex(profile.nameColor() != null ? profile.nameColor() : "WHITE");
        this.pronounsColor = ColorUtil.toHex(profile.pronounsColor() != null ? profile.pronounsColor() : "GRAY");
        this.suffixColor = ColorUtil.toHex(profile.suffixColor() != null ? profile.suffixColor() : "WHITE");
        this.messageColor = ColorUtil.toHex(profile.messageColor() != null ? profile.messageColor() : "WHITE");

        this.prefixBold = profile.prefixBold();
        this.prefixItalic = profile.prefixItalic();
        this.prefixUnderline = profile.prefixUnderline();
        this.nameBold = profile.nameBold();
        this.nameItalic = profile.nameItalic();
        this.nameUnderline = profile.nameUnderline();
        this.pronounsBold = profile.pronounsBold();
        this.pronounsItalic = profile.pronounsItalic();
        this.pronounsUnderline = profile.pronounsUnderline();
        this.suffixBold = profile.suffixBold();
        this.suffixItalic = profile.suffixItalic();
        this.suffixUnderline = profile.suffixUnderline();
        this.messageBold = profile.messageBold();
        this.messageItalic = profile.messageItalic();
        this.messageUnderline = profile.messageUnderline();

        this.prefixGradientStart = profile.prefixGradientStart() != null ? profile.prefixGradientStart() : "";
        this.prefixGradientEnd = profile.prefixGradientEnd() != null ? profile.prefixGradientEnd() : "";
        this.prefixRainbow = profile.prefixRainbow();

        this.nameGradientStart = profile.nameGradientStart() != null ? profile.nameGradientStart() : "";
        this.nameGradientEnd = profile.nameGradientEnd() != null ? profile.nameGradientEnd() : "";
        this.nameRainbow = profile.nameRainbow();

        this.pronounsGradientStart = profile.pronounsGradientStart() != null ? profile.pronounsGradientStart() : "";
        this.pronounsGradientEnd = profile.pronounsGradientEnd() != null ? profile.pronounsGradientEnd() : "";
        this.pronounsRainbow = profile.pronounsRainbow();

        this.suffixGradientStart = profile.suffixGradientStart() != null ? profile.suffixGradientStart() : "";
        this.suffixGradientEnd = profile.suffixGradientEnd() != null ? profile.suffixGradientEnd() : "";
        this.suffixRainbow = profile.suffixRainbow();

        this.messageGradientStart = profile.messageGradientStart() != null ? profile.messageGradientStart() : "";
        this.messageGradientEnd = profile.messageGradientEnd() != null ? profile.messageGradientEnd() : "";
        this.messageRainbow = profile.messageRainbow();
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder cmd,
                      @Nonnull UIEventBuilder events, @Nonnull Store<EntityStore> store) {
        cmd.append("Pages/ChatCustomization_Editor.ui");

        cmd.set("#PrefixField.Value", prefix);
        cmd.set("#NicknameField.Value", nickname);
        cmd.set("#PronounsField.Value", pronouns);
        cmd.set("#SuffixField.Value", suffix);

        cmd.set("#PrefixColorPicker.Value", prefixColor);
        cmd.set("#NameColorPicker.Value", nameColor);
        cmd.set("#PronounsColorPicker.Value", pronounsColor);
        cmd.set("#SuffixColorPicker.Value", suffixColor);
        cmd.set("#MessageColorPicker.Value", messageColor);

        cmd.set("#PrefixBold #CheckBox.Value", prefixBold);
        cmd.set("#PrefixItalic #CheckBox.Value", prefixItalic);
        cmd.set("#PrefixUnderline #CheckBox.Value", prefixUnderline);

        cmd.set("#NameBold #CheckBox.Value", nameBold);
        cmd.set("#NameItalic #CheckBox.Value", nameItalic);
        cmd.set("#NameUnderline #CheckBox.Value", nameUnderline);

        cmd.set("#PronounsBold #CheckBox.Value", pronounsBold);
        cmd.set("#PronounsItalic #CheckBox.Value", pronounsItalic);
        cmd.set("#PronounsUnderline #CheckBox.Value", pronounsUnderline);

        cmd.set("#SuffixBold #CheckBox.Value", suffixBold);
        cmd.set("#SuffixItalic #CheckBox.Value", suffixItalic);
        cmd.set("#SuffixUnderline #CheckBox.Value", suffixUnderline);

        cmd.set("#MessageBold #CheckBox.Value", messageBold);
        cmd.set("#MessageItalic #CheckBox.Value", messageItalic);
        cmd.set("#MessageUnderline #CheckBox.Value", messageUnderline);

        cmd.set("#PrefixGradientStartPicker.Value", prefixGradientStart.isEmpty() ? "#FFFFFF" : prefixGradientStart);
        cmd.set("#PrefixGradientEndPicker.Value", prefixGradientEnd.isEmpty() ? "#FFFFFF" : prefixGradientEnd);
        cmd.set("#PrefixRainbow #CheckBox.Value", prefixRainbow);
        cmd.set("#PrefixGradientPreview.Text", prefix.isEmpty() ? "[Prefix]" : "[" + prefix + "]");

        cmd.set("#NameGradientStartPicker.Value", nameGradientStart.isEmpty() ? "#FFFFFF" : nameGradientStart);
        cmd.set("#NameGradientEndPicker.Value", nameGradientEnd.isEmpty() ? "#FFFFFF" : nameGradientEnd);
        cmd.set("#NameRainbow #CheckBox.Value", nameRainbow);
        cmd.set("#NameGradientPreview.Text", nickname.isEmpty() ? playerRef.getUsername() : nickname);

        cmd.set("#PronounsGradientStartPicker.Value", pronounsGradientStart.isEmpty() ? "#AAAAAA" : pronounsGradientStart);
        cmd.set("#PronounsGradientEndPicker.Value", pronounsGradientEnd.isEmpty() ? "#AAAAAA" : pronounsGradientEnd);
        cmd.set("#PronounsRainbow #CheckBox.Value", pronounsRainbow);
        cmd.set("#PronounsGradientPreview.Text", pronouns.isEmpty() ? "(he/him)" : "(" + pronouns + ")");

        cmd.set("#SuffixGradientStartPicker.Value", suffixGradientStart.isEmpty() ? "#FFFFFF" : suffixGradientStart);
        cmd.set("#SuffixGradientEndPicker.Value", suffixGradientEnd.isEmpty() ? "#FFFFFF" : suffixGradientEnd);
        cmd.set("#SuffixRainbow #CheckBox.Value", suffixRainbow);
        cmd.set("#SuffixGradientPreview.Text", suffix.isEmpty() ? "[Suffix]" : "[" + suffix + "]");

        cmd.set("#MessageGradientStartPicker.Value", messageGradientStart.isEmpty() ? "#FFFFFF" : messageGradientStart);
        cmd.set("#MessageGradientEndPicker.Value", messageGradientEnd.isEmpty() ? "#FFFFFF" : messageGradientEnd);
        cmd.set("#MessageRainbow #CheckBox.Value", messageRainbow);

        cmd.set("#EditorContent.Visible", currentTab.equals("editor"));
        cmd.set("#GradientsContent.Visible", currentTab.equals("gradients"));
        cmd.set("#HelpContent.Visible", currentTab.equals("help"));

        buildPreview(cmd);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixField",
            EventData.of("@Prefix", "#PrefixField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NicknameField",
            EventData.of("@Nickname", "#NicknameField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsField",
            EventData.of("@Pronouns", "#PronounsField.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixField",
            EventData.of("@Suffix", "#SuffixField.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixColorPicker",
            EventData.of("@PrefixColor", "#PrefixColorPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NameColorPicker",
            EventData.of("@NameColor", "#NameColorPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsColorPicker",
            EventData.of("@PronounsColor", "#PronounsColorPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixColorPicker",
            EventData.of("@SuffixColor", "#SuffixColorPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#MessageColorPicker",
            EventData.of("@MessageColor", "#MessageColorPicker.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixBold #CheckBox",
            EventData.of("@PrefixBold", "#PrefixBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixItalic #CheckBox",
            EventData.of("@PrefixItalic", "#PrefixItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixUnderline #CheckBox",
            EventData.of("@PrefixUnderline", "#PrefixUnderline #CheckBox.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NameBold #CheckBox",
            EventData.of("@NameBold", "#NameBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NameItalic #CheckBox",
            EventData.of("@NameItalic", "#NameItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NameUnderline #CheckBox",
            EventData.of("@NameUnderline", "#NameUnderline #CheckBox.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsBold #CheckBox",
            EventData.of("@PronounsBold", "#PronounsBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsItalic #CheckBox",
            EventData.of("@PronounsItalic", "#PronounsItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsUnderline #CheckBox",
            EventData.of("@PronounsUnderline", "#PronounsUnderline #CheckBox.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixBold #CheckBox",
            EventData.of("@SuffixBold", "#SuffixBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixItalic #CheckBox",
            EventData.of("@SuffixItalic", "#SuffixItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixUnderline #CheckBox",
            EventData.of("@SuffixUnderline", "#SuffixUnderline #CheckBox.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#MessageBold #CheckBox",
            EventData.of("@MessageBold", "#MessageBold #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#MessageItalic #CheckBox",
            EventData.of("@MessageItalic", "#MessageItalic #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#MessageUnderline #CheckBox",
            EventData.of("@MessageUnderline", "#MessageUnderline #CheckBox.Value"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixGradientStartPicker",
            EventData.of("@PrefixGradientStart", "#PrefixGradientStartPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixGradientEndPicker",
            EventData.of("@PrefixGradientEnd", "#PrefixGradientEndPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PrefixRainbow #CheckBox",
            EventData.of("@PrefixRainbow", "#PrefixRainbow #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#PrefixGradientClear",
            EventData.of("Action", "ClearPrefixGradient"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NameGradientStartPicker",
            EventData.of("@NameGradientStart", "#NameGradientStartPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NameGradientEndPicker",
            EventData.of("@NameGradientEnd", "#NameGradientEndPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#NameRainbow #CheckBox",
            EventData.of("@NameRainbow", "#NameRainbow #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#NameGradientClear",
            EventData.of("Action", "ClearNameGradient"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsGradientStartPicker",
            EventData.of("@PronounsGradientStart", "#PronounsGradientStartPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsGradientEndPicker",
            EventData.of("@PronounsGradientEnd", "#PronounsGradientEndPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#PronounsRainbow #CheckBox",
            EventData.of("@PronounsRainbow", "#PronounsRainbow #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#PronounsGradientClear",
            EventData.of("Action", "ClearPronounsGradient"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixGradientStartPicker",
            EventData.of("@SuffixGradientStart", "#SuffixGradientStartPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixGradientEndPicker",
            EventData.of("@SuffixGradientEnd", "#SuffixGradientEndPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#SuffixRainbow #CheckBox",
            EventData.of("@SuffixRainbow", "#SuffixRainbow #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#SuffixGradientClear",
            EventData.of("Action", "ClearSuffixGradient"), false);

        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#MessageGradientStartPicker",
            EventData.of("@MessageGradientStart", "#MessageGradientStartPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#MessageGradientEndPicker",
            EventData.of("@MessageGradientEnd", "#MessageGradientEndPicker.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.ValueChanged, "#MessageRainbow #CheckBox",
            EventData.of("@MessageRainbow", "#MessageRainbow #CheckBox.Value"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#MessageGradientClear",
            EventData.of("Action", "ClearMessageGradient"), false);

        events.addEventBinding(CustomUIEventBindingType.Activating, "#TabEditor",
            EventData.of("Action", "TabEditor"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#TabGradients",
            EventData.of("Action", "TabGradients"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#TabHelp",
            EventData.of("Action", "TabHelp"), false);

        events.addEventBinding(CustomUIEventBindingType.Activating, "#SaveButton",
            EventData.of("Action", "Save"), false);
        events.addEventBinding(CustomUIEventBindingType.Activating, "#CancelButton",
            EventData.of("Action", "Cancel"), false);
    }

    private void buildPreview(UICommandBuilder cmd) {
        String displayName = nickname.isEmpty() ? playerRef.getUsername() : nickname;

        cmd.set("#PreviewPrefix.Text", prefix.isEmpty() ? "" : "[" + prefix + "] ");
        cmd.set("#PreviewPrefix.Style.TextColor", prefixColor);

        cmd.set("#PreviewName.Text", displayName);
        cmd.set("#PreviewName.Style.TextColor", nameColor);

        cmd.set("#PreviewPronouns.Text", pronouns.isEmpty() ? "" : " (" + pronouns + ")");
        cmd.set("#PreviewPronouns.Style.TextColor", pronounsColor);

        cmd.set("#PreviewSuffix.Text", suffix.isEmpty() ? "" : " [" + suffix + "]");
        cmd.set("#PreviewSuffix.Style.TextColor", suffixColor);

        cmd.set("#PreviewMessage.Style.TextColor", messageColor);

        cmd.set("#PrefixGradientPreview.Text", prefix.isEmpty() ? "[Prefix]" : "[" + prefix + "]");
        cmd.set("#PrefixGradientPreview.Style.TextColor", prefixGradientStart.isEmpty() ? "#FFFFFF" : prefixGradientStart);

        cmd.set("#NameGradientPreview.Text", displayName);
        cmd.set("#NameGradientPreview.Style.TextColor", nameGradientStart.isEmpty() ? "#FFFFFF" : nameGradientStart);

        cmd.set("#PronounsGradientPreview.Text", pronouns.isEmpty() ? "(he/him)" : "(" + pronouns + ")");
        cmd.set("#PronounsGradientPreview.Style.TextColor", pronounsGradientStart.isEmpty() ? "#AAAAAA" : pronounsGradientStart);

        cmd.set("#SuffixGradientPreview.Text", suffix.isEmpty() ? "[Suffix]" : "[" + suffix + "]");
        cmd.set("#SuffixGradientPreview.Style.TextColor", suffixGradientStart.isEmpty() ? "#FFFFFF" : suffixGradientStart);

        cmd.set("#MessageGradientPreview.Style.TextColor", messageGradientStart.isEmpty() ? "#FFFFFF" : messageGradientStart);

        cmd.set("#PrefixGradientStartPicker.Value", prefixGradientStart.isEmpty() ? "#FFFFFF" : prefixGradientStart);
        cmd.set("#PrefixGradientEndPicker.Value", prefixGradientEnd.isEmpty() ? "#FFFFFF" : prefixGradientEnd);
        cmd.set("#PrefixRainbow #CheckBox.Value", prefixRainbow);

        cmd.set("#NameGradientStartPicker.Value", nameGradientStart.isEmpty() ? "#FFFFFF" : nameGradientStart);
        cmd.set("#NameGradientEndPicker.Value", nameGradientEnd.isEmpty() ? "#FFFFFF" : nameGradientEnd);
        cmd.set("#NameRainbow #CheckBox.Value", nameRainbow);

        cmd.set("#PronounsGradientStartPicker.Value", pronounsGradientStart.isEmpty() ? "#AAAAAA" : pronounsGradientStart);
        cmd.set("#PronounsGradientEndPicker.Value", pronounsGradientEnd.isEmpty() ? "#AAAAAA" : pronounsGradientEnd);
        cmd.set("#PronounsRainbow #CheckBox.Value", pronounsRainbow);

        cmd.set("#SuffixGradientStartPicker.Value", suffixGradientStart.isEmpty() ? "#FFFFFF" : suffixGradientStart);
        cmd.set("#SuffixGradientEndPicker.Value", suffixGradientEnd.isEmpty() ? "#FFFFFF" : suffixGradientEnd);
        cmd.set("#SuffixRainbow #CheckBox.Value", suffixRainbow);

        cmd.set("#MessageGradientStartPicker.Value", messageGradientStart.isEmpty() ? "#FFFFFF" : messageGradientStart);
        cmd.set("#MessageGradientEndPicker.Value", messageGradientEnd.isEmpty() ? "#FFFFFF" : messageGradientEnd);
        cmd.set("#MessageRainbow #CheckBox.Value", messageRainbow);
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store,
                                @Nonnull EditorData data) {
        super.handleDataEvent(ref, store, data);

        if (data.prefix != null) this.prefix = data.prefix;
        if (data.nickname != null) this.nickname = data.nickname;
        if (data.pronouns != null) this.pronouns = data.pronouns;
        if (data.suffix != null) this.suffix = data.suffix;

        if (data.prefixColor != null) this.prefixColor = ColorUtil.toHex(data.prefixColor);
        if (data.nameColor != null) this.nameColor = ColorUtil.toHex(data.nameColor);
        if (data.pronounsColor != null) this.pronounsColor = ColorUtil.toHex(data.pronounsColor);
        if (data.suffixColor != null) this.suffixColor = ColorUtil.toHex(data.suffixColor);
        if (data.messageColor != null) this.messageColor = ColorUtil.toHex(data.messageColor);

        if (data.prefixBold != null) this.prefixBold = data.prefixBold;
        if (data.prefixItalic != null) this.prefixItalic = data.prefixItalic;
        if (data.prefixUnderline != null) this.prefixUnderline = data.prefixUnderline;
        if (data.nameBold != null) this.nameBold = data.nameBold;
        if (data.nameItalic != null) this.nameItalic = data.nameItalic;
        if (data.nameUnderline != null) this.nameUnderline = data.nameUnderline;
        if (data.pronounsBold != null) this.pronounsBold = data.pronounsBold;
        if (data.pronounsItalic != null) this.pronounsItalic = data.pronounsItalic;
        if (data.pronounsUnderline != null) this.pronounsUnderline = data.pronounsUnderline;
        if (data.suffixBold != null) this.suffixBold = data.suffixBold;
        if (data.suffixItalic != null) this.suffixItalic = data.suffixItalic;
        if (data.suffixUnderline != null) this.suffixUnderline = data.suffixUnderline;
        if (data.messageBold != null) this.messageBold = data.messageBold;
        if (data.messageItalic != null) this.messageItalic = data.messageItalic;
        if (data.messageUnderline != null) this.messageUnderline = data.messageUnderline;

        if (data.prefixGradientStart != null) this.prefixGradientStart = ColorUtil.toHex(data.prefixGradientStart);
        if (data.prefixGradientEnd != null) this.prefixGradientEnd = ColorUtil.toHex(data.prefixGradientEnd);
        if (data.prefixRainbow != null) this.prefixRainbow = data.prefixRainbow;

        if (data.nameGradientStart != null) this.nameGradientStart = ColorUtil.toHex(data.nameGradientStart);
        if (data.nameGradientEnd != null) this.nameGradientEnd = ColorUtil.toHex(data.nameGradientEnd);
        if (data.nameRainbow != null) this.nameRainbow = data.nameRainbow;

        if (data.pronounsGradientStart != null) this.pronounsGradientStart = ColorUtil.toHex(data.pronounsGradientStart);
        if (data.pronounsGradientEnd != null) this.pronounsGradientEnd = ColorUtil.toHex(data.pronounsGradientEnd);
        if (data.pronounsRainbow != null) this.pronounsRainbow = data.pronounsRainbow;

        if (data.suffixGradientStart != null) this.suffixGradientStart = ColorUtil.toHex(data.suffixGradientStart);
        if (data.suffixGradientEnd != null) this.suffixGradientEnd = ColorUtil.toHex(data.suffixGradientEnd);
        if (data.suffixRainbow != null) this.suffixRainbow = data.suffixRainbow;

        if (data.messageGradientStart != null) this.messageGradientStart = ColorUtil.toHex(data.messageGradientStart);
        if (data.messageGradientEnd != null) this.messageGradientEnd = ColorUtil.toHex(data.messageGradientEnd);
        if (data.messageRainbow != null) this.messageRainbow = data.messageRainbow;

        if (data.action != null) {
            switch (data.action) {
                case "Save" -> {
                    saveProfile();
                    this.close();
                    return;
                }
                case "Cancel" -> {
                    this.close();
                    return;
                }
                case "TabEditor" -> {
                    this.currentTab = "editor";
                    UICommandBuilder cmd = new UICommandBuilder();
                    cmd.set("#EditorContent.Visible", true);
                    cmd.set("#GradientsContent.Visible", false);
                    cmd.set("#HelpContent.Visible", false);
                    this.sendUpdate(cmd);
                    return;
                }
                case "TabGradients" -> {
                    this.currentTab = "gradients";
                    UICommandBuilder cmd = new UICommandBuilder();
                    cmd.set("#EditorContent.Visible", false);
                    cmd.set("#GradientsContent.Visible", true);
                    cmd.set("#HelpContent.Visible", false);
                    this.sendUpdate(cmd);
                    return;
                }
                case "TabHelp" -> {
                    this.currentTab = "help";
                    UICommandBuilder cmd = new UICommandBuilder();
                    cmd.set("#EditorContent.Visible", false);
                    cmd.set("#GradientsContent.Visible", false);
                    cmd.set("#HelpContent.Visible", true);
                    this.sendUpdate(cmd);
                    return;
                }
                case "ClearPrefixGradient" -> {
                    this.prefixGradientStart = "";
                    this.prefixGradientEnd = "";
                    this.prefixRainbow = false;
                }
                case "ClearNameGradient" -> {
                    this.nameGradientStart = "";
                    this.nameGradientEnd = "";
                    this.nameRainbow = false;
                }
                case "ClearPronounsGradient" -> {
                    this.pronounsGradientStart = "";
                    this.pronounsGradientEnd = "";
                    this.pronounsRainbow = false;
                }
                case "ClearSuffixGradient" -> {
                    this.suffixGradientStart = "";
                    this.suffixGradientEnd = "";
                    this.suffixRainbow = false;
                }
                case "ClearMessageGradient" -> {
                    this.messageGradientStart = "";
                    this.messageGradientEnd = "";
                    this.messageRainbow = false;
                }
            }
        }

        UICommandBuilder cmd = new UICommandBuilder();
        buildPreview(cmd);
        this.sendUpdate(cmd);
    }

    private void saveProfile() {
        ElementStyle newPrefixStyle = new ElementStyle(
            prefixColor.equalsIgnoreCase("#FFFFFF") ? null : prefixColor,
            prefixBold, prefixItalic, prefixUnderline,
            prefixGradientStart.isEmpty() ? null : prefixGradientStart,
            prefixGradientEnd.isEmpty() ? null : prefixGradientEnd,
            prefixRainbow
        );

        ElementStyle newNameStyle = new ElementStyle(
            nameColor.equalsIgnoreCase("#FFFFFF") ? null : nameColor,
            nameBold, nameItalic, nameUnderline,
            nameGradientStart.isEmpty() ? null : nameGradientStart,
            nameGradientEnd.isEmpty() ? null : nameGradientEnd,
            nameRainbow
        );

        ElementStyle newPronounsStyle = new ElementStyle(
            pronounsColor.equalsIgnoreCase("#AAAAAA") ? null : pronounsColor,
            pronounsBold, pronounsItalic, pronounsUnderline,
            pronounsGradientStart.isEmpty() ? null : pronounsGradientStart,
            pronounsGradientEnd.isEmpty() ? null : pronounsGradientEnd,
            pronounsRainbow
        );

        ElementStyle newSuffixStyle = new ElementStyle(
            suffixColor.equalsIgnoreCase("#FFFFFF") ? null : suffixColor,
            suffixBold, suffixItalic, suffixUnderline,
            suffixGradientStart.isEmpty() ? null : suffixGradientStart,
            suffixGradientEnd.isEmpty() ? null : suffixGradientEnd,
            suffixRainbow
        );

        ElementStyle newMessageStyle = new ElementStyle(
            messageColor.equalsIgnoreCase("#FFFFFF") ? null : messageColor,
            messageBold, messageItalic, messageUnderline,
            messageGradientStart.isEmpty() ? null : messageGradientStart,
            messageGradientEnd.isEmpty() ? null : messageGradientEnd,
            messageRainbow
        );

        PlayerChatProfile newProfile = new PlayerChatProfile(
            prefix.isEmpty() ? null : prefix,
            suffix.isEmpty() ? null : suffix,
            nickname.isEmpty() ? null : nickname,
            pronouns.isEmpty() ? null : pronouns,
            newPrefixStyle,
            newNameStyle,
            newPronounsStyle,
            newSuffixStyle,
            newMessageStyle,
            chatManager.getProfile(playerRef.getUuid()).hasSeenWelcome()
        );

        chatManager.setProfile(playerRef.getUuid(), newProfile);
    }

    public static class EditorData {
        public static final BuilderCodec<EditorData> CODEC = BuilderCodec.<EditorData>builder(EditorData.class, EditorData::new)
            .addField(new KeyedCodec<>("@Prefix", Codec.STRING), (d, s) -> d.prefix = s, d -> d.prefix)
            .addField(new KeyedCodec<>("@Nickname", Codec.STRING), (d, s) -> d.nickname = s, d -> d.nickname)
            .addField(new KeyedCodec<>("@Pronouns", Codec.STRING), (d, s) -> d.pronouns = s, d -> d.pronouns)
            .addField(new KeyedCodec<>("@Suffix", Codec.STRING), (d, s) -> d.suffix = s, d -> d.suffix)
            .addField(new KeyedCodec<>("@PrefixColor", Codec.STRING), (d, s) -> d.prefixColor = s, d -> d.prefixColor)
            .addField(new KeyedCodec<>("@NameColor", Codec.STRING), (d, s) -> d.nameColor = s, d -> d.nameColor)
            .addField(new KeyedCodec<>("@PronounsColor", Codec.STRING), (d, s) -> d.pronounsColor = s, d -> d.pronounsColor)
            .addField(new KeyedCodec<>("@SuffixColor", Codec.STRING), (d, s) -> d.suffixColor = s, d -> d.suffixColor)
            .addField(new KeyedCodec<>("@MessageColor", Codec.STRING), (d, s) -> d.messageColor = s, d -> d.messageColor)
            .addField(new KeyedCodec<>("@PrefixBold", Codec.BOOLEAN), (d, b) -> d.prefixBold = b, d -> d.prefixBold)
            .addField(new KeyedCodec<>("@PrefixItalic", Codec.BOOLEAN), (d, b) -> d.prefixItalic = b, d -> d.prefixItalic)
            .addField(new KeyedCodec<>("@PrefixUnderline", Codec.BOOLEAN), (d, b) -> d.prefixUnderline = b, d -> d.prefixUnderline)
            .addField(new KeyedCodec<>("@NameBold", Codec.BOOLEAN), (d, b) -> d.nameBold = b, d -> d.nameBold)
            .addField(new KeyedCodec<>("@NameItalic", Codec.BOOLEAN), (d, b) -> d.nameItalic = b, d -> d.nameItalic)
            .addField(new KeyedCodec<>("@NameUnderline", Codec.BOOLEAN), (d, b) -> d.nameUnderline = b, d -> d.nameUnderline)
            .addField(new KeyedCodec<>("@PronounsBold", Codec.BOOLEAN), (d, b) -> d.pronounsBold = b, d -> d.pronounsBold)
            .addField(new KeyedCodec<>("@PronounsItalic", Codec.BOOLEAN), (d, b) -> d.pronounsItalic = b, d -> d.pronounsItalic)
            .addField(new KeyedCodec<>("@PronounsUnderline", Codec.BOOLEAN), (d, b) -> d.pronounsUnderline = b, d -> d.pronounsUnderline)
            .addField(new KeyedCodec<>("@SuffixBold", Codec.BOOLEAN), (d, b) -> d.suffixBold = b, d -> d.suffixBold)
            .addField(new KeyedCodec<>("@SuffixItalic", Codec.BOOLEAN), (d, b) -> d.suffixItalic = b, d -> d.suffixItalic)
            .addField(new KeyedCodec<>("@SuffixUnderline", Codec.BOOLEAN), (d, b) -> d.suffixUnderline = b, d -> d.suffixUnderline)
            .addField(new KeyedCodec<>("@MessageBold", Codec.BOOLEAN), (d, b) -> d.messageBold = b, d -> d.messageBold)
            .addField(new KeyedCodec<>("@MessageItalic", Codec.BOOLEAN), (d, b) -> d.messageItalic = b, d -> d.messageItalic)
            .addField(new KeyedCodec<>("@MessageUnderline", Codec.BOOLEAN), (d, b) -> d.messageUnderline = b, d -> d.messageUnderline)
            .addField(new KeyedCodec<>("@PrefixGradientStart", Codec.STRING), (d, s) -> d.prefixGradientStart = s, d -> d.prefixGradientStart)
            .addField(new KeyedCodec<>("@PrefixGradientEnd", Codec.STRING), (d, s) -> d.prefixGradientEnd = s, d -> d.prefixGradientEnd)
            .addField(new KeyedCodec<>("@PrefixRainbow", Codec.BOOLEAN), (d, b) -> d.prefixRainbow = b, d -> d.prefixRainbow)
            .addField(new KeyedCodec<>("@NameGradientStart", Codec.STRING), (d, s) -> d.nameGradientStart = s, d -> d.nameGradientStart)
            .addField(new KeyedCodec<>("@NameGradientEnd", Codec.STRING), (d, s) -> d.nameGradientEnd = s, d -> d.nameGradientEnd)
            .addField(new KeyedCodec<>("@NameRainbow", Codec.BOOLEAN), (d, b) -> d.nameRainbow = b, d -> d.nameRainbow)
            .addField(new KeyedCodec<>("@PronounsGradientStart", Codec.STRING), (d, s) -> d.pronounsGradientStart = s, d -> d.pronounsGradientStart)
            .addField(new KeyedCodec<>("@PronounsGradientEnd", Codec.STRING), (d, s) -> d.pronounsGradientEnd = s, d -> d.pronounsGradientEnd)
            .addField(new KeyedCodec<>("@PronounsRainbow", Codec.BOOLEAN), (d, b) -> d.pronounsRainbow = b, d -> d.pronounsRainbow)
            .addField(new KeyedCodec<>("@SuffixGradientStart", Codec.STRING), (d, s) -> d.suffixGradientStart = s, d -> d.suffixGradientStart)
            .addField(new KeyedCodec<>("@SuffixGradientEnd", Codec.STRING), (d, s) -> d.suffixGradientEnd = s, d -> d.suffixGradientEnd)
            .addField(new KeyedCodec<>("@SuffixRainbow", Codec.BOOLEAN), (d, b) -> d.suffixRainbow = b, d -> d.suffixRainbow)
            .addField(new KeyedCodec<>("@MessageGradientStart", Codec.STRING), (d, s) -> d.messageGradientStart = s, d -> d.messageGradientStart)
            .addField(new KeyedCodec<>("@MessageGradientEnd", Codec.STRING), (d, s) -> d.messageGradientEnd = s, d -> d.messageGradientEnd)
            .addField(new KeyedCodec<>("@MessageRainbow", Codec.BOOLEAN), (d, b) -> d.messageRainbow = b, d -> d.messageRainbow)
            .addField(new KeyedCodec<>("Action", Codec.STRING), (d, s) -> d.action = s, d -> d.action)
            .build();

        private String prefix;
        private String nickname;
        private String pronouns;
        private String suffix;
        private String prefixColor;
        private String nameColor;
        private String pronounsColor;
        private String suffixColor;
        private String messageColor;
        private Boolean prefixBold;
        private Boolean prefixItalic;
        private Boolean prefixUnderline;
        private Boolean nameBold;
        private Boolean nameItalic;
        private Boolean nameUnderline;
        private Boolean pronounsBold;
        private Boolean pronounsItalic;
        private Boolean pronounsUnderline;
        private Boolean suffixBold;
        private Boolean suffixItalic;
        private Boolean suffixUnderline;
        private Boolean messageBold;
        private Boolean messageItalic;
        private Boolean messageUnderline;
        private String prefixGradientStart;
        private String prefixGradientEnd;
        private Boolean prefixRainbow;
        private String nameGradientStart;
        private String nameGradientEnd;
        private Boolean nameRainbow;
        private String pronounsGradientStart;
        private String pronounsGradientEnd;
        private Boolean pronounsRainbow;
        private String suffixGradientStart;
        private String suffixGradientEnd;
        private Boolean suffixRainbow;
        private String messageGradientStart;
        private String messageGradientEnd;
        private Boolean messageRainbow;
        private String action;
    }
}
