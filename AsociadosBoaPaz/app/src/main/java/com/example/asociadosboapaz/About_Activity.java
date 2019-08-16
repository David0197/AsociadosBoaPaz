package com.example.asociadosboapaz;

import android.content.Intent;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.Locale;

public class About_Activity extends AppCompatActivity {
    String mensaje="Contacto de BoaPaz Teléfono: 83 53 25 37.  Correo electrónico: infoboapaz@yimeil.com., Feisbook: BoaPaz Coop. Twiter: @paz_boa.  Instagram: BoaPaz." ;
    boolean isMenu = false;
    Button btn_menu;
    TextToSpeech repeatTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_);

        btn_menu = findViewById(R.id.btn_menu);
        final Vibrator vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

        repeatTTS = new TextToSpeech(this,  new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {

                Locale locSpanish = new Locale("spa", "MEX");
                int result = repeatTTS.setLanguage(locSpanish);
                repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);


            }

        });

        btn_menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String narrator="";

                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isMenu) {
                        narrator = "Volver al menú principal";
                        repeatTTS.speak(narrator, TextToSpeech.QUEUE_ADD, null);
                        isMenu=true;
                    } else {
                        narrator = "Ha vuelto al menú principal";
                        repeatTTS.speak(narrator, TextToSpeech.QUEUE_ADD, null);
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                        isMenu=false;
                    }
                }
            }
        });

    }
}
