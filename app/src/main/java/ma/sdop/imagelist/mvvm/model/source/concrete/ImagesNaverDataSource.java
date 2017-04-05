package ma.sdop.imagelist.mvvm.model.source.concrete;

import java.util.List;

import io.reactivex.Flowable;
import ma.sdop.imagelist.mvvm.model.ImageModel;
import ma.sdop.imagelist.mvvm.model.source.ImageType;
import ma.sdop.imagelist.mvvm.model.source.ImagesDataSource;

/**
 * Created by parkjoosung on 2017. 4. 5..
 */

public class ImagesNaverDataSource implements ImagesDataSource {

    @Override
    public <Parameter> Flowable<List<ImageModel>> next(Parameter parameter) {
        return null;
    }

    @Override
    public void switchDataSource(ImageType type) {

    }
}
