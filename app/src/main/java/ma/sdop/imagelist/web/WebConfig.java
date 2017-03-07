package ma.sdop.imagelist.web;

import java.util.Locale;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class WebConfig {
    private static final String INSTAGRAM = "https://www.instagram.com/";
    public static String server;

    static {
        server = INSTAGRAM;
    }

    public static class API {
        public static final String IMAGES = "https://www.instagram.com/%s/media/?max_id=%s";
        private final static Locale locale = Locale.getDefault();

        private static String format(String str, Object... args) {
            return String.format(locale, str, args);
        }

        public static String getImagesURL(String userId, String maximum) {
            return format(IMAGES, userId, maximum);
        }

    }

}
