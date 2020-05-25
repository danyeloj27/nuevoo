package com.example.androidauthmongodbnodejs.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
/*Retrofit es un cliente HTTP de tipo seguro para Android y Java. Retrofit hace sencillo conectar a un servicio web REST
traduciendo la API a interfaces Java. */
public interface IMyService { //Un interfaz es una lista de acciones que puede llevar a cabo un determinado objeto.
    @POST("register")//indica que queremos ejecutar una petición POST cuando este método es llamado
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("password") String password);

    @POST("login")//indica que queremos ejecutar una petición POST cuando este método es llamado
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                    @Field("password") String password);

    @POST("services")//indica que queremos ejecutar una petición POST cuando este método es llamado
    @FormUrlEncoded
    Observable<String> listServices(@Field("codigo") String codigo);

    /*@FormUrlEncoded: Esto indicará que la petición tendrá su tipo MIME (un campo de encabezado que identifica el formato
    del cuerpo de una petición o respuesta HTTP) establecido a application/x-www-form-urlencoded y también que sus nombres
    de campo y valores serán codificados en UTF-8 antes de ser codificados en URI. La anotación @Field("key") con nombre de
    parámetro debería empatar el nombre que la API espera. Retrofit convierte implícitamente los valores a cadenas de texto
    usando String.valueOf(Object), y las cadenas son entonces codificadas en forma de URL. Los valores null son ignorados.*/
}
