package ma.sdop.imagelist.web;

import android.content.Context;
import android.util.Log;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.web.parameter.InstagramParameterData;
import ma.sdop.imagelist.web.parameter.NParameterData;
import ma.sdop.imagelist.web.parameter.ParameterBaseData;
import ma.sdop.imagelist.web.dto.DtoBase;
import ma.sdop.imagelist.web.dto.instagram.ItemsDto;
import ma.sdop.imagelist.web.dto.n.Rss;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class GetImageTask extends BaseTask {
    private DtoBase results;
    private boolean moreAvailable;
    private ParameterBaseData parameter;

    public GetImageTask(Context context, ParameterBaseData parameter, OnCompletedListener onCompletedListener) {
        super(context, onCompletedListener);
        this.parameter = parameter;
        webWrapper = new WebWrapper(context);
        setUrl();
    }

    private void setUrl() {
        if (WebConfig.apiType == R.string.api_n ) {
            webWrapper.setUri(WebConfig.N.API);
            NParameterData parameterData = (NParameterData) parameter;
            webWrapper.addParameter(WebConfig.N.Parameter.Query, parameterData.getQuery())
                    .addParameter(WebConfig.N.Parameter.Display, parameterData.getDisplay());
            int start = parameterData.getStart();
            if ( start != 0 ) webWrapper.addParameter(WebConfig.N.Parameter.Start, start);
        } else if (WebConfig.apiType == R.string.api_instragram && parameter instanceof InstagramParameterData) {
            InstagramParameterData parameterData = (InstagramParameterData) parameter;
            webWrapper.setUri(WebConfig.INSTAGRAM.getApi(parameterData.getUserId(), parameterData.getMaxId()));
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            if (WebConfig.apiType == R.string.api_n) {
                results = webWrapper.get().getDto(Rss.class);
            } else {
                results = webWrapper.get().getDto(ItemsDto.class);
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if ( results != null  ) {
            if (WebConfig.apiType == R.string.api_n) {
                Rss concreteResults = (Rss) results;
                NParameterData parameterData = (NParameterData) parameter;
                parameterData.setTotalCount(concreteResults.getTotal());
                moreAvailable = parameterData.isNext();
                parameterData.setNextStart();

                Log.i(TAG, "total : " + concreteResults.getTotal());
                Log.i(TAG, "display : " + concreteResults.getChannel().getDisplay());
                Log.i(TAG, "start : " + concreteResults.getChannel().getStart());
            } else {
                ItemsDto concreteResults = (ItemsDto) results;
                moreAvailable = concreteResults.getMore_available();
                InstagramParameterData parameterData = (InstagramParameterData) parameter;
                String maxId = concreteResults.getLastId();

                if ( moreAvailable ) parameterData.setMaxId(maxId);
                else parameterData.setMaxId(null);

                Log.i(TAG, "onPostExecute moreAvailable " + moreAvailable + " maxId " + maxId);
            }
        }

        if ( onCompletedListener != null ) onCompletedListener.onCompleted(aBoolean == null ? false : aBoolean, results);
    }

    @Override
    public boolean isNext() {
        return moreAvailable;
    }

    @Override
    public ParameterBaseData getNextParameter() {
        return parameter;
    }
}