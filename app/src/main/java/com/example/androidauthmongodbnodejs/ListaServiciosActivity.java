package com.example.androidauthmongodbnodejs;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.androidauthmongodbnodejs.Retrofit.IMyService;
import com.example.androidauthmongodbnodejs.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ListaServiciosActivity extends AppCompatActivity {//  implements ListaServiciosAdapter.customButtonListener {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    String codigo;
    String nombre;
    ListView simpleListView;
    private ListaServiciosAdapter mAdapter;
    String valor;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_servicios_layout);
        simpleListView = findViewById(R.id.listView);
        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();//crea un nuevo RetrofitClient y obtiene los datos de la instancia
        iMyService = retrofitClient.create(IMyService.class);//crea un servicio a partir de iMyService

        Intent intent = getIntent();
        nombre = intent.getStringExtra("name");
        codigo = intent.getStringExtra("codigo");
        TextView tvName = (TextView)findViewById(R.id.txt_name);
        tvName.setText(codigo+"-"+nombre);
        compositeDisposable.add(iMyService.listServices(codigo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        JSONArray jsonObj = new JSONArray (response);
                        ArrayList<ClaseServicio> detalleServicio = new ArrayList<>();

                        for(int i=0; i < jsonObj.length() ; i++) {
                            HashMap<String,String> data = new HashMap<String,String>();
                            JSONObject item = new JSONObject(jsonObj.getString(i));
                            detalleServicio.add(new ClaseServicio(
                                    item.getString("servicio"),
                                    item.getString("pasajero"),
                                    item.getString("telefono"),
                                    item.getString("direccion"),
                                    item.getString("hora"),
                                    item.getString("pago"),
                                    item.getString("observaciones")
                            ));
                            valor = item.getString("servicio")+item.getString("pasajero")+item.getString("telefono")+item.getString("direccion")+item.getString("hora")+item.getString("pago")+item.getString("observaciones");
                        }
                        mAdapter = new ListaServiciosAdapter(ListaServiciosActivity.this, detalleServicio);
                        simpleListView.setAdapter(mAdapter);
                    }
                })
        );
    }
    /*
    @Override
    public void onButtonClickListner(int position, String value) {
        Toast.makeText(ListaServiciosActivity.this, "Button click " + value,
                Toast.LENGTH_SHORT).show();

    }*/

}
