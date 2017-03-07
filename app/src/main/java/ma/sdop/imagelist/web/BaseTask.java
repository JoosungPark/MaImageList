package ma.sdop.imagelist.web;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.data.ParameterBaseData;
import ma.sdop.imagelist.web.dto.DtoBase;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

abstract public class BaseTask extends AsyncTask<Void, Void, Boolean> {
    protected Context context;
    protected WebWrapper webWrapper;
    protected OnCompletedListener onCompletedListener;
    protected Dialog progressDialog;

    public BaseTask(Context context, OnCompletedListener onCompletedListener) {
        this.context = context;
        this.onCompletedListener = onCompletedListener;
        webWrapper = new WebWrapper(WebConfig.server);
        progressDialog = new Dialog(context, R.style.SimpleDialog);
        progressDialog.setCancelable(false);
        progressDialog.addContentView(new ProgressBar(context), new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        progressDialog.show();
    }

    public interface OnCompletedListener {
        <T extends DtoBase> void onCompleted(boolean isSuccess, T result);
    }

    public abstract boolean isNext();
    public abstract ParameterBaseData getNextParameter();
    public abstract DtoBase getResults();

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
    }
}
