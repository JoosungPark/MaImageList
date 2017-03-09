package ma.sdop.imagelist.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.BaseFragment;
import ma.sdop.imagelist.common.MaConstants;
import ma.sdop.imagelist.common.MaUtils;
import ma.sdop.imagelist.common.PickerDialog;
import ma.sdop.imagelist.common.data.ImageData;
import ma.sdop.imagelist.common.recycler.DataBindAdapter;
import ma.sdop.imagelist.common.recycler.DataBinder;
import ma.sdop.imagelist.web.parameter.InstagramParameterData;
import ma.sdop.imagelist.web.WebConfig;
import ma.sdop.imagelist.web.dto.DtoBase;
import ma.sdop.imagelist.web.BaseTask;
import ma.sdop.imagelist.web.TaskHandler;
import ma.sdop.imagelist.web.parameter.NParameterData;
import ma.sdop.imagelist.web.parameter.ParameterBaseData;

public class MaImageFragment extends BaseFragment {
    private EditText ma_image_input_id;
    private Button setting_button;
    private TextView setting_description_api;
    private View description_layout;

    private LinearLayoutManager linearLayoutManager;
    private MaImageAdapter maImageAdapter = null;
    private List<ImageData> listItems = new ArrayList<>();

    private int currentPosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ma_image, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");

        ma_image_input_id = (EditText) findViewById(R.id.ma_image_input_id);
        ma_image_input_id.setOnEditorActionListener(editorActionListener);
        RecyclerView ma_image_list = (RecyclerView) findViewById(R.id.ma_image_list);
        setting_button = (Button) findViewById(R.id.setting_button);
        setting_description_api = (TextView) findViewById(R.id.setting_description_api);

        description_layout = findViewById(R.id.description_layout);

        findViewById(R.id.ma_image_search_button).setOnClickListener(onClickListener);
        setting_button.setOnClickListener(onClickListener);
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        ma_image_list.setLayoutManager(linearLayoutManager);
        maImageAdapter = new MaImageAdapter(listItems);
        ma_image_list.setAdapter(maImageAdapter);
        ma_image_list.addOnScrollListener(onScrollListener);

