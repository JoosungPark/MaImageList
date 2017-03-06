package ma.sdop.imagelist.dto.instagram;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.dto.DtoBase;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class ItemsDto extends DtoBase {
    private String status;
    private boolean more_available;
    private List<ItemDto> items = new ArrayList<>();

    public void addItems(List<ItemDto> items) {
        this.items.addAll(items);
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getMore_available() {
        return more_available;
    }

    public void setMore_available(Boolean more_available) {
        this.more_available = more_available;
    }
}
