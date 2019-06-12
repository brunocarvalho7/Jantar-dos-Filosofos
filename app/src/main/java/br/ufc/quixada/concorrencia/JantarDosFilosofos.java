package br.ufc.quixada.concorrencia;

import android.os.Handler;

public class JantarDosFilosofos {

    public static final int QTD_FILOSOFOS = 5;

    public static int estado[] = new int[QTD_FILOSOFOS];
    public static Semaforo mutex = new Semaforo(1);
    public static Semaforo semaforos[] = new Semaforo[QTD_FILOSOFOS];
    public static Filosofo filosofos[] = new Filosofo[QTD_FILOSOFOS];

    private MainActivity mainActivity;

    public JantarDosFilosofos(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        for(int i = 0; i < QTD_FILOSOFOS; i++){
            estado[i] = 0;
        }

        filosofos[0] = new Filosofo("Platão", 0, mainActivity);
        filosofos[1] = new Filosofo("Socrates", 1, mainActivity);
        filosofos[2] = new Filosofo("Aristoteles", 2, mainActivity);
        filosofos[3] = new Filosofo("Tales", 3, mainActivity);
        filosofos[4] = new Filosofo("Sofocles", 4, mainActivity);

        for(int i = 0; i < QTD_FILOSOFOS; i++){
            semaforos[i] = new Semaforo(0);
        }

        for(int i = 0; i < QTD_FILOSOFOS; i++){
            filosofos[i].start();
        }

    }

    public void stop(){
        for(int i = 0; i < QTD_FILOSOFOS; i++){
            filosofos[i].interrupt();
        }
    }
}