        if ( maImageAdapter.getItemCount() > currentPosition ) {
            description_layout.setVisibility(View.GONE);
            ma_image_list.getLayoutManager().smoothScrollToPosition(ma_image_list, null, currentPosition);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == MaConstants.CODE.REQUEST_IMAGE_DETAIL && resultCode == Activity.RESULT_OK ) {
            currentPosition = data.getIntExtra(MaConstants.CURRENT_INDEX, currentPosition);
            Log.d(TAG, "onActivityResult currentPosition : " + currentPosition);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activity.finish();
    }

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if ( actionId == EditorInfo.IME_ACTION_DONE ) {
                onSearch();
                return true;
            } else {
                return false;
            }
        }
    };

    private TaskHandler taskHandler = null;

    private final BaseTask.OnCompletedListener onCompletedListener = new BaseTask.OnCompletedListener() {
        @Override
        public <T extends DtoBase> void onCompleted(boolean isSuccess, T result) {
            if ( result == null ) {
                @StringRes int wantedId;
                if ( reload ) {
                    wantedId = R.string.err_no_more_image;
                }  else {
                    description_layout.setVisibility(View.VISIBLE);
                    wantedId = R.string.err_empty_result;
                }
                MaUtils.showToast(activity, wantedId);
                reload = false;
                return;
            }

            if ( result.getCount() == 0 ) {
                description_layout.setVisibility(View.VISIBLE);
                MaUtils.showToast(activity, R.string.err_empty_result);
                return;
            }

            description_layout.setVisibility(View.GONE);

            for (ImageData imageData : result.getImageData()) listItems.add(imageData);

            reload = false;
            setting_button.setBackgroundResource(R.drawable.setting);
            maImageAdapter.notifyDataSetChanged();
        }
    };

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ma_image_search_button:
                    onSearch();
                    break;
                case R.id.setting_button:
                    onSetting();
                    break;
            }
        }
    };

    private static final int searchCount = 20;

    private void onSearch() {
        String userId = ma_image_input_id.getText().toString().trim();
        if (TextUtils.isEmpty(userId)) {
            MaUtils.showToast(activity, R.string.err_empty_user_id);
            return;
        }

        listItems.clear();
        maImageAdapter.notifyDataSetChanged();
        description_layout.setVisibility(View.GONE);

        hideIme();

        ParameterBaseData parameter = null;
        @StringRes int responseType = R.string.response_json;

        switch (WebConfig.apiType) {
            case R.string.api_n:
                parameter = new NParameterData(userId, searchCount, 1);
                responseType = R.string.response_xml;
                break;
            case R.string.api_instragram:
                parameter = new InstagramParameterData(userId, "");
                break;
        }

        WebConfig.responseType = responseType;

        taskHandler = new TaskHandler.Builder(activity)
                .setOnCompleteListener(onCompletedListener)
                .setParameter(parameter)
                .build();

        if (taskHandler != null ) taskHandler.execute();
    }

    private void hideIme() {
        View view = activity.getCurrentFocus();
        if ( view != null ) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private int[] pickerList = { R.string.api_instragram, R.string.api_n };

    private void onSetting() {
        new PickerDialog.Builder(activity, pickerList)
                .setOnPickerItemSelectedListener(onPickerItemSelectedListener)
                .setTitleRes(R.string.popup_title_select_api)
                .show();
    }

    private PickerDialog.OnPickerItemSelectedListener onPickerItemSelectedListener = new PickerDialog.OnPickerItemSelectedListener() {
        @Override
        public void OnPickerItemSelected(View view, int position, int stringResId) {
            if ( WebConfig.apiType != stringResId ) {
                WebConfig.apiType = stringResId;

                listItems.clear();
                maImageAdapter.notifyDataSetChanged();
                description_layout.setVisibility(View.VISIBLE);

                @StringRes int hintId = R.string.ma_image_search_hint_instagram;
                @StringRes int apiDescriptionId = R.string.description_instagram;
                ma_image_input_id.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
                if (WebConfig.apiType == R.string.api_n ) {
                    hintId = R.string.ma_image_search_hint_n;
                    apiDescriptionId = R.string.description_n;
                    ma_image_input_id.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
                }

                ma_image_input_id.setText("");
                ma_image_input_id.setHint(hintId);
                setting_description_api.setText(apiDescriptionId);
            }
        }
    };

    private boolean reload = false;

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        private boolean lastItemVisibleFlag = false;
        private int firstVisibleItem, visibleItemCount, totalItemCount;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if ( newState == RecyclerView.SCROLL_STATE_IDLE && lastItemVisibleFlag ) {
                if ( taskHandler != null && taskHandler.next()) reload = true;
                else MaUtils.showToast(activity, R.string.err_no_more_image);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            visibleItemCount = linearLayoutManager.getChildCount();
            totalItemCount = linearLayoutManager.getItemCount();
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

            lastItemVisibleFlag = ( totalItemCount > 0 ) && ( firstVisibleItem + visibleItemCount >= totalItemCount );
        }
    };

    private final class MaImageAdapter extends DataBindAdapter {
        private List<ImageData> imageItems;

        MaImageAdapter(List<ImageData> imageItems) {
            this.imageItems = imageItems;
        }

        @Override
        public int getItemCount() {
            return imageItems.size();
        }

        @Override
        public int getItemViewType(int position) {
            return 1;
        }

        @Override
        public DataBinder getDataBinder(int viewType) {
            return new MaImageBinder(this);
        }

        @Override
        public ImageData getItem(int position) {
            return imageItems.get(position);
        }
    }

    private final class MaImageBinder extends DataBinder<MaImageBinder.ViewHolder> {
        MaImageBinder(DataBindAdapter dataBindAdapter) {
            super(dataBindAdapter);
        }

        @Override
        public MaImageBinder.ViewHolder newViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ma_image_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void bindViewHolder(MaImageBinder.ViewHolder holder, final int position) {
            currentPosition = position;
            ImageData item = (ImageData) dataBindAdapter.getItem(position);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.ma_image_linearlayout.getLayoutParams();
            layoutParams.height = MaUtils.getImageHeight(activity, item.getWidth(), item.getHeight());
            holder.ma_image_linearlayout.setLayoutParams(layoutParams);

            holder.ma_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideIme();
                    activity.getReplaceBuilder(MaImageDetailFragment.class)
                            .setRequestCode(MaImageFragment.this, MaConstants.CODE.REQUEST_IMAGE_DETAIL)
                            .addParameter(MaConstants.CURRENT_INDEX, position)
                            .addParameter(MaConstants.TASK_HANDLER, taskHandler)
                            .addParameter(MaConstants.LIST_ITEMS, listItems)
                            .replace(true);

                }
            });

            Picasso.with(activity)
                    .load(item.getImageUrl())
                    .fit()
                    .centerInside()
                    .into(holder.ma_image);
        }

        final class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView ma_image;
            final LinearLayout ma_image_linearlayout;

            ViewHolder(View itemView) {
                super(itemView);
                ma_image = (ImageView) itemView.findViewById(R.id.ma_image);
                ma_image_linearlayout = (LinearLayout) itemView.findViewById(R.id.ma_image_linearlayout);
            }
        }
    }
}
