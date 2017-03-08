package ma.sdop.imagelist.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.BaseFragment;
import ma.sdop.imagelist.common.MaConstants;
import ma.sdop.imagelist.common.MaMovableTouchListener;
import ma.sdop.imagelist.common.MaUtils;
import ma.sdop.imagelist.common.recycler.DataBindAdapter;
import ma.sdop.imagelist.common.recycler.DataBinder;
import ma.sdop.imagelist.common.data.BaseData;
import ma.sdop.imagelist.common.data.InstagramParameterData;
import ma.sdop.imagelist.web.dto.DtoBase;
import ma.sdop.imagelist.web.dto.instagram.ItemDto;
import ma.sdop.imagelist.web.dto.instagram.ItemsDto;
import ma.sdop.imagelist.web.BaseTask;
import ma.sdop.imagelist.web.TaskHandler;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public class MaImageFragment extends BaseFragment {
    private EditText ma_image_input_id;
    private ImageView ma_image_search_button;
    private RecyclerView ma_image_list;
    private Button setting_button;

    private LinearLayoutManager linearLayoutManager;
    private MaImageAdapter maImageAdapter;
    private List<BaseData> listItems = new ArrayList<>();
    private boolean existItem;
    private @StringRes int apiType = R.string.api_instragram;

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
        ma_image_search_button = (ImageView) findViewById(R.id.ma_image_search_button);
        ma_image_list = (RecyclerView) findViewById(R.id.ma_image_list);
        setting_button = (Button) findViewById(R.id.setting_button);

        ma_image_search_button.setOnClickListener(onClickListener);
        setting_button.setOnClickListener(onClickListener);
        setting_button.setOnTouchListener(new MaMovableTouchListener(setting_button));

        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        ma_image_list.setLayoutManager(linearLayoutManager);
        maImageAdapter = new MaImageAdapter(listItems);
        ma_image_list.setAdapter(maImageAdapter);
        ma_image_list.addOnScrollListener(onScrollListener);

        switch (apiType) {
            case R.string.api_instragram:
                taskHandler = new TaskHandler.Builder(activity)
                        .setApiType(R.string.api_instragram)
                        .setOnCompleteListener(onCompletedListener)
                        .setParameter(new InstagramParameterData("design", ""))
                        .build();


        }
        if (taskHandler != null ) taskHandler.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed");
    }

    private TaskHandler taskHandler = null;

    private final BaseTask.OnCompletedListener onCompletedListener = new BaseTask.OnCompletedListener() {
        @Override
        public <T extends DtoBase> void onCompleted(boolean isSuccess, T result) {
            switch (apiType) {
                case R.string.api_instragram:
                    ItemsDto itemsDto = (ItemsDto) result;
                    for ( ItemDto itemDto : itemsDto.getItems() ) {
                        BaseData data = itemDto.getImageData();
                        if (data != null) listItems.add(data);
                    }
            }
            maImageAdapter.notifyDataSetChanged();
        }
    };

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ma_image_input_id:
                    break;
                case R.id.setting_button:
                    break;
            }
        }
    };

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        private boolean lastItemVisibleFlag = false;
        private int firstVisibleItem, visibleItemCount, totalItemCount;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if ( newState == RecyclerView.SCROLL_STATE_IDLE && lastItemVisibleFlag ) {
                if ( taskHandler != null ) taskHandler.next();
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
        private List<BaseData> imageItems;

        public MaImageAdapter(List<BaseData> imageItems) {
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
        public BaseData getItem(int position) {
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
            BaseData item = (BaseData) dataBindAdapter.getItem(position);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.ma_image_linearlayout.getLayoutParams();
            layoutParams.height = getImageHeight(item.getWidth(), item.getHeight());
            holder.ma_image_linearlayout.setLayoutParams(layoutParams);

            holder.ma_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.getReplaceBuilder(MaImageDetailFragment.class)
                            .setRequestCode(MaImageFragment.this, MaConstants.CODE.REQUEST_IMAGE_DETAIL)
                            .addParameter(MaConstants.CURRENT_INDEX, position)
                            .addParameter(MaConstants.TASK_HANDLER, taskHandler)
                            .replace(true);

                }
            });

            Log.d(TAG, "position : " + position);

            Picasso.with(activity)
                    .load(item.getImageUrl())
                    .fit()
                    .centerInside()
                    .into(holder.ma_image);
        }

        private int getImageHeight(int imageWidth, int imageHeight) {
            int expectedWidth = MaUtils.getWindowWidth(activity);
            int expectedHeight = expectedWidth * imageHeight / imageWidth;
            Log.d(TAG, String.format("image width : %d, image height : %d, expectedWidth : %d, expectedHeight : %d", imageWidth, imageHeight, expectedWidth, expectedHeight));
            return expectedHeight;
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
