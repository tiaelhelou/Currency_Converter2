package com.csc498_lau.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView buy;
    TextView sell;

    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){
            String result = "";
            URL url;
            HttpURLConnection http;

            try{
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while( data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }

            return result;
        }

        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try{
                JSONObject json = new JSONObject(s);
                String buy_rate = json.getString("buy_rate");
                String sell_rate = json.getString("sell_rate");
                buy.setText("Buy 1 USD at " + buy_rate + " LBP");
                sell.setText("Sell 1 USD at " + sell_rate + " LBP");

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buy = (TextView) findViewById(R.id.textView);
        sell = (TextView) findViewById(R.id.textView3);

        getSupportActionBar().hide();
        String url = "http://192.168.1.7/Currency_Converter/get_updated_rate.php";

        DownloadTask task = new DownloadTask();
        task.execute(url);

    }

    public void goToCalculator (View view) {

        Intent obj = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(obj);
    }

    public void refresh (View view){

        String url = "http://192.168.1.7/Currency_Converter/get_updated_rate.php";

        DownloadTask task = new DownloadTask();
        task.execute(url);

    }
}