package ma.sdop.imagelist.common.web.dto;

import java.util.List;

import ma.sdop.imagelist.common.data.ImageData;

/**
 * Created by parkjoosung on 2017. 3. 17..
 */

public class BaseDto implements DtoOperation {
    @Override
    public List<ImageData> getImageData() {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
