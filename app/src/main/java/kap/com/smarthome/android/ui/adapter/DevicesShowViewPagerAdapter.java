package kap.com.smarthome.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

import kap.com.smarthome.android.data.bean.Room;


/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class DevicesShowViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Room>  mRooms;
    private List<Fragment>  mFragments;

    public DevicesShowViewPagerAdapter(FragmentManager fm , List<Room> rooms , List<Fragment> fragments) {
        super(fm);
        mRooms = rooms;
        mFragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRooms.get(position).getNAME();
    }

    @Override
    public Fragment getItem(int position) {
        return  mFragments.get(position);
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }
}
