package com.leclowndu93150.chatcustomization.util;

import com.hypixel.hytale.protocol.MaybeBool;
import com.hypixel.hytale.server.core.Message;
import javax.annotation.Nonnull;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorUtil {

    private static final Map<String, String> NAMED_COLORS = new HashMap<>();

    static {
        NAMED_COLORS.put("BLACK", "#000000");
        NAMED_COLORS.put("DARK_BLUE", "#0000AA");
        NAMED_COLORS.put("DARK_GREEN", "#00AA00");
        NAMED_COLORS.put("DARK_AQUA", "#00AAAA");
        NAMED_COLORS.put("DARK_RED", "#AA0000");
        NAMED_COLORS.put("DARK_PURPLE", "#AA00AA");
        NAMED_COLORS.put("GOLD", "#FFAA00");
        NAMED_COLORS.put("GRAY", "#AAAAAA");
        NAMED_COLORS.put("DARK_GRAY", "#555555");
        NAMED_COLORS.put("BLUE", "#5555FF");
        NAMED_COLORS.put("GREEN", "#55FF55");
        NAMED_COLORS.put("AQUA", "#55FFFF");
        NAMED_COLORS.put("RED", "#FF5555");
        NAMED_COLORS.put("LIGHT_PURPLE", "#FF55FF");
        NAMED_COLORS.put("YELLOW", "#FFFF55");
        NAMED_COLORS.put("WHITE", "#FFFFFF");
        NAMED_COLORS.put("PINK", "#FF69B4");
        NAMED_COLORS.put("ORANGE", "#FFA500");
        NAMED_COLORS.put("CYAN", "#00FFFF");
        NAMED_COLORS.put("LIME", "#00FF00");
    }

    @Nonnull
    public static String[] getColorNames() {
        return NAMED_COLORS.keySet().toArray(new String[0]);
    }

    @Nonnull
    public static String toHex(@Nonnull String color) {
        String upper = color.toUpperCase();
        if (NAMED_COLORS.containsKey(upper)) {
            return NAMED_COLORS.get(upper);
        }
        if (color.startsWith("#") && color.length() == 9) {
            return color.substring(0, 7).toUpperCase();
        }
        if (color.startsWith("#") && color.length() == 7) {
            return color.toUpperCase();
        }
        return "#FFFFFF";
    }

    public static boolean isValidColor(@Nonnull String color) {
        String upper = color.toUpperCase();
        if (NAMED_COLORS.containsKey(upper)) {
            return true;
        }
        if (color.startsWith("#") && (color.length() == 7 || color.length() == 9)) {
            try {
                Integer.parseInt(color.substring(1, 7), 16);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Nonnull
    public static Color parseHex(@Nonnull String hex) {
        String h = hex.startsWith("#") ? hex.substring(1) : hex;
        return new Color(
            Integer.parseInt(h.substring(0, 2), 16),
            Integer.parseInt(h.substring(2, 4), 16),
            Integer.parseInt(h.substring(4, 6), 16)
        );
    }

    @Nonnull
    public static String toHex(@Nonnull Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }

    @Nonnull
    public static Color interpolate(@Nonnull Color start, @Nonnull Color end, float ratio) {
        ratio = Math.max(0, Math.min(1, ratio));
        int r = (int) (start.getRed() + (end.getRed() - start.getRed()) * ratio);
        int g = (int) (start.getGreen() + (end.getGreen() - start.getGreen()) * ratio);
        int b = (int) (start.getBlue() + (end.getBlue() - start.getBlue()) * ratio);
        return new Color(r, g, b);
    }

    @Nonnull
    public static String interpolateColor(@Nonnull String startColor, @Nonnull String endColor, float ratio) {
        Color start = parseHex(toHex(startColor));
        Color end = parseHex(toHex(endColor));
        Color result = interpolate(start, end, ratio);
        return toHex(result);
    }

    @Nonnull
    public static Message createGradientText(@Nonnull String text, @Nonnull String startColor, @Nonnull String endColor,
                                              boolean bold, boolean italic, boolean underline) {
        if (text.isEmpty()) {
            return Message.empty();
        }

        Color start = parseHex(toHex(startColor));
        Color end = parseHex(toHex(endColor));
        Message result = Message.empty();

        for (int i = 0; i < text.length(); i++) {
            float ratio = text.length() == 1 ? 0 : (float) i / (text.length() - 1);
            Color color = interpolate(start, end, ratio);
            Message charMsg = Message.raw(String.valueOf(text.charAt(i))).color(toHex(color));
            if (bold) charMsg.bold(true);
            if (italic) charMsg.italic(true);
            if (underline) charMsg.getFormattedMessage().underlined = MaybeBool.True;
            result.insert(charMsg);
        }

        return result;
    }

    @Nonnull
    public static Message createRainbowText(@Nonnull String text, boolean bold, boolean italic, boolean underline) {
        if (text.isEmpty()) {
            return Message.empty();
        }

        Message result = Message.empty();
        float hueStep = 1.0f / Math.max(text.length(), 1);

        for (int i = 0; i < text.length(); i++) {
            float hue = (hueStep * i) % 1.0f;
            Color color = Color.getHSBColor(hue, 1.0f, 1.0f);
            Message charMsg = Message.raw(String.valueOf(text.charAt(i))).color(toHex(color));
            if (bold) charMsg.bold(true);
            if (italic) charMsg.italic(true);
            if (underline) charMsg.getFormattedMessage().underlined = MaybeBool.True;
            result.insert(charMsg);
        }

        return result;
    }

    @Nonnull
    public static Message createStyledText(@Nonnull String text, @Nonnull String color,
                                            boolean bold, boolean italic, boolean underline) {
        Message msg = Message.raw(text).color(toHex(color));
        if (bold) msg.bold(true);
        if (italic) msg.italic(true);
        if (underline) msg.getFormattedMessage().underlined = MaybeBool.True;
        return msg;
    }

    @Nonnull
    public static Message applyMentions(@Nonnull String text, @Nonnull String mentionColor,
                                         @Nonnull String defaultColor, @Nonnull java.util.Set<String> playerNames) {
        Message result = Message.empty();
        StringBuilder currentWord = new StringBuilder();
        StringBuilder currentText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '@') {
                if (currentText.length() > 0) {
                    result.insert(Message.raw(currentText.toString()).color(toHex(defaultColor)));
                    currentText.setLength(0);
                }

                currentWord.setLength(0);
                currentWord.append('@');
                i++;
                while (i < text.length() && !Character.isWhitespace(text.charAt(i))) {
                    currentWord.append(text.charAt(i));
                    i++;
                }
                i--;

                String mention = currentWord.toString();
                String username = mention.substring(1);

                if (playerNames.contains(username.toLowerCase())) {
                    result.insert(Message.raw(mention).color(toHex(mentionColor)).bold(true));
                } else {
                    currentText.append(mention);
                }
            } else {
                currentText.append(c);
            }
        }

        if (currentText.length() > 0) {
            result.insert(Message.raw(currentText.toString()).color(toHex(defaultColor)));
        }

        return result;
    }
}
