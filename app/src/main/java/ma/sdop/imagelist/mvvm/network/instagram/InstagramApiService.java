package ma.sdop.imagelist.mvvm.network.instagram;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ma.sdop.imagelist.mvvm.model.ImageModel;
import ma.sdop.imagelist.mvvm.network.exception.GetImagesFailurException;
import retrofit2.Retrofit;

/**
 * Created by parkjoosung on 2017. 4. 11..
 */

public class InstagramApiService {
    private IInstagramApi instagramApi;
    private boolean isRequestApi;

    public InstagramApiService(Retrofit retrofit) {
        this.instagramApi = retrofit.create(IInstagramApi.class);
    }

    public boolean isRequestApi() {
        return isRequestApi;
    }

    public Flowable<List<ImageModel>> getImages(InstagramRequest request) {
        return instagramApi.getImages(request.getUserId(), request.getMaxId())
                .doOnSubscribe(disposable -> isRequestApi = true)
                .doOnTerminate(() -> isRequestApi = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(this::handleError)
                .toFlowable(BackpressureStrategy.BUFFER)
                .map(itemsDto -> itemsDto.getImageModelList());
    }

    private void handleError(Throwable throwable) {
        throw new GetImagesFailurException(throwable);
    }

}
