package br.ufc.quixada.concorrencia;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private ImageView filosofo1, filosofo2, filosofo3, filosofo4, filosofo5;
    private TextView txtLog;
    private JantarDosFilosofos jantarDosFilosofos;

    public final Handler handler = new Handler();

    public final CustomRunnable filosofosComendo[] = new CustomRunnable[JantarDosFilosofos.QTD_FILOSOFOS];
    public final CustomRunnable filosofosPensando[] = new CustomRunnable[JantarDosFilosofos.QTD_FILOSOFOS];
    public final CustomRunnable filosofosFaminto[] = new CustomRunnable[JantarDosFilosofos.QTD_FILOSOFOS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filosofo1 = findViewById(R.id.filosofo1);
        filosofo2 = findViewById(R.id.filosofo2);
        filosofo3 = findViewById(R.id.filosofo3);
        filosofo4 = findViewById(R.id.filosofo4);
        filosofo5 = findViewById(R.id.filosofo5);

        txtLog = findViewById(R.id.txtLog);

        findViewById(R.id.btnIniciar).setOnClickListener(this);
        findViewById(R.id.btnParar).setOnClickListener(this);

        for(int i=0; i< JantarDosFilosofos.QTD_FILOSOFOS; i++)
            filosofosComendo[i] = new CustomRunnable(Filosofo.COMENDO, i);

        for(int i=0; i< JantarDosFilosofos.QTD_FILOSOFOS; i++)
            filosofosPensando[i] = new CustomRunnable(Filosofo.PENSANDO, i);

        for(int i=0; i< JantarDosFilosofos.QTD_FILOSOFOS; i++)
            filosofosFaminto[i] = new CustomRunnable(Filosofo.FAMINTO, i);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnIniciar){
            jantarDosFilosofos = new JantarDosFilosofos(this);
        }else if(id == R.id.btnParar){
            jantarDosFilosofos.stop();
        }
    }

    private void updateUI(int filosofoId, int estado){
        if(estado == Filosofo.COMENDO){
            if(filosofoId == 0)
                filosofo1.setImageResource(R.drawable.filosofo_1_comendo);
            else if(filosofoId == 1)
                filosofo2.setImageResource(R.drawable.filosofo_2_comendo);
            else if(filosofoId == 2)
                filosofo3.setImageResource(R.drawable.filosofo_3_comendo);
            else if(filosofoId == 3)
                filosofo4.setImageResource(R.drawable.filosofo_4_comendo);
            else
                filosofo5.setImageResource(R.drawable.filosofo_5_comendo);

            txtLog.setText(JantarDosFilosofos.filosofos[filosofoId].getName() + " está COMENDO");
        }else if(estado == Filosofo.FAMINTO){
            if(filosofoId == 0)
                filosofo1.setImageResource(R.drawable.filosofo_1_faminto);
            else if(filosofoId == 1)
                filosofo2.setImageResource(R.drawable.filosofo_2_faminto);
            else if(filosofoId == 2)
                filosofo3.setImageResource(R.drawable.filosofo_3_faminto);
            else if(filosofoId == 3)
                filosofo4.setImageResource(R.drawable.filosofo_4_faminto);
            else
                filosofo5.setImageResource(R.drawable.filosofo_5_faminto);

            txtLog.setText(JantarDosFilosofos.filosofos[filosofoId].getName() + " está FAMINTO");
        }else if(estado == Filosofo.PENSANDO){
            if(filosofoId == 0)
                filosofo1.setImageResource(R.drawable.filosofo_1_pensando);
            else if(filosofoId == 1)
                filosofo2.setImageResource(R.drawable.filosofo_2_pensando);
            else if(filosofoId == 2)
                filosofo3.setImageResource(R.drawable.filosofo_3_pensando);
            else if(filosofoId == 3)
                filosofo4.setImageResource(R.drawable.filosofo_4_pensando);
            else
                filosofo5.setImageResource(R.drawable.filosofo_5_pensando);

            txtLog.setText(JantarDosFilosofos.filosofos[filosofoId].getName() + " está PENSANDO");
        }


    }

    public class CustomRunnable implements Runnable{

        private int estado;
        private int filosofo;

        public CustomRunnable(int estado, int filosofo) {
            this.estado = estado;
            this.filosofo = filosofo;
        }

        @Override
        public void run() {
            updateUI(estado, filosofo);
        }
    }

}
