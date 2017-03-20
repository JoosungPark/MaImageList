package ma.sdop.imagelist.common.web.dto.json.gson;

public class ImageDto {
    private ResolutionDto standard_resolution;
    private ResolutionDto thumbnail;
    private ResolutionDto low_resolution;

    public ResolutionDto getLow_resolution() {
        return low_resolution;
    }

    public void setLow_resolution(ResolutionDto low_resolution) {
        this.low_resolution = low_resolution;
    }

    public ResolutionDto getStandard_resolution() {
        return standard_resolution;
    }

    public void setStandard_resolution(ResolutionDto standard_resolution) {
        this.standard_resolution = standard_resolution;
    }

    public ResolutionDto getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ResolutionDto thumbnail) {
        this.thumbnail = thumbnail;
    }
}
