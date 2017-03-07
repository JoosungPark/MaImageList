package ma.sdop.imagelist.common;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public class BaseFragment extends Fragment {
    protected String TAG = getClass().getSimpleName();
    protected BaseActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (BaseActivity) getActivity();
    }

    protected View findViewById(@IdRes int id) {
        return getView().findViewById(id);
    }
}
