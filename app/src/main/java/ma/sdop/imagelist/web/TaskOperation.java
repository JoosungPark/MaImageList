package ma.sdop.imagelist.web;

/**
 *
 * TaskOperation.
 *
 * @author parkjoosung
 *
 */
public interface TaskOperation {
    void execute();
    boolean next();
    void setOnCompletedListener(BaseTask.OnCompletedListener listener);
}
