package ma.sdop.imagelist.web.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public abstract class AbstractResult extends ResponseAdapter {

    public static JsonDeserializer<Date> dateDeserialize = new JsonDeserializer() {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return json == null?null:new Date(json.getAsLong());
        }
    };

    public <Dto> Dto getModel(Class<Dto> dtoClass, String... depths) throws JSONException {
        return this.fromJson(dtoClass, depths);
    }

    protected <T> List<T> getModelList(Class<T> type) throws JSONException {
        return fromJsonList(type);
    }

    protected <T> List<T> fromJsonList(Class<T> type) throws JSONException {
        ArrayList list = new ArrayList();
        JSONArray array = getJSONArray(new String[0]);
        if(array != null) {
            for(int i = 0; i < array.length(); ++i) {
                list.add(this.fromJson(type, array.getJSONObject(i)));
            }
        }

        return list;
    }

    protected JSONObject getJSON(String... depthJsonKey) throws JSONException {
        if(getBody() != null && getBody().length() != 0) {
            if(depthJsonKey != null && depthJsonKey.length != 0) {
                JSONObject json = new JSONObject(this.getBody());
                String[] tempDepths = depthJsonKey;
                int depth = depthJsonKey.length;

                for(int index = 0; index < depth; ++index) {
                    String key = tempDepths[index];
                    if(json.isNull(key)) {
                        return null;
                    }

                    json = json.getJSONObject(key);
                }

                return json;
            } else {
                return new JSONObject(this.getBody());
            }
        } else {
            return null;
        }
    }

    protected JSONArray getJSONArray(String... depthJsonKey) throws JSONException {
        if(getBody() != null && getBody().length() != 0) {
            if(depthJsonKey != null && depthJsonKey.length != 0) {
                JSONObject json = new JSONObject(getBody());

                for(int i = 0; i < depthJsonKey.length - 1; ++i) {
                    if(json.isNull(depthJsonKey[i])) {
                        return null;
                    }

                    json = json.optJSONObject(depthJsonKey[i]);
                }

                if(json.isNull(depthJsonKey[depthJsonKey.length - 1])) {
                    return null;
                } else {
                    return json.optJSONArray(depthJsonKey[depthJsonKey.length - 1]);
                }
            } else {
                return new JSONArray(getBody());
            }
        } else {
            return null;
        }
    }

    public <Dto> Dto fromJson(Class<Dto> dtoClass, JSONObject json) {
        Gson gson = getReponseGson();
        Object result = gson.fromJson(json.toString(), dtoClass);
        return (Dto) result;
    }

    public <Dto> Dto fromJson(Class<Dto> dtoClass, String... depths) throws JSONException {
        JSONObject jsonObject = this.getJSON(depths);
        if(jsonObject == null) {
            return null;
        } else {
            Gson gson = getReponseGson();
            Object result = gson.fromJson(jsonObject.toString(), dtoClass);
            return (Dto) result;
        }
    }

    private static Gson getReponseGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, dateDeserialize);
        return builder.create();
    }

}
