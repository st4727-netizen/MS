package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Integer idSolicitud;

    @JsonProperty("id_cliente")
    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @JsonProperty("id_estatus")
    @Column(name = "id_estatus")
    private Integer idEstatus;

    private LocalDateTime fecha;

    @Column(nullable = false)
    private Boolean estado = true;

    // Getters y setters
    public Integer getIdSolicitud() {
        return idSolicitud;
    }
    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }
    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getEstado() {
        return estado;
    }
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}

