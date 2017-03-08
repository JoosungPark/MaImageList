package ma.sdop.imagelist.common;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import ma.sdop.imagelist.R;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

public class BaseActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    private String fragmentTag;
    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        fragmentManager = super.getFragmentManager();
    }

    public int getHomeFragmentParentsId() {
        return R.id.ma_main_fragments_parents;
    }

    public <T extends BaseFragment>T getFragment(Class<T> fragmentClass){
        T frag = null;
        try {
            frag = (T)fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) frag;
    }

    public ReplaceBuilder getReplaceBuilder(Class<? extends BaseFragment> fragmentClass) {
        return ReplaceBuilder.getBuilder(this, getHomeFragmentParentsId(), fragmentClass)
                .setTag(BaseActivity.class.getSimpleName())
                .setStackName(fragmentClass.getSimpleName());
    }

    @Override
    public void onBackPressed() {
        if ( fragmentManager == null ) {
            super.onBackPressed();
        } else if ( fragmentManager.getBackStackEntryCount() > 0 ) {
            BaseFragment fragment = getCurrentFragment();
            fragment.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    private BaseFragment getCurrentFragment() {
        return (BaseFragment) fragmentManager.findFragmentByTag(fragmentTag);
    }

}
