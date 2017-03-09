package ma.sdop.imagelist.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.common.data.ImageData;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */
public class DtoBase implements Serializable {
    public List<ImageData> getImageData() {
        return new ArrayList<>();
    }
    public int getCount() { return 0; }
}
