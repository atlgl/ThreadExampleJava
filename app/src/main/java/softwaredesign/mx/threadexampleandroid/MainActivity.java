package softwaredesign.mx.threadexampleandroid;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtnumero;
    Button btncalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtnumero=(EditText) findViewById(R.id.txtnumfacto);
        btncalcular=(Button) findViewById(R.id.btncalcular);

        btncalcular.setOnClickListener(clickListener);

    }

    Button.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //funciona pero aparece wait for process
            //txtnumero.setText(calcularFacto(txtnumero.getText().toString()));

            //funciona pero aparece wair for proceees
            ThreadFactorial factorial=new ThreadFactorial(txtnumero.getText().toString());
            factorial.run();
            //ejecuta un hilo sobre la interfaz grafica para actualizar lo correcto es usar un handler
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // txtnumero.setText(factorial.getNumero());
                }
            });
        }
    };

    public String calcularFacto(String dato){
        int valor=Integer.parseInt(dato);
        return String.valueOf(factorial(valor));
    }

    /***
     * Metodo que calcula el Factorial de un numero
     * @param n
     * @return
     */
    public int factorial(int n){
        int res=1;
        for (int i=1;i<=n;i++){
            res*=i;
            SystemClock.sleep(1000);
        }
        return res;
    }


}
