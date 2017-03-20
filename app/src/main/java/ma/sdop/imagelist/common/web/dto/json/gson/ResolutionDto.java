package ma.sdop.imagelist.common.web.dto.json.gson;

import ma.sdop.imagelist.common.web.dto.BaseDto;

/**
 * Created by parkjoosung on 2017. 3. 20..
 */

public class ResolutionDto extends BaseDto {
    private int width;
    private int height;
    private String url;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
