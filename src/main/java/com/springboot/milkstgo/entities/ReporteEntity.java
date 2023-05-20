package com.springboot.milkstgo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "reportes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "quincena")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date quincena;

    @Column(name = "codigo_proveedor")
    private String codigo_proveedor;

    @Column(name = "nombre_proveedor")
    private String nombre_proveedor;

    @Column(name = "kls_leche")
    private int kls_leche;

    @Column(name = "dias_envio_leche")
    private int diasEnvioLeche;

    @Column(name = "avg_Kls_leche")
    private int avgKls_leche;

    @Column(name = "variacion_leche")
    private int variacion_leche;

    @Column(name = "grasa")
    private int grasa;

    @Column(name = "variacion_grasa")
    private int variacion_grasa;

    @Column(name = "solidos_totales")
    private int solidos_totales;

    @Column(name = "variacion_st")
    private int variacion_st;

    @Column(name = "pago_leche")
    private int pago_leche;

    @Column(name = "pago_grasa")
    private int pago_grasa;

    @Column(name = "pago_st")
    private int pago_st;

    @Column(name = "bonificacion_frecuencia")
    private int bonificacion_frecuencia;

    @Column(name = "dct_leche")
    private int dct_leche;

    @Column(name = "dct_grasa")
    private int dct_grasa;

    @Column(name = "dct_st")
    private int dct_st;

    @Column(name = "pago_total")
    private int pago_total;

    @Column(name = "monto_retencion")
    private int monto_retencion;

    @Column(name = "monto_final")
    private int monto_final;
}
