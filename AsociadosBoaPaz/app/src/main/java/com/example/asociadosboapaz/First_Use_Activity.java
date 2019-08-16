package com.example.asociadosboapaz;


import android.content.Intent;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class First_Use_Activity extends AppCompatActivity {

TextToSpeech repeatTTS;
Button btn_woman_gender, btn_man_gender, btn_visual_assistance, btn_audible_assistance, btn_motor_assistance, btn_registry;
//Las variables booleanas permitirán saber si un botón ha sido presionado anteriormente, para emitir primero un mensaje de voz y
//la segunda vez que sea pulsado, realizará la acción propia del botón.
boolean isWoman = false, isMan = false, isVisual=false, isAudible=false, isMotor=false, isRegistry=false;
String gender="", disability="Ninguna";
UserData user;

SQLite database = new SQLite(this, "AsociadosBoaPaz", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first__use_);

        btn_woman_gender = findViewById(R.id.btn_woman_gender);
        btn_man_gender = findViewById(R.id.btn_man_gender);
        btn_visual_assistance = findViewById(R.id.btn_visual_assistance);
        btn_audible_assistance = findViewById(R.id.btn_audible_assistance);
        btn_motor_assistance = findViewById(R.id.btn_motor_assistance);
        btn_registry = findViewById(R.id.btn_registry);


        //Permite acceder al servicio de vibración del dispositivo móvil.
        final Vibrator vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

        //Este try-catch busca si existe un usuario ya registrado, evitando que colapse la aplicación si el resultado es nulo.
        try {
            user = database.SelectUserByID(1);
            Toast message = Toast.makeText(getApplicationContext(), user.id, Toast.LENGTH_LONG);
            message.show();
        } catch (Exception e) {

        }
        //Condición lógica que determina si el usuario existe o no, por defecto el primer usuario creado tendrá id=1
        if (user.id==0){

        final String mensaje = "Bienvenido a la aplicación móvil de BoaPaz, esta pantalla solo aparecerá esta vez, para generar un perfil adecuado para el usuario,  ,"+
                    "A continuación, deberá especificar su género y que tipo de discapacidad posee, en caso de necesitar asistencia, puede pulsar los botones una vez por"+
                    "botón para escuchar que acción realiza cada uno";


            repeatTTS = new TextToSpeech(this,  new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {

                    //La variable Locale permite elegir un idioma específico para el narrador de texto.
                    Locale locSpanish = new Locale("spa", "MEX");
                    int result = repeatTTS.setLanguage(locSpanish);
                    repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);


                }

            });


        }else if (user.disability.equals("visual")){

//Las Flag permiten borrar las actividades creadas anteriormente, de manera que el usuario no pueda volver a esta actividad.
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


        btn_woman_gender.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String mensaje;

                isMan=false;
                isVisual=false;
                isAudible=false;
                isMotor=false;
                isRegistry=false;

//repeatTTS.isSpeaking() permite saber si el narrador de voz aún está leyendo el texto, de manera que esta condición solo es falsa
//cuando el narrador ha terminado de leer el texto. Esto evita que el usuario ponga en cola varios textos para el narrador simultáneamente.
            if (repeatTTS.isSpeaking()){

            }else{
                //El vibrador del dispositivo debe ser configurado en milisegundos, que indican el tiempo que durará vibrando.
                vibrator.vibrate(300);
                if (!isWoman){


                    mensaje = "Género Femenino";
                    repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                    isWoman = true;
                }else{
                    mensaje = "Ha elegido Género Femenino";
                    gender="femenino";
                    repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                    isWoman = false;
                }
            }

            }
        });

        btn_man_gender.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String mensaje;


                isWoman = false;
                isVisual = false;
                isAudible = false;
                isMotor = false;
                isRegistry = false;
                if (repeatTTS.isSpeaking()) {

                } else {
                    //El vibrador del dispositivo debe ser configurado en milisegundos, que indican el tiempo que durará vibrando.
                    vibrator.vibrate(300);
                    if (!isMan) {
                        mensaje = "Género Masculino";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isMan = true;
                    } else {
                        mensaje = "Ha elegido Género Masculino";
                        gender = "masculino";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isMan = false;
                    }


                }
            }
        });

        btn_visual_assistance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String mensaje;


                isMan=false;
                isWoman=false;
                isAudible=false;
                isMotor=false;
                isRegistry=false;
                if (repeatTTS.isSpeaking()){

                }else {
                    //El vibrador del dispositivo debe ser configurado en milisegundos, que indican el tiempo que durará vibrando.
                    vibrator.vibrate(300);
                    if (!isVisual) {
                        mensaje = "Discapacidad Visual";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isVisual = true;
                    } else {
                        mensaje = "Ha elegido Discapacidad Visual";
                        disability = "visual";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isVisual = false;
                    }


                }
            }
        });

        btn_audible_assistance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String mensaje;


                isMan=false;
                isWoman=false;
                isVisual=false;
                isMotor=false;
                isRegistry=false;

                if (repeatTTS.isSpeaking()){

                }else {
                    //El vibrador del dispositivo debe ser configurado en milisegundos, que indican el tiempo que durará vibrando.
                    vibrator.vibrate(300);
                    if (!isAudible) {
                        mensaje = "Discapacidad Audible";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isAudible = true;
                    } else {
                        mensaje = "Ha elegido Discapacidad Audible";
                        disability = "audible";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isAudible = false;
                    }

                }

            }
        });

        btn_motor_assistance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String mensaje;


                isMan=false;
                isWoman=false;
                isVisual=false;
                isAudible=false;
                isRegistry=false;
                if (repeatTTS.isSpeaking()){

                }else {
                    //El vibrador del dispositivo debe ser configurado en milisegundos, que indican el tiempo que durará vibrando.
                    vibrator.vibrate(300);
                    if (!isMotor) {
                        mensaje = "Discapacidad Motora";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isMotor = true;
                    } else {
                        mensaje = "Ha elegido Discapacidad Motora";
                        disability = "motora";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isMotor = false;
                    }


                }
            }
        });

        btn_registry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String mensaje ;


                isMan=false;
                isWoman=false;
                isVisual=false;
                isAudible=false;
                isMotor=false;

                if (repeatTTS.isSpeaking()){

                }else {
                    //El vibrador del dispositivo debe ser configurado en milisegundos, que indican el tiempo que durará vibrando.
                    vibrator.vibrate(300);
                    if (!isRegistry) {
                        mensaje = "Registrar Datos";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isRegistry = true;
                    } else {
                        if (gender.equals("")) {
                            mensaje = "Debe elegir al menos un género";
                            repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        } else {

                            database.InsertUsers(gender, disability);
                            mensaje = "Datos registrados con éxito";
                            repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);

                            //Dependiendo la discapacidad elegida, se cargará una actividad diferente, adaptada al tipo de perfil elegido.
                            if( disability.equals( "visual")){
                                Intent intent = new Intent(v.getContext(), AudioNews.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                isRegistry = false;
                            }else{
                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                isRegistry = false;
                            }


                        }

                    }

                }

            }
        });

    }


}




