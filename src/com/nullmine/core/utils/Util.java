package com.nullmine.core.utils;

import java.util.*;

public class Util {

    public static List<String> smartAutocomplete(List<String> variants, String[] args) {
        List<String> autocomplete = new ArrayList<>();

        for (String variant : variants) {
            if (variant.startsWith(args[args.length - 1])) {
                autocomplete.add(variant);
            }
        }

        return autocomplete;
    }
}
