package ma.sdop.imagelist.web;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.web.response.MaResult;
import ma.sdop.imagelist.web.response.ResponseAdapter;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * just wrapper class about okhttp.
 *
 * @author parkjoosung
 */
public class WebWrapper {
    private final Context context;

    private OkHttpClient client;
    private String host;
    private String uri;

    private HashMap<String, String> parameters = new HashMap();
    private HashMap<String, String> headers = new HashMap();
    private HashMap<String, File> files = new HashMap();

    private Call call;

    public WebWrapper(Context context) {
        this.context = context;
        setHost();
        client = new OkHttpClient.Builder().build();
    }

    public WebWrapper setUri(String uri) {
        if ( uri != null ) this.uri = uri;
        return this;
    }

    public WebWrapper setHost() {
        switch (WebConfig.apiType) {
            case R.string.api_instragram:
                this.host = WebConfig.HOST_INSTAGRAM;
                break;
            case R.string.api_n:
                this.host = WebConfig.HOST_N;
                break;
        }
        return this;
    }

    public WebWrapper addParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    public WebWrapper addParameter(String key, int value) {
        parameters.put(key, "" + value);
        return this;
    }

    public WebWrapper addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    private void initHeader() {
        switch (WebConfig.apiType) {
            case R.string.api_instragram:
                break;
            case R.string.api_n:
                addHeader(WebConfig.N.Header.KEY_CLIENT_ID, WebConfig.N.Header.VALUE_CLIENT_ID);
                addHeader(WebConfig.N.Header.KEY_CLIENT_SECRET, WebConfig.N.Header.VALUDE_CLIENT_SECRET);
                break;
        }
    }

    public MaResult get() throws IOException, WebException, JSONException {
        initHeader();
        return get(MaResult.class);
    }

    private <ResultType extends ResponseAdapter> ResultType get(Class<ResultType> resultType) throws IOException, WebException {
        HttpUrl.Builder builder =  HttpUrl.parse(getFullUrl()).newBuilder();

        String[] keys = getParameterKeys();
        String[] httpUrl = keys;
        int reqestBuilder = keys.length;

        for(int request = 0; request < reqestBuilder; ++request) {
            String response = httpUrl[request];
            builder.addEncodedQueryParameter(response, parameters.get(response));
        }

        Request.Builder requestBuilder = (new Request.Builder()).url(builder.build());
        addHeaderAll(requestBuilder);
        Request request = requestBuilder.build();
        clearAllParams();
        this.call = this.client.newCall(request);
        Response response = this.call.execute();
        ResponseAdapter result = this.getResult(response, resultType);
        this.unexpectedCode(response, result.getBody());
        return (ResultType) result;
    }

    public String getFullUrl() throws MalformedURLException {
        return (new URL(new URL(this.host), this.uri)).toString();
    }

    private String[] getParameterKeys() {
        if(parameters.size() == 0) {
            return new String[0];
        } else {
            String[] keys = parameters.keySet().toArray(new String[0]);
            return keys == null ? new String[0] : keys;
        }
    }

    private String[] getHeaderKeys() {
        if(headers.size() == 0) {
            return new String[0];
        } else {
            String[] keys = headers.keySet().toArray(new String[0]);
            return keys == null ? new String[0] : keys;
        }
    }

    private void addHeaderAll(Request.Builder reqestBuilder) {
        String[] headerKeys = getHeaderKeys();
        int headerKeysLength = headerKeys.length;

        for(int index = 0; index < headerKeysLength; ++index) {
            String key = headerKeys[index];
            if(this.headers.get(key) != null) {
                reqestBuilder.addHeader(key, headers.get(key));
            }
        }
    }

    private void clearAllParams() {
        headers.clear();
        parameters.clear();
        files.clear();
    }

    private <ResultType extends ResponseAdapter> ResultType getResult(Response response, Class<ResultType> type) throws IOException {
        try {
            Constructor constructor = type.getConstructor(new Class[0]);
            ResponseAdapter obj = (ResponseAdapter)constructor.newInstance(new Object[0]);
            obj.setResponse(response);
            obj.setBody(response.body().string());
            return (ResultType) obj;
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(type.getName() + " class does not implement Constructor(Response)", ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(type.getName() + " class does not implement Constructor InvocationTargetException", ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(type.getName() + " class can not make instance Constructor(Response)", ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(type.getName() + " class illegalAccess Constructor(Response)", ex);
        }
    }

    private void unexpectedCode(Response response, String bodyString) throws WebException {
        if(!response.isSuccessful()) {
            Log.e(this.getClass().getSimpleName(), "Server Response Error Unexpected code:" + response.code());
            Log.e(this.getClass().getSimpleName(), response.message());
            throw new WebException(response, bodyString, "Unexpected code " + response);
        }
    }
}
