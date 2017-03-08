package ma.sdop.imagelist.common;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by parkjoosung on 2017. 3. 8..
 */

public class MaMovableTouchListener implements View.OnTouchListener {
    private static final float defaultDelta = 15;

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

    private float getMaxValue(float value1, float value2) {
        if ( value1 > value2 ) return value1;
        else return value2;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float currentX, currentY;
        int action = event.getAction();
        switch (action ) {
            case MotionEvent.ACTION_DOWN: {
                preX = event.getX();
                preY = event.getY();
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
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
