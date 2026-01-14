package com.leclowndu93150.chatcustomization.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.leclowndu93150.chatcustomization.data.PlayerChatProfile;
import com.leclowndu93150.chatcustomization.manager.ChatManager;
import javax.annotation.Nonnull;

public class ChatProfileCommand extends AbstractPlayerCommand {
    private final ChatManager chatManager;

    public ChatProfileCommand(@Nonnull ChatManager chatManager) {
        super("chatprofile", "View your current chat profile settings");
        this.chatManager = chatManager;
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
                          @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        PlayerChatProfile profile = chatManager.getProfile(playerRef.getUuid());

        context.sendMessage(Message.raw("=== Your Chat Profile ===").color("GOLD"));
        context.sendMessage(Message.raw("Prefix: ").insert(
            profile.prefix() != null ? Message.raw("[" + profile.prefix() + "]").color(profile.prefixColor() != null ? profile.prefixColor() : "WHITE") : Message.raw("(not set)").color("GRAY")
        ));
        context.sendMessage(Message.raw("Suffix: ").insert(
            profile.suffix() != null ? Message.raw("[" + profile.suffix() + "]").color(profile.suffixColor() != null ? profile.suffixColor() : "WHITE") : Message.raw("(not set)").color("GRAY")
        ));
        context.sendMessage(Message.raw("Nickname: ").insert(
            profile.nickname() != null ? Message.raw(profile.nickname()) : Message.raw("(using real name)").color("GRAY")
        ));
        context.sendMessage(Message.raw("Pronouns: ").insert(
            profile.pronouns() != null ? Message.raw(profile.pronouns()) : Message.raw("(not set)").color("GRAY")
        ));
        context.sendMessage(Message.raw("Name Color: ").insert(
            profile.nameColor() != null ? Message.raw(profile.nameColor()).color(profile.nameColor()) : Message.raw("(default)").color("GRAY")
        ));
        context.sendMessage(Message.raw("Message Color: ").insert(
            profile.messageColor() != null ? Message.raw(profile.messageColor()).color(profile.messageColor()) : Message.raw("(default)").color("GRAY")
        ));

        // Show preview
        context.sendMessage(Message.raw(""));
        context.sendMessage(Message.raw("Preview:").color("GOLD"));

        Message preview = Message.empty();
        if (profile.prefix() != null) {
            Message prefixMsg = Message.raw("[" + profile.prefix() + "] ");
            if (profile.prefixColor() != null) {
                prefixMsg.color(profile.prefixColor());
            }
            preview.insert(prefixMsg);
        }

        String displayName = profile.nickname() != null ? profile.nickname() : playerRef.getUsername();
        Message nameMsg = Message.raw(displayName);
        if (profile.nameColor() != null) {
            nameMsg.color(profile.nameColor());
        } else {
            nameMsg.color("YELLOW");
        }
        preview.insert(nameMsg);

        if (profile.pronouns() != null) {
            preview.insert(Message.raw(" (" + profile.pronouns() + ")").color("GRAY"));
        }

        if (profile.suffix() != null) {
            Message suffixMsg = Message.raw(" [" + profile.suffix() + "]");
            if (profile.suffixColor() != null) {
                suffixMsg.color(profile.suffixColor());
            }
            preview.insert(suffixMsg);
        }

        preview.insert(Message.raw(": "));

        Message contentMsg = Message.raw("Hello, world!");
        if (profile.messageColor() != null) {
            contentMsg.color(profile.messageColor());
        } else {
            contentMsg.color("WHITE");
        }
        preview.insert(contentMsg);

        context.sendMessage(preview);
    }
}
