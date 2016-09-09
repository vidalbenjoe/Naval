package com.ph.archilonian.naval.Utilities.GUI;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialleanback.MaterialLeanBack;
import com.ph.archilonian.naval.R;

public class TestViewHolder extends MaterialLeanBack.ViewHolder{

    public TextView textView;
    public ImageView imageView;

    public TestViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
