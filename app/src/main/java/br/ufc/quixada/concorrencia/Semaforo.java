package br.ufc.quixada.concorrencia;

import android.util.Log;

public class Semaforo {

    private static final String TAG = "Filosofo";

    protected int contador;

    public Semaforo(){
        this.contador = 0;
    }

    public Semaforo(int contador){
        this.contador = contador;
    }

    public synchronized void decrementar(){
        while(this.contador == 0){
            try{
                wait();
            }catch (InterruptedException ex){
                Log.e(TAG, "Erro ao decrementar o valor do semaforo: " + ex.getMessage() );
            }
        }

        this.contador--;
    }

    public synchronized void incrementar(){
        this.contador++;
        notify();
    }

}
