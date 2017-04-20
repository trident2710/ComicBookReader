package com.dev.trident.comicbookreader.other;

public class Constants {


    public static final int MAX_PAGE_HEIGHT = 1600;
    public static final int MAX_PAGE_WIDTH = 2000;


    public static final String SETTINGS_NAME = "SETTINGS_COMICS";

    public static final String SETTINGS_PAGE_VIEW_MODE = "SETTINGS_PAGE_VIEW_MODE";
    public static final String SETTINGS_READING_LEFT_TO_RIGHT = "SETTINGS_READING_LEFT_TO_RIGHT";


    public enum PageViewMode {
        ASPECT_FILL(0),
        ASPECT_FIT(1),
        FIT_WIDTH(2);

        PageViewMode(int n) {
            native_int = n;
        }
        public final int native_int;
    }
}
