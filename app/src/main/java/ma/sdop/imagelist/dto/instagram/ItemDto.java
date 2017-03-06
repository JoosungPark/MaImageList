package ma.sdop.imagelist.dto.instagram;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.dto.DtoBase;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class ItemDto extends DtoBase {
    private ImageDto images;

    public ImageDto getImages() {
        return images;
    }

    public void setImages(ImageDto images) {
        this.images = images;
    }
}
