package softwaredesign.mx.threadexampleandroid;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by upam on 16/02/17.
 */

public class ThreadFactorial extends Thread {

    private Handler handler;

    public String numero;

    public ThreadFactorial(String numero) {
        this.setName("Hilo Calcula Factorial");

        this.numero = numero;
    }

    @Override
    public void run() {
        numero=calcularFacto(numero);
        Message message=new Message();
        message.what=1;
        message.obj=numero;
        handler.sendMessage(message);
    }

    /***
     * Metodo que calcula un numero fatorial
     * @param dato en cadena
     * @return regresa una cadena
     */
    private String calcularFacto(String dato){
        int valor=Integer.parseInt(dato);
        return String.valueOf(factorial(valor));
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

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
