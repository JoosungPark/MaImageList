package ma.sdop.imagelist.network;

import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import ma.sdop.imagelist.network.response.MaResult;
import ma.sdop.imagelist.network.response.ResponseAdapter;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class WebWrapper {
    private OkHttpClient client;
    private String host;
    private String uri;
    private String parameter = "";

    private HashMap<String, String> parameters = new HashMap();
    private HashMap<String, String> headers = new HashMap();
    private HashMap<String, File> files = new HashMap();

    private Call call;

    public WebWrapper(String host) {
        setHost(host);
        client = new OkHttpClient.Builder().build();
    }

    public WebWrapper setUri(String uri) {
        if ( uri != null ) {
            this.uri = uri;
        }
        return this;
    }

    public WebWrapper setHost(String host) {
        if ( host != null ) {
            this.host = host;
        }
        return this;
    }

    public MaResult get() throws IOException, WebException, JSONException {
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
        return (new URL(new URL(this.host), this.uri)).toString() + this.parameter;
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
        String[] tempesKeys = headerKeys;
        int headerKeysLength = headerKeys.length;

        for(int index = 0; index < headerKeysLength; ++index) {
            String key = tempesKeys[index];
            if(this.headers.get(key) != null) {
                reqestBuilder.addHeader(key, headers.get(key));
            }
        }
    }

    private void clearAllParams() {
        headers.clear();
        parameters.clear();
        files.clear();
        parameter = "";
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
