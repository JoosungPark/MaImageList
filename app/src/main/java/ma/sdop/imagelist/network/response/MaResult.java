package ma.sdop.imagelist.network.response;

import org.json.JSONException;

import java.util.List;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class MaResult extends Result {
    public <T>T getDto(Class<T> type, String... deps) throws JSONException {
        return getModel(type, deps);
    }

    public <T>List<T> getDtoList(Class<T> type) throws JSONException {
        return getModelList(type);
    }
}
