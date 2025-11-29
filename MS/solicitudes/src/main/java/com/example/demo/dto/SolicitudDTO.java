package com.example.demo.dto;

import java.time.LocalDateTime;

public class SolicitudDTO {
    private Integer idCliente;
    private Integer idEstatus;
    private LocalDateTime fecha;

    // Getters y Setters
    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public Integer getIdEstatus() { return idEstatus; }
    public void setIdEstatus(Integer idEstatus) { this.idEstatus = idEstatus; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
