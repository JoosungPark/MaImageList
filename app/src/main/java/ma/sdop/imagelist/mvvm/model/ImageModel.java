package ma.sdop.imagelist.mvvm.model;

/**
 * Created by parkjoosung on 2017. 4. 5..
 */

public class ImageModel {
    protected int width;
    protected int height;
    protected String imageUrl;

    public ImageModel(int width, int height, String imageUrl) {
        this.width = width;
        this.height = height;
        this.imageUrl = imageUrl;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
