package ma.sdop.imagelist;

import android.os.Bundle;

import ma.sdop.imagelist.common.BaseActivity;
import ma.sdop.imagelist.common.DisableLogger;
import ma.sdop.imagelist.mvvm.func.images.ImagesFragment;
import uk.co.senab.photoview.log.LogManager;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogManager.setLogger(new DisableLogger());

//        getReplaceBuilder(MaImageFragment.class).replace(true);

        getReplaceBuilder(ImagesFragment.class).replace(true);
    }
}
