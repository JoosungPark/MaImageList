package ma.sdop.imagelist.common.web;

import android.content.Context;
import android.support.annotation.NonNull;

import ma.sdop.imagelist.web.parameter.ParameterBaseData;


/**
 * TaskHandler manage several task based on BaseTask.
 * it is possible to add specific api server if needs.
 *
 * @author parkjoosung
 */
public class TaskHandler implements TaskOperation {
    private BaseTask task;
    private Context context;
    private BaseTask.OnCompletedListener onCompletedListener;
    private ParameterBaseData parameter;

    public TaskHandler(Context context, BaseTask.OnCompletedListener onCompletedListener, ParameterBaseData parameter) {
        this.context = context;
        this.onCompletedListener = onCompletedListener;
        this.parameter = parameter;
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

        public TaskHandler build() {
            TaskHandler taskHandler = new TaskHandler(context, onCompletedListener, parameter);
            return taskHandler;
        }
    }
}