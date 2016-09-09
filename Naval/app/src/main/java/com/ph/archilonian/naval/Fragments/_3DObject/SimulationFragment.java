package com.ph.archilonian.naval.Fragments._3DObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ph.archilonian.naval.Assessments.NavalAssessmentActivity;
import com.ph.archilonian.naval.Database.AssessDB.QUIZDBAdapter;
import com.ph.archilonian.naval.Database.AssessDB.QuizModel;
import com.ph.archilonian.naval.Database.AssessDB.SessionCache;
import com.ph.archilonian.naval.DrawerMenu.MenuActivity;
import com.ph.archilonian.naval.R;
import com.ph.archilonian.naval.Utilities.Constants;
import com.ph.archilonian.naval.Utilities.CustomUtils;
import com.ph.archilonian.naval.Utilities.InstanceHelperClass;
import com.special.ResideMenu.ResideMenu;

import org.rajawali3d.Object3D;
import org.rajawali3d.animation.Animation;
import org.rajawali3d.animation.Animation3D;
import org.rajawali3d.animation.TranslateAnimation3D;
import org.rajawali3d.bounds.IBoundingVolume;
import org.rajawali3d.cameras.ArcballCamera;
import org.rajawali3d.debug.DebugLight;
import org.rajawali3d.debug.DebugVisualizer;
import org.rajawali3d.debug.GridFloor;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderAWD;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.math.Matrix4;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Cube;
import org.rajawali3d.util.GLU;
import org.rajawali3d.util.ObjectColorPicker;
import org.rajawali3d.util.OnObjectPickedListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;


public class SimulationFragment extends ObjectHolderFragment implements
        OnTouchListener, View.OnClickListener {

    QUIZDBAdapter myDb;
    Intent intent;
    SessionCache QuizSession;
    int retake;
    int prevTotal;
    int curTotal;
    String initVal = "1";
    String finalDate, strCategory;
    Bundle bundle;


    static boolean isObjectMoving = false, isObjectSelected = false;
    Cube mCubeBox;
    Object3D objShip, objParts, objFinger;
    LoaderAWD parserParts, parserShip, parserFinger;
    Material material;
    ImageButton btnRotateDrag, btnRandomFixed, btnInfo, btnRefresh;
    Button btnTakeQuiz;
    ResideMenu resideMenu;
    DirectionalLight lightFront, lightBack;
    TextView txtDesc, txtTitle, txtDirs;
    Animation3D anim;

    String[] strArrayTitles = {
            "Gas Turbine",
            "Diesel Engine",
            "Steam Engine",
            "Bulbous Bow",
            "Rudder",
            "Stern of Ship",
            "Main Deck",
            "Funnel of Ship",
            "Forecastle",
            "Deck of Ship",
            "Ship Crane",
            "Anchor",
            "Shaft of Ship",
            "Propelers",
            "Bow Thruster"
    };

    String[] strArrayDirections = {
            "Drag the Gas Engine within the tail part of the hull.",
            "Drag the Diesel Engine at the left side of the Gas Engine.",
            "Drag the Steam Engine at the left side of the Diesel Engine.",
            "Drag the Bulbous bow at the front part of the hull.",
            "Drag the Rudder at the right bottom corner of the hull.",
            "Drag the Stern Object at the tail of the hull.",
            "Drag the Main Deck object beside the left side of the stern of ship.",
            "Drag the Funnel object at the top of the Main Deck of the ship.",
            "Drag the Forecastle object at front part of the hull.",
            "Drag the Deck of the Ship at the middle part of the hull.",
            "Drag the Ship Crane at the left side of the Main Deck of the ship.",
            "Drag the Anchor object at the front part of the hull and below of the Forecastle of the Ship.",
            "Drag the Shaft at the right bottom corner of the hull.",
            "Drag the Propeler object at the right side of the Shaft of the Ship.",
            "Drag the Bow Thruster at the left bottom corner of the hull."
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QuizSession = new SessionCache(getActivity());
        openDB();

        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, yyyy");
        finalDate = timeFormat.format(date);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ((View) mRajawaliSurface).setOnTouchListener(this);

        LinearLayout mainLinear = new LinearLayout(getActivity());
        mainLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 8.0f));
        mainLinear.setOrientation(LinearLayout.VERTICAL);
        mainLinear.setGravity(Gravity.RIGHT);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f);
        params1.setMargins(0, 50, 0, 0);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f);
        params2.setMargins(0, 600, 0, 0);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f);
        params3.setMargins(30, 700, 30, 0);

        LinearLayout secondLinear = new LinearLayout(getActivity());
        secondLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 8.0f));
        secondLinear.setOrientation(LinearLayout.HORIZONTAL);
        secondLinear.setWeightSum(1);
        secondLinear.setGravity(Gravity.RIGHT);
        mainLinear.addView(secondLinear);

        btnRefresh = new ImageButton(getActivity());
        btnRefresh.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnRefresh.setImageDrawable(getResources().getDrawable(R.drawable.ic_reset));
        btnRefresh.setOnClickListener((TouchAndDragRenderer) mRenderer);
        secondLinear.addView(btnRefresh);
