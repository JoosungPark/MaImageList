package ma.sdop.imagelist.web;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.common.ApiType;
import ma.sdop.imagelist.common.data.BaseData;
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
    private ApiType apiType = ApiType.Instagram;
    private BaseTask.OnCompletedListener onCompletedListener;
    private ParameterBaseData parameter;

    public TaskHandler(Context context, ApiType apiType, BaseTask.OnCompletedListener onCompletedListener, ParameterBaseData parameter, List<DtoBase> cachedResult) {
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
            case Instagram:
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
    public ApiType getApiType() {
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
            cachedResult.add(result);
        }
    };

    public static class Builder {
        private Context context;
        private ApiType apiType = ApiType.Instagram;
        private BaseTask.OnCompletedListener onCompletedListener;
        private ParameterBaseData parameter;
        private List<DtoBase> cachedResult;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setApiType(ApiType apiType) {
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
