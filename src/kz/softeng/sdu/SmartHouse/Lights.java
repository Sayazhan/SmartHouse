package kz.softeng.sdu.SmartHouse;

import Animation.AnimationFactory;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Sayazhan on 16.05.2015.
 */
public class Lights extends Activity{

    private ViewAnimator viewAnimatorLight, viewAnimatorLight1, viewAnimatorLight2, viewAnimatorLight3,
            viewAnimatorLight4, viewAnimatorLight5;

    private ImageView imageViewLight1, imageViewLight2, imageViewLight3, imageViewLight4, imageViewLight5,
            imageViewLight6, imageViewLight7, imageViewLight8, imageViewLight9,
            imageViewLight10, imageViewLight11, imageViewLight12, imageView1Light3, imageViewLight15;

    //Text To Speech instance
    private TextToSpeech tts;

    private boolean bnewauto=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lights);

        speechaction();

        viewAnimatorLight = (ViewAnimator) this.findViewById(R.id.viewFlipperLight);
        viewAnimatorLight1 = (ViewAnimator) this.findViewById(R.id.viewFlipperLight1);
        viewAnimatorLight2 = (ViewAnimator) this.findViewById(R.id.viewFlipperLight2);
        viewAnimatorLight3 = (ViewAnimator) this.findViewById(R.id.viewFlipperLight3);
        viewAnimatorLight4 = (ViewAnimator) this.findViewById(R.id.viewFlipperLight4);
        viewAnimatorLight5 = (ViewAnimator) this.findViewById(R.id.viewFlipperLight5);

        imageViewLight1 = (ImageView) this.findViewById(R.id.imageViewLight1);
        imageViewLight2 = (ImageView) this.findViewById(R.id.imageViewLight2);

        imageViewLight3 = (ImageView) this.findViewById(R.id.imageViewLight3);
        imageViewLight4 = (ImageView) this.findViewById(R.id.imageViewLight4);

        imageViewLight5 = (ImageView) this.findViewById(R.id.imageViewLight5);
        imageViewLight6 = (ImageView) this.findViewById(R.id.imageViewLight6);

        imageViewLight7 = (ImageView) this.findViewById(R.id.imageViewLight7);
        imageViewLight8 = (ImageView) this.findViewById(R.id.imageViewLight8);

        imageViewLight9 = (ImageView) this.findViewById(R.id.imageViewLight9);
        imageViewLight10 = (ImageView) this.findViewById(R.id.imageViewLight10);

        imageViewLight11 = (ImageView) this.findViewById(R.id.imageViewLight11);
        imageViewLight12 = (ImageView) this.findViewById(R.id.imageViewLight12);


        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AnimationFactory.flipTransition(viewAnimatorLight, AnimationFactory.FlipDirection.LEFT_RIGHT);

            }
        };
        View.OnClickListener listener1 = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AnimationFactory.flipTransition(viewAnimatorLight1, AnimationFactory.FlipDirection.LEFT_RIGHT);

            }
        };
        View.OnClickListener listener2 = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AnimationFactory.flipTransition(viewAnimatorLight2, AnimationFactory.FlipDirection.LEFT_RIGHT);

            }
        };
        View.OnClickListener listener3 = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AnimationFactory.flipTransition(viewAnimatorLight3, AnimationFactory.FlipDirection.LEFT_RIGHT);

            }
        };
        View.OnClickListener listener4 = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AnimationFactory.flipTransition(viewAnimatorLight4, AnimationFactory.FlipDirection.LEFT_RIGHT);

            }
        };
        View.OnClickListener listener5 = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AnimationFactory.flipTransition(viewAnimatorLight5, AnimationFactory.FlipDirection.LEFT_RIGHT);

            }
        };

        viewAnimatorLight.setOnClickListener(listener);
        viewAnimatorLight1.setOnClickListener(listener1);
        viewAnimatorLight2.setOnClickListener(listener2);
        viewAnimatorLight3.setOnClickListener(listener3);
        viewAnimatorLight4.setOnClickListener(listener4);
        viewAnimatorLight5.setOnClickListener(listener5);

        imageViewLight1.setOnClickListener(listener);
        imageViewLight2.setOnClickListener(listener);

        imageViewLight3.setOnClickListener(listener1);
        imageViewLight4.setOnClickListener(listener1);

        imageViewLight5.setOnClickListener(listener2);
        imageViewLight6.setOnClickListener(listener2);

        imageViewLight7.setOnClickListener(listener3);
        imageViewLight8.setOnClickListener(listener3);

        imageViewLight9.setOnClickListener(listener4);
        imageViewLight10.setOnClickListener(listener4);

        imageViewLight11.setOnClickListener(listener5);
        imageViewLight12.setOnClickListener(listener5);

    }

    private void speechaction() {
        tts = new TextToSpeech(Lights.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");


                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else if (!bnewauto) {
                        tts.speak("This is lights menu. You can turn on or turn off lights in several rooms.", TextToSpeech.QUEUE_FLUSH, map);
                        bnewauto = true;
                    }
                }
            }
        });
    }
}