//
        btnInfo = new ImageButton(getActivity());
        btnInfo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnInfo.setImageDrawable(getResources().getDrawable(R.drawable.ic_info));
        btnInfo.setOnClickListener((TouchAndDragRenderer) mRenderer);
        secondLinear.addView(btnInfo);

        txtTitle = new TextView(getActivity());
        txtTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        try {
            txtTitle.setText(strArrayTitles[Constants.shipPartsIndex]);
        } catch (Exception e) {
            e.printStackTrace();
            Constants.shipPartsIndex = 0;
        }
        txtTitle.setTextSize(20);
        txtTitle.setGravity(Gravity.CENTER);
        txtTitle.setTextColor(Color.WHITE);
        secondLinear.addView(txtTitle);

        btnRotateDrag = new ImageButton(getActivity());
        btnRotateDrag.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnRotateDrag.setImageDrawable(getResources().getDrawable(R.drawable.ic_rotate_view));
        btnRotateDrag.setOnClickListener((TouchAndDragRenderer) mRenderer);
        secondLinear.addView(btnRotateDrag);
//
        btnRandomFixed = new ImageButton(getActivity());
        btnRandomFixed.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        btnRandomFixed.setImageDrawable(getResources().getDrawable(R.drawable.ic_random));
        btnRandomFixed.setImageDrawable(getResources().getDrawable(R.drawable.ic_drag_object));
        btnRandomFixed.setOnClickListener((TouchAndDragRenderer) mRenderer);
        secondLinear.addView(btnRandomFixed);

        txtDesc = new TextView(getActivity());
        txtDesc.setLayoutParams(params1);
        if (Constants.shipObjIndex < 14) {
            txtDesc.setText("Step " + (Constants.shipObjIndex + 1) + ": " + strArrayDirections[Constants.shipObjIndex]);
        }
        txtDesc.setTextSize(13);
        txtDesc.setTypeface(null, Typeface.BOLD_ITALIC);
        txtDesc.setGravity(Gravity.CENTER);
        txtDesc.setTextColor(Color.WHITE);
        mainLinear.addView(txtDesc);

        txtDirs = new TextView(getActivity());
        txtDirs.setLayoutParams(params2);
        txtDirs.setTextSize(13);
        txtDirs.setTypeface(null, Typeface.BOLD_ITALIC);
        txtDirs.setGravity(Gravity.CENTER);
        mainLinear.addView(txtDirs);
        if (Constants.shipObjIndex != 15) {
            if (Constants.shipObjIndex != Constants.shipPartsIndex) {
                txtDirs.setText("Invalid Object! It must be " + strArrayTitles[Constants.shipObjIndex] + ".");
                txtDirs.setTextColor(Color.RED);
            } else {
                txtDirs.setText("Valid Object.");
                txtDirs.setTextColor(Color.GREEN);
            }
        } else {
            txtDirs.setText("Ship assembled successfully!");
            txtDirs.setTextColor(Color.GREEN);
            viewDialogTakeQuiz();
        }


//        if (Constants.isRandomized)
//            btnRandomFixed.setImageDrawable(getResources().getDrawable(R.drawable.ic_random));
//        else
//            btnRandomFixed.setImageDrawable(getResources().getDrawable(R.drawable.ic_notrandom));

        mLayout.addView(mainLinear);
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
        resideMenu.addIgnoredView(mLayout);

        if (Constants.isGuideEnabled) {
            viewInstructions();
        }
