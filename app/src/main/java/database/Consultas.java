package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by danie on 01/12/2017.
 */

public class Consultas {
    Context context;
    public Consultas(Context pContext){
      context=pContext;
    }

    public void insertarConsulta(String pFileName,JSONArray jsonObject){
        UsuarioSQLiteHelper usr = new UsuarioSQLiteHelper(context);
        SQLiteDatabase db = usr.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("nivel",pFileName);
        cv.put("objeto",jsonObject.toString());
        db.insert("niveles",null,cv);
        db.close();
    }

    public JSONArray consultarDatos(String pFileName){
        UsuarioSQLiteHelper usr = new UsuarioSQLiteHelper(context);

        JSONArray json = null;
        SQLiteDatabase db = usr.getWritableDatabase();
        Cursor fila = db.rawQuery("select objeto from niveles where  nivel='"+pFileName+"'",null);
        if(fila.moveToFirst()){
            try {
                 json = new JSONArray(fila.getString(0).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Log.wtf("ERROR","No se encontró la consulta.");
        }
        db.close();
        return json;
    }

}
