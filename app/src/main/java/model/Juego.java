package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import co.edu.uelboesque.memory.R;
import database.Consultas;
import database.UsuarioSQLiteHelper;

/**
 * Created by danie on 25/11/2017.
 */

public class Juego {
    private Consultas sql;
    private Context context;
    private ArrayList<Pintura> pinturas;

    public Juego(Context context) {
        //RELACIÓN CON LA BASE DE DATOS (CONSULTAS) QUE REALIZAN LA CONSULTAS EN ESTA.
        sql = new Consultas(context);

        this.context = context;
        pinturas = new ArrayList<>();
    }

    /**
     * DAR PINTURA
     * MÉTODO QUE RETORNA EL ARRAYLIST DE PINTURAS
     * @return ARRAYLIST<PINTURA>
     */
    public ArrayList<Pintura> darPintura() {
        return pinturas;
    }

    /**
     * MÉTODO PINTAR
     * MÉTODO QUE RECIBE POR PARÁMETRO LAS POSICIONES (X,Y) A LAS CUALES VA A PINTAR.
     * SI LA PINTURA EXISTE -> ELIMITA LA POSICIÓN ENCONTRADA.
     * SI LA PINTURA NO EXISTE -> AGREGA EN LA ÚLTIMA POSICIÓN LAS COORDENADAS EN EL ARREGLO.
     * @param posX
     * @param posY
     * @return SI SE PINTÓ O NO
     */
    public boolean pintar(int posX, int posY) {
        boolean posible = true;
      for(int i=0; i<pinturas.size();i++){
          if( pinturas.get(i)!=null &&pinturas.get(i).darPosicionX()==posX && pinturas.get(i).darPosicionY()==posY){
              posible=false;
              pinturas.remove(i);
          }
      }
        if (posible == true) {
            Pintura temp = new Pintura(posX, posY);
            pinturas.add(temp);
        }
        return posible;
    }

    /**
     * MÉTODO FINALIZAR PARTIDA ACTUAL
     * MÉTODO QUE RECIBE COMO PARÁMETRO EL NIVEL ACTUAL, CON BASE A ESTE REALIZA UNA CONSULTA EN LA BASE DE DATOS (RECIBIENDO UN JSON) Y ASÍ OBTENEMOS LA PUNTUACIÓN FINAL TENIENDO EN CUENTA LAS SIMILITUDES EN (X,Y).
     * @param lvlActual
     * @return
     */
    public String finalizarPartidaActual(int lvlActual){
        int puntuacionFinal = 0;
        ArrayList<Pintura> pinturaTemp = new ArrayList<>();
        try {
            JSONObject sqlJsonObject = new JSONObject();
            JSONArray sqlJsonArray = sql.consultarDatos("nivel"+lvlActual);
            for (int i=0; i<sqlJsonArray.length();i++){
                sqlJsonObject = sqlJsonArray.getJSONObject(i);
                Pintura temp = new Pintura(Integer.parseInt(sqlJsonObject.getString("posX").trim()),Integer.parseInt(sqlJsonObject.getString("posY").trim()));
                pinturaTemp.add(temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i=0; i<pinturas.size();i++){
        for(int j=0; j<pinturaTemp.size();j++){
         if(pinturas.get(i).darPosicionX()==pinturaTemp.get(j).darPosicionX() && pinturas.get(i).darPosicionY()==pinturaTemp.get(j).darPosicionY()){
             puntuacionFinal ++;
             }
         }
        }
        return "Puntuación " + puntuacionFinal +"/"+ pinturaTemp.size();
    }

    /**
     * MÉTODO REINICIAR JUEGO
     * MÉTODO QUE SE ENCARGA DE LIMPIAR EL ARREGLO DE PINTURAS.
     */
    public void reiniciarJuego() {
       pinturas.clear();
    }

    /**
     * MÉTODO 'VERIFY'
     * MÉTODO QUE SE ENCARGA QUE COMPROBAR SI ES POSIBLE EL MOVIMIENTO (DER,IZQ,ARR,ABJ) CON BASE A UNOS INVERTALOS ESTABLECIDOS.
     * LA MATRÍZ DEL CANVAS ES DE 8X8 DE ESTA FORMA EL INVERVALO MÁXIMO SERÁ 8 Y EL MÍNIMO SERÁ 0
     * @param pCase
     * @param posX
     * @param posY
     * @return es posible o no el movimiento.
     */
   public boolean verify(int pCase,int posX, int posY){
       boolean valid = false;
    switch (pCase){
        case 0:
            if(posX  >=7){
                valid = true;
            }
            break;

        case 1:
            if(posX <=0){
                valid = true;
            }
            break;

        case 2:
            if(posY <= 0){
                valid = true;
            }
            break;

        case 3:
            if(posY >= 7){
                valid = true;
            }
            break;
    }

    return valid;
   }

    /**
     * MÉTODO GUARDAR NIVELES BASE DE DATOS
     * MÉTODO QUE CON BASE AL ARREGLO DE PINTURAS, GUARDA LAS POSICIONES (X,Y), DE CADA PINTURA EN UN ARREGLO JSON EN LA BASE DE DATOS.
     * @param pNivelNombre nombre del nivel creado.
     */
   public void guardarNivelBaseDeDatos(String pNivelNombre) {
       JSONArray jsonArray = new JSONArray();
       JSONObject jsonObject = null;
       for (int i = 0; i < pinturas.size(); i++) {
           jsonObject = new JSONObject();
           try {
               jsonObject.put("name", pNivelNombre);
               jsonObject.put("posX", pinturas.get(i).darPosicionX());
               jsonObject.put("posY", pinturas.get(i).darPosicionY());
               jsonArray.put(jsonObject);
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }
       sql.insertarConsulta(pNivelNombre, jsonArray);
   }

    /**
     * MÉTODO BUSCAR NIVEL
     * MÉTODO QUE CONSUTA EN LA BASE DE DATOS UN NIVEL CON EL NOMBRE RECIBIDO POR PARÁMETRO.
     * @param pNivelNombre nombre del nivel a guardar.
     */
   public void buscarNivel(String pNivelNombre){
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = sql.consultarDatos(pNivelNombre);
            if(jsonArray!= null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    Pintura temp = new Pintura(Integer.parseInt(jsonObject.getString("posX").trim()), Integer.parseInt(jsonObject.getString("posY").trim()));
                    pinturas.add(temp);
                }
            }else{
                Toast.makeText(context,"No se pudo cargar la partida.",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}