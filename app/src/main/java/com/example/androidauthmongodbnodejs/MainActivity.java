package com.example.androidauthmongodbnodejs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.androidauthmongodbnodejs.Retrofit.IMyService;
import com.example.androidauthmongodbnodejs.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView txt_create_account;
    MaterialEditText edt_login_email, edt_login_password;
    Button btn_login;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    /*Un disposable es una interface que permite hacer seguimiento de los observers y subscribers que se se
    usan en el main activity a fin que los mismos se puedan limpiar, esto maneja los leaks de memoria y evita crashes en la app*/
    IMyService iMyService;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();//crea un nuevo RetrofitClient y obtiene los datos de la instancia
        iMyService = retrofitClient.create(IMyService.class);//crea un servicio a partir de iMyService

        /*Retrofit is a REST Client for Java and Android. It makes it relatively easy to retrieve and upload JSON (or other
        structured data) via a REST based webservice.*/

        //Init View
        edt_login_email = findViewById(R.id.edt_email);
        /*Busca la referencia de un elemento con id edt_email dentro del layout contenedor (activity_main.xml. ) y la almacena
        en la variable edt_login_email*/
        edt_login_password = findViewById(R.id.edt_password);
        /*Busca la referencia de un elemento con id edt_password dentro del layout contenedor (activity_main.xml. ) y la almacena
        en la variable edt_login_password*/
        btn_login = findViewById(R.id.btn_login);
        /*Busca la referencia de un elemento con id btn_login dentro del layout contenedor (activity_main.xml. ) y la almacena
        en la variable btn_login*/

        //Evento OnClick del botón

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lama a la función loginUser y le pasa como parámetros las variables edt_login_email y edt_login_password (VER FUNCIÓN)
                loginUser(edt_login_email.getText().toString(),
                        edt_login_password.getText().toString());
            }
        });

        txt_create_account = findViewById(R.id.txt_create_account);
        //Evento OnClick de la etiqueta
        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crea una instancia en register_layout de R.layout.register_layout
                final View register_layout = LayoutInflater.from(MainActivity.this)//the final keyword is used to denote constants. It can be used with variables, methods, and classes.
                        .inflate(R.layout.register_layout, null);

                //Crea un nuevo cuadro de dialogo
                new MaterialStyledDialog.Builder(MainActivity.this)
                        .setTitle("REGISTRATION")
                        .setDescription("Fill all fields")
                        .setCustomView(register_layout)//asigna como vista a register_layout
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("REGISTER")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                MaterialEditText edt_login_email = register_layout.findViewById(R.id.edt_email);
                                MaterialEditText edt_login_name = register_layout.findViewById(R.id.edt_name);
                                MaterialEditText edt_login_password = register_layout.findViewById(R.id.edt_password);

                                if(TextUtils.isEmpty(edt_login_email.getText())){
                                    Toast.makeText(MainActivity.this, "Email can't be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if(TextUtils.isEmpty(edt_login_name.getText())){
                                    Toast.makeText(MainActivity.this, "Name can't be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if(TextUtils.isEmpty(edt_login_password.getText())){
                                    Toast.makeText(MainActivity.this, "Pasword can't be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                registerUser(edt_login_email.getText().toString(),
                                        edt_login_name.getText().toString(),
                                        edt_login_password.getText().toString());
                            }
                        }).show();
            }
        });
    }

    private void registerUser(String email, String name, String password) {
        /*Se agrega un observable que viene dado de la función registerUser de la interface iMyService que recibe los parametros:
        email,name,password*/
        compositeDisposable.add(iMyService.registerUser(email,name,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        //el response es lo que emite el observable, en este caso el response del endpoint configurado en el servicio
                    }
                })
        );
        /*NOTAS:
        ARTÍCULO PARA LEER: https://code.tutsplus.com/es/tutorials/concurrency-in-rxjava-2--cms-29288
        1. Los Schedulers en RxJava son usados para ejecutar una unidad de trabajo sobre un hilo.
        2. Schedulers.io(): crea y devuelve un Scheduler diseñado para trabajo límite-I/O tal como desempeñar llamadas asíncronas de
        red o leer y escribir a la base de datos. Estas tareas no son intensivas con el CPU
        3. Usando el operador de concurrencia subscribeOn(), especificas que el Scheduler debería realizar la operación en el flujo
        Observable. Este entonces empujará los valores a los Observers usando el mismo hilo.
        4. El trabajo del operador de concurrencia observeOn(), por el otro lado, es cambiar las emisiones subsecuentes a otro hilo
        o Scheduler. Usamos este operador para controlar sobre qué hilo recibirán los consumidores las emisiones. A diferencia del
        operador subscribeOn(), el operador observerOn() puede ser aplicado múltiples veces en la cadena de operador, de ahí
        cambiando el Scheduler más de una vez.*/
    }

    private void loginUser(String email, String password) {
        //Evalua que el componente no esté vacío, de ser asi muestra un mensaje
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email can't be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //Evalua que el componente no esté vacío, de ser asi muestra un mensaje
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password can't be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }

        compositeDisposable.add(iMyService.loginUser(email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String response) throws Exception {
                    JSONObject jsonObj = new JSONObject(response);
                    String string1 = jsonObj.getString("value");
                    String nombre = jsonObj.getString("name");
                    String codigo = jsonObj.getString("codigo");
                    String string2 = string1.replaceAll("\"", "");
                    boolean a = Boolean.parseBoolean(string2);
                    if (a){
                        openMain(nombre, codigo);
                    } else {
                        Toast.makeText(MainActivity.this, nombre+": "+string2, Toast.LENGTH_LONG).show();
                    }
                }
            })
        );
    }

    private void openMain(String name, String codigo){
        Intent intent = new Intent(this, MenuActivity.class);
        this.startActivity(intent);
    }

    private void openList(String name, String codigo){
        Intent intent = new Intent(this, ServiceActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("codigo", codigo);
        this.startActivity(intent);
    }
}
