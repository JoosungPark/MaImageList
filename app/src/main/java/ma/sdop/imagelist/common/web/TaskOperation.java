package ma.sdop.imagelist.common.web;

/**
 *
 * TaskOperation.
 *
 * @author parkjoosung
 *
 */
public interface TaskOperation<ResultType> {
    void execute();
    boolean next();
    void setOnCompletedListener(BaseTask.OnCompletedListener listener);
}
