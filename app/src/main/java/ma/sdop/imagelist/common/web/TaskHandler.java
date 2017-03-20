package ma.sdop.imagelist.common.web;

import android.content.Context;
import android.support.annotation.NonNull;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.web.dto.ImageDtoOperation;
import ma.sdop.imagelist.common.web.parameter.BaseParameter;
import ma.sdop.imagelist.common.web.parameter.InstagramParameter;


/**
 * TaskHandler manage several task based on BaseTask.
 * it is possible to add specific api server if needs.
 *
 * @author parkjoosung
 */
public class TaskHandler<ResultType extends ImageDtoOperation> implements TaskOperation {
    private GetImageTask<ResultType> task;
    private Context context;
    private BaseTask.OnCompletedListener onCompletedListener;
    private BaseParameter parameter;
    private WebWrapper webWrapper;
    private Class<ResultType> resultType;

    private TaskHandler(@NonNull Context context, @NonNull Class<ResultType> resultType,
                        BaseTask.OnCompletedListener onCompletedListener, WebWrapper webWrapper, BaseParameter parameter) {
        this.context = context;
        this.resultType = resultType;
        this.onCompletedListener = onCompletedListener;
        this.webWrapper = webWrapper;
        if ( webWrapper == null ) setDefaultWebWrapper();
        this.parameter = parameter;
    }

    private void setDefaultWebWrapper() {
        webWrapper = new WebWrapper(context, WebConfig.HOST_INSTAGRAM);
        InstagramParameter parameterData = (InstagramParameter) parameter;
        webWrapper.setUri(WebConfig.INSTAGRAM.getApi(parameterData.getUserId(), parameterData.getMaxId()));
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
        task = new GetImageTask<>(context, parameter, onCompletedListener, webWrapper, resultType);
    }

    @Override
    public boolean next() {
        if ( task != null && task.isNext() ) {
            parameter = task.getNextParameter();

            if ( WebConfig.apiType == R.string.api_instragram) {
                InstagramParameter parameterData = (InstagramParameter) parameter;
                webWrapper.setUri(WebConfig.INSTAGRAM.getApi(parameterData.getUserId(), parameterData.getMaxId()));
            }

            createTask();

            if (task != null ) {
                task.execute();
                return true;
            }
        }

        return false;
    }

    @Override
    public void setOnCompletedListener(BaseTask.OnCompletedListener onCompletedListener) {
        this.onCompletedListener = onCompletedListener;
    }

    public static class Builder<ResultType extends ImageDtoOperation> {
        private Context context;
        private BaseTask.OnCompletedListener onCompletedListener;
        private BaseParameter parameter;
        private WebWrapper webWrapper;

        private Class<ResultType> resultType;

        @SuppressWarnings("unchecked")
        public  Builder(@NonNull Context context, Class<ResultType> resultType) {
            this.context = context;
            this.resultType = resultType;
        }

        public Builder setParameter(BaseParameter parameter) {
            this.parameter = parameter;
            return this;
        }

        public Builder setOnCompletedListener(BaseTask.OnCompletedListener onCompletedListener) {
            this.onCompletedListener = onCompletedListener;
            return this;
        }

        public Builder setWebWrapper(WebWrapper webWrapper) {
            this.webWrapper = webWrapper;
            return this;
        }

        public TaskHandler build() {
            TaskHandler taskHandler = new TaskHandler<>(context, resultType, onCompletedListener, webWrapper, parameter);
            return taskHandler;
        }
    }
}
