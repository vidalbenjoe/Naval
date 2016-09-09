package com.ph.archilonian.naval.Fragments._3DObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ph.archilonian.naval.DrawerMenu.MenuActivity;
import com.ph.archilonian.naval.R;
import com.ph.archilonian.naval.Utilities.Constants;
import com.special.ResideMenu.ResideMenu;

import org.rajawali3d.Object3D;
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

import javax.microedition.khronos.opengles.GL10;

public class PreviewPartsFragment extends ObjectHolderFragment implements
        OnTouchListener {

    boolean isObjectMoving = false, isObjectSelected = false;
    Cube mCubeBox;
    Object3D objShip, objParts;
    LoaderAWD parserParts, parserShip;
    Material material;
    ResideMenu resideMenu;
    DirectionalLight lightFront, lightBack;
    TextView txtTitle, txtDirs;

    String[] strArrayTitles = {
            "Marine Gas Turbine",
            "Marine Diesel Engine",
            "Marine Steam Engine",
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
            "Bow Thruster",
            "Hull"
    };

    String[] strArrayDescriptions = {
            "It also called a combustion turbine, is a type of internal combustion engine. It has an upstream rotating compressor coupled to a downstream turbine, and a combustion chamber in between.",
            "It is an internal combustion engine in which ignition of the fuel that has been injected into the combustion chamber is initiated by the high temperature which a gas achieves when greatly compressed.",
            "It is a steam engine that is used to power a ship or boat. This article deals mainly with marine steam engines of the reciprocating type, which were in use from the inception of the steamboat in the early 19th century to their last years of large-scale manufacture during World War II. ",
            "It is a protruding bulb at the bow (or front) of a ship just below the waterline. The bulb modifies the way the water flows around the hull, reducing drag and thus increasing speed, range, fuel efficiency, and stability.",
            "It is a primary control surface used to steer a ship, boat, submarine, hovercraft, aircraft, or other conveyance that moves through a fluid medium (generally air or water). On an aircraft the rudder is used primarily to counter adverse yaw and p-factor and is not the primary control used to turn the airplane.",
            "The stern is the back or aft-most part of a ship or boat, technically defined as the area built up over the sternpost, extending upwards from the counter rail to the taffrail.",
            "It is a Principal Deck of a Vessel, It is the uppermost complete deck extending from bow to stern. A steel ship's hull may be considered a structural beam with the main deck forming the upper flange of a box girder and the keel forming the lower strength member. The main deck may act as a tension member when the ship is supported by a single wave amidships, or as a compression member when the ship is supported between waves forward and aft.",
            "It is the smokestack or chimney on a ship used to expel boiler steam and smoke or engine exhaust. They are also commonly referred to as stacks. Funnels are usually made of stainless steel, aluminium, glass, or plastic. The material used in its construction should be sturdy enough to withstand the weight of the substance being transferred, and it should not react with the substance.",
            "Refers to the upper deck of a sailing ship forward of the foremast, or the forward part of a ship with the sailors' living quarters. Related to the latter meaning is the phrase \"before the mast\" which denotes anything related to ordinary sailors, as opposed to a ship's officers.",
            "A deck is a permanent covering over a compartment or a hull of a ship. On a boat or ship, the primary or upper deck is the horizontal structure which forms the 'roof' for the hull, which both strengthens the hull and serves as the primary working surface.",
            "A large, tall machine used for moving heavy objects, typically by suspending them from a projecting arm or beam.",
            "A device or iron so shaped to grip the bottom and holds a vessel at anchor by the anchor chain. Anchors can either be temporary or permanent. Permanent anchors are used in the creation of a mooring, and are rarely moved; a specialist service is normally needed to move or maintain them. Vessels carry one or more temporary anchors, which may be of different designs and weights.",
            "A drive shaft, driveshaft, driving shaft, propeller shaft (prop shaft), or Cardan shaft is a mechanical component for transmitting torque and rotation, usually used to connect other components of a drive train that cannot be connected directly because of distance or the need to allow for relative movement between them.",
            "It is a type of fan that transmits power by converting rotational motion into thrust. A pressure difference is produced between the forward and rear surfaces of the air foil-shaped blade, and a fluid (such as air or water) is accelerated behind the blade.",
            "Bow thruster or stern thruster is a transversal propulsion device built into, or mounted to, either the bow or stern, of a ship or boat, to make it more manoeuvrable. Bow thrusters make docking easier, since they allow the captain to turn the vessel to port or starboard side, without using the main propulsion mechanism which requires some forward motion for turning.",
            "It is the watertight body of a ship or boat. Above the hull is the superstructure and/or deckhouse, where present. The line where the hull meets the water surface is called the waterline. The structure of the hull varies depending on the vessel type."
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ((View) mRajawaliSurface).setOnTouchListener(this);

        LinearLayout mainLinear = new LinearLayout(getActivity());
        mainLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mainLinear.setOrientation(LinearLayout.VERTICAL);
        mainLinear.setGravity(Gravity.RIGHT);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f);
        params1.setMargins(0, 50, 0, 0);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f);
        params2.setMargins(10, 0, 10, 130);

        LinearLayout secondLinear = new LinearLayout(getActivity());
        secondLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        secondLinear.setOrientation(LinearLayout.HORIZONTAL);
        secondLinear.setWeightSum(1);
        secondLinear.setGravity(Gravity.RIGHT);
        mainLinear.addView(secondLinear);

        txtTitle = new TextView(getActivity());
        txtTitle.setLayoutParams(params1);
        txtTitle.setText(strArrayTitles[Constants.shipPartsIndex]);
        txtTitle.setTextSize(25);
        txtTitle.setGravity(Gravity.CENTER);
        txtTitle.setTextColor(Color.WHITE);
        secondLinear.addView(txtTitle);

        txtDirs = new TextView(getActivity());
        txtDirs.setTextColor(Color.WHITE);
        txtDirs.setTextSize(11);
        txtDirs.setGravity(Gravity.CENTER);
        txtDirs.setText(strArrayDescriptions[Constants.shipPartsIndex]);
        mainLinear.addView(txtDirs);

        mLayout.addView(mainLinear);
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
        resideMenu.addIgnoredView(mLayout);
        return mLayout;
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
                ((TouchAndDragRenderer) mRenderer).moveSelectedObject(event.getX(),
                        event.getY());
                break;
            case MotionEvent.ACTION_UP:
                ((TouchAndDragRenderer) mRenderer).stopMovingSelectedObject();
                break;
        }

        return isObjectMoving;

    }


    private final class TouchAndDragRenderer extends AExampleRenderer implements OnObjectPickedListener {
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
                R.raw.shippart12,
                R.raw.hull

        };

        int[] shipBodyObjects = {
                R.raw.shipbody1,
                R.raw.shipbody2,
                R.raw.shipbody3,
                R.raw.shipbody4,
                R.raw.shipbody5,
                R.raw.shipbody6,
                R.raw.shipbody7,
                R.raw.shipbody8,
                R.raw.shipbody9,
                R.raw.shipbody10,
                R.raw.ship};

        double[] cubeX = {
                2.985618827893172, 2.16967344112234,
                2.231487966420033, -3.0227073919489604,
                -0.475968142282985, 1.1682856118796294,
                -3.057462808468623, 2.6889118266751235,
                3.146335686930214, -2.8990791914194745,

                -2.8990791914194745, -2.8990791914194745,
                -2.8990791914194745, -2.8990791914194745,
                -2.8990791914194745, -2.8990791914194745
        };
        double[] cubeY = {
                0.9639618834769772, 0.9021103330868163,
                1.4711444455534728, 0.8278882459343837,
                0.6423335947638998, 0.9392211877594993,
                0.592852203328944, 0.27122391461586653,
                0.25885375566066043, 0.24648359670545544,

                0.25885375566066043, 0.24648359670545544,
                0.25885375566066043, 0.24648359670545544,
                0.25885375566066043, 0.24648359670545544
        };
        double[] cubeZ = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};


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
                if (Constants.shipPartsIndex == 9)
                    arcball.setPosition(0, 13, 20);
                else
                    arcball.setPosition(0, 7, 8);
                arcball.setScale(1.000, 1.000, 1.000);
                getCurrentScene().replaceAndSwitchCamera(getCurrentCamera(), arcball);

                material = new Material();
                material.enableLighting(true);
                material.setDiffuseMethod(new DiffuseMethod.Lambert());
                material.setColor(Color.LTGRAY);

                mCubeBox = new Cube(1);
                mCubeBox.setMaterial(material);
                mCubeBox.setScale(.01, .01, .01);
                mCubeBox.setColor(0xff990000);
                mCubeBox.setPosition(new Vector3(cubeX[Constants.shipPartsIndex], cubeY[Constants.shipPartsIndex], cubeZ[Constants.shipPartsIndex]));
                mPicker.registerObject(mCubeBox);
                getCurrentScene().addChild(mCubeBox);
                Log.i("CUBESCALE-", mCubeBox.getScaleX() + " : " + mCubeBox.getScaleY() + " : " + mCubeBox.getScaleZ() + " POSITION: " + mCubeBox.getPosition());

                renderObjects();

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("TouchCatch", e.getMessage() + "");
            }
        }

        @Override
        protected void onRender(long ellapsedRealtime, double deltaTime) {
            super.onRender(ellapsedRealtime, deltaTime);
            Log.i("CUBESCALE-", mCubeBox.getScaleX() + " : " + mCubeBox.getScaleY() + " : " + mCubeBox.getScaleZ() + " POSITION: " + mCubeBox.getPosition());
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
                        getCurrentScene().setBackgroundColor(0xff00bfff);
                        parserShip = new LoaderAWD(mContext.getResources(), mTextureManager, shipBodyObjects[Constants.shipObjIndex]);
                        parserShip.parse();
                        objShip = parserShip.getParsedObject();
                        objShip.setColor(Color.parseColor("#616161"));
                        objShip.setPosition(0, 0, 0);
                        getCurrentScene().addChild(objShip);
                        isObjectSelected = false;
                    } else {
                        getCurrentScene().setBackgroundColor(0xff000000);
                    }
                }
            } catch (ParsingException e) {
                e.printStackTrace();
                Log.i("TouchCatch-1", e.getMessage() + " ");

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
        }

        private void renderObjects() {

            Log.i("SHIPPARTINDEX", Constants.shipPartsIndex + "");

            try {
                getCurrentScene().removeChild(objParts);
                getCurrentScene().removeChild(objShip);

                // Rendering Ship Parts
                parserParts = new LoaderAWD(mContext.getResources(), mTextureManager, partsObjects[Constants.shipPartsIndex]);
                parserParts.parse();
                objParts = new Object3D();
                objParts = parserParts.getParsedObject();
                objParts.setPosition(0, 0, 0);
                objParts.setScale(3.0, 3.0, 3.0);
                objParts.setColor(Color.parseColor("#616161"));
                mPicker.registerObject(objParts);
                getCurrentScene().addChild(objParts);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

}
