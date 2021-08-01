package com.android.abulkhayerbijoy.utils;

import android.graphics.Typeface;
import android.util.Log;

import com.android.abulkhayerbijoy.startup.Startup;

import java.util.HashMap;

public class CustomFontLoader {
    static CustomFontLoader customFontLoader;
    HashMap<String, String> fontMap = new HashMap<>();
    HashMap<String, Typeface> fontCache = new HashMap<>();

    public static CustomFontLoader getInstance() {
        if (customFontLoader == null)
            customFontLoader = new CustomFontLoader();
        return customFontLoader;
    }

    public void addFont(String alias, String fontName) {
        fontMap.put(alias, fontName);
    }

    public Typeface getFont(String alias) {
        String fontFilename = fontMap.get(alias);
        if (fontFilename == null) {
            Log.e("", "Font not available with name " + alias);
            return null;
        }
        if (fontCache.containsKey(alias))
            return fontCache.get(alias);
        else {
            Typeface typeface = Typeface.createFromAsset(Startup.getContext().getAssets(), "font/" + fontFilename);
            fontCache.put(fontFilename, typeface);
            return typeface;
        }
    }
}