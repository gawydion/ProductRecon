package com.example.marcin.upc;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
//import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;


public class ReaderActivity extends AppCompatActivity {
    // ogarnąć context - chyba aktualna aktywność
    Context context = this;

    //stworzenie nowego obiektu Product do przechowywania danych
    //TODO: dodać bundle
    public Product product = new Product("", "", "", 0);

    //przycisk do włączenia skanowania
    private Button scan_UPC;
    private Button updateUPC;
    private Button deleteUPC;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        final Activity activity = this;

        //przypisanie konkretnego przycisku
        scan_UPC = (Button) findViewById(R.id.scan_UPC);
        updateUPC = (Button) findViewById(R.id.update_btn);

        updateUPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerClient serverClient = new ServerClient(ReaderActivity.this);
                serverClient.updateUPC();
                cleanInterface();
            }
        });

        deleteUPC = (Button) findViewById(R.id.delete_btn);

        deleteUPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ServerClient serverClient = new ServerClient(ReaderActivity.this);
                serverClient.deleteUPC();
                cleanInterface();
            }
        });

        //po nacisnieciu przycisku scan_btn wykona się Intencja - skanowanie
        scan_UPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Skanuj");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(true);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Reader Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            EditText editText_UPC = (EditText)findViewById(R.id.editText_UPC);

            if(result != null)
            {
                if(result.getContents() == null)
                {
                    Toast.makeText(this, "Skan przerwany", Toast.LENGTH_LONG).show();
                }
                else
                {
                   if(result.getContents().length()<3){
                       EditText editText_Ilosc = (EditText)findViewById(R.id.editText_Ilosc);
                       editText_Ilosc.setText(result.getContents(), TextView.BufferType.EDITABLE);
                   }
                   else if(result.getContents().length()>3 && result.getContents().length()<14){
                        cleanInterface();
                        product.setProductNumber(result.getContents());
                        editText_UPC.setText(product.getProductNumber(), TextView.BufferType.EDITABLE);
                        new LoadDataTask1().execute();
                   }
                   else{
                       EditText editText_IMEI = (EditText)findViewById(R.id.editText_IMEI);
                       editText_IMEI.setText(result.getContents() + "," + editText_IMEI.getText(), TextView.BufferType.EDITABLE);
                   }

                }
            }

            else
            {
                super.onActivityResult(requestCode, resultCode, data);
            }

        }

    private class LoadDataTask1 extends AsyncTask<Void, Void, String> {

        private String TAG = "HTTP";
        StringBuilder sb;
        private String requestUrl;

        @Override
        protected void onPreExecute() {
            requestUrl = "http://gawydion.pl/api.php/BC_tab/" + product.getProductNumber();
            sb = new StringBuilder();
        }

        @Override
        protected String doInBackground(Void... params) {
            String data = "";

            try {
                URL url = new URL(requestUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                data = readDataFromStream(in);
                urlConnection.disconnect();
            } catch (UnknownHostException exception) {
                Log.e("network", "Unknown host: " + requestUrl);
                exception.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            return data;
        }

        String temp;
        @Override
        protected void onPostExecute(String s) {

            try{
                JSONObject jsonData = new JSONObject(s);
                product.setProductName((String) jsonData.get("Name"));
                }

            catch(JSONException e){
                Log.e(TAG, e.toString());
            }

            EditText editText_Nazwa = (EditText)findViewById(R.id.editText_Nazwa);
            editText_Nazwa.setText(product.getProductName(), TextView.BufferType.EDITABLE);

            if(product.getProductName()==""){
                editText_Nazwa.setText("Szukam...", TextView.BufferType.EDITABLE);

                new LoadDataTask2().execute();
            }
        }

        private void writeDataToStream(OutputStream out, String s) {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            try {
                bw.write(s);
                bw.close();
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }

        private String readDataFromStream(InputStream is) {
            String line;
            BufferedReader reader = null;
            StringBuilder data = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(is));
                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
            return data.toString();
        }
    }

    private class LoadDataTask2 extends AsyncTask<Void, Void, String> {

        private String TAG = "HTTP";

        @Override
        protected String doInBackground(Void... params) {
            String data = "";

            ////OPCJA z YAHOO

            try {
                Document doc;

                doc = Jsoup.connect("https://search.yahoo.com/search;_ylc=X3oDMTFiN25laTRvBF9TAzIwMjM1MzgwNzUEaXRjAzEEc2VjA3NyY2hfcWEEc2xrA3NyY2h3ZWI-?p=" + product.getProductNumber() + "&fr=yfp-t&fp=1&toggle=1&cop=mss&ei=UTF-8").userAgent("Chrome").ignoreHttpErrors(true).timeout(0).get();

                Elements links = doc.select("li[class=g]");
                data = doc.text();
                for (Element link : links) {
                    Elements titles = link.select("h3[class=r]");
                    data = titles.text();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        String temp;
        @Override
        protected void onPostExecute(String s) {
            EditText editText_Nazwa = (EditText)findViewById(R.id.editText_Nazwa);
            editText_Nazwa.setText(s.substring(311,370), TextView.BufferType.EDITABLE);
        }




        private void writeDataToStream(OutputStream out, String s) {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            try {
                bw.write(s);
                bw.close();
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }

        private String readDataFromStream(InputStream is) {
            String line;
            BufferedReader reader = null;
            StringBuilder data = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(is));
                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
            return data.toString();
        }
    }

    public void writeExternalStorage(View view)
    {
        EditText editText_UPC;
        EditText editText_Nazwa;
        EditText editText_IMEI;
        EditText editText_Ilosc;

        editText_UPC = (EditText) findViewById(R.id.editText_UPC);
        editText_Nazwa = (EditText) findViewById(R.id.editText_Nazwa);
        editText_IMEI = (EditText) findViewById(R.id.editText_IMEI);
        editText_Ilosc = (EditText) findViewById(R.id.editText_Ilosc);


        DialogFragment dialog = new SaveAlertBox();
        dialog.show(getFragmentManager() , "MyDialogFragmentTag");

        //Toast.makeText(this, "Nananan", Toast.LENGTH_LONG).show();
/*
        ServerClient serverClient = new ServerClient(this);

        //tymczasowo!!
        serverClient.saveUPC();

        if(editText_IMEI!=null & editText_Ilosc!= null) {
            serverClient.addProduct();
        }
        */
    }

    public class CaptureActivityPortrait extends CaptureActivity {
    //Nothing in side.
    }

    void cleanInterface(){
        //wyczyści teksty przy nowym skanowaniu
        EditText editText_UPC = (EditText)findViewById(R.id.editText_UPC);
        EditText editText_Nazwa = (EditText)findViewById(R.id.editText_Nazwa);
        EditText editText_IMEI = (EditText)findViewById(R.id.editText_IMEI);
        EditText editText_Ilosc = (EditText)findViewById(R.id.editText_Ilosc);

        editText_UPC.setText("", TextView.BufferType.EDITABLE);
        editText_Nazwa.setText("", TextView.BufferType.EDITABLE);
        editText_IMEI.setText("", TextView.BufferType.EDITABLE);
        editText_Ilosc.setText("", TextView.BufferType.EDITABLE);

        //wyczysci obiekt przy nowym skanowaniu
        product.setProductNumber("");
        product.setProductName("");
        product.setProductDescription("");
        product.setPrice(0);
    }
}





