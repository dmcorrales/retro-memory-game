package model;

/**
 * Created by danie on 25/11/2017.
 */

public class Pintura {
    /**
     * ATRIBUTOS
     */

    //POSICIÓN X DE LA PINTURA EN COORDENADAS
    private int posX;
    //POSICIÓN Y DE LA PINTURA EN COORDENADAS
    private int posY;

    /**
     * MÉTODO CONSTRUCTOR DE LA CLASE PINTURA
     */
    public Pintura(int pPosX, int pPosY) {
        posX = pPosX;
        posY = pPosY;
    }

    //RETORNA LA POSICIÓN X DE LA PINTURA
    public int darPosicionX(){
        return posX;
    }

    // CAMBIA LA POSICIÓN X DE LA PINTURA
    public void cambiarPosicionX(int n){
        posX = n;
    }

    // RETORNA LA POSICION Y DE LA PINTURA
    public int darPosicionY(){
        return posY;
    }

    // CAMBIA LA POSICIÓN Y DE LA PINTURA
    public void cambiarPosicionY(int n){
        posY = n;
     }

}
