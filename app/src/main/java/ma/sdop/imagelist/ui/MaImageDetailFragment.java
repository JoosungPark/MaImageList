package ma.sdop.imagelist.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.ApiType;
import ma.sdop.imagelist.common.BaseActivity;
import ma.sdop.imagelist.common.BaseFragment;
import ma.sdop.imagelist.common.MaConstants;
import ma.sdop.imagelist.common.data.BaseData;
import ma.sdop.imagelist.common.data.InstagramParameterData;
import ma.sdop.imagelist.common.data.ParameterBaseData;
import ma.sdop.imagelist.web.BaseTask;
import ma.sdop.imagelist.web.TaskHandler;
import ma.sdop.imagelist.web.dto.DtoBase;
import ma.sdop.imagelist.web.dto.instagram.ItemDto;
import ma.sdop.imagelist.web.dto.instagram.ItemsDto;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public class MaImageDetailFragment extends BaseFragment {
    private static final int PAGE_COUNT = 10;

    private ViewPager ma_image_viewpager;
    private MaImagePagerAdapter pagerAdapter;

    private List<BaseData> listItems = new ArrayList<>();
    private int currentIndex = 0;
    private TaskHandler taskHandler;
    private ApiType apiType = ApiType.Instagram;

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

    private void initialize() {
        currentIndex = (Integer) parameters.get(MaConstants.CURRENT_INDEX);
        taskHandler = (TaskHandler) parameters.get(MaConstants.TASK_HANDLER);
        taskHandler.setOnCompletedListener(onCompletedListener);
        apiType = taskHandler.getApiType();

        switch (apiType) {
            case Instagram:
                List<DtoBase> itemsDtoList = taskHandler.getResults();
                for (DtoBase itemsDto : itemsDtoList ) addBaseData(itemsDto);

        }

        ma_image_viewpager = (ViewPager) findViewById(R.id.ma_image_viewpager);

        List<MaImageView> list = new ArrayList<>();
        for (int i=0; i<listItems.size(); i++) {
            list.add(new MaImageView(activity, listItems.get(i)));
        }

        pagerAdapter = new MaImagePagerAdapter(activity, list);
        ma_image_viewpager.setAdapter(pagerAdapter);
        ma_image_viewpager.setCurrentItem(currentIndex);
    }

    private void addBaseData(DtoBase dtoBase) {
        if ( dtoBase instanceof  ItemsDto ) {
            ItemsDto itemsDto = (ItemsDto) dtoBase;
            for ( ItemDto itemDto : itemsDto.getItems() ) {
                BaseData data = itemDto.getImageData();
                if (data != null) listItems.add(data);
            }
        }
    }

    private final BaseTask.OnCompletedListener onCompletedListener = new BaseTask.OnCompletedListener() {
        @Override
        public <T extends DtoBase> void onCompleted(boolean isSuccess, T result) {
            switch (apiType) {
                case Instagram:
                    addBaseData(result);
            }
        }
    };

    private final class MaImagePagerAdapter extends PagerAdapter {
        private final Context context;
        private final List<MaImageView> items;

        public MaImagePagerAdapter(Context context, List<MaImageView> items) {
            super();
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = items.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            if ( items.contains(object) ) return items.indexOf(object);
            else return POSITION_NONE;
        }
    }
}
