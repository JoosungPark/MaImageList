package ma.sdop.imagelist.common.web.operator.xml;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

public class SimpleXmlOperator implements XmlOperator {
    @Override
    public <Dto> Dto getDto(String body, Class<Dto> dtoClass, String... depths) throws Exception {
        return getXmlModel(body, dtoClass);
    }

    @Override
    public <Dto> List<Dto> getDtoList(String body, Class<Dto> type) throws Exception {
        return null;
    }

    private <Dto> Dto getXmlModel(String json, Class<Dto> dtoClass) throws Exception {
        String source = json;
        Serializer serializer = new Persister();
        return serializer.read(dtoClass, source);
    }
}
