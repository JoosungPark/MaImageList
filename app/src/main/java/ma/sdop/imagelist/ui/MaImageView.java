package ma.sdop.imagelist.ui;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ma.sdop.imagelist.R;
import ma.sdop.imagelist.common.MaUtils;
import ma.sdop.imagelist.common.data.BaseData;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public class MaImageView extends FrameLayout {
    private Context context;
    private BaseData item;
    private ImageView ma_image;

    private PhotoViewAttacher photoViewAttacher;

    public MaImageView(Context context, BaseData item) {
        super(context);
        this.context = context;
        this.item = item;
        inflate(context, R.layout.ma_image_view, this);
        initialize();

    }

    private void initialize() {
        ma_image = (ImageView) findViewById(R.id.ma_image);

        Picasso.with(context)
                .load(item.getImageUrl())
                .fit()
                .centerInside()
                .into(ma_image);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        photoViewAttacher = new PhotoViewAttacher(ma_image);
        photoViewAttacher.setMinimumScale((float)1);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        photoViewAttacher.cleanup();
    }
}
