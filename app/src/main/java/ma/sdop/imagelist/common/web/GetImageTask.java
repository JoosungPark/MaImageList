package ma.sdop.imagelist.common.web;

import android.content.Context;

import ma.sdop.imagelist.common.web.dto.ImageDtoOperation;
import ma.sdop.imagelist.common.web.parameter.BaseParameter;

class GetImageTask<ResultType extends ImageDtoOperation> extends BaseTask {
    private ResultType results;
    private boolean moreAvailable = false;
    private BaseParameter parameter;

    private Class<ResultType> resultType;

    GetImageTask(Context context, BaseParameter parameter, OnCompletedListener onCompletedListener, WebWrapper webWrapper, Class<ResultType> resultType) {
        super(context, onCompletedListener);
        this.parameter = parameter;
        this.webWrapper = webWrapper;
        this.resultType = resultType;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            results = webWrapper.get().getDto(resultType);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if ( results != null ) moreAvailable = results.isNext(parameter);
        if ( onCompletedListener != null ) onCompletedListener.onCompleted(aBoolean == null ? false : aBoolean, results);
    }

    @Override
    public boolean isNext() {
        return moreAvailable;
    }

    @Override
    public BaseParameter getNextParameter() {
        return parameter;
    }
}
