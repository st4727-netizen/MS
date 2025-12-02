package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "correccion_de_datos")
public class CorreccionDeDatos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_correccion")
    private Integer idCorreccion;

    @Column(name = "id_solicitud", nullable = false)
    @JsonProperty("id_solicitud")
    private Integer idSolicitud;

    @Column(name = "campo_modificado", nullable = false)
    @JsonProperty("campo_modificado")
    private String campoModificado;

    @Column(name = "valor_anterior")
    @JsonProperty("valor_anterior")
    private String valorAnterior;

    @Column(name = "valor_nuevo")
    @JsonProperty("valor_nuevo")
    private String valorNuevo;

    @Column(name = "fecha_correccion")
    private LocalDateTime fechaCorreccion;

    @Column(nullable = false)
    private Boolean estado = true;

    @PrePersist
    protected void onCreate() {
        this.fechaCorreccion = LocalDateTime.now();
        this.estado = true;
    }

    // Getters & Setters
    public Integer getIdCorreccion() {
        return idCorreccion;
    }

    public void setIdCorreccion(Integer idCorreccion) {
        this.idCorreccion = idCorreccion;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getCampoModificado() {
        return campoModificado;
    }

    public void setCampoModificado(String campoModificado) {
        this.campoModificado = campoModificado;
    }

    public String getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public String getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public LocalDateTime getFechaCorreccion() {
        return fechaCorreccion;
    }

    public void setFechaCorreccion(LocalDateTime fechaCorreccion) {
        this.fechaCorreccion = fechaCorreccion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
