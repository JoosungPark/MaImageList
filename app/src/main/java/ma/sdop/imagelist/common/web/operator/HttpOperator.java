package ma.sdop.imagelist.common.web.operator;

import java.util.List;

/**
 * Created by parkjoosung on 2017. 3. 16..
 */

public interface HttpOperator {
    <Dto> Dto getDto(String body, Class<Dto> dtoClass, String... depths) throws Exception;
    <Dto> List<Dto> getDtoList(String body, Class<Dto> type) throws Exception;
}
