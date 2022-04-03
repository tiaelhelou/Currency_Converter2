package com.csc498_lau.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity2 extends AppCompatActivity {

    TextView amount_to_convert;
    EditText amount;
    Boolean get_method = false;
    String usd;
    String lbp;

    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... arg0) {

            if (get_method == false) {
                try {
                    String amount_lbp = (String) arg0[0];
                    String amount_usd = (String) arg0[1];

                    String link = "http://192.168.1.7/Currency_Converter/get_amount.php";
                    String data = URLEncoder.encode("lbp", "UTF-8") + "=" +
                            URLEncoder.encode(amount_lbp, "UTF-8");
                    data += "&" + URLEncoder.encode("usd", "UTF-8") + "=" +
                            URLEncoder.encode(amount_usd, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (get_method == true){
                String result = "";
                URL url;
                HttpURLConnection http;

                try {
                    url = new URL(arg0[0]);
                    http = (HttpURLConnection) url.openConnection();

                    InputStream in = http.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int data = reader.read();

                    while (data != -1) {
                        char current = (char) data;
                        result += current;
                        data = reader.read();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

                return result;
            }
            return null;
        }

        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try{
                JSONObject json = new JSONObject(s);

                if (usd == "0"){
                    String converted_amount_usd = json.getString("converted_amount_usd");
                    Toast.makeText(getApplicationContext(), converted_amount_usd, Toast.LENGTH_LONG).show();
                }
                else if (usd != "0"){
                    String converted_amount_lbp = json.getString("converted_amount_lbp");
                    Toast.makeText(getApplicationContext(), converted_amount_lbp, Toast.LENGTH_LONG).show();
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        amount_to_convert = (TextView) findViewById(R.id.textView2);
        amount = (EditText) findViewById(R.id.editTextTextPersonName);

        getSupportActionBar().hide();
    }

    public void goToMain (View view) {

        Intent obj = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(obj);
    }

    public void fadeAmountToConvert(View view) {
        if(amount_to_convert.getText().toString().equals("The amount LBP or USD")){
            amount_to_convert.setText("");
        }
    }

    public void convertToLbp (View view){

        usd = amount.getText().toString();
        lbp = "0";

        get_method = false;

        DownloadTask task = new DownloadTask();
        task.execute(usd, lbp);

        String url = "http://192.168.1.7/Currency_Converter/get_result.php";

        get_method = true;

        DownloadTask task1 = new DownloadTask();
        task1.execute(url);

    }

    public void convertToUsd (View view){

        lbp = amount.getText().toString();
        usd = "0";

        get_method = false;

        DownloadTask task = new DownloadTask();
        task.execute(usd, lbp);


        String url = "http://192.168.1.7/Currency_Converter/get_result.php";

        get_method = true;

        DownloadTask task1 = new DownloadTask();
        task1.execute(url);
    }

    public void reset (View view){
        amount_to_convert.setText("The amount LBP or USD");
        amount.setText("");
        get_method = false;
    }
}