package ma.sdop.imagelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ma.sdop.imagelist.common.BaseActivity;
import ma.sdop.imagelist.common.ReplaceBuilder;
import ma.sdop.imagelist.dto.instagram.ItemsDto;
import ma.sdop.imagelist.ui.MaImageFragment;
import ma.sdop.imagelist.web.GetImageTask;
import ma.sdop.imagelist.web.TaskHandler;

public class MainActivity extends BaseActivity {
    private ItemsDto results = new ItemsDto();

    private TaskHandler taskHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getReplaceBuilder(MaImageFragment.class).replace(true);

        taskHandler = new GetImageTask("design", null, onCompletedListener);
        taskHandler.execute();
    }

    private GetImageTask.OnCompletedListener onCompletedListener = new GetImageTask.OnCompletedListener() {
        @Override
        public void onCompleted(boolean isSuccess, ItemsDto result) {
            results = result;
        }
    };
}
