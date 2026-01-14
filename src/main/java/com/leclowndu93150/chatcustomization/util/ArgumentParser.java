package com.leclowndu93150.chatcustomization.util;

import javax.annotation.Nonnull;

public class ArgumentParser {

    private static boolean isQuote(char c) {
        return c == '"' || c == '"' || c == '"';
    }


    private static String stripQuotesInternal(String arg) {
        while (arg.length() > 0 && isQuote(arg.charAt(arg.length() - 1))) {
            arg = arg.substring(0, arg.length() - 1);
        }
        while (arg.length() > 0 && isQuote(arg.charAt(0))) {
            arg = arg.substring(1);
        }
        return arg;
    }

    @Nonnull
    public static String stripQuotes(@Nonnull String input) {
        return stripQuotesInternal(input);
    }

}
