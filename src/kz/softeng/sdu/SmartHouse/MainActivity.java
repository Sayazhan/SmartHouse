package kz.softeng.sdu.SmartHouse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Animation.AnimationFactory.*;
import Animation.AnimationFactory;

import java.util.*;

/**
 * Created by Sayazhan on 06.05.2015.
 */

public class MainActivity extends Activity implements RecognitionListener {
    /**
     * Called when the activity is first created.
     */
    private EditText username, password;
    private boolean usernameb = false, passwordb = false;
    private boolean firsttime = true;
    private boolean bnewauto = false, blogin = false, bpassword = false;
    private static TextToSpeech tts;

    private static SpeechRecognizer speech = null;
    private static Intent recognizerIntent;

    private static boolean isSpeaking = false;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        if(!isSpeaking && !firsttime){
//            speech.startListening(recognizerIntent);
//        }

        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");

                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else if (bnewauto == false) {
                        tts.speak("Hello! Do you want to autorizise?", TextToSpeech.QUEUE_FLUSH, map);
                        bnewauto = true;
                        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                            @Override
                            public void onStart(String s) {
                                System.out.println("Hello is started");
                                isSpeaking = true;
                            }

                            @Override
                            public void onDone(String s) {
                                System.out.println("Hello is finished");
                                tts.shutdown();
                                isSpeaking = false;
                                firsttime = false;
                            }

                            @Override
                            public void onError(String s) {
                                System.out.println("Hello is error");
                            }
                        });
                    }
                }

            }
        });
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        //touch screen
        final View touchView = findViewById(R.id.login_form);
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //if (!isSpeaking && !firsttime) {
                speech.startListening(recognizerIntent);
                //}
                return true;

            }
        });
        //button onclick
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplication(), MainView.class));
            }
        });
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        text = matches.get(0);
        System.out.println("This is text: " + text);
        if (text == "yes") {
            startActivity(new Intent(getApplication(), MainView.class));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            System.out.println("Method: onPause");
        }
    }

    @Override
    public void onEndOfSpeech() {
        speech.stopListening();
        System.out.println("Method: stopListening");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        speech.destroy();
        tts.shutdown(); //mTts is your TextToSpeech Object
    }

    @Override
    public void onError(int i) {
        String errorMessage = getErrorText(i);
        Toast.makeText(getApplication(), "ERROR" + errorMessage, Toast.LENGTH_SHORT).show();
        speech.stopListening();
        speech.destroy();
        System.out.println("Method: onError");
        speech.startListening(recognizerIntent);
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

}