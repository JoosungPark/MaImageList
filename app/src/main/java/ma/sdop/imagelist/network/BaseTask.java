package ma.sdop.imagelist.network;

import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

abstract public class BaseTask extends AsyncTask<Void, Void, Boolean> implements ImageTaskInterface {
    protected WebWrapper webWrapper;

    public BaseTask() {
        super();
        webWrapper = new WebWrapper(WebConfig.server);
    }

    @Override
    public void execute() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            execute();
        }
    }
}
