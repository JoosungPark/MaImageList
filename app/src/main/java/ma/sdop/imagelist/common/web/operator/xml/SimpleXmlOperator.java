package ma.sdop.imagelist.common.web.operator.xml;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

import ma.sdop.imagelist.common.web.dto.BaseDto;

public class SimpleXmlOperator implements XmlOperator {
    @Override
    public <Dto extends BaseDto> Dto getDto(String body, Class<Dto> dtoClass, String... depths) throws Exception {
        return getXmlModel(body, dtoClass);
    }

    @Override
    public <Dto extends BaseDto> List<Dto> getDtoList(String body, Class<Dto> type) throws Exception {
        return null;
    }

    private <Dto extends BaseDto> Dto getXmlModel(String json, Class<Dto> dtoClass) throws Exception {
        String source = json;
        Serializer serializer = new Persister();
        return serializer.read(dtoClass, source);
    }
}
