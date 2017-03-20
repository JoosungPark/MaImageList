package ma.sdop.imagelist.common.web;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import ma.sdop.imagelist.common.web.operator.HttpOperator;
import ma.sdop.imagelist.common.web.operator.json.GsonJsonOperator;
import ma.sdop.imagelist.common.web.result.ResultAdapter;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebWrapper {
    private final Context context;
    private final OkHttpClient client;
    private final String host;
    private String uri;

    private HashMap<String, String> parameters = new HashMap<>();
    private HashMap<String, String> headers = new HashMap<>();
    private HashMap<String, File> files = new HashMap<>();

    private Call call;
    private HttpOperator operator;

    public WebWrapper(@NonNull Context context, @NonNull String host) {
        this.context = context;
        this.host = host;
        client = new OkHttpClient.Builder().build();
    }

    public WebWrapper setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public WebWrapper addParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    public WebWrapper addParameter(String key, int value) {
        parameters.put(key, String.valueOf(value));
        return this;
    }

    public WebWrapper addParameter(String key, float value) {
        parameters.put(key, String.valueOf(value));
        return this;
    }

    public WebWrapper addParameter(String key, double value) {
        parameters.put(key, String.valueOf(value));
        return this;
    }

    public WebWrapper addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public WebWrapper setOperator(HttpOperator operator) {
        this.operator = operator;
        return this;
    }

    public ResultAdapter get() throws IOException, WebException {
        if ( operator == null ) operator = new GsonJsonOperator();
        return get(ResultAdapter.class);
    }

    private <ResultType extends ResultAdapter> ResultAdapter get(Class<ResultType> resultType) throws IOException, WebException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(getFullUrl()).newBuilder();

        String[] parameterKeys = getParameterKeys();
        for ( String parameterKey : parameterKeys ) urlBuilder.addEncodedQueryParameter(parameterKey, parameters.get(parameterKey));

        Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.build());

        String[] headerKeys = getHeaderKeys();
        for ( String headerKey : headerKeys ) requestBuilder.addHeader(headerKey, headers.get(headerKey));

        clear();

        Request request = requestBuilder.build();
        call = client.newCall(request);
        Response response = call.execute();

        ResultAdapter resultAdapter = getResultAdapter(response, resultType);
        checkResponse(response, resultAdapter.getBody());

        return resultAdapter;
    }

    private String getFullUrl() throws MalformedURLException {
        return (new URL(new URL(host), uri)).toString();
    }

    private String[] getParameterKeys() {
        if ( parameters.size() == 0 ) return new String[0];
        else return parameters.keySet().toArray(new String[0]);
    }

    private String[] getHeaderKeys() {
        if ( headers.size() == 0 ) return new String[0];
        else return headers.keySet().toArray(new String[0]);
    }

    private void clear() {
        headers.clear();
        parameters.clear();
        files.clear();
    }

    private <ResultType extends ResultAdapter> ResultAdapter getResultAdapter(Response response, Class<ResultType> type) throws IOException {
        try {
            Constructor constructor = type.getConstructor();
            ResultAdapter adapter = (ResultAdapter) constructor.newInstance();
            adapter.setResponse(response);
            adapter.setBody(response.body().string());
            adapter.setOperator(operator);

            return adapter;
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(type.getName() + " class does not implement Constructor(Response)", ex);
        }  catch (InvocationTargetException ex) {
            throw new RuntimeException(type.getName() + " class does not implement Constructor InvocationTargetException", ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(type.getName() + " class can not make instance Constructor(Response)", ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(type.getName() + " class illegalAccess Constructor(Response)", ex);
        }
    }

    private void checkResponse(Response response, String bodyString) throws WebException {
        if(!response.isSuccessful()) {
            Log.e(getClass().getSimpleName(), "Server Response Error Unexpected code:" + response.code());
            Log.e(getClass().getSimpleName(), response.message());
            throw new WebException(response, bodyString, "Unexpected code " + response);
        }
    }
}
