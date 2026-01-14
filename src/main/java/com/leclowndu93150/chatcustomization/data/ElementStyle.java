package com.leclowndu93150.chatcustomization.data;

import javax.annotation.Nullable;

public record ElementStyle(
    @Nullable String color,
    boolean bold,
    boolean italic,
    boolean underline,
    @Nullable String gradientStart,
    @Nullable String gradientEnd,
    boolean rainbow
) {
    public static final ElementStyle EMPTY = new ElementStyle(null, false, false, false, null, null, false);

    public ElementStyle withColor(@Nullable String color) {
        return new ElementStyle(color, bold, italic, underline, gradientStart, gradientEnd, rainbow);
    }

    public ElementStyle withBold(boolean bold) {
        return new ElementStyle(color, bold, italic, underline, gradientStart, gradientEnd, rainbow);
    }

    public ElementStyle withItalic(boolean italic) {
        return new ElementStyle(color, bold, italic, underline, gradientStart, gradientEnd, rainbow);
    }

    public ElementStyle withUnderline(boolean underline) {
        return new ElementStyle(color, bold, italic, underline, gradientStart, gradientEnd, rainbow);
    }

    public ElementStyle withGradient(@Nullable String start, @Nullable String end) {
        return new ElementStyle(color, bold, italic, underline, start, end, rainbow);
    }

    public ElementStyle withRainbow(boolean rainbow) {
        return new ElementStyle(color, bold, italic, underline, gradientStart, gradientEnd, rainbow);
    }

    public boolean hasGradient() {
        return gradientStart != null && gradientEnd != null;
    }

    public boolean hasCustomizations() {
        return color != null || bold || italic || underline || gradientStart != null || rainbow;
    }
}
