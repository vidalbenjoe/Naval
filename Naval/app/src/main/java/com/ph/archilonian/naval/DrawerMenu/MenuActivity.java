package com.ph.archilonian.naval.DrawerMenu;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ph.archilonian.naval.Assessments.QuizSemesterFragment;
import com.ph.archilonian.naval.Fragments.AboutTheAppFragment;
import com.ph.archilonian.naval.Fragments.Lessons.SemesterTabFragment;
import com.ph.archilonian.naval.Fragments._3DObject.PreviewPartsFragment;
import com.ph.archilonian.naval.Fragments._3DObject.SimulationDifficulties;
import com.ph.archilonian.naval.Fragments._3DObject.SimulationFragment;
import com.ph.archilonian.naval.R;
import com.ph.archilonian.naval.Utilities.Constants;
import com.ph.archilonian.naval.Utilities.CustomUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MenuActivity extends FragmentActivity implements View.OnClickListener {
    public static BottomBar mBottomBar;
    private ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemParts;
    private ResideMenuItem itemSimulate;
    private ResideMenuItem itemAbout;
    TextView headerNavTitle;
    public static ArrayList<HashMap<String, Integer>> arrayListRandom, arrayListFixed, arrayPartsPreview;
    HashMap<String, Integer> hm;
    Fragment currentFragment;

    int[] images_3DObjects = {
            R.drawable.gasturbine,
            R.drawable.dieselengine,
            R.drawable.steamengine,
            R.drawable.bulbousbow,
            R.drawable.rudder,
            R.drawable.imgpart1,
            R.drawable.imgpart2,
            R.drawable.imgpart3,
            R.drawable.imgpart4,
            R.drawable.imgpart5,
            R.drawable.imgpart6,
            R.drawable.imgpart7,
            R.drawable.imgpart8,
            R.drawable.imgpart9,
            R.drawable.imgpart10
    };

    int[] images_3DObjectsPartsPreview = {
            R.drawable.gasturbine,
            R.drawable.dieselengine,
            R.drawable.steamengine,
            R.drawable.bulbousbow,
            R.drawable.rudder,
            R.drawable.imgpart1,
            R.drawable.imgpart2,
            R.drawable.imgpart3,
            R.drawable.imgpart4,
            R.drawable.imgpart5,
            R.drawable.imgpart6,
            R.drawable.imgpart7,
            R.drawable.imgpart8,
            R.drawable.imgpart9,
            R.drawable.imgpart10,
            R.drawable.hull
    };


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        mContext = this;
        setUpMenu();
        initSimulationArrays();

        headerNavTitle = (TextView) findViewById(R.id.headerNavTitle);

        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/monarchy.ttf");
        headerNavTitle.setTypeface(face);

        Constants.setHeaderText("Naval Archilonian");
        headerNavTitle.setText(Constants.getHeaderText());

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
//                mMessageView.setText(getMessage(menuItemId, false));
                switchFragment(menuItemId, false);
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
//                Toast.makeText(getApplicationContext(), getMessage(menuItemId, true), Toast.LENGTH_SHORT).show();
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorPrimary));
        mBottomBar.mapColorForTab(1, "#319d65");
        mBottomBar.mapColorForTab(2, "#7B1FA2");
//        mBottomBar.mapColorForTab(3, "#FF5252");


        if (savedInstanceState == null)
//            changeFragment(new SemesterTabFragment());
            CustomUtils.loadFragment(new SemesterTabFragment(), true, this);
    }

    private void initSimulationArrays() {
        arrayListFixed = new ArrayList<HashMap<String, Integer>>();
        for (int i = 0; i < images_3DObjects.length; i++) {
            hm = new HashMap<String, Integer>();
            hm.put("image", images_3DObjects[i]);
            hm.put("position", i);
            arrayListFixed.add(hm);
        }

        arrayListRandom = new ArrayList<HashMap<String, Integer>>();
        for (int i = 0; i < images_3DObjects.length; i++) {
            hm = new HashMap<String, Integer>();
            hm.put("image", images_3DObjects[i]);
            hm.put("position", i);
            arrayListRandom.add(hm);
        }
        Collections.shuffle(arrayListRandom);
        arrayPartsPreview = new ArrayList<HashMap<String, Integer>>();
        for (int i = 0; i < images_3DObjectsPartsPreview.length; i++) {
            hm = new HashMap<String, Integer>();
            hm.put("image", images_3DObjectsPartsPreview[i]);
            hm.put("position", i);
            arrayPartsPreview.add(hm);
        }

    }


    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
