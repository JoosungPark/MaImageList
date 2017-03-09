package ma.sdop.imagelist.common;

import android.app.Application;

import ma.sdop.imagelist.BuildConfig;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                if (BuildConfig.DEBUG){ ex.printStackTrace();}
                defaultHandler.uncaughtException(thread, ex);
            }
        });
    }
}
