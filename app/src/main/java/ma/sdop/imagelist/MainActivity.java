package ma.sdop.imagelist;

import android.os.Bundle;

import ma.sdop.imagelist.common.BaseActivity;
import ma.sdop.imagelist.ui.MaImageFragment;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getReplaceBuilder(MaImageFragment.class).replace(true);
    }
}
