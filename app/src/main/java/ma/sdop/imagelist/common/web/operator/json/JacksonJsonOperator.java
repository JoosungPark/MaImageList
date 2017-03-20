package ma.sdop.imagelist.common.web.operator.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import ma.sdop.imagelist.common.web.operator.HttpOperator;

public class JacksonJsonOperator implements HttpOperator {
    @Override
    public <Dto> Dto getDto(String body, Class<Dto> dtoClass, String... depths) throws Exception {
        return getBean(body, dtoClass);
    }

    @Override
    public <Dto> List<Dto> getDtoList(String body, Class<Dto> type) throws Exception {
        return null;
    }

    private <Dto> Dto getBean(String body, Class<Dto> dtoClass) throws Exception {
        ObjectMapper mapper = getObjectMapper();
        return mapper.readValue(body, dtoClass);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    private <Dto> List<Dto> getBeanList(String body, Class<Dto> dtoClass) throws Exception {
        ObjectMapper mapper = getObjectMapper();
        return mapper.readValue(body, new TypeReference<List<Dto>>() { });
    }
}
