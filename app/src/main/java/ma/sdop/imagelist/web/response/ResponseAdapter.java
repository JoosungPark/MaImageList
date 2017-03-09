package ma.sdop.imagelist.web.response;

import okhttp3.Response;

public class ResponseAdapter {
    protected Response response;
    protected String body;

    public ResponseAdapter() {
    }

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
}
