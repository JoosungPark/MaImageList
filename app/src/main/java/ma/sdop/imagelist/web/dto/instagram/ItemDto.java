package ma.sdop.imagelist.web.dto.instagram;

import ma.sdop.imagelist.common.data.ImageData;
import ma.sdop.imagelist.web.dto.DtoBase;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class ItemDto extends DtoBase {
    private ImageDto images;
    private String id;

    public ImageDto getImages() {
        return images;
    }

    public void setImages(ImageDto images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImageData getConcreteImageData() {
        ImageData result = null;
        if ( images != null ) {
            ResolutionDto resolutionDto = images.getStandard_resolution();
            if ( resolutionDto != null ) {
                result = new ImageData(resolutionDto.getWidth(), resolutionDto.getHeight(), resolutionDto.getUrl());
            }
        }

        return result;
    }
}
