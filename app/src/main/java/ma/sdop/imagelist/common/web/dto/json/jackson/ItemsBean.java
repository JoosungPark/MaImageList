package ma.sdop.imagelist.common.web.dto.json.jackson;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.common.data.ImageData;
import ma.sdop.imagelist.common.web.dto.BaseDto;
import ma.sdop.imagelist.common.web.parameter.BaseParameter;
import ma.sdop.imagelist.common.web.parameter.InstagramParameter;

/**
 * Created by parkjoosung on 2017. 3. 20..
 */

public class ItemsBean extends BaseDto {
    private String status;
    private boolean more_available;
    private List<ItemBean> items = new ArrayList<>();

    public void addItems(List<ItemBean> items) {
        this.items.addAll(items);
    }

    public List<ItemBean> getItems() {
        return items;
    }

    public void setItems(List<ItemBean> items) {
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
        for ( ItemBean itemDto : items ) {
            ImageData data = itemDto.getConcreteImageData();
            if (data != null) imageList.add(data);
        }
        return imageList;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public boolean isNext(BaseParameter parameter) {
        InstagramParameter parameterData = (InstagramParameter) parameter;
        String maxId = getLastId();
        if ( more_available ) parameterData.setMaxId(maxId);
        else parameterData.setMaxId(null);

        return more_available;
    }
}
