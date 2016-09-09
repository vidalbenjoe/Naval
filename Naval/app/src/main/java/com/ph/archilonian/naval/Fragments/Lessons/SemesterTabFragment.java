package com.ph.archilonian.naval.Fragments.Lessons;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.backendless.Backendless;
import com.ph.archilonian.naval.Database.DatabaseSource;
import com.ph.archilonian.naval.DrawerMenu.MenuActivity;
import com.ph.archilonian.naval.Fragments.Lessons.TopicList.FinalsTopicListFragment;
import com.ph.archilonian.naval.Fragments.Lessons.TopicList.MidtermTopicListFragment;
import com.ph.archilonian.naval.Fragments.Lessons.TopicList.PrelimTopicListFragment;
import com.ph.archilonian.naval.R;
import com.ph.archilonian.naval.Utilities.Constants;
import com.special.ResideMenu.ResideMenu;

import static com.ph.archilonian.naval.DrawerMenu.MenuActivity.mBottomBar;

public class SemesterTabFragment extends Fragment {
    private View parentView;
    ResideMenu resideMenu;
    LinearLayout ignoreView;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    DatabaseSource dbSource;
    @Override
    public void onStart() {
        super.onStart();
        mBottomBar.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.tab_layout, container, false);
        Backendless.initApp(getActivity(), Constants.APPLICATION_ID, Constants.SECRET_KEY, Constants.VERSION);
        dbSource = new DatabaseSource(getActivity());
        setUpViews();

        ignoreView = (LinearLayout) parentView.findViewById(R.id.ignoreView);
        tabLayout = (TabLayout) parentView.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) parentView.findViewById(R.id.viewpager);

        tabLayout.addTab(tabLayout.newTab().setText("Prelim"));
        tabLayout.addTab(tabLayout.newTab().setText("Midterm"));
        tabLayout.addTab(tabLayout.newTab().setText("Finals"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setAdapter(new PagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(0);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setUpViews() {
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("tabpos:", "" + position);
            switch (position) {
                case 0:
                    PrelimTopicListFragment prelimtopicFragment = new PrelimTopicListFragment();
                    return prelimtopicFragment;
                case 1:
                    MidtermTopicListFragment midtermtopicFragment = new MidtermTopicListFragment();
                    return midtermtopicFragment;
                case 2:
                    FinalsTopicListFragment finalstopicFragment = new FinalsTopicListFragment();
                    return finalstopicFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }

    }

}