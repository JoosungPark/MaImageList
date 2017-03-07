package ma.sdop.imagelist.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.BaseFragment;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public class MaImageFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ma_image, container, false);
    }
}
