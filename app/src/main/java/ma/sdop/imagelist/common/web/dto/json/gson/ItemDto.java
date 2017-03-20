package ma.sdop.imagelist.common.web.dto.json.gson;

import ma.sdop.imagelist.common.data.ImageData;

public class ItemDto {
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
