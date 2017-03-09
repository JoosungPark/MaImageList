package ma.sdop.imagelist.ui;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.data.ImageData;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public class MaImageView extends FrameLayout {
    private Context context;
    private ImageData item;
    private ImageView ma_image;
    private PhotoViewAttacher photoViewAttacher;
    private View button_close;
    private OnClickListener onClickListener;
    private Rect rect;

    public MaImageView(Context context, ImageData item, OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.item = item;
        inflate(context, R.layout.ma_image_view, this);
        initialize();
        button_close = findViewById(R.id.button_close);
        this.onClickListener = onClickListener;
    }

    private void initialize() {
        ma_image = (ImageView) findViewById(R.id.ma_image);

        Picasso.with(context)
                .load(item.getImageUrl())
                .fit()
                .centerInside()
                .into(ma_image);
    }

    private PhotoViewAttacher.OnViewTapListener onViewTapListener = new PhotoViewAttacher.OnViewTapListener() {
        @Override
        public void onViewTap(View view, float x, float y) {
            rect = new Rect();
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
