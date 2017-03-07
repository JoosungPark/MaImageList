package ma.sdop.imagelist.web;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.common.data.InstagramParameterData;
import ma.sdop.imagelist.common.data.ParameterBaseData;
import ma.sdop.imagelist.web.dto.DtoBase;
import ma.sdop.imagelist.web.dto.instagram.ImageDto;
import ma.sdop.imagelist.web.dto.instagram.ItemsDto;
import ma.sdop.imagelist.web.dto.instagram.ResolutionDto;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class GetImageTask extends BaseTask {
    private ItemsDto results;
    private String userId;
    private String maxId;
    private boolean moreAvailable;

    public GetImageTask(Context context, String userId, String maxId, OnCompletedListener onCompletedListener) {
        super(context, onCompletedListener);
        this.userId = userId;
        this.maxId = maxId;
        setUri();
    }

    private void setUri() {
        webWrapper.setUri(WebConfig.API.getImagesURL(userId, maxId));
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            results = webWrapper.get().getDto(ItemsDto.class);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (WebException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if ( results != null  ) {
            moreAvailable = results.getMore_available();
            if ( moreAvailable ) maxId = results.getLastImageUrl();
            else maxId = null;
        }

        for ( OnCompletedListener listener : onCompletedListenerList ) {
            listener.onCompleted(aBoolean == null ? false : aBoolean, results);
        }
    }

    @Override
    public boolean isNext() {
        return moreAvailable;
    }

    @Override
    public ParameterBaseData getNextParameter() {
        return new InstagramParameterData(userId, maxId);
    }

    @Override
    public ItemsDto getResults() {
        return results;
    }
}