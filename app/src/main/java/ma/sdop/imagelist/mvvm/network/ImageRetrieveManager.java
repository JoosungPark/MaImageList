package ma.sdop.imagelist.mvvm.network;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.processors.PublishProcessor;
import ma.sdop.imagelist.common.web.dto.json.gson.ItemsDto;
import ma.sdop.imagelist.mvvm.model.ImageModel;
import ma.sdop.imagelist.mvvm.model.source.ImageType;

/**
 * Created by parkjoosung on 2017. 4. 10..
 */

public class ImageRetrieveManager {
    private static ImageRetrieveManager instance;

    private ImageType type;
    private final List<ImageModel> imageModelList;
    private final CompositeDisposable disposables;
    private final PublishProcessor<Integer> paginator;

    private ImageRetrieveManager() {
        imageModelList = new ArrayList<>();
        type = ImageType.INSTAGRAM;
        disposables = new CompositeDisposable();
        paginator = PublishProcessor.create();
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

    public void getImages(String userId) {

    }

    public void getImages(String query, int display) {
        getImages(instagramDisposable);
    }

    private void getImages(DisposableObserver<?> observer) {

    }

    private DisposableObserver<ItemsDto> instagramDisposable = new DisposableObserver<ItemsDto>() {
        @Override
        public void onNext(ItemsDto itemsDto) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

}
