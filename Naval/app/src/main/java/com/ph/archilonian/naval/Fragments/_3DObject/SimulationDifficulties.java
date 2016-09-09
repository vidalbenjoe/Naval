package com.ph.archilonian.naval.Fragments._3DObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ph.archilonian.naval.R;
import com.ph.archilonian.naval.Utilities.Constants;
import com.ph.archilonian.naval.Utilities.CustomUtils;

/**
 * Created by luigigo on 8/28/16.
 */
public class SimulationDifficulties extends Fragment implements View.OnClickListener {

    Button btnNormal, btnRandomize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.layout_simulation_difficulties, container, false);

        btnNormal = (Button) mainView.findViewById(R.id.btnNormal);
        btnRandomize = (Button) mainView.findViewById(R.id.btnRandomized);
        btnNormal.setOnClickListener(this);
        btnRandomize.setOnClickListener(this);
        return mainView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnNormal:
                Constants.isRandomized = false;
                break;

            case R.id.btnRandomized:
                Constants.isRandomized = true;
                break;
        }
        Constants.shipObjIndex = 0;
        CustomUtils.loadFragment(new SimulationFragment(), true, getActivity());
    }
}
