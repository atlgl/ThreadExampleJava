package softwaredesign.mx.threadexampleandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.InputStream;

//http://www.androidcurso.com/index.php/tutoriales-android/36-unidad-5-entradas-en-android-teclado-pantalla-tactil-y-sensores/271-hilos-de-ejecucion-en-android
public class MainActivity extends AppCompatActivity {

    EditText txtnumero;
    Button btncalcular;
    ImageView imagen;
    Button btnDescargarImagen;
    TextView txtTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtnumero=(EditText) findViewById(R.id.txtnumfacto);
        btncalcular=(Button) findViewById(R.id.btncalcular);
        imagen=(ImageView) findViewById(R.id.imagen);
        btnDescargarImagen=(Button) findViewById(R.id.btndescargarImagen);
        txtTexto=(TextView) findViewById(R.id.txtarchivo);

        btncalcular.setOnClickListener(clickListener);
        btnDescargarImagen.setOnClickListener(clickListenerdescargar);
    }

    Handler handler;



    Button.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //funciona pero aparece wait for process
            //txtnumero.setText(calcularFacto(txtnumero.getText().toString()));

            //funciona pero aparece wait for proceees
            // Thread.currentThread();

            final ThreadFactorial factorial=new ThreadFactorial(txtnumero.getText().toString());

            handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if(msg.what==1){
                        txtnumero.setText(factorial.getNumero());

                    }

                }
            };
            factorial.setHandler(handler);
            factorial.start();

            Toast.makeText(getBaseContext(),"Numero de hilo"+factorial.getId(),Toast.LENGTH_SHORT).show();
            //txtnumero.setText(factorial.getNumero());


            //ejecuta un hilo sobre la interfaz grafica para actualizar lo correcto es usar un handler
            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // txtnumero.setText(factorial.getNumero());
                }
            });*/


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

    Button.OnClickListener clickListenerdescargar=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DownloadImage image=new DownloadImage();
            image.execute("http://www.legionx.com.mx/imagenes/paris.jpg");
            DownloadFile file=new DownloadFile();
            file.execute("");

        }
    };

    private class DownloadFile extends AsyncTask<String,Integer,String>{


        @Override
        protected String doInBackground(String... params) {
            String urltexto="http://www.legionx.com.mx/imagenes/inglaterra.txt";
            String texto="";
            String datos="";
            URL url= null;
            try {
                url = new URL(urltexto);
                HttpURLConnection con=(HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.connect();

                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(con.getInputStream()));

                while ((texto=bufferedReader.readLine())!=null){
                    datos+=texto;
                }

                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return datos;
        }

        @Override
        protected void onPostExecute(String s) {

            txtTexto.setText(s);

           }
    }


   private class DownloadImage extends AsyncTask<String,Integer,Bitmap>{

       @Override
       protected Bitmap doInBackground(String... params) {
           String urlimage=params[0];
           try {
               URL url=new URL(urlimage);
               HttpURLConnection con=(HttpURLConnection)url.openConnection();
               con.setDoInput(true);
               con.connect();
               InputStream is=con.getInputStream();
               BitmapFactory.Options options=new BitmapFactory.Options();
               options.inPreferredConfig= Bitmap.Config.RGB_565;
               Bitmap bitmap01 = BitmapFactory.decodeStream(is,null,options);
               con.disconnect();
               return bitmap01;


           }catch (Exception ex){
               ex.printStackTrace();
               return null;
           }
       }

       @Override
       protected void onPostExecute(Bitmap bitmap) {
           imagen.setImageBitmap(bitmap);
       }
   }




}
