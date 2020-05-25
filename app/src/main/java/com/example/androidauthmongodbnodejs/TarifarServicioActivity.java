package com.example.androidauthmongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ncorti.slidetoact.SlideToActView;

public class TarifarServicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarifar_servicio_layout);
        SlideToActView sta = (SlideToActView) findViewById(R.id.btn_tarifar);
        sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView view) {
                /*
                Intent intent = new Intent(TarifarServicioActivity.this, PosicionarServicioActivity.class);
                TarifarServicioActivity.this.startActivity(intent);
                */
            }
        });
    }
}
