package com.example.androidauthmongodbnodejs;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_exit;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    String codigo;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_layout);
        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();//crea un nuevo RetrofitClient y obtiene los datos de la instancia
        iMyService = retrofitClient.create(IMyService.class);//crea un servicio a partir de iMyService

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        codigo = intent.getStringExtra("codigo");
        TextView tvName = (TextView)findViewById(R.id.txt_name);
        tvName.setText(codigo+"-"+name);

        View v = findViewById(R.id.btn_exit);
        v.setOnClickListener(this);
    }

    public void onClick(View arg0){
        if (arg0.getId() == R.id.btn_exit){
            listServices(codigo);
        }

    }

    private void listServices(String codigo) {
        compositeDisposable.add(iMyService.listServices(codigo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        /*final ListView listview = (ListView) findViewById(R.id.listView);

                        JSONArray jsonObj = new JSONArray (response);
                        String string1 = jsonObj.getString(0);
                        String tamano = String.valueOf(jsonObj.length());
                        final ArrayList<String> list = new ArrayList<String>();
                        ArrayList<String> items = new ArrayList<String>();
                        for(int i=0; i < jsonObj.length() ; i++) {
                            JSONObject item = new JSONObject(jsonObj.getString(i));
                            String name = "PASAJERO: "+item.getString("pasajero")+" DIRECCIÓN: "+item.getString("direccion");
                            list.add(name);
                        }*/
                        JSONArray jsonObj = new JSONArray (response);
                        ArrayList<HashMap<String,String>> listdata = new ArrayList<HashMap<String,String>>();
                        for(int i=0; i < jsonObj.length() ; i++) {
                            HashMap<String,String> data = new HashMap<String,String>();
                            JSONObject item = new JSONObject(jsonObj.getString(i));
                            data.put("pasajero", item.getString("pasajero"));
                            data.put("direccion", item.getString("direccion"));
                            listdata.add(data);
                        }

                        String [] from = {
                                "pasajero", "direccion"
                        };

                        int [] to = {
                                R.id.txt_pasajero,R.id.txt_direccion
                        };

                        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), listdata, R.layout.detail_services_layout,from,to);
                        ListView simpleListView = findViewById(R.id.listView);
                        simpleListView.setAdapter(simpleAdapter);

                        /*final ArrayAdapter adapter = new ArrayAdapter(ServiceActivity.this,
                                R.layout.list_services, list);
                        listview.setAdapter(adapter);*/

                    }
                })
        );
    }




}
