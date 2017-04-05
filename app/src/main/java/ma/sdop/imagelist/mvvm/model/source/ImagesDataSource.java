package ma.sdop.imagelist.mvvm.model.source;

import java.util.List;

import io.reactivex.Flowable;
import ma.sdop.imagelist.mvvm.model.ImageModel;

/**
 * Created by parkjoosung on 2017. 4. 5..
 */

public interface ImagesDataSource {
    <Parameter> Flowable<List<ImageModel>> next(Parameter parameter);
    void switchDataSource(ImageType type);
}
