package com.example.asociadosboapaz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

TextToSpeech repeatTTS;
Button btn_web_oficial, btn_facebook, btn_instagram, btn_listener, btn_audionews, btn_about;
boolean isWeb = false, isFacebook = false, isInstagram=false, isAudioNes=false, isAbout=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        final Vibrator vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

        btn_web_oficial = findViewById(R.id.btn_web_oficial);
        btn_facebook = findViewById(R.id.btn_facebook);
        btn_instagram = findViewById(R.id.btn_instagram);
        btn_listener = findViewById(R.id.btn_listener);
        btn_audionews = findViewById(R.id.btn_audionews);
        btn_about = findViewById(R.id.btn_about );

   final String mensaje = "Bienvenido a BoaPaz, , en este menú podrá acceder a los medios de comunicación oficiales de la cooperativa, ,"+
                "La primera opción corresponde a la página oficial, , la segunda opción al feisbuk de la cooperativa,  , y la tercera opción a la cuenta de Instagrám";



        new CountDownTimer(100000, 100) {

            public void onTick(long millisUntilFinished) {


            }

            public void onFinish() {

            }
        }.start();


        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        repeatTTS = new TextToSpeech(this,  new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {

                Locale locSpanish = new Locale("spa", "MEX");
                int result = repeatTTS.setLanguage(locSpanish);
                repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);




            }

        });

        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
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
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches


                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                Toast message = Toast.makeText(getApplicationContext(), matches.get(0), Toast.LENGTH_LONG);
                message.show();

                //displaying the first match
                if (matches != null)
                    repeatTTS.speak(matches.get(0), TextToSpeech.QUEUE_ADD, null);

                if (matches.get(0).equals("Facebook")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/CoopeBoaPaz/"));
                    startActivity(intent);
                }

                if (matches.get(0).equals("Instagram")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/boapaz/"));
                    startActivity(intent);
                }

                if (matches.get(0).equals("página oficial")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://boapaz.com/"));
                    startActivity(intent);
                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


        findViewById(R.id.btn_listener).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();

                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

                        break;
                }
                return false;
            }
        });



        btn_web_oficial.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String mensaje = "Página oficial";

                isFacebook = false;
                isInstagram = false;
                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isWeb) {
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isWeb = true;
                    } else {

                        //Uri.parse permite tomar una URL y transformarla para que un Intent pueda usarla.
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://boapaz.com/"));
                        startActivity(intent);
                        isWeb = false;
                    }


                }
            }
        });

        btn_facebook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

//En algunos casos es necesario escribir mal las palabras, principalmente si son de otro idioma al seleccionado en el Text to Speech
//para que la pronunciación sea la correcta.
                String mensaje = "Feisbuk";


                isInstagram = false;
                isWeb = false;

                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isFacebook) {
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isFacebook = true;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/CoopeBoaPaz/"));
                        startActivity(intent);
                        isFacebook = false;
                    }
                }
            }
        });

        btn_instagram.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String mensaje = "Instagrám";


                isFacebook = false;
                isWeb = false;

                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isInstagram) {
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isInstagram = true;
                    } else {



                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/boapaz/"));
                        startActivity(intent);
                        isInstagram = false;
                    }
                }
            }

        });

        btn_audionews.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            String narrator="";

                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isAudioNes) {
                        narrator = "Escuchar noticias";
                        repeatTTS.speak(narrator, TextToSpeech.QUEUE_ADD, null);
                        isAudioNes=true;
                    } else {
                        narrator = "Ha elegido escuchar noticias";
                        repeatTTS.speak(narrator, TextToSpeech.QUEUE_ADD, null);
                        Intent intent = new Intent(v.getContext(), AudioNews.class);
                        startActivity(intent);
                        isAudioNes=false;
                    }
                }
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String narrator="";

                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isAbout) {
                        narrator = "Acerca de BoaPaz";
                        repeatTTS.speak(narrator, TextToSpeech.QUEUE_ADD, null);
                        isAbout=true;
                    } else {
                        narrator = "Ha eligido acerca de BoaPaz";
                        repeatTTS.speak(narrator, TextToSpeech.QUEUE_ADD, null);
                        Intent intent = new Intent(v.getContext(), About_Activity.class);
                        startActivity(intent);
                        isAbout=false;
                    }
                }
            }
        });

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

}
