package ma.sdop.imagelist.web.response;

import org.json.JSONException;

import java.util.List;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.web.WebConfig;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class MaResult extends AbstractResult {
    public <T>T getDto(Class<T> type, String... deps) throws Exception {
        switch (WebConfig.responseType) {
            case R.string.response_xml:
                return getXmlModel(type);
            case R.string.response_json:
                return getModel(type, deps);
        }
        return getModel(type, deps);
    }

    public <T>List<T> getDtoList(Class<T> type) throws Exception {
        return getModelList(type);
    }
}
