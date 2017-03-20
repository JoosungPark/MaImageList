package ma.sdop.imagelist.common.web;

import android.support.annotation.StringRes;

import java.util.Locale;

import ma.sdop.imagelist.R;

public class WebConfig {
    public static final String HOST_INSTAGRAM = "https://www.instagram.com/";
    public static final String HOST_N = "https://www.naver.com";

    public static String server;
    public static @StringRes int responseType;
    public static @StringRes int apiType;

    static {
        server = HOST_INSTAGRAM;
        apiType = R.string.api_instragram;
        responseType = R.string.response_json;
    }

    private final static Locale locale = Locale.getDefault();
    private static String format(String str, Object... args) {
        return String.format(locale, str, args);
    }

    public static class INSTAGRAM {
        public static final String API = "https://www.instagram.com/%s/media/?max_id=%s";

        public static String getApi(String userId, String maximum) {
            return format(API, userId, maximum);
        }
    }

    public static class N {
        public static final String API = "https://openapi.naver.com/v1/search/image.xml";

        public static class Header {
            public static final String KEY_CLIENT_ID = "X-Naver-Client-Id";
            public static final String KEY_CLIENT_SECRET= "X-Naver-Client-Secret";

            public static final String VALUE_CLIENT_ID = "umIJckqN7rjeKoq4KRVp";
            public static final String VALUDE_CLIENT_SECRET = "0ByHFL53vv";
        }

        public static class Parameter {
            public static final String Query = "query";
            public static final String Display = "display";
            public static final String Start = "start";
        }

        public static final String getApi() {
            return API;
        }
    }

}
