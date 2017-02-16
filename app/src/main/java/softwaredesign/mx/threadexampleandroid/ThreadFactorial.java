package softwaredesign.mx.threadexampleandroid;

import android.os.SystemClock;

/**
 * Created by upam on 16/02/17.
 */

public class ThreadFactorial extends Thread {

    public String numero;

    public ThreadFactorial(String numero) {
        this.numero = numero;
    }

    private String calcularFacto(String dato){
        int valor=Integer.parseInt(dato);
        return String.valueOf(factorial(valor));
    }

    @Override
    public void run() {

        numero=calcularFacto(numero);
    }

    /***
     * Metodo  que calcula un factorial de un numero
     * @param n
     * @return
     */
    private int factorial(int n){
        int res=1;
        for (int i=1;i<=n;i++){
            res*=i;
            SystemClock.sleep(1000);
        }
        return res;
    }

    public String getNumero() {
        return numero;
    }

    public String setNumero(String numero) {
        return this.numero = numero;
    }
}
