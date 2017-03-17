package ma.sdop.imagelist.common.web.result;

import java.util.List;

import ma.sdop.imagelist.common.web.dto.BaseDto;
import ma.sdop.imagelist.common.web.operator.HttpOperator;
import okhttp3.Response;

/**
 * Created by parkjoosung on 2017. 3. 17..
 */

public class ResultAdapter {
    private Response response;
    private String body;
    private HttpOperator operator;

    public Response getResponse() {
        return response;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public HttpOperator getOperator() {
        return operator;
    }

    public void setOperator(HttpOperator operator) {
        this.operator = operator;
    }

    public <Dto> Dto getDto(Class<Dto> dtoClass, String... depths) throws Exception {
        return operator.getDto(body, dtoClass, depths);
    }

    public <Dto> List<Dto> getDtoList(Class<Dto> type) throws Exception {
        return operator.getDtoList(body, type);
    }
}
