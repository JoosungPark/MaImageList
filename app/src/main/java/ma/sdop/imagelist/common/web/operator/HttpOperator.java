package ma.sdop.imagelist.common.web.operator;

import java.util.List;

public interface HttpOperator {
    <Dto> Dto getDto(String body, Class<Dto> dtoClass, String... depths) throws Exception;
    <Dto> List<Dto> getDtoList(String body, Class<Dto> type) throws Exception;
}
