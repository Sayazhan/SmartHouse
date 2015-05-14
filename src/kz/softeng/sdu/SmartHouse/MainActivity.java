package kz.softeng.sdu.SmartHouse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.*;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import Animation.AnimationFactory.*;
import Animation.AnimationFactory;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.*;

/**
 * Created by Sayazhan on 06.05.2015.
 */

public class MainActivity extends Activity  {
    /**
     * Called when the activity is first created.
     */
    private EditText username, password;
    private boolean usernameb = false, passwordb = false;
    private boolean firsttime = true;
    private boolean bnewauto = false, blogin = false, bpassword = false;
    private static TextToSpeech tts;

    private String[] text1, text2;
    private String parseusername, parsepassword;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private static boolean isSpeaking = false;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getActionBar().hide();

        //connect to Parse.com
        Parse.initialize(this, "toOhhrlCZ9Gds2sX1JW55Z2r71JIoFiWr6eRba8A",
                "PlM3Q4Q1EslwcualVJlJdgCTtJTk3bfvCUF9own0");

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
                    }
                }
            }
        });

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        //button onclick
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), MainView.class));
            }
        });
        ImageButton btn1 = (ImageButton) findViewById(R.id.btnSpeak);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        text1 = new String[100];
        text2 = new String[100];

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = "";
                    text = matches.get(0);

                    System.out.println("This is text: " + text);
                    if (text.equals("yes")) {
                        startActivity(new Intent(getApplication(), MainView.class));
                    }else if ((text.contains("username") || text.contains("user name")) && (text.contains("password")||text.contains("passwords"))){
                        System.out.println("username and password found!!!");
                        text1 = text.split(" ");
                        for(int i=0; i<text1.length;i++){
                            if(text1[i].equals("username")){
                                parseusername=text1[i+2];
                                username.setText(text1[i+2]);
                            }else if(text1[i].equals("user")){
                                parseusername=text1[i+3];
                                username.setText(text1[i+3]);
                            }
                            if(text1[i].equals("password") || text1[i].equals("passwords") && text1.length==i+2){
                                parsepassword = text1[i+1];
                                password.setText(text1[i+1]);
                            }
                        }
                     }
                }
                break;
            }
        }
        //progressdialog
        final ProgressDialog dlg = new ProgressDialog(
                MainActivity.this);
        dlg.setTitle("Please wait");
        dlg.setMessage("Logging in. Please wait.");
        dlg.show();
        //check do we have this user
        ParseUser.logInInBackground(username.getText()
                        .toString(), password.getText().toString(),
                new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            Toast.makeText(MainActivity.this,
                                    e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        } else {

                            Intent intent = new Intent(
                                    MainActivity.this,
                                    MainView.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Method: onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tts.shutdown(); //mTts is your TextToSpeech Object
    }

}