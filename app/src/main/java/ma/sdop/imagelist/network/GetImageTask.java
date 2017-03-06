package ma.sdop.imagelist.network;

import org.json.JSONException;

import java.io.IOException;

import ma.sdop.imagelist.dto.instagram.ImageDto;
import ma.sdop.imagelist.dto.instagram.ItemsDto;
import ma.sdop.imagelist.dto.instagram.ResolutionDto;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class GetImageTask extends BaseTask {
    private OnCompletedListener onCompletedListener;
    private ItemsDto results;
    private String userId;
    private String maxId;
    private boolean moreAvailable;

    public GetImageTask(String userId, String maxId, OnCompletedListener onCompletedListener) {
        super();

        this.userId = userId;
        this.maxId = maxId;
        this.onCompletedListener = onCompletedListener;

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
            if ( moreAvailable ) {
                if ( results.getItems().size() > 0 ) {
                    ImageDto lastImage = results.getItems().get(results.getItems().size()-1).getImages();
                    ResolutionDto resolutionDto = lastImage == null ? null : lastImage.getStandard_resolution();
                    maxId = resolutionDto == null ? null : resolutionDto.getUrl();
                }
                maxId = null;
            }
        }

        if ( onCompletedListener != null ) onCompletedListener.onCompleted(aBoolean == null ? false : aBoolean, results);
    }

    @Override
    public boolean next() {
        if ( moreAvailable ) {
            setUri();
            execute();
        }

        return moreAvailable;
    }

    public interface OnCompletedListener {
        void onCompleted(boolean isSuccess, ItemsDto result);
    }
}