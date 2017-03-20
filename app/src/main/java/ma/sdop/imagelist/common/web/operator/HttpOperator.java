package ma.sdop.imagelist.common.web.operator;

import java.util.List;

import ma.sdop.imagelist.common.web.dto.BaseDto;

public interface HttpOperator {
    <Dto extends BaseDto> Dto getDto(String body, Class<Dto> dtoClass, String... depths) throws Exception;
    <Dto extends BaseDto> List<Dto> getDtoList(String body, Class<Dto> type) throws Exception;
}
