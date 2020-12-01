package co.edu.uelboesque.memory;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import model.Pintura;

/**
 * Created by danie on 22/11/2017.
 */

public class CanvasClass extends View {

    private ArrayList<Pintura> pinturas;
    int ancho; int largo;
    int puntoMedioX;        int puntoMedioY;
    int posX= puntoMedioX;  int posY= puntoMedioY;
    int posMartizX=0;
    int posMartizY=0;
    int tempX = 0; int tempY= 0;
    public CanvasClass(Context context) {
        super(context);
        pinturas = new ArrayList<Pintura>();
        setBackgroundColor(Color.WHITE);
    }

    public void onDraw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);

        Paint paintArray = new Paint();
        paintArray.setColor(Color.BLUE);
        paintArray.setStyle(Paint.Style.FILL);
        paintArray.setStrokeWidth(1);

        Paint paint2 = new Paint();
        paint2.setColor(Color.GRAY);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(5);

         ancho = canvas.getWidth();
         largo = canvas.getHeight();

        for(int i=0; i<ancho; i++){
            canvas.drawRect(i*ancho/8,largo,0,0,paint2);
        }

        for(int i=0; i<largo; i++){
            canvas.drawRect(ancho,i*largo/8,0,0,paint2);
        }

        for(int i=0; i<pinturas.size(); i++){
            canvas.drawCircle(pinturas.get(i).darPosicionX()*(puntoMedioX*2)+puntoMedioX,pinturas.get(i).darPosicionY()*(puntoMedioY*2)+puntoMedioY,(puntoMedioX+puntoMedioY)/2,paintArray);
        }
        puntoMedioX=(0+ancho/8)/2;  puntoMedioY=(0+largo/8)/2;
        tempX= posX+puntoMedioX;
        tempY = posY+puntoMedioY;
        canvas.drawCircle(tempX,tempY,(puntoMedioX+puntoMedioY)/2,paint);

    }

    public void asignarPinturas(ArrayList<Pintura> pPintura){
    pinturas = pPintura;
        this.invalidate();
    }

    public int darPosicionX(){return posMartizX;}
    public int darPosicionY(){return posMartizY;}
    public void derecha(){posX += puntoMedioX*2;posMartizX+=1;this.invalidate();}
    public void izquierda(){posX -= puntoMedioX*2;posMartizX-=1;this.invalidate();}
    public void arriba(){posY -=puntoMedioY*2;posMartizY-=1; this.invalidate();}
    public void abajo(){posY +=puntoMedioY*2;posMartizY+=1; this.invalidate();}


}
