package ma.sdop.imagelist.mvvm.images;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by parkjoosung on 2017. 4. 5..
 */

public class ImagesViewModel implements ImagesContract.ViewModel {
    @NonNull private final ImagesContract.View imagesView;
    @NonNull private final CompositeDisposable subscription;

    public ImagesViewModel(@NonNull ImagesContract.View imagesView) {
        this.imagesView = checkNotNull(imagesView, "imagesView cannot be null!");
        this.subscription = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
