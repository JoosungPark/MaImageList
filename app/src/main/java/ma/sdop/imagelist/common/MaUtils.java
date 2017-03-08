package ma.sdop.imagelist.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import ma.sdop.imagelist.R;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public class MaUtils {
    public static int getWindowWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static Dialog getProgressDialog(Context context) {
        Dialog progressDialog = new Dialog(context, R.style.SimpleDialog);
        progressDialog.setCancelable(false);
        progressDialog.addContentView(new ProgressBar(context), new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return progressDialog;
    }
}
