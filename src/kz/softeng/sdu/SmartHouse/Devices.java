package kz.softeng.sdu.SmartHouse;

import Animation.AnimationFactory;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewAnimator;

/**
 * Created by Sayazhan on 16.05.2015.
 */
public class Devices extends Activity {
    private static final int    BG_COLOR_START = (int)0xFF1D92DB;
    private static final int    BG_COLOR_REVERSE_POINT = (int)0xFF6DA7BD;
    private static final int    BG_COLOR_RESUME_POINT = (int)0xFF409BBD;
    private static final int    BG_COLOR_END = (int)0xFFA5B7BD;
    private static final int    ANIM_DURATION = 1500;
    private ObjectAnimator      mColorAnim1;
    private Button mAnimButton1;
    private View mColorView;
    private ColorDrawable mBg;

    private ObjectAnimator mColorAnim2;
    private Button mAnimButton2;
    private View mWashView;
    private ColorDrawable mWashBg;

    private ObjectAnimator mColorAnim3;
    private Button mAnimButton3;
    private View mMultiWashView;
    private ColorDrawable mMultiWashBg;

    private LinearLayout mLayout;
    private LayoutTransition mLT;
    private Animator mAppearAnim;
    private Button  mAppearBtn;
    private ImageView mImg, mImg1, imageViewNew1, imageViewNew2;
    private ViewAnimator viewAnimatorNew1;

    private boolean setva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_animator);

        viewAnimatorNew1 = (ViewAnimator) this.findViewById(R.id.viewFlipperNew);

        imageViewNew1 = (ImageView) this.findViewById(R.id.imageViewNew1);
        imageViewNew2 = (ImageView) this.findViewById(R.id.imageViewNew2);

        mImg = (ImageView)findViewById(R.id.lt_img);
        mImg1 = (ImageView)findViewById(R.id.lt_img1);

        //  Load the custom "appear" animation and create a LayoutTransition
        mAppearAnim = AnimatorInflater.loadAnimator(this, R.animator.custom_appear);
        mLayout = (LinearLayout)findViewById(R.id.container);
        mLT = new LayoutTransition();
        mLT.setAnimator(LayoutTransition.APPEARING, mAppearAnim);

        //  Set the start delay of our custom animation to be the
        //  duration of the "change appearing" animation so it does
        //  not begin before the other views have "made room".
        Animator ca = mLT.getAnimator(LayoutTransition.CHANGE_APPEARING);
        mAppearAnim.setStartDelay(ca.getDuration());
        mLayout.setLayoutTransition(mLT);

        //  Use a button to trigger the image to come or go
        mAppearBtn = (Button)findViewById(R.id.btn_lt);
        mAppearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImg.getVisibility() == View.GONE) {
                    mImg.setAlpha(0f);
                    mImg.setVisibility(View.VISIBLE);
                } else  if (mImg1.getVisibility() == View.GONE) {
                    mImg1.setAlpha(0f);
                    mImg1.setVisibility(View.VISIBLE);
                } else if (viewAnimatorNew1.getVisibility() == View.GONE){
                    viewAnimatorNew1.setAlpha(0f);
                    viewAnimatorNew1.setVisibility(View.VISIBLE);
                    setva = true;
                }
            }
        });
        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImg.setImageResource(R.drawable.clean_house_off);
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AnimationFactory.flipTransition(viewAnimatorNew1, AnimationFactory.FlipDirection.LEFT_RIGHT);

            }
        };
            viewAnimatorNew1.setOnClickListener(listener);

            imageViewNew1.setOnClickListener(listener);
            imageViewNew2.setOnClickListener(listener);


    }
}
