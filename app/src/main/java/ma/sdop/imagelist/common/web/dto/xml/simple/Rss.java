package ma.sdop.imagelist.common.web.dto.xml.simple;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.common.data.ImageData;
import ma.sdop.imagelist.common.web.dto.BaseDto;
import ma.sdop.imagelist.common.web.parameter.BaseParameter;
import ma.sdop.imagelist.common.web.parameter.NParameter;

@Root
public class Rss extends BaseDto {
    @Element
    private Channel channel;

    @Attribute
    private String version;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public List<ImageData> getImageData() {
        List<ImageData> list = new ArrayList<>();
        if ( channel != null && channel.getItem() != null ) {
            for ( Item item : channel.getItem() ) {
                ImageData data = new ImageData(item.getSizewidth(), item.getSizeheight(), item.getLink());
                list.add(data);
            }
        }

        return list;
    }

    public int getTotal() {
        int total = 0;
        if ( channel != null ) total = channel.getTotal();
        return total;
    }

    public int getCount() {
        int count=0;
        if ( channel != null ) count = channel.getDisplay();
        return count;
    }

    @Override
    public boolean isNext(BaseParameter parameter) {
        NParameter parameterData = (NParameter) parameter;
        parameterData.setTotalCount(getTotal());
        parameterData.setNextStart();

        return parameterData.isNext();
    }
}
