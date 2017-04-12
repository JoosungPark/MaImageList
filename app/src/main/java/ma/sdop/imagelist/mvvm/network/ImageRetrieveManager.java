package ma.sdop.imagelist.mvvm.network;

import android.util.Log;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import ma.sdop.imagelist.mvvm.model.ImageModel;
import ma.sdop.imagelist.mvvm.model.ImageType;
import ma.sdop.imagelist.mvvm.network.instagram.InstagramApiService;
import ma.sdop.imagelist.mvvm.network.instagram.InstagramRequest;
import ma.sdop.imagelist.mvvm.network.retrofit.RetrofitFactory;

/**
 * Created by parkjoosung on 2017. 4. 10..
 */

public class ImageRetrieveManager {
    private static ImageRetrieveManager instance;

    private ImageType type;
    private final List<ImageModel> imageModelList;
    private final CompositeDisposable compositeDisposable;
    private final PublishProcessor<Integer> paginator;
    private final InstagramApiService instagramApiService;
    private int pageNumber = 1;

    private ImageRetrieveManager() {
        imageModelList = new ArrayList<>();
        type = ImageType.INSTAGRAM;
        compositeDisposable = new CompositeDisposable();
        paginator = PublishProcessor.create();
        instagramApiService = new InstagramApiService(RetrofitFactory.getInstance().getAdapter(ApiType.INSTAGRAM));
    }

    public static ImageRetrieveManager getInstance() {
        synchronized (ImageRetrieveManager.class) {
            if (instance == null) instance = new ImageRetrieveManager();
            return instance;
        }
    }

    public void switchService(ImageType type) {
        this.type = type;
    }

    private void subscribeFromInstagram(String userId, String maxId) {
        Disposable disposable = paginator
                .onBackpressureBuffer()
                .concatMap(new Function<Integer, Publisher<List<ImageModel>>>() {
                    @Override
                    public Publisher<List<ImageModel>> apply(@NonNull Integer integer) throws Exception {
                        return instagramApiService.getImages(new InstagramRequest(userId, maxId));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageModelList -> {
                            for ( ImageModel imageModel : imageModelList ) {
                                Log.d("dd", imageModel.getImageUrl());
                            }
                        }

                );

        compositeDisposable.add(disposable);

        paginator.onNext(pageNumber);
    }

    private InstagramRequest getInstagramRequest(String userId, String maxId) {
        return new InstagramRequest(userId, maxId);
    }

    public void getImages(String userId, String maxId) {
        subscribeFromInstagram(userId, maxId);
    }

    public void getImages(String query, int display) {

    }



}
