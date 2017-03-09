package ma.sdop.imagelist.web.dto.n;

import org.simpleframework.xml.Element;

import ma.sdop.imagelist.web.dto.DtoBase;

@Element
public class Item extends DtoBase {
    @Element private String title;

    @Element private String link;

    @Element private String thumbnail;

    @Element private int sizeheight;

    @Element private int sizewidth;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getSizeheight() {
        return sizeheight;
    }

    public void setSizeheight(int sizeheight) {
        this.sizeheight = sizeheight;
    }

    public int getSizewidth() {
        return sizewidth;
    }

    public void setSizewidth(int sizewidth) {
        this.sizewidth = sizewidth;
    }
}
