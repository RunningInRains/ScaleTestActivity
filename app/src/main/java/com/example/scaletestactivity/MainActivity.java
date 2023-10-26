package com.example.scaletestactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final float PIVOT_INIT_SCALE = 0.8f;
    private TextView mNormalScaleTextView;
    private Button mNormalScaleButton;
    private Button mNormalScaleButtonReset;
    private TextView mPivotScaleTextView;
    private Button mPivotScaleButton;
    private Button mPivotScaleButtonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNormalScaleTextView = findViewById(R.id.tv_scale_normal);
        mNormalScaleButton = findViewById(R.id.btn_start_scale_normal);
        mNormalScaleButtonReset = findViewById(R.id.btn_start_scale_normal_reset);

        mPivotScaleTextView = findViewById(R.id.tv_scale_pivot);
        mPivotScaleButton = findViewById(R.id.btn_start_scale_pivot);
        mPivotScaleButtonReset = findViewById(R.id.btn_start_scale_pivot_reset);


        mPivotScaleTextView.setPivotX(0.0f);
        mPivotScaleTextView.setPivotY(0.0f);
        mPivotScaleTextView.setScaleX(PIVOT_INIT_SCALE);
        mPivotScaleTextView.setScaleY(PIVOT_INIT_SCALE);

        setNormalScaleButtonListener();
        setResetNormalListener();
        setPivotScaleButtonListener();
        setResetPivotListener();
    }

    private void setNormalScaleButtonListener() {
        TextView textView = mNormalScaleTextView;
        if (mNormalScaleButton == null || textView == null) {
            return;
        }
        mNormalScaleButton.setOnClickListener(v -> {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            float initScale = 1.0f;
            float targetScale = 0.5f;
            valueAnimator.addUpdateListener(animation -> {
                float animateValue = (float) animation.getAnimatedValue();
                float animateScale = initScale + (targetScale - initScale) * animateValue;
                textView.setScaleX(animateScale);
                textView.setScaleY(animateScale);
            });
            valueAnimator.start();
        });
    }

    private void setPivotScaleButtonListener() {
        TextView textView = mPivotScaleTextView;
        if (mPivotScaleButton == null || textView == null) {
            return;
        }

        mPivotScaleButton.setOnClickListener(v -> {
            float initScale = PIVOT_INIT_SCALE;
            float targetScale = 0.4f;
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.addUpdateListener(animation -> {
                int with = textView.getWidth();
                int height = textView.getHeight();
                Log.i("wangfeng", "width=" + with + ",height=" + height);
                float animateValue = (float) animation.getAnimatedValue();
                float animateScale = initScale + (targetScale - initScale) * animateValue;
                textView.setScaleX(animateScale);
                textView.setScaleY(animateScale);
                textView.setPivotX((initScale - animateScale) * with / (2 * (1- animateScale)));
                textView.setPivotY((initScale - animateScale) * height / (2 * (1- animateScale)));
            });
            valueAnimator.start();
        });
    }

    private void setResetNormalListener() {
        if (mNormalScaleButtonReset == null) {
            return;
        }
        mNormalScaleButtonReset.setOnClickListener(v->{
            if (mNormalScaleTextView != null) {
                mNormalScaleTextView.setScaleX(1.0f);
                mNormalScaleTextView.setScaleY(1.0f);
            }
        });
    }

    private void setResetPivotListener() {
        if (mPivotScaleButtonReset == null) {
            return;
        }
        mPivotScaleButtonReset.setOnClickListener(v->{
            if (mPivotScaleTextView != null) {
                mPivotScaleTextView.setPivotX(0.0f);
                mPivotScaleTextView.setPivotY(0.0f);
                mPivotScaleTextView.setScaleX(PIVOT_INIT_SCALE);
                mPivotScaleTextView.setScaleY(PIVOT_INIT_SCALE);
            }
        });
    }
}