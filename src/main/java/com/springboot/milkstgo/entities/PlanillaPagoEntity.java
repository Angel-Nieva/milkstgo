package com.springboot.milkstgo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "planilla_Pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaPagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "quincena", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date quincena;

    @Column(name = "codigo_proveedor", nullable = false)
    private String codigo_proveedor;

    @Column(name = "nombre_proveedor", nullable = false)
    private String nombre_proveedor;

    @Column(name = "kls_leche", nullable = false)
    private int kls_leche;

    @Column(name = "dias_envio_leche", nullable = false)
    private int diasEnvioLeche;

    @Column(name = "avg_Kls_leche", nullable = false)
    private int avgKls_leche;

    @Column(name = "variacion_leche", nullable = false)
    private int variacion_leche;

    @Column(name = "grasa", nullable = false)
    private int grasa;

    @Column(name = "variacion_grasa", nullable = false)
    private int variacion_grasa;

    @Column(name = "solidos_totales", nullable = false)
    private int solidos_totales;

    @Column(name = "variacion_st", nullable = false)
    private int variacion_st;

    @Column(name = "pago_leche", nullable = false)
    private int pago_leche;

    @Column(name = "pago_grasa", nullable = false)
    private int pago_grasa;

    @Column(name = "pago_st", nullable = false)
    private int pago_st;

    @Column(name = "bonificacion_frecuencia", nullable = false)
    private int bonificacion_frecuencia;

    @Column(name = "dct_leche", nullable = false)
    private int dct_leche;

    @Column(name = "dct_grasa", nullable = false)
    private int dct_grasa;

    @Column(name = "dct_st", nullable = false)
    private int dct_st;

    @Column(name = "pago_total", nullable = false)
    private int pago_total;

    @Column(name = "monto_retencion", nullable = false)
    private int monto_retencion;

    @Column(name = "monto_final", nullable = false)
    private int monto_final;
}
