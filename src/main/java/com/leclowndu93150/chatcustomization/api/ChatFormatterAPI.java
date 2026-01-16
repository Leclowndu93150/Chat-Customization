package com.leclowndu93150.chatcustomization.api;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Public API for other mods to integrate with the chat formatting system.
 *
 * This allows multiple mods to contribute to chat formatting without conflicts.
 * Processors are called in priority order (lower = earlier).
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // In your mod's setup/start method:
 * ChatFormatterAPI.registerProcessor(new ChatProcessor() {
 *     @Override
 *     public String getId() {
 *         return "mymod:admin_prefix";
 *     }
 *
 *     @Override
 *     public int getPriority() {
 *         return 50; // Run early (before message styling)
 *     }
 *
 *     @Override
 *     public Message process(PlayerRef sender, String originalMessage, Message currentFormatted) {
 *         if (isAdmin(sender)) {
 *             return Message.empty()
 *                 .insert(Message.raw("[ADMIN] ").color("#FF0000"))
 *                 .insert(currentFormatted);
 *         }
 *         return currentFormatted;
 *     }
 * });
 * }</pre>
 *
 * <h2>Priority Guidelines:</h2>
 * <ul>
 *   <li>0-49: Early processors (add prefixes, modify raw message)</li>
 *   <li>50-99: Default ChatCustomization formatting</li>
 *   <li>100-149: Late processors (add suffixes, wrap message)</li>
 *   <li>150+: Final processors (logging, analytics)</li>
 * </ul>
 */
public final class ChatFormatterAPI {
    private static final List<ChatProcessor> processors = new CopyOnWriteArrayList<>();
    private static boolean sorted = true;

    private ChatFormatterAPI() {}

    /**
     * Registers a chat processor. Processors are called in priority order.
     *
     * @param processor The processor to register
     */
    public static void registerProcessor(@Nonnull ChatProcessor processor) {
        // Remove any existing processor with same ID
        processors.removeIf(p -> p.getId().equals(processor.getId()));
        processors.add(processor);
        sorted = false;
    }

    /**
     * Unregisters a chat processor by its ID.
     *
     * @param processorId The ID of the processor to remove
     * @return true if a processor was removed
     */
    public static boolean unregisterProcessor(@Nonnull String processorId) {
        return processors.removeIf(p -> p.getId().equals(processorId));
    }

    /**
     * Gets a registered processor by ID.
     *
     * @param processorId The processor ID
     * @return The processor, or null if not found
     */
    @Nullable
    public static ChatProcessor getProcessor(@Nonnull String processorId) {
        return processors.stream()
            .filter(p -> p.getId().equals(processorId))
            .findFirst()
            .orElse(null);
    }

    /**
     * Gets all registered processors (sorted by priority).
     *
     * @return List of processors
     */
    @Nonnull
    public static List<ChatProcessor> getProcessors() {
        ensureSorted();
        return new ArrayList<>(processors);
    }

    /**
     * Processes a message through all registered processors.
     * Called internally by ChatCustomizationPlugin.
     *
     * @param sender The player sending the message
     * @param originalMessage The original raw message text
     * @param baseFormatted The message after ChatCustomization formatting
     * @return The final formatted message
     */
    @Nonnull
    public static Message processMessage(@Nonnull PlayerRef sender,
                                         @Nonnull String originalMessage,
                                         @Nonnull Message baseFormatted) {
        ensureSorted();

        Message result = baseFormatted;
        for (ChatProcessor processor : processors) {
            if (processor.isEnabled()) {
                try {
                    result = processor.process(sender, originalMessage, result);
                    if (result == null) {
                        result = baseFormatted;
                    }
                } catch (Exception e) {
                    System.err.println("[ChatFormatterAPI] Processor '" + processor.getId() + "' threw exception: " + e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * Creates a simple processor from a lambda.
     *
     * @param id Unique identifier for this processor
     * @param priority Processing priority (lower = earlier)
     * @param processor The processing function
     * @return A ChatProcessor instance
     */
    @Nonnull
    public static ChatProcessor createProcessor(@Nonnull String id,
                                                int priority,
                                                @Nonnull BiFunction<PlayerRef, Message, Message> processor) {
        return new ChatProcessor() {
            @Override
            public String getId() {
                return id;
            }

            @Override
            public int getPriority() {
                return priority;
            }

            @Override
            public Message process(PlayerRef sender, String originalMessage, Message currentFormatted) {
                return processor.apply(sender, currentFormatted);
            }
        };
    }

    private static void ensureSorted() {
        if (!sorted) {
            processors.sort(Comparator.comparingInt(ChatProcessor::getPriority));
            sorted = true;
        }
    }
}
