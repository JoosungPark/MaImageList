package ma.sdop.imagelist.web;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public interface TaskOperation {
    void execute();
    boolean next();
    void setOnCompletedListener(BaseTask.OnCompletedListener listener);
}
