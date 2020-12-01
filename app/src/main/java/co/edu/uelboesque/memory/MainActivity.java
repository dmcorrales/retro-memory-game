package co.edu.uelboesque.memory;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnJugar, btnInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicio los botones
        this.inicializarBotones();

    }

    public void inicializarBotones() {
        btnJugar = (Button) findViewById(R.id.btnPlay);
        btnJugar.setOnClickListener(this);

        btnInformacion = (Button) findViewById(R.id.btnOptions);
        btnInformacion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlay:
                Intent i = new Intent(this, GameActivity.class);
                startActivity(i);
                    break;

            case R.id.btnOptions:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                dialog.setMessage("Daniel Corrales.\nContacto: danieldmc123@hotmail.com ");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                });

                dialog.show();
                break;
        }
    }
}
