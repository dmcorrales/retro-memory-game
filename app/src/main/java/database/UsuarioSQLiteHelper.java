package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by danie on 01/12/2017.
 */

public class UsuarioSQLiteHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "niveles.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * MÉTODO CONSTRUCTOR CLASE SQLITEHELPER
     * Método que utilizar como referencia a super, como su paquete "SQLiteAssetHelper".
     */
    public UsuarioSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
