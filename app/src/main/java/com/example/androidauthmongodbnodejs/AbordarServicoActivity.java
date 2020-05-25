package com.example.androidauthmongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ncorti.slidetoact.SlideToActView;

public class AbordarServicoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abordar_servico_layout);
        SlideToActView sta = (SlideToActView) findViewById(R.id.btn_abordo);
        sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView view) {
                Intent intent = new Intent(AbordarServicoActivity.this, TarifarServicioActivity.class);
                AbordarServicoActivity.this.startActivity(intent);
            }
        });
    }
}
