package ma.sdop.imagelist.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.BaseFragment;
import ma.sdop.imagelist.common.MaConstants;
import ma.sdop.imagelist.common.data.BaseData;
import ma.sdop.imagelist.web.BaseTask;
import ma.sdop.imagelist.web.TaskHandler;
import ma.sdop.imagelist.web.dto.DtoBase;
import ma.sdop.imagelist.web.dto.instagram.ItemDto;
import ma.sdop.imagelist.web.dto.instagram.ItemsDto;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public class MaImageDetailFragment extends BaseFragment {
    private ViewPager ma_image_viewpager;
    private MaImagePagerAdapter pagerAdapter;

    private int currentIndex = 0;
    private TaskHandler taskHandler;
    private @StringRes int apiType = R.string.api_instragram;
    private List<MaImageView> subViews;

    private boolean reloading = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ma_detail_image, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.d(TAG, "onBackPressed");
    }

    private void initialize() {
        currentIndex = (Integer) parameters.get(MaConstants.CURRENT_INDEX);
        taskHandler = (TaskHandler) parameters.get(MaConstants.TASK_HANDLER);
        taskHandler.setOnCompletedListener(onCompletedListener);
        apiType = taskHandler.getApiType();

        subViews = new ArrayList<>();

        switch (apiType) {
            case R.string.api_instragram:
                List<DtoBase> itemsDtoList = taskHandler.getResults();
                for (DtoBase itemsDto : itemsDtoList ) addSubviews(itemsDto);
        }

        ma_image_viewpager = (ViewPager) findViewById(R.id.ma_image_viewpager);

        pagerAdapter = new MaImagePagerAdapter(activity, subViews);
        ma_image_viewpager.setAdapter(pagerAdapter);
        ma_image_viewpager.addOnPageChangeListener(onPageChangeListener);
        ma_image_viewpager.setCurrentItem(currentIndex);
    }

    private void addSubviews(DtoBase dtoBase) {
        if ( dtoBase instanceof  ItemsDto ) {
            ItemsDto itemsDto = (ItemsDto) dtoBase;
            for ( ItemDto itemDto : itemsDto.getItems() ) {
                BaseData data = itemDto.getImageData();
                if (data != null)  subViews.add(new MaImageView(activity, data));
            }
        }

        if ( pagerAdapter != null && pagerAdapter.getCount() > currentIndex + 2 ) {
            Log.e(TAG, "Complete reloading data.");
            pagerAdapter.notifyDataSetChanged();
            if ( reloading ) ma_image_viewpager.setCurrentItem(currentIndex+1);
        }
    }

    private final BaseTask.OnCompletedListener onCompletedListener = new BaseTask.OnCompletedListener() {
        @Override
        public <T extends DtoBase> void onCompleted(boolean isSuccess, T result) {
            switch (apiType) {
                case R.string.api_instragram:
                    addSubviews(result);
            }
        }
    };

    private final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        private boolean isDraggingAtLast = false;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageSelected(int position) {
            currentIndex = position;
            Log.d(TAG, "currentIndex : " + currentIndex);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            int lastIndex = pagerAdapter.getCount() - 1;
            if ( currentIndex == lastIndex && state == ViewPager.SCROLL_STATE_DRAGGING ) {
                isDraggingAtLast = true;
            } else if ( isDraggingAtLast && state == ViewPager.SCROLL_STATE_IDLE ) {
                Log.e(TAG, "reload data");
                reloading = true;
                taskHandler.next();
            } else {
                isDraggingAtLast = false;
                reloading = false;
            }
        }
    };

    private final class MaImagePagerAdapter extends PagerAdapter {
        private final Context context;
        private final List<MaImageView> subViews;

        public MaImagePagerAdapter(Context context, List<MaImageView> subViews) {
            super();
            this.context = context;
            this.subViews = subViews;
        }

        @Override
        public int getCount() {
            return subViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = subViews.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(subViews.get(position));
        }

        @Override
        public int getItemPosition(Object object) {
            if ( subViews.contains(object) ) return subViews.indexOf(object);
            else return POSITION_NONE;
        }

        public MaImageView getView(int position) {
            if ( position >0 && position < subViews.size() ) return subViews.get(position);
            else return null;
        }
    }
}
