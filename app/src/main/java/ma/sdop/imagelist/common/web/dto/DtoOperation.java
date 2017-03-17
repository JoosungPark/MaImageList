package ma.sdop.imagelist.common.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.common.data.ImageData;

/**
 * Created by parkjoosung on 2017. 3. 17..
 */

public interface DtoOperation extends Serializable {
    List<ImageData> getImageData();
    int getCount();
}
