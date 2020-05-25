package com.example.androidauthmongodbnodejs;

public class ClaseServicio {
    private String reserva;
    private String pasajero;
    private String telefono;
    private String direccion;
    private String hora;
    private String pago;
    private String observaciones;

    public ClaseServicio(String reserva, String pasajero, String telefono, String direccion, String hora, String pago, String observaciones) {
        this.reserva = reserva;
        this.pasajero = pasajero;
        this.telefono = telefono;
        this.direccion = direccion;
        this.hora = hora;
        this.pago = pago;
        this.observaciones = observaciones;
    }

    public String getReserva() {
        return reserva;
    }

    public void setReserva(String reserva) {
        this.reserva = reserva;
    }

    public String getPasajero() {
        return pasajero;
    }

    public void setPasajero(String pasajero) {
        this.pasajero = pasajero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
