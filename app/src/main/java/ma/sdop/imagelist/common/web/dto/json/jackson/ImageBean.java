package ma.sdop.imagelist.common.web.dto.json.jackson;

import ma.sdop.imagelist.common.web.dto.BaseDto;

/**
 * Created by parkjoosung on 2017. 3. 20..
 */

public class ImageBean extends BaseDto {
    private ResolutionBean standard_resolution;
    private ResolutionBean thumbnail;
    private ResolutionBean low_resolution;

    public ResolutionBean getLow_resolution() {
        return low_resolution;
    }

    public void setLow_resolution(ResolutionBean low_resolution) {
        this.low_resolution = low_resolution;
    }

    public ResolutionBean getStandard_resolution() {
        return standard_resolution;
    }

    public void setStandard_resolution(ResolutionBean standard_resolution) {
        this.standard_resolution = standard_resolution;
    }

    public ResolutionBean getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ResolutionBean thumbnail) {
        this.thumbnail = thumbnail;
    }
}
