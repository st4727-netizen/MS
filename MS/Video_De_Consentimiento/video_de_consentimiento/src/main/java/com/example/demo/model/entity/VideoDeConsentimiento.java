package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "video_de_consentimiento")
public class VideoDeConsentimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_video")
    private Integer idVideo;

    @Column(name = "id_solicitud", nullable = false)
    private Integer idSolicitud;

    @Column(name = "fecha")
    private Timestamp fecha;

    @Column(name = "url_video", length = 250)
    private String urlVideo;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;
    
    // Getters y setters

    public Integer getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Integer idVideo) {
        this.idVideo = idVideo;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }
    
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}

