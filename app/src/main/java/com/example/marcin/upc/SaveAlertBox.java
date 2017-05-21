package com.example.marcin.upc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marcin on 21.03.2017.
 */

public class SaveAlertBox extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        EditText editText_UPC = (EditText) getActivity().findViewById(R.id.editText_UPC);
        EditText editText_Nazwa = (EditText) getActivity().findViewById(R.id.editText_Nazwa);
        EditText editText_IMEI = (EditText) getActivity().findViewById(R.id.editText_IMEI);
        EditText editText_Ilosc = (EditText) getActivity().findViewById(R.id.editText_Ilosc);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Dane do zapisu");
        builder.setMessage(
                "" + editText_Nazwa.getText() + "\n" +
                "Ilość: " + editText_Ilosc.getText() + "\n" +
                "S/N: " + editText_UPC.getText() + "\n" +
                "IMEI: " + editText_IMEI.getText());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
///*

                EditText editText_UPC = (EditText) getActivity().findViewById(R.id.editText_UPC);
                EditText editText_Nazwa = (EditText) getActivity().findViewById(R.id.editText_Nazwa);
                EditText editText_IMEI = (EditText) getActivity().findViewById(R.id.editText_IMEI);
                EditText editText_Ilosc = (EditText) getActivity().findViewById(R.id.editText_Ilosc);


                ServerClient serverClient = new ServerClient(getActivity());
                serverClient.saveUPC();

                //Integer.parseInt(myEditText.getText().toString())

                StockProductPOJO product = new StockProductPOJO();


                if(!editText_IMEI.getText().toString().equals("") & !editText_Ilosc.getText().toString().equals("")) {

                    product.setUPC(editText_UPC.getText().toString());
                    product.setName(editText_Nazwa.getText().toString());
                    product.setPrice(232.96);

                    for(int i=0; i<Integer.parseInt(editText_Ilosc.getText().toString()); i++){
                        product.setIMEI(editText_IMEI.getText().toString().split(",")[i]);
                        serverClient.addProduct(product);
                    }

                }

                editText_UPC.setText("", TextView.BufferType.EDITABLE);
                editText_Nazwa.setText("", TextView.BufferType.EDITABLE);
                editText_IMEI.setText("", TextView.BufferType.EDITABLE);
                editText_Ilosc.setText("", TextView.BufferType.EDITABLE);

                Toast.makeText(getActivity().getApplicationContext(), "Zapisano", Toast.LENGTH_LONG).show();

                /*
                String externalStorageState = Environment.getExternalStorageState();

                if(Environment.MEDIA_MOUNTED.equals(externalStorageState)) {
                    File Root = Environment.getExternalStorageDirectory();
                    File Dir = new File(Root.getAbsolutePath() + "/UPC_data");

                    if (!Dir.exists()) {
                        Dir.mkdir();
                    }

                    File file = new File(Dir, "UPC_database.txt");
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file, true); //true dodaje opcję append -> dopisywanie do pliku a nie tworzenie nowego

                        try {
                            fileOutputStream.write((editText_UPC.getText() + ","+ editText_Nazwa.getText() + "," + editText_Ilosc.getText()+ "," + editText_IMEI.getText() + "\n").getBytes());
                            fileOutputStream.close();

                            /* //ZAPIS DO BAZY DANYCH KODÓW KRESKOWYCH
                            Gson gson = new GsonBuilder().setLenient().create();

                             // Retrofit - obsługuje zapytania HTTP
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(ServerApi.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create(gson))
                                    .build();

                            ServerApi serverApi =
                                    retrofit.create(ServerApi.class);

                            ProductPOJO product = new ProductPOJO();
                            product.setLP("");
                            product.setName(editText_Nazwa.getText().toString());
                            product.setUPC(editText_UPC.getText().toString());

                            Call<ProductPOJO> call = serverApi.createProduct(product);
                            call.enqueue(new Callback<ProductPOJO>() {
                                @Override
                                public void onResponse(Call<ProductPOJO> call, Response<ProductPOJO> response) {

                                }

                                @Override
                                public void onFailure(Call<ProductPOJO> call, Throwable t) {

                                }});
                             //ZAPIS DO BAZY DANYCH KODÓW KRESKOWYCH



                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Brak karty SD", Toast.LENGTH_LONG).show();
                }
*/
            }
        });
        builder.setNegativeButton("Cofnij", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity().getApplicationContext(), "Popraw dane...", Toast.LENGTH_SHORT).show();
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
