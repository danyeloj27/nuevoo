package com.example.androidauthmongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ncorti.slidetoact.SlideToActView;

public class IniciarServicioActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_servicio_layout);
        Intent intent = getIntent();
        final String reserva = intent.getStringExtra("reserva");
        TextView txt_reserva = (TextView)findViewById(R.id.txt_reserva);
        txt_reserva.setText(reserva);
        SlideToActView sta = (SlideToActView) findViewById(R.id.example);
        sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView view) {
                Intent intent = new Intent(IniciarServicioActivity.this, PosicionarServicioActivity.class);
                IniciarServicioActivity.this.startActivity(intent);
            }
        });
    }
}
