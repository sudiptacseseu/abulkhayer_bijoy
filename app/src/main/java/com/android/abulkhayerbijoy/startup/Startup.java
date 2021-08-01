package com.android.abulkhayerbijoy.startup;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import android.content.Context;
import android.content.ContextWrapper;
import com.android.abulkhayerbijoy.utils.CustomFontLoader;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.pixplicity.easyprefs.library.Prefs;

public class Startup extends MultiDexApplication {

    CustomFontLoader customFontLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Stetho.initializeWithDefaults(this);
        initEasyPerf();
        loadFonts();
        Iconify.with(new FontAwesomeModule());
        MultiDex.install(this);
    }

    private void loadFonts() {
        customFontLoader = CustomFontLoader.getInstance();
        customFontLoader.addFont("roboto_black", "roboto_black.ttf");
        customFontLoader.addFont("roboto_black_italic", "roboto_blackitalic.ttf");
        customFontLoader.addFont("roboto_bold", "roboto_bold.ttf");
        customFontLoader.addFont("roboto_bold_italic", "roboto_Bolditalic.ttf");
        customFontLoader.addFont("roboto_italic.ttf", "roboto_italic.ttf");
        customFontLoader.addFont("roboto_light", "roboto_light.ttf");
        customFontLoader.addFont("roboto_light_italic", "roboto_lightitalic.ttf");
        customFontLoader.addFont("roboto_medium", "roboto_medium.ttf");
        customFontLoader.addFont("roboto_medium_italic", "roboto_mediumitalic.ttf");
        customFontLoader.addFont("roboto_regular", "roboto_regular.ttf");
        customFontLoader.addFont("roboto_thin", "roboto_Thin.ttf");
        customFontLoader.addFont("roboto_thin_italic", "roboto_Thinitalic.ttf");
        customFontLoader.addFont("roboto_condensed_bold", "robotoCondensed_Bold.ttf");
        customFontLoader.addFont("roboto_condensed_bold_italic", "robotoCondensed_Bolditalic.ttf");
        customFontLoader.addFont("roboto_condensed_italic", "robotoCondensed_italic.ttf");
        customFontLoader.addFont("roboto_condensed_light", "robotoCondensed_light.ttf");
        customFontLoader.addFont("roboto_condensed_light-italic", "robotoCondensed_lightitalic.ttf");
        customFontLoader.addFont("roboto_condensed_regular", "robotoCondensed_regular.ttf");
        customFontLoader.addFont("olivier", "olivier.ttf");
        customFontLoader.addFont("siyamrupali", "siyamrupali.ttf");
    }


    private void initEasyPerf() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

    private static Startup instance;

    public static Startup getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }
}



