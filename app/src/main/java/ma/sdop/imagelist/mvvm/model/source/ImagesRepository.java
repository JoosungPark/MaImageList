package ma.sdop.imagelist.mvvm.model.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Flowable;
import ma.sdop.imagelist.mvvm.model.ImageModel;
import ma.sdop.imagelist.mvvm.model.source.concrete.ImagesInstagramDataSource;
import ma.sdop.imagelist.mvvm.model.source.concrete.ImagesNaverDataSource;

/**
 * Created by parkjoosung on 2017. 4. 5..
 */

public class ImagesRepository implements ImagesDataSource {
    @Nullable
    private static ImagesRepository INSTANCE = null;

    @NonNull
    private final ImagesDataSource instagramDataSource;

    @NonNull
    private final ImagesDataSource naverDataSource;

    @NonNull
    private ImagesDataSource currentDataSource;

    private ImagesRepository() {
        instagramDataSource = new ImagesInstagramDataSource();
        naverDataSource = new ImagesNaverDataSource();
        currentDataSource = instagramDataSource;
    }

    @Override
    public <Parameter> Flowable<List<ImageModel>> next(Parameter parameter) {
        return currentDataSource.next(parameter);
    }

    @Override
    public void switchDataSource(ImageType type) {
        if (type == ImageType.INSTAGRAM) {
            currentDataSource = instagramDataSource;
        } else if ( type == ImageType.NAVER ) {
            currentDataSource = naverDataSource;
        } else {
            currentDataSource = instagramDataSource;
        }
    }

    public static ImagesRepository getInstance() {
        if ( INSTANCE == null ) INSTANCE = new ImagesRepository();

        return INSTANCE;
    }
}
