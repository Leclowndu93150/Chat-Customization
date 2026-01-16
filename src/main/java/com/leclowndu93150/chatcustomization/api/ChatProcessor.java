package com.leclowndu93150.chatcustomization.api;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import javax.annotation.Nonnull;

/**
 * Interface for chat message processors.
 *
 * Implement this interface to modify chat messages in the formatting pipeline.
 * Register your processor with {@link ChatFormatterAPI#registerProcessor(ChatProcessor)}.
 *
 * <h2>Processing Order:</h2>
 * <ol>
 *   <li>Player sends chat message</li>
 *   <li>ChatCustomization applies base formatting (name, prefix, suffix, colors)</li>
 *   <li>All registered processors are called in priority order</li>
 *   <li>Final message is sent to all players</li>
 * </ol>
 *
 * <h2>Priority Guidelines:</h2>
 * <ul>
 *   <li>0-49: Early - Add prefixes before the formatted message</li>
 *   <li>50-99: Normal - ChatCustomization operates here</li>
 *   <li>100-149: Late - Add suffixes or wrap the message</li>
 *   <li>150+: Final - Logging, analytics, or final modifications</li>
 * </ul>
 */
public interface ChatProcessor {

    /**
     * Unique identifier for this processor.
     * Use format "modid:processor_name" to avoid conflicts.
     *
     * @return The processor ID
     */
    @Nonnull
    String getId();

    /**
     * Processing priority. Lower values run first.
     *
     * @return The priority (default: 100)
     */
    default int getPriority() {
        return 100;
    }

    /**
     * Whether this processor is currently enabled.
     *
     * @return true if enabled (default: true)
     */
    default boolean isEnabled() {
        return true;
    }

    /**
     * Process a chat message.
     *
     * @param sender The player who sent the message
     * @param originalMessage The original raw message text (unformatted)
     * @param currentFormatted The current formatted message (may have been modified by earlier processors)
     * @return The modified message (never return null - return currentFormatted if no changes)
     */
    @Nonnull
    Message process(@Nonnull PlayerRef sender,
                    @Nonnull String originalMessage,
                    @Nonnull Message currentFormatted);
}
