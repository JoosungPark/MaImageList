package ma.sdop.imagelist.common.web.operator.json;

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
 * Created by parkjoosung on 2017. 3. 16..
 */

public class GsonJsonOperator extends JsonOperator {
    private static JsonDeserializer<Date> dateDeserialize = new JsonDeserializer<Date>() {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return json == null?null:new Date(json.getAsLong());
        }
    };

    @Override
    public <Dto> Dto getDto(String body, Class<Dto> dtoClass, String... depths) throws Exception {
        return fromJson(body, dtoClass, depths);
    }

    @Override
    public <Dto> List<Dto> getDtoList(String body, Class<Dto> type) throws Exception {
        return fromJsonList(body, type);
    }

    private <Dto> Dto fromJson(String json, Class<Dto> dtoClass, String... depths) throws JSONException {
        JSONObject jsonObject = getJSON(json, depths);
        if(jsonObject == null) {
            return null;
        } else {
            Gson gson = getGson();
            return gson.fromJson(jsonObject.toString(), dtoClass);
        }
    }

    private <DTO> List<DTO> fromJsonList(String json, Class<DTO> type) throws JSONException {
        ArrayList<DTO> list = new ArrayList<>();
        JSONArray array = getJSONArray(json);
        if (array != null) {
            for ( int i=0; i < array.length(); i++ ) {
                list.add(fromJson(type, array.getJSONObject(i)));
            }
        }

        return list;
    }

    private  <Dto> Dto fromJson(Class<Dto> dtoClass, JSONObject json) {
        Gson gson = getGson();
        return gson.fromJson(json.toString(), dtoClass);
    }

    private JSONObject getJSON(String json, String... depthJsonKey) throws JSONException {
        if(json != null && json.length() != 0) {
            if (depthJsonKey != null && depthJsonKey.length != 0) {
                JSONObject jsonObject = new JSONObject(json);

                for (String key : depthJsonKey ) {
                    if ( jsonObject.isNull(key) ) return null;
                    jsonObject = jsonObject.getJSONObject(key);
                }

                return jsonObject;
            } else {
                return new JSONObject(json);
            }
        } else {
            return null;
        }
    }

    private JSONArray getJSONArray(String json, String... depthJsonKey) throws JSONException {
        if ( json != null && json.length() != 0 ) {
            if (depthJsonKey != null && depthJsonKey.length != 0) {
                JSONObject jsonObject = new JSONObject(json);

                for (String key : depthJsonKey ) {
                    if ( jsonObject.isNull(key) ) return null;
                    jsonObject = jsonObject.getJSONObject(key);
                }

                if (jsonObject.isNull(depthJsonKey[depthJsonKey.length - 1])) {
                    return null;
                } else {
                    return jsonObject.optJSONArray(depthJsonKey[depthJsonKey.length - 1]);
                }
            } else {
                return new JSONArray(json);
            }
        }
        return null;
    }

    private static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, dateDeserialize);
        return builder.create();
    }
}
