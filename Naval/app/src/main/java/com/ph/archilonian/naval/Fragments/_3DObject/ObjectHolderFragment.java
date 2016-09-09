package com.ph.archilonian.naval.Fragments._3DObject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ph.archilonian.naval.DrawerMenu.MenuActivity;
import com.ph.archilonian.naval.R;
import com.ph.archilonian.naval.Utilities.Constants;
import com.ph.archilonian.naval.Utilities.CustomUtils;
import com.special.ResideMenu.ResideMenu;

import org.rajawali3d.renderer.ISurfaceRenderer;
import org.rajawali3d.renderer.Renderer;
import org.rajawali3d.view.IDisplay;
import org.rajawali3d.view.ISurface;

import java.util.ArrayList;
import java.util.HashMap;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.ph.archilonian.naval.DrawerMenu.MenuActivity.mBottomBar;

public abstract class ObjectHolderFragment extends Fragment implements IDisplay, OnClickListener {

    protected ProgressBar mProgressBarLoader;
    protected String mExampleTitle;
    protected RelativeLayout mLayout;
    protected ISurface mRajawaliSurface;
    protected ISurfaceRenderer mRenderer;

    Fragment currentFragment;
    HorizontalScrollView horizontalScrollView;
    LinearLayout linear3DObjects;
    ImageView imageView;
    ResideMenu resideMenu;
    ArrayList<HashMap<String, Integer>> arrayList;
    TextView txtReset, txtInformation, txtRotate, txtDragObject, txtRandomizeList, txtNormalList;
    LinearLayout linearResetButton, linearInformation, linearRotateView, linearDragObject, linearRandomizeList, linearNormalList;
    AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        mBottomBar.hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the view
        mLayout = (RelativeLayout) inflater.inflate(getLayoutID(), container, false);
        mLayout.setGravity(Gravity.RIGHT);

        mLayout.findViewById(R.id.relative_layout_loader_container).bringToFront();

        // Find the TextureView
        mRajawaliSurface = (ISurface) mLayout.findViewById(R.id.rajwali_surface);

        // Create the loader
        mProgressBarLoader = (ProgressBar) mLayout.findViewById(R.id.progress_bar_loader);
        mProgressBarLoader.setVisibility(View.GONE);

        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
        horizontalScrollView = (HorizontalScrollView) mLayout.findViewById(R.id.horizontalscrollview);
//        materialLeanBack = (MaterialLeanBack) mLayout.findViewById(R.id.materialLeanBack);

        linear3DObjects = (LinearLayout) mLayout.findViewById(R.id.linear3DObjects);
        resideMenu.addIgnoredView(mLayout);

        // Setting up arraylist
        currentFragment = getFragmentManager().findFragmentById(R.id.main_fragment);
        if (currentFragment.getClass().getSimpleName().equals("PreviewPartsFragment")) {
            arrayList = MenuActivity.arrayPartsPreview;
        } else {
            if (Constants.isRandomized) {
                arrayList = MenuActivity.arrayListRandom;
            } else {
                arrayList = MenuActivity.arrayListFixed;
            }
        }
//        if (currentFragment.getClass().getSimpleName().equals("PreviewPartsFragment")) {
////            CustomUtils.loadFragment(new PreviewPartsFragment(), true, getActivity());
//        }else {
        for (int i = 0; i < arrayList.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(170, 170);
            params.setMargins(10, 10, 10, 10);
            imageView = new ImageView(getActivity());
            imageView.setBackgroundColor(Color.TRANSPARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(arrayList.get(i).get("image")).into(imageView);
            imageView.setTag(arrayList.get(i).get("position"));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.i("ObjectClassFragment", currentFragment.getClass().getSimpleName());
                    if (currentFragment.getClass().getSimpleName().equals("SimulationFragment")) {
                        CustomUtils.loadFragment(new SimulationFragment(), true, getActivity());
                    } else if (currentFragment.getClass().getSimpleName().equals("PreviewPartsFragment")) {
                        CustomUtils.loadFragment(new PreviewPartsFragment(), true, getActivity());
                    }
                    Constants.shipPartsIndex = (int) view.getTag();
                    Log.i("PartIndex", "" + Constants.shipPartsIndex);

                }
            });
            linear3DObjects.addView(imageView);
        }
        getActivity().setTitle(mExampleTitle);
//        }

        // Create the renderer
        mRenderer = createRenderer();
        onBeforeApplyRenderer();
        applyRenderer();


        return mLayout;
    }


    protected void onBeforeApplyRenderer() {

    }

    protected void applyRenderer() {
        mRajawaliSurface.setSurfaceRenderer(mRenderer);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mLayout != null)
            mLayout.removeView((View) mRajawaliSurface);
    }

    @Override
    public int getLayoutID() {
        return R.layout.rajawali_textureview_fragment;
    }

    protected void hideLoader() {
        mProgressBarLoader.post(new Runnable() {
            @Override
            public void run() {
                mProgressBarLoader.setVisibility(View.GONE);
            }
        });
    }

    protected void showLoader() {
        mProgressBarLoader.post(new Runnable() {
            @Override
            public void run() {
                mProgressBarLoader.setVisibility(View.VISIBLE);
            }
        });
    }

    protected abstract class AExampleRenderer extends Renderer {

        public AExampleRenderer(Context context) {
            super(context);
        }

        @Override
        public void onOffsetsChanged(float v, float v2, float v3, float v4, int i, int i2) {

        }

        @Override
        public void onTouchEvent(MotionEvent event) {

        }

        @Override
        public void onRenderSurfaceCreated(EGLConfig config, GL10 gl, int width, int height) {
            showLoader();
            super.onRenderSurfaceCreated(config, gl, width, height);
            hideLoader();
        }

        @Override
        protected void onRender(long ellapsedRealtime, double deltaTime) {
            super.onRender(ellapsedRealtime, deltaTime);
        }
    }
}
