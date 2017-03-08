package ma.sdop.imagelist.web;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.data.InstagramParameterData;
import ma.sdop.imagelist.common.data.ParameterBaseData;
import ma.sdop.imagelist.web.dto.DtoBase;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class TaskHandler implements TaskOperation {
    private BaseTask task;
    private List<DtoBase> cachedResult = new ArrayList<>();
    private Context context;
    private @StringRes int apiType = R.string.api_instragram;
    private BaseTask.OnCompletedListener onCompletedListener;
    private ParameterBaseData parameter;

    public TaskHandler(Context context, @StringRes int apiType, BaseTask.OnCompletedListener onCompletedListener, ParameterBaseData parameter, List<DtoBase> cachedResult) {
        this.context = context;
        this.apiType = apiType;
        this.onCompletedListener = onCompletedListener;
        this.parameter = parameter;
        if ( cachedResult == null ) this.cachedResult = new ArrayList<>();
        else this.cachedResult = cachedResult;
    }

    @Override
    public void execute() {
        createTask();

        if (task != null) {
            task.execute();
        }
    }

    private void createTask() {
        task = null;
        switch (apiType) {
            case R.string.api_instragram:
                if ( parameter instanceof InstagramParameterData) {
                    InstagramParameterData instagramParameterData = (InstagramParameterData) parameter;
                    task = new GetImageTask(context, instagramParameterData.getUserId(),instagramParameterData.getMaxId(), onCompletedListener);
                }
        }

        if ( task != null ) task.addOnCompletedListener(cachedOnCompletedListener);
    }

    @Override
    public boolean next() {
        if ( task != null && task.isNext() ) {
            parameter = task.getNextParameter();
            createTask();

            if (task != null ) {
                task.execute();
                return true;
            }
        }

        return false;
    }

    @Override
    public ParameterBaseData getParameters() {
        return parameter;
    }

    @Override
    public @StringRes int getApiType() {
        return apiType;
    }

    @Override
    public List<DtoBase> getResults() {
        return cachedResult;
    }

    @Override
    public void setOnCompletedListener(BaseTask.OnCompletedListener onCompletedListener) {
        this.onCompletedListener = onCompletedListener;
    }

    private BaseTask.OnCompletedListener cachedOnCompletedListener = new BaseTask.OnCompletedListener() {
        @Override
        public <T extends DtoBase> void onCompleted(boolean isSuccess, T result) {
            if ( result != null ) cachedResult.add(result);
        }
    };

    public static class Builder {
        private Context context;
        private @StringRes int apiType = R.string.api_instragram;
        private BaseTask.OnCompletedListener onCompletedListener;
        private ParameterBaseData parameter;
        private List<DtoBase> cachedResult;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setApiType(@StringRes int apiType) {
            this.apiType = apiType;
            return this;
        }

        public Builder setOnCompleteListener(BaseTask.OnCompletedListener onCompleteListener) {
            this.onCompletedListener = onCompleteListener;
            return this;
        }

        public Builder setParameter(ParameterBaseData parameter) {
            this.parameter = parameter;
            return this;
        }

        public Builder setCachedResult(List<DtoBase> cachedResult) {
            this.cachedResult = cachedResult;
            return this;
        }

        public TaskHandler build() {
            TaskHandler taskHandler = new TaskHandler(context, apiType, onCompletedListener, parameter, cachedResult);
            return taskHandler;
        }
    }
}
