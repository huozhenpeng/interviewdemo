package com.example.interviewdemo;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextView mView;
    private SeekBar seekbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView=findViewById(R.id.tv);
        seekbar=findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("abc",(event.getX()-mView.getLeft())+"      "+event.getY());
        return super.onTouchEvent(event);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float[] points = new float[2];
        //源码中使用的这种方式其实也是将触点坐标转换为相对于自己的左上角，因为她在判断范围的时候（pointInView）
        //相当于是（0，0）到（right-left,bottom-top）
        //points[0]+=mView.getScrollX()-mView.getLeft();
        //points[1]+=mView.getScrollY()-mView.getTop();
        Log.e("abc",points[0]+"  "+points[1]);
        progress=progress*2;
        mView.setRotation(progress);
        mView.setTranslationX(-progress);
        mView.setTranslationY(-progress);
        Matrix matrix = getViewMatrix(mView);
        if (matrix != null) {
            matrix.mapPoints(points);
        }
        Log.e("abc",String.format("绿点在View中吗？  %s",
                pointInView(mView, points) ? "是的" : "不不不不"));
        MToast.showToast(this,String.format("绿点在View中吗？  %s",
                pointInView(mView, points) ? "是的" : "不不不不"));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private Matrix getViewMatrix(View view) {
        try {
            Method getInverseMatrix = View.class.getDeclaredMethod("getInverseMatrix");
            getInverseMatrix.setAccessible(true);
            return (Matrix) getInverseMatrix.invoke(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean pointInView(View view, float[] points) {
        try {
            Method pointInView = View.class.getDeclaredMethod("pointInView", float.class, float.class);
            pointInView.setAccessible(true);
            return (boolean) pointInView.invoke(view, points[0], points[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
