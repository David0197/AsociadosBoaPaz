package com.example.asociadosboapaz;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Objects;

public class AudioNews extends AppCompatActivity {

    String  mensaje="A continuación tiene disponible un panel donde puede elegir el día, mes y año de la noticia que desee escuchar, cada panel tiene su propia instrucción" ;
    boolean isDia_antes = false,  isDia_despues=false, isMes_antes=false, isMes_despues=false,  isYear_antes=false, isYear_despues=false,  isEscuchar_noticias=false,  isVolver_audio_news_activity=false;
    Button btn_dia_antes, btn_dia, btn_dia_despues, btn_mes_antes, btn_mes, btn_mes_despues, btn_year_antes, btn_year, btn_year_despues, btn_escuchar_noticias, btn_volver_audio_news_activity;
    File documentos;
    int numero_dia=28, numero_mes=2, numero_year=2016;
    TextToSpeech repeatTTS;

    private long downloadID;

    //Método que compara el ID recibido con el de la descarga, para confirmar que el archivo se ha descargado con éxito.
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (downloadID == id) {

                try {

                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"noticias.txt");

                    StringBuilder text = new StringBuilder();

                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');

                    }

                    br.close();

                    repeatTTS.speak(text.toString(), TextToSpeech.QUEUE_ADD, null);


                } catch (IOException ex) {

                    Toast  message = Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG);
                    message.show();

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_news);

        btn_dia_antes = findViewById(R.id.btn_dia_antes);
        btn_dia = findViewById(R.id.btn_dia);
        btn_dia_despues = findViewById(R.id.btn_dia_despues);
        btn_mes_antes = findViewById(R.id.btn_mes_antes);
        btn_mes = findViewById(R.id.btn_mes);
        btn_mes_despues = findViewById(R.id.btn_mes_despues);
        btn_year_antes = findViewById(R.id.btn_year_antes);
        btn_year = findViewById(R.id.btn_year);
        btn_year_despues = findViewById(R.id.btn_year_despues);
        btn_escuchar_noticias = findViewById(R.id.btn_escuchar_noticias);
        btn_volver_audio_news_activity = findViewById(R.id.btn_volver_audio_news_activity);

        final Vibrator vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);


        repeatTTS = new TextToSpeech(this,  new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {

                Locale locSpanish = new Locale("spa", "MEX");
                int result = repeatTTS.setLanguage(locSpanish);
                repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);


            }

        });



        btn_dia_antes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isDia_antes) {
                        mensaje = "Elegir un día anterior";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isDia_antes = true;
                    } else if (numero_dia<2) {

                        repeatTTS.speak("No puede elegir un día menor a " + numero_dia, TextToSpeech.QUEUE_ADD, null);

                    } else {

                        numero_dia += -1;
                        repeatTTS.speak("Ha elegido el día " + numero_dia, TextToSpeech.QUEUE_ADD, null);

                    }

                }
            }
        });

        btn_dia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    mensaje = "Seleccione el día deseado con los botones a la izquierda y derecha de este botón";
                    repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);

                }
            }
        });


        btn_dia_despues.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isDia_despues) {
                        mensaje = "Elegir un día posterior";

                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isDia_despues = true;

                    } else {

                        switch(numero_mes){

                            case 1-3-5-7-8-10-12:

                                if (numero_dia>30) {
                                    mensaje = "No puede elegir un día mayor a "+numero_dia;

                                    repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);


                                } else {

                                    numero_dia += 1;
                                    repeatTTS.speak("Ha elegido el día " + numero_dia, TextToSpeech.QUEUE_ADD, null);

                                }

                                break;
                            case 2:

                                if (numero_dia>28) {
                                    mensaje = "No puede elegir un día mayor a "+numero_dia;

                                    repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);



                                } else {

                                    numero_dia += 1;
                                    repeatTTS.speak("Ha elegido el día " + numero_dia, TextToSpeech.QUEUE_ADD, null);

                                }
                                break;
                            case 3-6-9-11:

                                if (numero_dia>29) {
                                    mensaje = "No puede elegir un día mayor a "+numero_dia;

                                    repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);



                                } else {

                                    numero_dia += 1;
                                    repeatTTS.speak("Ha elegido el día " + numero_dia, TextToSpeech.QUEUE_ADD, null);

                                }
                                break;
                            default:
                                break;
                        }

                    }

                }
            }
        });


        btn_mes_antes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isMes_antes) {
                        mensaje = "Elegir un mes anterior";

                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isMes_antes = true;
                    } else if (numero_mes<2) {

                        repeatTTS.speak("No puede elegir un mes menor a " + numero_mes, TextToSpeech.QUEUE_ADD, null);

                    } else {

                        numero_mes += -1;
                        repeatTTS.speak("Ha elegido el mes " + numero_mes, TextToSpeech.QUEUE_ADD, null);

                    }
                }
            }
        });


        btn_mes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);

                    mensaje = "Seleccione el mes deseado con los botones a la izquierda y derecha de este botón";
                    repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                }



            }
        });


        btn_mes_despues.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isMes_despues) {
                        mensaje = "Elegir un mes posterior";

                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isMes_despues = true;
                    } else if (numero_mes>11) {

                        repeatTTS.speak("No puede elegir un mes mayor a " + numero_mes, TextToSpeech.QUEUE_ADD, null);

                    } else {

                        numero_mes += 1;
                        repeatTTS.speak("Ha elegido el mes " + numero_mes, TextToSpeech.QUEUE_ADD, null);

                    }

                }
            }
        });


        btn_year_antes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isYear_antes) {
                        mensaje = "Elegir un año anterior";

                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isYear_antes = true;
                    } else if (numero_year<2017) {

                        repeatTTS.speak("No puede elegir un año menor a " + numero_year, TextToSpeech.QUEUE_ADD, null);

                    } else {

                        numero_year += -1;
                        repeatTTS.speak("Ha elegido el año " + numero_year, TextToSpeech.QUEUE_ADD, null);

                    }
                }
            }
        });


        btn_year.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    mensaje = "Seleccione el añp deseado con los botones a la izquierda y derecha de este botón";
                    repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                }


            }
        });


        btn_year_despues.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isYear_despues) {
                        mensaje = "Elegir un año posterior";

                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isYear_despues = true;
                    } else if (numero_year > 2019) {
                        repeatTTS.speak("No existen registros de años posteriores a "+numero_year, TextToSpeech.QUEUE_ADD, null);

                    } else {
                        numero_year += 1;
                        repeatTTS.speak("Ha elegido el año " + numero_year, TextToSpeech.QUEUE_ADD, null);
                    }

                }
            }
        });


        btn_escuchar_noticias.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isEscuchar_noticias) {
                        mensaje = "Escuchar Noticias";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isEscuchar_noticias = true;
                    } else {

                        downloadID = DownloadData(Uri.parse("https://okenos.000webhostapp.com/noticias.txt"), view);

                        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));



                    }

                }
            }
        });


        btn_volver_audio_news_activity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                if (repeatTTS.isSpeaking()) {

                } else {
                    vibrator.vibrate(300);
                    if (!isVolver_audio_news_activity) {
                        mensaje = "Volver al menú principal";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        isVolver_audio_news_activity=true;
                    } else {
                        mensaje = "Ha vuelto al menú principal";
                        repeatTTS.speak(mensaje, TextToSpeech.QUEUE_ADD, null);
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                        isVolver_audio_news_activity=false;
                    }
                }
            }
        });



    }




    // Método que borra el registro de descargas
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }


    //Este método se encarga de descargar las noticias elegidas por el usuario, en formato txt. Las noticias tendrán un formato de nombre
    //que será dd/mm/aaaa, d=día, m=mes, a=año, para realizar la búsqueda por medio de un algoritmo.
    private long DownloadData (Uri uri, View v) {

        long downloadReference;

        // Create request for android download manager
        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle("Data Download");

        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        //Set the local destination for the downloaded file to a path
        //within the application's external files directory

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"noticias.txt");

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);


        return downloadReference;
    }

}
