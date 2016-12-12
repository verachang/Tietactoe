package com.test.game;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;


public class MainActivity extends Activity implements View.OnClickListener{
    private static String TAG = "VERA-GAME";

    private boolean mFlag = false, mGameFinish = false;
    private GridLayout mGridLayout;
    private ImageView mImageView;
    private View mChildView;
    private Button mStartgame, mReset;
    private int mArray[] = new int[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.default_imageView);
        mGridLayout = (GridLayout) findViewById(R.id.gridlayout);
        mStartgame = (Button) findViewById(R.id.startgame);
        mReset = (Button) findViewById(R.id.reset);
        mStartgame.setOnClickListener(this);
        mReset.setOnClickListener(this);


        for(int index =0; index < mGridLayout.getChildCount();index++)
        {
            View tmp = mGridLayout.getChildAt(index);
            tmp.setOnClickListener(this);
            mArray[index] = (index+1)*10;

        }


        mImageView.animate().setListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mImageView.setVisibility(View.INVISIBLE);
                if(mFlag) {
                    mChildView.setBackgroundResource(R.drawable.red);
                }else {
                    mChildView.setBackgroundResource(R.drawable.yellow);
                }

                if((mArray[0] == mArray[1] && mArray[1] == mArray[2]) || (mArray[3] == mArray[4] && mArray[4] == mArray[5]) ||
                        (mArray[6] == mArray[7] && mArray[7] == mArray[8]) || (mArray[0] == mArray[3] && mArray[3] == mArray[6]) ||
                        (mArray[1] == mArray[4] && mArray[4] == mArray[7]) || (mArray[2] == mArray[5] && mArray[5] == mArray[8]) ||
                        (mArray[0] == mArray[4] && mArray[4] == mArray[8]) || (mArray[2] == mArray[4] && mArray[4] == mArray[6]))
                {
                    mGameFinish = true;
                    if(mFlag)
                    {
                        CreateResultDialog("RED color win !!!");
                    }else
                    {
                        CreateResultDialog("Yellow color win !!!");
                    }
                }
                else
                {
                    boolean tmpflag = true;
                     for(int i = 0; i< 9; i++)
                     {
                         if(mArray[i] > 9)
                         {
                             tmpflag = false;
                             break;
                         }
                     }
                    if(tmpflag)
                    {
                        mGameFinish = true;
                        CreateResultDialog("End in a draw !!!");
                    }
                }

                mFlag = !mFlag;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.startgame)
        {
            CreateStartGameDialog();
            return;
        }
        else if(view.getId() == R.id.reset)
        {
            for(int index =0; index < mGridLayout.getChildCount();index++)
            {
                View tmp = mGridLayout.getChildAt(index);
                tmp.setBackgroundResource(0);
                mArray[index] = (index+1)*10;
            }
            mFlag = false;
            mGameFinish = false;
            return;
        }

        if(mGameFinish)
        {
            return;
        }

        if(mFlag) {
            mImageView.setBackgroundResource(R.drawable.red);
        }else {
            mImageView.setBackgroundResource(R.drawable.yellow);
        }

        int [] location = new int[2];
        view.getLocationInWindow (location);
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setTranslationX(location[0]);
        mImageView.setTranslationY(0);
        mImageView.animate().translationX(location[0]).translationY(location[1]).setDuration(300).rotation(0.5f);
        mChildView = view;

        switch (view.getId())
        {
            case R.id.LinearLayout1:
                mArray[0] = mFlag ? 2 : 1;
                break;
            case R.id.LinearLayout2:
                mArray[1] = mFlag ? 2 : 1;
                break;
            case R.id.LinearLayout3:
                mArray[2] = mFlag ? 2 : 1;
                break;
            case R.id.LinearLayout4:
                mArray[3] = mFlag ? 2 : 1;
                break;
            case R.id.LinearLayout5:
                mArray[4] = mFlag ? 2 : 1;
                break;
            case R.id.LinearLayout6:
                mArray[5] = mFlag ? 2 : 1;
                break;
            case R.id.LinearLayout7:
                mArray[6] = mFlag ? 2 : 1;
                break;
            case R.id.LinearLayout8:
                mArray[7] = mFlag ? 2 : 1;
                break;
            case R.id.LinearLayout9:
                mArray[8] = mFlag ? 2 : 1;
                break;
        }

    }
    public void CreateStartGameDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Notice");
        alertDialog.setMessage("Please Select which color fast");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "RED",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mFlag = true;
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yellow",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mFlag = false;
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void CreateResultDialog(String Result){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Notice");
        alertDialog.setMessage(Result);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }
}
