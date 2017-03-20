package ma.sdop.imagelist.common.web.dto.json.jackson;

import ma.sdop.imagelist.common.data.ImageData;
import ma.sdop.imagelist.common.web.dto.BaseDto;

/**
 * Created by parkjoosung on 2017. 3. 20..
 */

public class ItemBean extends BaseDto {
    private ImageBean images;
    private String id;

    public ImageBean getImages() {
        return images;
    }

    public void setImages(ImageBean images) {
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
            ResolutionBean resolutionDto = images.getStandard_resolution();
            if ( resolutionDto != null ) {
                result = new ImageData(resolutionDto.getWidth(), resolutionDto.getHeight(), resolutionDto.getUrl());
            }
        }

        return result;
    }
}
