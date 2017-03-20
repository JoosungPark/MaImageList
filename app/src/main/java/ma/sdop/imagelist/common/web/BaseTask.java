package ma.sdop.imagelist.common.web;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.MaUtils;
import ma.sdop.imagelist.common.web.dto.BaseDto;
import ma.sdop.imagelist.common.web.parameter.BaseParameter;

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
        <T extends BaseDto> void onCompleted(boolean isSuccess, T result);
    }

    public abstract boolean isNext();
    public abstract BaseParameter getNextParameter();

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

    protected String getHost() {
        switch (WebConfig.apiType) {
            case R.string.api_instragram:
                return WebConfig.HOST_INSTAGRAM;
            case R.string.api_n:
                return WebConfig.HOST_N;
            default:
                return WebConfig.HOST_INSTAGRAM;
        }
    }
}
