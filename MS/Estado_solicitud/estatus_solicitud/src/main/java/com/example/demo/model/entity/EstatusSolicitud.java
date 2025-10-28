package com.example.demo.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estatus_solicitud")
public class EstatusSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_estatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_estatus", nullable = false)
    private NombreEstatus nombre_estatus;

    @Column(nullable = false)
    private Boolean estado = true;
    
    public enum NombreEstatus {
        EN_PROCESO,
        VALIDADA,
        RECHAZADA
    }

    // Getters y Setters
    public Integer getId_estatus() {
        return id_estatus;
    }

    public void setId_estatus(Integer id_estatus) {
        this.id_estatus = id_estatus;
    }

    public NombreEstatus getNombre_estatus() {
        return nombre_estatus;
    }

    public void setNombre_estatus(NombreEstatus nombre_estatus) {
        this.nombre_estatus = nombre_estatus;
    }
    
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}


