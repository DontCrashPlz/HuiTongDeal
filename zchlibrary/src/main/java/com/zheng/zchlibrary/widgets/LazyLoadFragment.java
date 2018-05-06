package com.zheng.zchlibrary.widgets;

import android.support.v4.app.Fragment;

/**
 * Lazy Load Fragment: Auto judge the status of Fragment.Run onVisible() when the Fragment is visible.
 * Created by Zheng on 2017/10/26.
 */

public abstract class LazyLoadFragment extends Fragment {

    public boolean isVisible;

    /**
     * if current Fragment is visible to User, run the method onVisible();
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            isVisible= true;
            onVisible();
        }else{
            isVisible= false;
            onInVisible();
        }
    }

    /**
     * When the current Fragment is Visible to User, Load it.
     */
    protected void onVisible(){
        lazyLoad();
    }

    protected void onInVisible(){

    }

    public abstract void lazyLoad();

}
