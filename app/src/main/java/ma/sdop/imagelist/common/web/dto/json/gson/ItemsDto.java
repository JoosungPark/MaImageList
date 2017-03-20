package ma.sdop.imagelist.common.web.dto.json.gson;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.common.data.ImageData;
import ma.sdop.imagelist.common.web.dto.BaseDto;

public class ItemsDto extends BaseDto {
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

    public String getLastId() {
        if ( items != null && items.size() > 0 ) {
            return  items.get(items.size()-1).getId();
        } else {
            return null;
        }
    }

    @Override
    public List<ImageData> getImageData() {
        List<ImageData> imageList = new ArrayList<>();
        for ( ItemDto itemDto : items ) {
            ImageData data = itemDto.getConcreteImageData();
            if (data != null) imageList.add(data);
        }
        return imageList;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }
}
