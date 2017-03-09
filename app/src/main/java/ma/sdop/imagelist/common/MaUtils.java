package ma.sdop.imagelist.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import ma.sdop.imagelist.R;

/**
 * it is a utility.
 *
 * @author parkjoosung
 *
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

    public static int getImageHeight(Context context, int imageWidth, int imageHeight) {
        int expectedWidth = MaUtils.getWindowWidth(context);
        int expectedHeight = expectedWidth * imageHeight / imageWidth;
//        Log.d(TAG, String.format("image width : %d, image height : %d, expectedWidth : %d, expectedHeight : %d", imageWidth, imageHeight, expectedWidth, expectedHeight));
        return expectedHeight;
    }

    public static void showToast(Context context, @StringRes int stringId) {
        Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show();
    }
}
