package ca.light.points.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NavigationManager {

    private FragmentManager mFragmentManager;
    private int mFragmentPlaceholder;

    public NavigationManager(FragmentManager fragmentManager, int fragmentPlaceHolder) {
        mFragmentManager = fragmentManager;
        mFragmentPlaceholder = fragmentPlaceHolder;
    }

    public void navigateTo(FragmentBuilder fragmentBuilder) {
        String tag = fragmentBuilder.getTag();
        Fragment fragment = fragmentBuilder.build();

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(mFragmentPlaceholder, fragment, tag);
        ft.addToBackStack(tag);
        ft.commit();
    }

    public int getBackStackCount() {
        return mFragmentManager.getBackStackEntryCount();
    }
}
