package br.ufc.quixada.concorrencia;

import android.util.Log;

public class Filosofo extends Thread {

    private static final String TAG = "Filosofo";

    public static final int PENSANDO = 0;
    public static final int FAMINTO = 1;
    public static final int COMENDO = 2;

    private int id;
    private MainActivity mainActivity;

    public Filosofo(String nome, int id, MainActivity mainActivity){
        super(nome);
        this.id = id;
        this.mainActivity = mainActivity;
    }

    public void comFome(){
        JantarDosFilosofos.estado[this.id] = FAMINTO;
        mainActivity.handler.post(mainActivity.filosofosFaminto[this.id]);
        Log.i(TAG, "O filosofo " + getName() + " está FAMINTO");
    }

    public void come(){
        JantarDosFilosofos.estado[this.id] = COMENDO;
        Log.i(TAG, "O filosofo " + getName() + " está COMENDO");

        mainActivity.handler.post(mainActivity.filosofosComendo[this.id]);

        try{
            Thread.sleep(1000L);
        }catch (InterruptedException ex){
            Log.e(TAG, "Ocorreu um erro enquanto o filosofo "+ getName() + "estava comendo: " + ex.getMessage() );
        }
    }

    public void pensa(){
        JantarDosFilosofos.estado[this.id] = PENSANDO;

        mainActivity.handler.post(mainActivity.filosofosPensando[this.id]);
        Log.i(TAG, "O filosofo " + getName() + " está PENSANDO");

        try{
            Thread.sleep(1000L);
        }catch (InterruptedException ex){
            Log.e(TAG, "Ocorreu um erro enquanto o filosofo "+ getName() + "estava pensando: " + ex.getMessage() );
        }
    }

    public void largarGarfo(){
        JantarDosFilosofos.mutex.decrementar();

        pensa();

        JantarDosFilosofos.filosofos[getVizinhoEsquerda()].tentarObterGarfos();
        JantarDosFilosofos.filosofos[getVizinhoDireita()].tentarObterGarfos();

        JantarDosFilosofos.mutex.incrementar();
    }

    public void pegarGarfo(){
        JantarDosFilosofos.mutex.decrementar();

        comFome();

        tentarObterGarfos();

        JantarDosFilosofos.mutex.incrementar();

        JantarDosFilosofos.semaforos[this.id].decrementar();
    }

    public void tentarObterGarfos(){
        if(JantarDosFilosofos.estado[this.id] == FAMINTO &&
            JantarDosFilosofos.estado[getVizinhoEsquerda()] != COMENDO &&
            JantarDosFilosofos.estado[getVizinhoDireita()] != COMENDO){

            come();
            JantarDosFilosofos.semaforos[this.id].incrementar();
        }
    }


    public int getVizinhoEsquerda(){
        if(this.id == 0)
            return JantarDosFilosofos.QTD_FILOSOFOS - 1;

        return (this.id - 1) % JantarDosFilosofos.QTD_FILOSOFOS;
    }

    public int getVizinhoDireita(){
        return (this.id + 1) % JantarDosFilosofos.QTD_FILOSOFOS;
    }

    @Override
    public void run() {
        try{
            pensa();

            do{
                pegarGarfo();
                Thread.sleep(1000L);
                largarGarfo();
            }while(true);

        }catch (InterruptedException ex){
            Log.e(TAG, "Erro na execução do metodo run: " + ex.getMessage() );
            return;
        }
    }
}