//        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.7f);

        // create menu items
        itemHome = new ResideMenuItem(this, R.drawable.home, "Home");
        itemParts = new ResideMenuItem(this, R.drawable.cogs, "Parts");
        itemSimulate = new ResideMenuItem(this, R.drawable.ship, "Simulate");
//        itemCalendar = new ResideMenuItem(this, R.drawable.usericon, "Settings");
        itemAbout = new ResideMenuItem(this, R.drawable.info, "About");

        itemHome.setOnClickListener(this);
        itemParts.setOnClickListener(this);
        itemSimulate.setOnClickListener(this);
        itemAbout.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemParts, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSimulate, ResideMenu.DIRECTION_LEFT);
//        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemAbout, ResideMenu.DIRECTION_RIGHT);

        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome) {
//            changeFragment(new SemesterTabFragment());
            CustomUtils.loadFragment(new SemesterTabFragment(), true, this);
            mBottomBar.show();
            mBottomBar.setDefaultTabPosition(0);
        } else if (view == itemParts) {
            CustomUtils.loadFragment(new PreviewPartsFragment(), true, this);
            Constants.shipPartsIndex = 0;
            mBottomBar.hide();
        } else if (view == itemSimulate) {
            mBottomBar.hide();
            CustomUtils.loadFragment(new SimulationDifficulties(), true, MenuActivity.this);
            Constants.shipPartsIndex = 0;
            mBottomBar.hide();
        } else if (view == itemAbout) {
            CustomUtils.loadFragment(new AboutTheAppFragment(), false, MenuActivity.this);
            mBottomBar.hide();
        }
        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
//            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
//            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment) {
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu() {
        return resideMenu;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        currentFragment = fm.findFragmentById(R.id.main_fragment);
        String strCurrentFragment = currentFragment.getClass().getSimpleName();


        if (strCurrentFragment.equals("SimulationFragment") || strCurrentFragment.equals("PreviewPartsFragment")) {

            for (int i = 2; i < fm.getBackStackEntryCount(); i++) {
                fm.popBackStack();
            }
            if (strCurrentFragment.equals("SimulationFragment"))
                CustomUtils.loadFragment(new SemesterTabFragment(), true, this);
            else {
                CustomUtils.loadFragment(new SimulationFragment(), true, this);
            }
        } else {
//            Toast.makeText(MenuActivity.this, "AssessmentBack" + fm.getBackStackEntryCount(), Toast.LENGTH_SHORT).show();
            if (fm.getBackStackEntryCount() > 1) {
                fm.popBackStack();
            } else if (fm.getBackStackEntryCount() == 1) {
                new AlertDialog.Builder(this)
                        .setTitle("Logout?")
                        .setMessage("Are you sure you want to logout?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                MenuActivity.this.finish();
                            }
                        }).create().show();
            }
        }
    }

    private void switchFragment(int menuItemId, boolean isReselection) {
        String message = "Content for ";
        switch (menuItemId) {
            case R.id.bb_menu_lessons:
                message += "Lessons";
                CustomUtils.loadFragment(new SemesterTabFragment(), true, this);
                break;
            case R.id.bb_menu_assessment:
                message += "Assessment";
                CustomUtils.loadFragment(new QuizSemesterFragment(), true, this);
                break;

            case R.id.bb_menu_parts:
                message += "Parts";
                CustomUtils.loadFragment(new PreviewPartsFragment(), true, this);
                Constants.shipPartsIndex = 0;
                mBottomBar.hide();
                break;
        }

        if (isReselection) {
            message += "Already Selected!";
            Toast.makeText(MenuActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}