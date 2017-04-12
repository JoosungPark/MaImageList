package ma.sdop.imagelist.mvvm.func.images;

import ma.sdop.imagelist.mvvm.BaseView;
import ma.sdop.imagelist.mvvm.BaseViewModel;
import ma.sdop.imagelist.mvvm.model.ImageType;

/**
 * Created by parkjoosung on 2017. 4. 5..
 */

public interface ImagesContract {
    interface View extends BaseView<ViewModel> {
    }

    interface ViewModel extends BaseViewModel {
        void switchService(ImageType imageType);
        void getImages();
        void next();
    }
}
