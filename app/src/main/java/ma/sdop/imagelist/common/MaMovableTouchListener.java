package ma.sdop.imagelist.common;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by parkjoosung on 2017. 3. 8..
 */


/**
 * i wanted to implement movable touch listener.
 * however some problems still remain, put off that work.
 *
 * problem : certainly distinguish touch event between movable and click event.
 *
 * @author parkjoosung
 */
public class MaMovableTouchListener implements View.OnTouchListener {
    private static final float defaultDelta = 40;

    private float preX;
    private float preY;
    private float delta = defaultDelta;

    public MaMovableTouchListener(View target) {
        boolean isRelative = target.getLayoutParams() instanceof RelativeLayout.LayoutParams;
        if ( !isRelative ) throw new IllegalArgumentException("MaMovableTouch can be applied only if RelativeLayout.");
    }

    public MaMovableTouchListener(View target, int delta) {
        this(target);
        this.delta = delta;
    }

    private final Handler handler = new Handler();
    private Runnable convertFlag = new Runnable() {
        @Override
        public void run() {
            flag = true;
        }
    };

    private boolean flag = false;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean result = true;
        float currentX, currentY;
        int action = event.getAction();

        switch (action ) {
            case MotionEvent.ACTION_DOWN: {
                preX = event.getX();
                preY = event.getY();
                flag = true;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                currentX = event.getX();
                currentY = event.getY();
                float xDelta = Math.max(preX, currentX) - Math.min(preX, currentY);
                float yDelta = Math.max(preY, currentX) - Math.min(preY, currentY);

                if ( xDelta < delta || yDelta < delta ) { return true; }

                currentX = event.getRawX();
                currentY = event.getRawY();

                ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                marginParams.setMargins((int)(currentX - preX), (int)(currentY - preY), 0, 0);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
                view.setLayoutParams(layoutParams);
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if ( flag ) handler.postDelayed(convertFlag, 500);
                break;
        }
        return !flag;
    }
}
