package ma.sdop.imagelist.web;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.web.parameter.InstagramParameterData;
import ma.sdop.imagelist.web.parameter.ParameterBaseData;
import ma.sdop.imagelist.web.dto.DtoBase;

/**
 * Created by parkjoosung on 2017. 3. 6..
 */

public class TaskHandler implements TaskOperation {
    private BaseTask task;
    private List<DtoBase> cachedResult = new ArrayList<>();
    private Context context;
    private BaseTask.OnCompletedListener onCompletedListener;
    private ParameterBaseData parameter;

    public TaskHandler(Context context, BaseTask.OnCompletedListener onCompletedListener, ParameterBaseData parameter, List<DtoBase> cachedResult) {
        this.context = context;
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
        task = new GetImageTask(context, parameter, onCompletedListener);
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
    public void setOnCompletedListener(BaseTask.OnCompletedListener onCompletedListener) {
        this.onCompletedListener = onCompletedListener;
    }

    public static class Builder {
        private Context context;
        private BaseTask.OnCompletedListener onCompletedListener;
        private ParameterBaseData parameter;
        private List<DtoBase> cachedResult;

        public Builder(@NonNull Context context) {
            this.context = context;
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
            TaskHandler taskHandler = new TaskHandler(context, onCompletedListener, parameter, cachedResult);
            return taskHandler;
        }
    }
}
