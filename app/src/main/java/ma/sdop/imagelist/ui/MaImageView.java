package ma.sdop.imagelist.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.MaUtils;
import ma.sdop.imagelist.common.data.ImageData;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MaImageView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private ImageData item;
    private ImageView ma_image;
    private PhotoViewAttacher photoViewAttacher;
    private View button_close;
    private OnClickListener onClickListener;

    public MaImageView(Context context, ImageData item, OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.item = item;
        inflate(context, R.layout.ma_image_view, this);
        initialize();
        button_close = findViewById(R.id.button_close);
        button_close.setOnClickListener(onClickListener);
        this.onClickListener = onClickListener;
    }

    private void initialize() {
        ma_image = (ImageView) findViewById(R.id.ma_image);
        resizeImageView();

        Picasso.with(context)
                .load(item.getImageUrl())
                .fit()
                .centerInside()
                .into(ma_image);
    }

    private void resizeImageView() {
        ViewGroup.LayoutParams layoutParams = ma_image.getLayoutParams();
        layoutParams.height = MaUtils.getImageHeight(context, item.getWidth(), item.getHeight());
        ma_image.setLayoutParams(layoutParams);
    }

    // when close button overlap with photo image, it is ignored onClickEvent
    // because onTouch method is implemented at photo image view.
    // so, in this case this onViewTapListener would be fire onClickEvnet.
    private PhotoViewAttacher.OnViewTapListener onViewTapListener = new PhotoViewAttacher.OnViewTapListener() {
        @Override
        public void onViewTap(View view, float x, float y) {
            Log.d(TAG, "onViewTapListener called");
            Rect rect = new Rect();
            button_close.getDrawingRect(rect);
            offsetDescendantRectToMyCoords(button_close, rect);

            if ( x > rect.left && x < rect.right && y > rect.top && y < rect.bottom && onClickListener != null ) onClickListener.onClick(button_close);
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        photoViewAttacher = new PhotoViewAttacher(ma_image);
        photoViewAttacher.setMinimumScale((float)1);
        photoViewAttacher.setOnViewTapListener(onViewTapListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        photoViewAttacher.cleanup();
    }
}
