package com.example.androidauthmongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ncorti.slidetoact.SlideToActView;

public class PosicionarServicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posicionar_servicio_layout);
        SlideToActView sta = (SlideToActView) findViewById(R.id.btn_enposicion);
        sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView view) {
                Intent intent = new Intent(PosicionarServicioActivity.this, AbordarServicoActivity.class);
                PosicionarServicioActivity.this.startActivity(intent);
            }
        });
    }
}
