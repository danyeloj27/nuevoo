package com.example.androidauthmongodbnodejs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class ListaServiciosAdapter  extends ArrayAdapter<ClaseServicio>{

    customButtonListener customListner;

    public interface customButtonListener {
        public void onButtonClickListner(int position, String value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private Context mContext;
    private List<ClaseServicio> listaServicios = new ArrayList<>();
    String nroreserva;

    public ListaServiciosAdapter(Context context, ArrayList<ClaseServicio> list){
        super(context, 0, list);
        this.mContext = context;
        this.listaServicios = list;
    }

    @Override
    public int getCount() {
        return listaServicios.size();
    }

    @Override
    public ClaseServicio getItem(int position) {
        return listaServicios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.detail_services_layout,parent,false);

        ClaseServicio detalleServicio = listaServicios.get(position);

        TextView reserva = listItem.findViewById(R.id.txt_servicio);
        TextView pasajero = listItem.findViewById(R.id.txt_pasajero);
        TextView telefono = listItem.findViewById(R.id.txt_telefono);
        TextView direccion = listItem.findViewById(R.id.txt_direccion);
        TextView hora = listItem.findViewById(R.id.txt_hora);
        TextView pago = listItem.findViewById(R.id.txt_pago);
        TextView observaciones = listItem.findViewById(R.id.txt_observaciones);
        Button btn_aceptarservicio = listItem.findViewById(R.id.btn_aceptarservicio);

        nroreserva = detalleServicio.getReserva();

        reserva.setText(detalleServicio.getReserva());
        pasajero.setText(detalleServicio.getPasajero());
        telefono.setText(detalleServicio.getTelefono());
        direccion.setText(detalleServicio.getDireccion());
        hora.setText(detalleServicio.getHora());
        pago.setText(detalleServicio.getPago());
        observaciones.setText(detalleServicio.getObservaciones());
        final String temp = detalleServicio.getReserva();

        btn_aceptarservicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (customListner != null) {
                    customListner.onButtonClickListner(position,temp);
                }
                Toast.makeText(v.getContext(), "Reserva Seleccionada: " + temp, Toast.LENGTH_SHORT).show();*/
                Intent inciarServicio = new Intent(mContext, IniciarServicioActivity.class);
                inciarServicio.putExtra("reserva", temp);
                mContext.startActivity(inciarServicio);
            }
        });

        return listItem;
    }

}
