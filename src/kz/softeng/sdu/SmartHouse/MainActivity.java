package kz.softeng.sdu.SmartHouse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Animation.AnimationFactory.*;
import Animation.AnimationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sayazhan on 06.05.2015.
 */

public class MainActivity extends Activity implements RecognitionListener {
    /**
     * Called when the activity is first created.
     */
    private EditText username, password;
    private boolean usernameb=false, passwordb=false;
    private boolean newauto=false;
    private static TextToSpeech tts;

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;

    private static boolean isSpeaking = false;
    private static BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED) && tts != null)
            {
                isSpeaking = false;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        tts.speak("Hello! Do you want to autorizise?", TextToSpeech.QUEUE_ADD, null);
                        newauto=true;
                    }
                }

            }
        });

        if(tts.isSpeaking()){
            speech.startListening(recognizerIntent);
        }

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        final View touchView = findViewById(R.id.login_form);
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplication(), MainView.class));

            }
        });
//        boolean validationError = false;
//        StringBuilder validationErrorMessage = new StringBuilder(
//                "Please ");
//        if (isEmpty(username)) {
//            validationError = true;
//            validationErrorMessage.append("enter a username");
//        }
//        if (isEmpty(password)) {
//            if (validationError) {
//                validationErrorMessage.append(", and ");
//            }
//            validationError = true;
//            validationErrorMessage.append("enter a password");
//        }
//        validationErrorMessage.append(".");
//
//        if (validationError) {
//            Toast.makeText(MainActivity.this,
//                    validationErrorMessage.toString(),
//                    Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        final ProgressDialog dlg = new ProgressDialog(
//                MainActivity.this);
//        dlg.setTitle("Please wait");
//        dlg.setMessage("Logging in. Please wait.");
//        dlg.show();

    }

//    private boolean isEmpty(EditText etText){
//        if(etText.getText().toString().trim().length() > 0){
//            return false;
//        } else {
//            return true;
//        }
    //Hellooo
//    }



    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        text = matches.get(0);
        Toast.makeText(getApplication(),"This is text: "+text ,Toast.LENGTH_SHORT).show();
        if(text=="Да"){
            startActivity(new Intent(getApplication(), MainView.class));
        }
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
    public void onError(int i) {
        String errorMessage = getErrorText(i);
        Toast.makeText(getApplication(), "ERROR" + errorMessage, Toast.LENGTH_SHORT).show();
        speech.stopListening();
        speech.destroy();
        System.out.println("Method: onError");
    }

    public static boolean isSpeaking() {
        return isSpeaking;
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
