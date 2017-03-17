package ma.sdop.imagelist.common.web.operator.xml;

import java.util.List;

/**
 * Created by parkjoosung on 2017. 3. 16..
 */

public class SimpleXmlOperator extends XmlOperator {
    @Override
    public <Dto> Dto getDto(String body, Class<Dto> dtoClass, String... depths) throws Exception {
        return null;
    }

    @Override
    public <Dto> List<Dto> getDtoList(String body, Class<Dto> type) throws Exception {
        return null;
    }
}
