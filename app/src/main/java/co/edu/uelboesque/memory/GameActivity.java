package co.edu.uelboesque.memory;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import model.Juego;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout layoutCanvas;
    private CanvasClass canvas;
    int lvlActual = 0;
    private Button right,left,up,down,pintar,operaciones,acciones,comprobar;
    private Chronometer cronometro;
    private final static int RIGHT = 0;
    private final static int LEFT = 1;
    private final static int UP = 2;
    private final static int DOWN = 3;


    Timer timer;

    private Juego model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Inicio Componentes
        canvas = new CanvasClass(this);
        model = new Juego(getApplicationContext());

        layoutCanvas = (LinearLayout) findViewById(R.id.layoutCanvas);
        LinearLayout.LayoutParams lparams = new     LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,     LinearLayout.LayoutParams.WRAP_CONTENT);
        this.layoutCanvas.addView(canvas,lparams);

        cronometro = (Chronometer) findViewById(R.id.chronometer3);
        //Inicio botones
        inicializarBotones();


    }
    public void inicializarTimer(){
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.stop();
    }

    public void inicializarBotones(){
        right = (Button) findViewById(R.id.right);
        right.setOnClickListener(this);

        left = (Button) findViewById(R.id.left);
        left.setOnClickListener(this);

        up = (Button) findViewById(R.id.up);
        up.setOnClickListener(this);

        down = (Button) findViewById(R.id.down);
        down.setOnClickListener(this);

        pintar = (Button) findViewById(R.id.btnPintar);
        pintar.setOnClickListener(this);

        operaciones=(Button) findViewById(R.id.btnOperaciones);
        operaciones.setOnClickListener(this);

        acciones = (Button) findViewById(R.id.btnAccion);
        acciones.setOnClickListener(this);

        comprobar = (Button) findViewById(R.id.btnComprobar);
        comprobar.setVisibility(View.INVISIBLE);
        comprobar.setOnClickListener(this);

    }

    public void guardarNivelBaseDeDatos(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingresa nombre del nivel a guardar");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                model.guardarNivelBaseDeDatos(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right:
              if(  model.verify(RIGHT,canvas.darPosicionX(),canvas.darPosicionY())==false){
                  canvas.derecha();
                  canvas.invalidate();
              }
                break;
            case R.id.left:

                if(  model.verify(LEFT,canvas.darPosicionX(),canvas.darPosicionY())==false){
                    canvas.izquierda();
                    canvas.invalidate();
                }
                break;
            case R.id.up:
                if( model.verify(UP,canvas.darPosicionX(),canvas.darPosicionY())==false){
                    canvas.arriba();
                    canvas.invalidate();
                }
                break;
            case R.id.down:
                if(  model.verify(DOWN,canvas.darPosicionX(),canvas.darPosicionY())==false){
                    canvas.abajo();
                    canvas.invalidate();
                }
                break;
            case R.id.btnPintar:
                model.pintar(canvas.darPosicionX(),canvas.darPosicionY());
                    canvas.asignarPinturas(model.darPintura());
                break;
            case R.id.btnOperaciones:
                this.guardarNivelBaseDeDatos();
                canvas.asignarPinturas(model.darPintura());
                break;
            case R.id.btnAccion:
                acciones.setVisibility(View.INVISIBLE);
               this.iniciarJuego();
                break;
            case R.id.btnComprobar:
                Toast.makeText(getApplicationContext(),model.finalizarPartidaActual(lvlActual),Toast.LENGTH_LONG).show();
                this.iniciarJuego();
                break;
        }
    }


boolean mIsStarted=false;
    int pLoader=3;
    public void iniciarJuego() {
        this.inicializarTimer();
        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                     switch (pLoader){
                         case 3:
                             model.reiniciarJuego();
                             model.buscarNivel("cargando"+pLoader);
                             canvas.asignarPinturas(model.darPintura());
                             pLoader--;
                             break;

                         case 2:
                             model.reiniciarJuego();
                             model.buscarNivel("cargando"+pLoader);
                             canvas.asignarPinturas(model.darPintura());
                             pLoader--;
                             break;

                         case 1:
                             model.reiniciarJuego();
                             model.buscarNivel("cargando"+pLoader);
                             canvas.asignarPinturas(model.darPintura());
                             pLoader--;
                             break;

                         case 0:
                             lvlActual +=1;
                             model.reiniciarJuego();
                             model.buscarNivel("nivel"+lvlActual);
                             canvas.asignarPinturas(model.darPintura());
                             pLoader--;
                             break;

                         case -1:
                             comprobar.setVisibility(View.VISIBLE);
                             model.reiniciarJuego();
                             canvas.asignarPinturas(model.darPintura());
                             timer.cancel();
                             pLoader=3;
                             cronometro.setBase(SystemClock.elapsedRealtime());
                             cronometro.stop();
                             if (!mIsStarted) {
                                 cronometro.setBase(SystemClock.elapsedRealtime());
                                 cronometro.start();
                                 mIsStarted = true;}
                             cronometro.start();
                             break;
                     }
                    }

                });
            }
        }, 1000, 3000);

    }
}
