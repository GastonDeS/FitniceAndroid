package com.example.fitnice.viewmodel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class DisableSeekBarView extends androidx.appcompat.widget.AppCompatSeekBar {
    public DisableSeekBarView(Context context) {
        super(context);
    }

    public DisableSeekBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisableSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
