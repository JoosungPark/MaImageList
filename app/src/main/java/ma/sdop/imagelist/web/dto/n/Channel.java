package ma.sdop.imagelist.web.dto.n;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Collection;

import ma.sdop.imagelist.web.dto.DtoBase;

/**
 * Created by parkjoosung on 2017. 3. 9..
 */

@Element
public class Channel extends DtoBase{
    @Element private String title;
    @Element private String link;
    @Element private String description;
    @Element private String lastBuildDate;

    @Element private int total;
    @Element private int start;
    @Element private int display;

    @ElementList (inline = true, required = false)
    private ArrayList<Item> item;

    public ArrayList<Item> getItem() {
        return item;
    }

    public void setItem(ArrayList<Item> item) {
        this.item = item;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