//        viewDialogTakeQuiz();
        return mLayout;
    }

    private void viewDialogTakeQuiz() {
        AlertDialog.Builder builder = InstanceHelperClass.getAlertDialogBuilderInstance(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("");
        View view = inflater.inflate(R.layout.layout_takequiz, null);
        builder.setView(view);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();


                QuizModel.setAssessmentCategory("SimulationQuiz");

                if (QuizSession.hasFlQuiz1()) {
                    final Dialog dialogquiz = new Dialog(getActivity(), R.style.DialogAnim);
                    dialogquiz.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogquiz.setContentView(R.layout.dialog_signout);
                    Button bYes = (Button) dialogquiz.findViewById(R.id.buttonOk);
                    Button bNo = (Button) dialogquiz.findViewById(R.id.buttonCancel);
                    TextView tvalertmessage = (TextView) dialogquiz.findViewById(R.id.tvalertmessage);
                    HashMap<String, String> quizRecord = QuizSession.getTotalSum();
                    retake = Integer.parseInt(quizRecord.get(SessionCache.REPEATING1));
                    prevTotal = Integer.parseInt(quizRecord.get(SessionCache.NAV_MAX_ITEM1));

//                        CustomUtils.setQuestionCategory(CustomUtils.getQuestionCategory());
                        // this condition will use if retake is
                        // value 1 to 2
                        myDb.deleteQuiz(QuizModel.getAssessmentSem());
                        QuizSession.StoreFlLastQuizTaken(finalDate);
                        QuizSession.StoreAllLastQuizTaken(finalDate);
                        int sum = retake + 1;
                        myDb.addAUXquiz(1, QuizModel.getAssessmentSem(), "", "0 %");
                        curTotal = prevTotal + 5;
                        QuizSession.StoreTotal1(Integer.toString(curTotal));
                        QuizSession.FinishSessionNum1(Integer.toString(sum));
                        bundle = new Bundle();
                        intent = new Intent(getActivity(), NavalAssessmentActivity.class);
//                        bundle.putString("questcat", QuizModel.getAssessmentSem());
                        Log.i("sumElse: ", "" + sum);
                        intent.putExtra("retakeNum", sum);
                        intent.putExtras(bundle);
                        startActivity(intent);


                } else {
                    QuizSession.StoreFlLastQuizTaken(finalDate);
                    QuizSession.StoreAllLastQuizTaken(finalDate);
                    int passVal = Integer.parseInt(initVal);
                    myDb.addAUXquiz(1, QuizModel.getAssessmentSem(), initVal, "0 %");
                    curTotal = prevTotal + 5;
                    QuizSession.StoreTotal1(Integer.toString(curTotal));
                    QuizSession.FinishSessionNum1(initVal);
                    bundle = new Bundle();
                    intent = new Intent(getActivity(), NavalAssessmentActivity.class);
                    bundle.putString("questcat", QuizModel.getAssessmentSem());
                    Log.i("passValSum: ", "" + passVal);
                    intent.putExtra("retakeNum", passVal);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });
        builder.setNeutralButton("Reset Simulation", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Constants.shipObjIndex = 0;
                alertDialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        alertDialog.show();


    }

    private void viewInstructions() {
        AlertDialog.Builder builder = InstanceHelperClass.getAlertDialogBuilderInstance(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("");
        View view = inflater.inflate(R.layout.layout_instructions, null);
        builder.setView(view);
        guideListClickListener(view);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        builder.setNegativeButton("Don't show this again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Constants.isGuideEnabled = false;
            }
        });

        alertDialog = builder.create();

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        alertDialog.show();


    }

    private void guideListClickListener(View view) {
        linearResetButton = (LinearLayout) view.findViewById(R.id.linearResetView);
        linearInformation = (LinearLayout) view.findViewById(R.id.linearInformation);
        linearRotateView = (LinearLayout) view.findViewById(R.id.linearRotateView);
        linearDragObject = (LinearLayout) view.findViewById(R.id.linearDragObject);
//        linearRandomizeList = (LinearLayout) view.findViewById(R.id.linearRandomizeList);
//        linearNormalList = (LinearLayout) view.findViewById(R.id.linearNormalList);

        linearResetButton.setOnClickListener(this);
        linearInformation.setOnClickListener(this);
        linearRotateView.setOnClickListener(this);
        linearDragObject.setOnClickListener(this);
//        linearRandomizeList.setOnClickListener(this);
//        linearNormalList.setOnClickListener(this);

        txtReset = (TextView) view.findViewById(R.id.txtReset);
        txtInformation = (TextView) view.findViewById(R.id.txtInforamtion);
        txtRotate = (TextView) view.findViewById(R.id.txtRotate);
        txtDragObject = (TextView) view.findViewById(R.id.txtDragObject);
//        txtRandomizeList = (TextView) view.findViewById(R.id.txtRandomize);
//        txtNormalList = (TextView) view.findViewById(R.id.txtNormalList);


    }

    protected void onBeforeApplyRenderer() {

    }

    protected void applyRenderer() {
        mRajawaliSurface.setSurfaceRenderer(mRenderer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearResetView:
                showGuidesDescriptions(0);
                break;

            case R.id.linearInformation:
                showGuidesDescriptions(1);
                break;


            case R.id.linearRotateView:
                showGuidesDescriptions(2);
                break;


            case R.id.linearDragObject:
                showGuidesDescriptions(3);
                break;

//
//            case R.id.linearRandomizeList:
//                showGuidesDescriptions(4);
//                break;
//
//
//            case R.id.linearNormalList:
//                showGuidesDescriptions(5);
//                break;
        }
    }

    private void showGuidesDescriptions(int i) {

        switch (i) {
            case 0:
                if (txtReset.getVisibility() == View.VISIBLE) {
                    txtReset.setVisibility(View.GONE);
                    txtInformation.setVisibility(View.GONE);
                    txtDragObject.setVisibility(View.GONE);
                    txtRotate.setVisibility(View.GONE);
//                    txtRandomizeList.setVisibility(View.GONE);
//                    txtNormalList.setVisibility(View.GONE);
                } else {
                    txtReset.setVisibility(View.VISIBLE);
                    txtInformation.setVisibility(View.GONE);
                    txtDragObject.setVisibility(View.GONE);
                    txtRotate.setVisibility(View.GONE);
//                    txtRandomizeList.setVisibility(View.GONE);
//                    txtNormalList.setVisibility(View.GONE);
                }
                break;

            case 1:
                if (txtInformation.getVisibility() == View.VISIBLE) {
                    txtReset.setVisibility(View.GONE);
                    txtInformation.setVisibility(View.GONE);
                    txtDragObject.setVisibility(View.GONE);
                    txtRotate.setVisibility(View.GONE);
//                    txtRandomizeList.setVisibility(View.GONE);
//                    txtNormalList.setVisibility(View.GONE);
                } else {
                    txtInformation.setVisibility(View.VISIBLE);
                    txtReset.setVisibility(View.GONE);
                    txtDragObject.setVisibility(View.GONE);
                    txtRotate.setVisibility(View.GONE);
//                    txtRandomizeList.setVisibility(View.GONE);
//                    txtNormalList.setVisibility(View.GONE);
                }
                break;

            case 2:
                if (txtRotate.getVisibility() == View.VISIBLE) {
                    txtReset.setVisibility(View.GONE);
                    txtInformation.setVisibility(View.GONE);
                    txtDragObject.setVisibility(View.GONE);
                    txtRotate.setVisibility(View.GONE);
//                    txtRandomizeList.setVisibility(View.GONE);
//                    txtNormalList.setVisibility(View.GONE);
                } else {
                    txtRotate.setVisibility(View.VISIBLE);
                    txtReset.setVisibility(View.GONE);
                    txtInformation.setVisibility(View.GONE);
                    txtDragObject.setVisibility(View.GONE);
//                    txtRandomizeList.setVisibility(View.GONE);
//                    txtNormalList.setVisibility(View.GONE);
                }
                break;

            case 3:
                if (txtDragObject.getVisibility() == View.VISIBLE) {
                    txtReset.setVisibility(View.GONE);
                    txtInformation.setVisibility(View.GONE);
                    txtDragObject.setVisibility(View.GONE);
                    txtRotate.setVisibility(View.GONE);
//                    txtRandomizeList.setVisibility(View.GONE);
//                    txtNormalList.setVisibility(View.GONE);

                } else {
                    txtDragObject.setVisibility(View.VISIBLE);
                    txtReset.setVisibility(View.GONE);
                    txtInformation.setVisibility(View.GONE);
                    txtRotate.setVisibility(View.GONE);
//                    txtRandomizeList.setVisibility(View.GONE);
//                    txtNormalList.setVisibility(View.GONE);
                }
                break;

//            case 4:
//                if (txtRandomizeList.getVisibility() == View.VISIBLE) {
//                    txtReset.setVisibility(View.GONE);
//                    txtInformation.setVisibility(View.GONE);
//                    txtDragObject.setVisibility(View.GONE);
//                    txtRotate.setVisibility(View.GONE);
//                    txtRandomizeList.setVisibility(View.GONE);
//                    txtNormalList.setVisibility(View.GONE);
//                } else {
//                    txtRandomizeList.setVisibility(View.VISIBLE);
//                    txtReset.setVisibility(View.GONE);
//                    txtInformation.setVisibility(View.GONE);
//                    txtDragObject.setVisibility(View.GONE);
//                    txtRotate.setVisibility(View.GONE);
//                    txtNormalList.setVisibility(View.GONE);
//                }
//                break;
//
//            case 5:
//                if (txtNormalList.getVisibility() == View.VISIBLE) {
//                    txtReset.setVisibility(View.GONE);
//                    txtInformation.setVisibility(View.GONE);
//                    txtDragObject.setVisibility(View.GONE);
//                    txtRotate.setVisibility(View.GONE);
//                    txtRandomizeList.setVisibility(View.GONE);
//                    txtNormalList.setVisibility(View.GONE);
//                } else {
//                    txtNormalList.setVisibility(View.VISIBLE);
//                    txtReset.setVisibility(View.GONE);
//                    txtInformation.setVisibility(View.GONE);
//                    txtDragObject.setVisibility(View.GONE);
//                    txtRotate.setVisibility(View.GONE);
//                    txtRandomizeList.setVisibility(View.GONE);
//                }
//                break;
        }

    }


    @Override
    public AExampleRenderer createRenderer() {
        return new TouchAndDragRenderer(getActivity());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.i("isObjectTouchEvent", event.getPointerCount() + "");
        Log.i("isObjectMoving", isObjectMoving + " onTouch");

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ((TouchAndDragRenderer) mRenderer).getObjectAt(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                ((TouchAndDragRenderer) mRenderer).moveSelectedObject(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                ((TouchAndDragRenderer) mRenderer).stopMovingSelectedObject();
                break;
        }

        return isObjectMoving;

    }


    private final class TouchAndDragRenderer extends AExampleRenderer implements OnObjectPickedListener, View.OnClickListener {
        private ObjectColorPicker mPicker;
        private Object3D mSelectedObject;
        private int[] mViewport;
        private double[] mNearPos4;
        private double[] mFarPos4;
        private Vector3 mNearPos;
        private Vector3 mFarPos;
        private Vector3 mNewObjPos;
        private Matrix4 mViewMatrix;
        private Matrix4 mProjectionMatrix;
        ArcballCamera arcball;
        float x, y;
        Context context;

        int[] partsObjects = {
                R.raw.gasturbine3,
                R.raw.dieselengine2,
                R.raw.steamengine1,
                R.raw.shippart1,
                R.raw.shippart2,
                R.raw.shippart3,
                R.raw.shippart4,
                R.raw.shippart5,
                R.raw.shippart6,
                R.raw.shippart7,
                R.raw.shippart8,
                R.raw.shippart9,
                R.raw.shippart10,
                R.raw.shippart11,
                R.raw.shippart12
        };

        int[] shipBodyObjects = {
                R.raw.emptyhull,
                R.raw.enginehull1,
                R.raw.enginehull2,
                R.raw.enginehull3,
                R.raw.shipbody2,
                R.raw.shipbody3,
                R.raw.shipbody4,
                R.raw.shipbody5,
                R.raw.shipbody6,
                R.raw.shipbody7,
                R.raw.shipbody8,
                R.raw.shipbody9,
                R.raw.shipbody10,
                R.raw.shipbody11,
                R.raw.shipbody12,
                R.raw.ship};


        double[] cubeX = {
                1.4673814444358613, 0.5986067375836505,
                -0.13243516477897083,
                -2.9188702935168944, 2.9824390560510707,
                2.8870860741383195, 2.1030697173316777,
                2.1458095799621892, -2.7493533505929872,
                -0.5986067375836505, 1.3084593762119063,
                -3.057462808468623, 2.6889118266751235,
                3.146335686930214, -2.8990791914194745};

        double[] cubeY = {
                0.2541941058800341, 0.41321265965793985,
                0.3284025052881028,
                0.2568089291475416, 0.2541941058800341,
                0.837262136399024, 0.9220722907688622,
                1.0916919519544848, 0.7312497672137527,
                0.6994457974365501, 0.9220722907688622,
                0.592852203328944, 0.27122391461586653,
                0.25885375566066043, 0.24648359670545544};

        double[] cubeZ = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};


        public TouchAndDragRenderer(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        protected void initScene() {
            mViewport = new int[]{0, 0, getViewportWidth(), getViewportHeight()};
            mNearPos4 = new double[4];
            mFarPos4 = new double[4];
            mNearPos = new Vector3();
            mFarPos = new Vector3();
            mNewObjPos = new Vector3();
            mViewMatrix = getCurrentCamera().getViewMatrix();
            mProjectionMatrix = getCurrentCamera().getProjectionMatrix();

            mPicker = new ObjectColorPicker(this);
            mPicker.setOnObjectPickedListener(this);


            try {

                lightFront = new DirectionalLight();
                lightFront.setLookAt(1, -1, -5);
                lightFront.enableLookAt();
                lightFront.setPower(1.5f);
                getCurrentScene().addLight(lightFront);

                lightBack = new DirectionalLight();
                lightBack.setLookAt(1, -1, 5);
                lightBack.enableLookAt();
                lightBack.setPower(1.5f);
                getCurrentScene().addLight(lightBack);

                DebugVisualizer debugViz = new DebugVisualizer(this);
                debugViz.addChild(new GridFloor(20, 0x555555, 1, 30));
                debugViz.addChild(new DebugLight(lightFront, 0x999900, 1));
                getCurrentScene().addChild(debugViz);

                arcball = new ArcballCamera(mContext, ((Activity) mContext).findViewById(R.id.linear_drawer_layout));
                arcball.setPosition(0, 7, 10);
                arcball.setScale(1.000, 1.000, 1.000);
                getCurrentScene().replaceAndSwitchCamera(getCurrentCamera(), arcball);

                material = new Material();
                material.enableLighting(true);
                material.setDiffuseMethod(new DiffuseMethod.Lambert());
                material.setColor(Color.LTGRAY);

                if (Constants.shipObjIndex <= 14) {
                    parserFinger = new LoaderAWD(mContext.getResources(), mTextureManager, R.raw.pointingfinger);
                    parserFinger.parse();
                    objFinger = parserFinger.getParsedObject();
                    objFinger.setColor(0xff00bfff);
                    objFinger.setPosition(new Vector3(cubeX[Constants.shipPartsIndex], cubeY[Constants.shipPartsIndex] + 1, 0));
                    getCurrentScene().addChild(objFinger);

                    anim = new TranslateAnimation3D(new Vector3(cubeX[Constants.shipPartsIndex], cubeY[Constants.shipPartsIndex], 0));
                    anim.setDurationMilliseconds(600);
                    anim.setRepeatMode(Animation.RepeatMode.REVERSE_INFINITE);
                    anim.setTransformable3D(objFinger);
                    getCurrentScene().registerAnimation(anim);
                    anim.play();
                }

                mCubeBox = new Cube(1);
                mCubeBox.setMaterial(material);
                mCubeBox.setScale(.03, .03, .03);
                mCubeBox.setColor(Color.TRANSPARENT);
                mCubeBox.setPosition(new Vector3(cubeX[Constants.shipPartsIndex], cubeY[Constants.shipPartsIndex], 0));
//                mPicker.registerObject(mCubeBox);
                getCurrentScene().addChild(mCubeBox);
                renderObjects();

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("TouchCatch", e.getMessage() + "");
            }
        }

        @Override
        protected void onRender(long ellapsedRealtime, double deltaTime) {
            super.onRender(ellapsedRealtime, deltaTime);


            if (Constants.shipObjIndex != 15) {
                Log.i("CUBESCALE", mCubeBox.getScaleX() + " : " + mCubeBox.getScaleY() + " : " + mCubeBox.getScaleZ() + " POSITION: " + mCubeBox.getPosition());
                Log.i("SHIPBODY", Constants.shipObjIndex + "");
                Log.i("SHIPPART", Constants.shipPartsIndex + "");
                try {
                    IBoundingVolume bbox = objParts.getGeometry().getBoundingBox();
                    bbox.transform(objParts.getModelMatrix());

                    IBoundingVolume bbox2 = mCubeBox.getGeometry().getBoundingBox();
                    bbox2.transform(mCubeBox.getModelMatrix());

                    if (bbox.intersectsWith(bbox2) && isObjectSelected) {
                        if (Constants.shipPartsIndex == Constants.shipObjIndex) {
                            getCurrentScene().removeChild(objShip);
                            getCurrentScene().removeChild(objParts);

                            Constants.shipObjIndex += 1;
//                            getCurrentScene().setBackgroundColor(0xff00bfff);
                            parserShip = new LoaderAWD(mContext.getResources(), mTextureManager, shipBodyObjects[Constants.shipObjIndex]);
                            parserShip.parse();
                            objShip = parserShip.getParsedObject();
                            objShip.setColor(Color.parseColor("#616161"));
                            objShip.setPosition(0, 0, 0);
                            objShip.setBackSided(true);
                            objShip.setDoubleSided(true);
                            getCurrentScene().addChild(objShip);
                            isObjectSelected = false;
                            CustomUtils.loadFragment(new SimulationFragment(), true, getActivity());
                        } else {
                            getCurrentScene().setBackgroundColor(0xff000000);
                        }
                    }
                } catch (ParsingException e) {
                    e.printStackTrace();
                    Log.i("TouchCatch-1", e.getMessage() + " ");

                }
            }
        }

        public void onRenderSurfaceSizeChanged(GL10 gl, int width, int height) {
            super.onRenderSurfaceSizeChanged(gl, width, height);
            mViewport[2] = getViewportWidth();
            mViewport[3] = getViewportHeight();
            mViewMatrix = getCurrentCamera().getViewMatrix();
            mProjectionMatrix = getCurrentCamera().getProjectionMatrix();


        }

        public void getObjectAt(float x, float y) {
            mPicker.getObjectAt(x, y);
        }

        public void onObjectPicked(Object3D object) {
            mSelectedObject = object;
        }

        public void moveSelectedObject(float x, float y) {
            this.x = x;
            this.y = y;
            if (mSelectedObject == null) {
                isObjectSelected = true;
                return;
            }
            GLU.gluUnProject(x, getViewportHeight() - y, 0, mViewMatrix.getDoubleValues(), 0, mProjectionMatrix.getDoubleValues(), 0, mViewport, 0, mNearPos4, 0);
            GLU.gluUnProject(x, getViewportHeight() - y, 1.f, mViewMatrix.getDoubleValues(), 0, mProjectionMatrix.getDoubleValues(), 0, mViewport, 0, mFarPos4, 0);
            mNearPos.setAll(mNearPos4[0] / mNearPos4[3], mNearPos4[1] / mNearPos4[3], mNearPos4[2] / mNearPos4[3]);
            mFarPos.setAll(mFarPos4[0] / mFarPos4[3], mFarPos4[1] / mFarPos4[3], mFarPos4[2] / mFarPos4[3]);
            double factor = (Math.abs(mSelectedObject.getZ()) + mNearPos.z + 8/* + addZ*/) / (getCurrentCamera().getFarPlane() - getCurrentCamera().getNearPlane());

            mNewObjPos.setAll(mFarPos);
            mNewObjPos.subtract(mNearPos);
            mNewObjPos.multiply(factor);
            mNewObjPos.add(mNearPos);

            mSelectedObject.setX(mNewObjPos.x);
            mSelectedObject.setY(mNewObjPos.y);

        }

        public void stopMovingSelectedObject() {
//            isObjectMoving = false;
        }

        @Override
        public void onClick(View view) {
            if (view.equals(btnRotateDrag)) {
//                if (isObjectMoving) {
                btnRotateDrag.setImageDrawable(getResources().getDrawable(R.drawable.ic_rotate_view));
                isObjectMoving = false;
                Toast.makeText(this.getContext(), "Rotate Mode On/ Drag Mode Off", Toast.LENGTH_SHORT).show();
                CustomUtils.loadFragment(new SimulationFragment(), true, getActivity());
//                } else {
//                    btnRotateDrag.setImageDrawable(getResources().getDrawable(R.drawable.ic_drag_object));
//                    isObjectMoving = true;
//                    Toast.makeText(this.getContext(), "Rotate Mode Off/ Drag Mode On ", Toast.LENGTH_SHORT).show();
//                    arcball = new ArcballCamera(mContext, ((Activity) mContext).findViewById(R.id.linear_drawer_layout));
//                    if (Constants.shipObjIndex == 3 || Constants.shipObjIndex == 4)
//                        arcball.setPosition(0, 2, 13);
//                    else
//                        arcball.setPosition(0, 7, 10);
//
//                    getCurrentScene().replaceAndSwitchCamera(getCurrentCamera(), arcball);
//                }
            } else if (view.equals(btnInfo)) {
                CustomUtils.loadFragment(new PreviewPartsFragment(), true, getActivity());
            } else if (view.equals(btnRefresh)) {
                CustomUtils.loadFragment(new SimulationFragment(), true, getActivity());
                Constants.shipObjIndex = 0;
            } else {
                isObjectMoving = true;
                Toast.makeText(this.getContext(), "Rotate Mode Off/ Drag Mode On ", Toast.LENGTH_SHORT).show();
                arcball = new ArcballCamera(mContext, ((Activity) mContext).findViewById(R.id.linear_drawer_layout));
                if (Constants.shipObjIndex == 3 || Constants.shipObjIndex == 4)
                    arcball.setPosition(0, 2, 13);
                else
                    arcball.setPosition(0, 7, 10);
                CustomUtils.loadFragment(new SimulationFragment(), true, getActivity());
//
//                    getCurrentScene().replaceAndSwitchCamera(getCurrentCamera(), arcball);
//                if (Constants.isRandomized) {
//                    Constants.isRandomized = false;
//                    btnRandomFixed.setImageDrawable(getResources().getDrawable(R.drawable.ic_notrandom));
//                    Toast.makeText(this.getContext(), "Randomization Off", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Constants.isRandomized = true;
//                    btnRandomFixed.setImageDrawable(getResources().getDrawable(R.drawable.ic_random));
//                    Toast.makeText(this.getContext(), "Randomization On", Toast.LENGTH_SHORT).show();
//                }
//                CustomUtils.loadFragment(new SimulationFragment(), true, getActivity());
            }
        }

        private void renderObjects() {

            Log.i("SHIPPARTINDEX", Constants.shipPartsIndex + "");

            try {
                getCurrentScene().removeChild(objParts);
                getCurrentScene().removeChild(objShip);

                if (Constants.shipObjIndex != 15) {
                    // Rendering Ship Parts
                    parserParts = new LoaderAWD(mContext.getResources(), mTextureManager, partsObjects[Constants.shipPartsIndex]);
                    parserParts.parse();
                    objParts = new Object3D();
                    objParts = parserParts.getParsedObject();
                    objParts.setPosition(0, 2, 0);
                    objParts.setColor(Color.parseColor("#616161"));
                    mPicker.registerObject(objParts);
                    getCurrentScene().addChild(objParts);
                }

                // Rendering Ship Body
                parserShip = new LoaderAWD(mContext.getResources(), mTextureManager, shipBodyObjects[Constants.shipObjIndex]);
                parserShip.parse();
                objShip = parserShip.getParsedObject();
                objShip.setColor(Color.parseColor("#616161"));
                objShip.setPosition(0, 0, 0);
                objShip.setDoubleSided(true);
                objShip.setBackSided(true);
                getCurrentScene().addChild(objShip);

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("TouchCatch-1", e.getMessage() + " ");
            }
        }

    }


    @Override
    public void onPause() {
        super.onPause();
//        addZ = 13;
    }

    private void openDB() {
        myDb = new QUIZDBAdapter(getActivity());
        myDb.open();
    }


}
