package ma.sdop.imagelist.common;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public class ReplaceBuilder {
    private BaseActivity activity;
    private BaseFragment fragment;
    private String stackName;
    private boolean isAllowingStateLoss = false;
    private int parentsId;
    private String tag;

    private ReplaceBuilder(BaseActivity activity, int parentsId, Class<? extends BaseFragment> fragmentClass) {
        this.activity = activity;
        this.parentsId = parentsId;
        fragment = activity.getFragment(fragmentClass);
        try {
            if ( fragment == null ) fragment = fragmentClass.newInstance();
        } catch ( IllegalAccessException | InstantiationException e ) {
            e.printStackTrace();
        }
    }

    public void replace(boolean isHistory) {
        final FragmentTransaction transaction = activity.getFragmentManager().beginTransaction().replace(parentsId, fragment, tag);
        if ( isHistory ) {
            if ( isAllowingStateLoss ) transaction.addToBackStack(stackName).commitAllowingStateLoss();
            else transaction.addToBackStack(stackName).commit();
        } else {
            if ( isAllowingStateLoss || !isScreenOn() ) transaction.commitAllowingStateLoss();
            else {
                try {
                    transaction.commit();
                } catch ( IllegalStateException e ) {
                    e.printStackTrace();
                    transaction.commitAllowingStateLoss();
                }
            }
        }
    }

    private boolean isScreenOn() {
        PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        boolean result = false;
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH ) result = powerManager.isInteractive();
        else if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH ) result = powerManager.isScreenOn();
        return result;
    }

    public ReplaceBuilder setTag(String tag){
        this.tag = tag;
        return this;
    }

    public ReplaceBuilder setStackName(String stackName){
        this.stackName = stackName;
        return this;
    }

    public ReplaceBuilder addParameter(String key, Object value) {
        fragment.getParameters().add(key, value);
        return this;
    }

    public ReplaceBuilder addBundle(Bundle bundle) {
        fragment.setArguments(bundle);
        return this;
    }

    /**
     * set allowing state loss.
     * if isAllowingStateLoss set ture, fragment is possible that exchange itself under other activity.
     *
     * @param isAllowingStateLoss
     * @return
     */
    public ReplaceBuilder setAllowingStateLoss(boolean isAllowingStateLoss) {
        this.isAllowingStateLoss = isAllowingStateLoss;
        return this;
    }

    public static ReplaceBuilder getBuilder(BaseActivity activity, int parentsId, Class<? extends BaseFragment> fragmentClass){
        return new ReplaceBuilder(activity, parentsId, fragmentClass);
    }
}
