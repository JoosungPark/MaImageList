package ma.sdop.imagelist.common.data;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

abstract public class BaseData {
    protected int width;
    protected int height;
    protected String imageUrl;
    protected int current=0;

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

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
