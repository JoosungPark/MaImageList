package ma.sdop.imagelist.web;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.MaUtils;
import ma.sdop.imagelist.web.parameter.ParameterBaseData;
import ma.sdop.imagelist.web.dto.DtoBase;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

abstract public class BaseTask extends AsyncTask<Void, Void, Boolean> {
    protected String TAG = getClass().getSimpleName();

    protected Context context;
    protected WebWrapper webWrapper;
    protected OnCompletedListener onCompletedListener;
    protected Dialog progressDialog;

    public BaseTask(Context context, OnCompletedListener onCompletedListener) {
        this.context = context;
        this.onCompletedListener = onCompletedListener;
        progressDialog = MaUtils.getProgressDialog(context);
    }

    public interface OnCompletedListener {
        <T extends DtoBase> void onCompleted(boolean isSuccess, T result);
    }

    public abstract boolean isNext();
    public abstract ParameterBaseData getNextParameter();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        MaUtils.showToast(context, R.string.err_api_call);
    }
}
