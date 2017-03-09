package ma.sdop.imagelist.common;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaseFragment extends Fragment {
    protected String TAG = getClass().getSimpleName();
    protected BaseActivity activity;
    protected Map<String, Object> parameters = new LinkedHashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
    }

    protected View findViewById(@IdRes int id) {
        return getView().findViewById(id);
    }

    protected Map<String, Object> getParameters() {
        return parameters;
    }

    protected void setFragmentResult() {
        if ( getTargetFragment() != null ) getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
    }

    protected void setFragmentResult(Intent data) {
        if ( getTargetFragment() != null ) getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
    }

    protected void setFragmentResult(int resultCode, Intent data) {
        if ( getTargetFragment() != null ) getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, data);
    }

    public void onBackPressed() {
        activity.getFragmentManager().popBackStack();
    }
}
