package ma.sdop.imagelist.mvvm.images;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.BaseFragment;

/**
 * Created by parkjoosung on 2017. 4. 5..
 */

public class ImagesFragment extends BaseFragment {

    @BindView(R.id.ma_image_input_id)
    private EditText imageInputId;

    @BindView(R.id.setting_description_api)
    private TextView settingDescriptionApi;

    @BindView(R.id.description_layout)
    private View descriptionLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ma_image, container, false);
        ButterKnife.bind(this, view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
