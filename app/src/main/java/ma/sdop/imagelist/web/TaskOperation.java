package ma.sdop.imagelist.web;

import android.support.annotation.StringRes;

import java.util.List;

import ma.sdop.imagelist.common.data.ParameterBaseData;
import ma.sdop.imagelist.web.dto.DtoBase;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public interface TaskOperation {
    void execute();
    boolean next();
    List<DtoBase> getResults();
    ParameterBaseData getParameters();
    @StringRes int getApiType();
    void setOnCompletedListener(BaseTask.OnCompletedListener listener);
}
