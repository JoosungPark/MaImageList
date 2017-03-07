package ma.sdop.imagelist.ui;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ma.sdop.imagelist.R;
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

        photoViewAttacher = new PhotoViewAttacher(ma_image);
    }
}
